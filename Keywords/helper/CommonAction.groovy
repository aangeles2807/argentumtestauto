package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.Date
import java.text.SimpleDateFormat
import javax.json.Json
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event
import javax.swing.JOptionPane

import org.stringtemplate.v4.compiler.STParser.ifstat_return

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class CommonAction {

	private String llave = null;
	private String valor = null;
	private Map<String, String> mapResponseBodyKeyAndValue = null;
	private ArrayList<Map<String, String>> mapResponseBody = null;
	private JsonParser jsonParser;
	private Event jasonParserEvent;
	private static final String projectPath = new File("").getAbsolutePath();
	private Date date;
	private SimpleDateFormat simpleDateFormat;
	private ReportGenerator reportGenerator;
	private ResponseObject responseObject;
	private String apiPath;
	private String message;

	public CommonAction(){

	}

	// Singleton Pattern
	//*************************************************************
	private static CommonAction commonActionVariable = null;

	public static CommonAction getUniqueIntance() {

		if(commonActionVariable == null) {

			commonActionVariable = new CommonAction();
		}

		return commonActionVariable;
	}
	//*************************************************************

	/**
	 * 
	 * Can get one of two objects types:
	 *  
	 * 1-) Get an ArrayList of Map<String, String>, where each Map is the set of Keys and Value of response body of the URL consulted from API.
	 * 2-) When the response content is not a JSon structure then return the text.
	 * 
	 * @param RequestObject - Object with all request data to consult the API.
	 * @return Object - can be an ArrayList<Map<String, String>> or String.
	 */
	public Object getResponseContentIntoMapOrString(RequestObject requestObject){
		
		return getResponseContentIntoMapOrString(requestObject, false);
	}
	
	/**
	 * 
	 * Can get one of two objects types:
	 *  
	 * 1-) Get an ArrayList of Map<String, String>, where each Map is the set of Keys and Value of response body of the URL consulted from API.
	 * 2-) When the response content is not a JSon structure then return the text.
	 * 
	 * @param requestObject - Object with all request data to consult the API.
	 * @param ifInverseCase - true is case is inverse, otherwise false. 
	 * @return Object - can be an ArrayList<Map<String, String>> or String.
	 */
	public Object getResponseContentIntoMapOrString(RequestObject requestObject, boolean ifInverseCase){
		
		reportGenerator = ReportGenerator.getUniqueIntance();

		// ****************
		// Get the API Path
		// ****************
		apiPath = "";

		for(int i=0; i < requestObject.getRestUrl().split("/").length; i++){

			if (i >= 3 && !requestObject.getRestUrl().split("/")[i].contains("?")) {

				apiPath += "/" + requestObject.getRestUrl().split("/")[i];
			}
			else if (requestObject.getRestUrl().split("/")[i].contains("?")) {

				apiPath += "/" + requestObject.getRestUrl().split("/")[i].substring(0, requestObject.getRestUrl().split("/")[i].indexOf("?"));
			}
		}
		
		// *************************
		// Generate template message
		// *************************
		
		message = String.valueOf("El servicio web: <b>${apiPath}</b> que fue consultado con el/los parametro(s): <br><br>");
		
		for (String key in requestObject.getVariables().keySet()) {
			
			if (!key.equals("GlobalVariable")) {
				
				message += String.valueOf("${key} : ${requestObject.getVariables().get(key)}<br>");
			}
		}
		
		// ***************************************
		// Consult API and get the response object
		// ***************************************
		
		responseObject = WS.sendRequestAndVerify(requestObject, FailureHandling.STOP_ON_FAILURE);
		
		// ********************
		// Verify response code
		// ********************
		
		if (responseObject.getStatusCode() == 200) {
			
			// *******************************************************
			// Verify if response body is in JSon format or plain text
			// *******************************************************
			
			try {
				
				Json.createParser(responseObject.getBodyContent().getInputStream()).next();
			}
			catch (Exception e) {
				
				if (e.getMessage().contains("Expected Tokens [CURLYOPEN, SQUAREOPEN]")) {
					
					message += String.valueOf("<br>Obtuvo la siguiente respuesta: ${responseObject.getResponseText().trim()}.<br>");
					
					message += String.valueOf("<br>En un lapso de tiempo de: <b>${responseObject.getElapsedTime()} ms</b>.<br><br>");
					
					message += String.valueOf("<b>Observación: Este tiempo es medido desde que se envía la solicitud hasta que se recibe el último byte de la respuesta.</b>");
					
					if (ifInverseCase) {
						
						reportGenerator.setLogStatusFAIL(message);
						
						throw new RuntimeException(message);
					}
					else{
						
						reportGenerator.setLogStatusINFO(message);
						
						return String.valueOf(responseObject.getResponseText().trim());
					}
				}
			}			
			
			// **********************************************************
			// Convert/Parse response content body into JSonParser Object
			// **********************************************************
			
			jsonParser = Json.createParser(responseObject.getBodyContent().getInputStream());

			if (mapResponseBodyKeyAndValue == null) {

				mapResponseBodyKeyAndValue = new HashMap<String, String>();
			}
			else{

				mapResponseBodyKeyAndValue.clear();
			}

			if (mapResponseBody == null) {

				mapResponseBody = new ArrayList<>();
			}
			else{

				mapResponseBody.clear();
			}

			while(jsonParser.hasNext()){

				jasonParserEvent = jsonParser.next();

				// Si el evento es el inicio de un llave
				if (jasonParserEvent == Event.KEY_NAME) {

					llave = jsonParser.getString().trim();

					// Muevo el lector del JSon a la siguiente posicion, que es el valor de la llave
					jasonParserEvent = jsonParser.next();

					// Si el valor del evento siguiente es un string
					if (jasonParserEvent == Event.VALUE_STRING) {

						valor = String.valueOf(jsonParser.getString()).trim();
						mapResponseBodyKeyAndValue.put(llave, valor);
					}

					// Si el valor del evento siguiente es un numero
					else if (jasonParserEvent == Event.VALUE_NUMBER) {

						valor = String.valueOf(jsonParser.getBigDecimal()).trim();
						mapResponseBodyKeyAndValue.put(llave, valor);
					}

					// Si el valor del evento siguiente es un boolean en estado false
					else if (jasonParserEvent == Event.VALUE_FALSE) {

						valor = String.valueOf(false).trim();
						mapResponseBodyKeyAndValue.put(llave, valor);
					}

					// Si el valor del evento siguiente es un boolean en estado true
					else if (jasonParserEvent == Event.VALUE_TRUE) {

						valor = String.valueOf(true).trim();
						mapResponseBodyKeyAndValue.put(llave, valor);
					}

					// Si el valor del evento siguiente es un nullo
					else if (jasonParserEvent == Event.VALUE_NULL) {

						valor = String.valueOf(null).trim();
						mapResponseBodyKeyAndValue.put(llave, valor);
					}

					// si el evento siguiente es una llave sin valor, pues inyecta solo el llave y el key es vacio
					else {

						mapResponseBodyKeyAndValue.put(llave, "");
					}

					// Luego del Event.KEY_NAME si es el evento siguiente es el inicio de un objeto
					if (jasonParserEvent == Event.START_OBJECT) {

						mapResponseBody.add(mapResponseBodyKeyAndValue);
						mapResponseBodyKeyAndValue = new HashMap<String, String>();
					}

					// Luego del Event.KEY_NAME si es el evento siguiente es el final de un inicio de un arreglo
					if (jasonParserEvent == Event.START_ARRAY) {

						mapResponseBody.add(mapResponseBodyKeyAndValue);
						mapResponseBodyKeyAndValue = new HashMap<String, String>();
					}

					// Luego del Event.KEY_NAME si es el evento siguiente es el final de un objeto
					if (jasonParserEvent == Event.END_OBJECT) {

						mapResponseBody.add(mapResponseBodyKeyAndValue);
						mapResponseBodyKeyAndValue = new HashMap<String, String>();
					}

					// Luego del Event.KEY_NAME si es el evento siguiente es el final de un final de un arreglo
					if (jasonParserEvent == Event.END_ARRAY) {

					}
				}

				// fueta del Event.KEY_NAME si el evento siguiente es el final de un objeto
				if (jasonParserEvent == Event.END_OBJECT) {

					mapResponseBody.add(mapResponseBodyKeyAndValue);
					mapResponseBodyKeyAndValue = new HashMap<String, String>();
				}

				// fueta del Event.KEY_NAME si el evento siguiente es el inicio de un array
				if (jasonParserEvent == Event.START_ARRAY) {

					if (!mapResponseBodyKeyAndValue.isEmpty()) {

						mapResponseBody.add(mapResponseBodyKeyAndValue);
						mapResponseBodyKeyAndValue = new HashMap<String, String>();
					}
				}

				// fueta del Event.KEY_NAME si el evento siguiente es el final de un array
				if (jasonParserEvent == Event.END_ARRAY) {
					//System.out.println("\nFinal de Arreglo\n");
				}
			}
			
			message += String.valueOf("<br>Obtuvo la siguiente respuesta:<br><br>");
			
			for(Map<String, String> contentBodyMap : mapResponseBody){
				
				for(String key : contentBodyMap.keySet()){
					
					message += key + " : " + contentBodyMap.get(key) + "<br>";
				}
			}
			
			message += String.valueOf("<br>En un lapso de tiempo de: <b>${responseObject.getElapsedTime()} ms</b>.<br><br>");
			
			message += String.valueOf("<b>Observación: Este tiempo es medido desde que se envía la solicitud hasta que se recibe el último byte de la respuesta.</b>");
			
			reportGenerator.setLogStatusINFO(message);
		}
		else{

			message += String.valueOf("<br>Lanzo el codigo de respuesta HTTP: <b>${responseObject.getStatusCode()}</b>.<br>");
			
			message += String.valueOf("<br>Mostrando el mensaje: <b>${responseObject.getResponseText()}</b>.");
			
			if (ifInverseCase) {
				
				reportGenerator.setLogStatusPASS(message);
			}
			else{
				
				throw new RuntimeException(message);
			}
		}

		return mapResponseBody;
	}

	/**
	 * Get the rest URL from request object.
	 * 
	 * @param requestObject
	 * @return the API's URL.
	 */
	public String getRequestURL(RequestObject requestObject){

		return requestObject.getRestUrl();
	}

	/**
	 * Get the path of the project in a String type.
	 * 
	 * @return String
	 */
	public static String getProjectpath() {

		return projectPath;
	}

	/**
	 * Get the actual time in the specific format.
	 * 
	 * @param timeFormat - time format
	 * @return String
	 */
	public String getActualTimeInSpecificFormat(String timeFormat){

		if (date == null) {

			date = new Date();
		}

		if (simpleDateFormat == null) {

			simpleDateFormat = new SimpleDateFormat();

			simpleDateFormat.applyPattern(timeFormat);
		}
		else{

			simpleDateFormat.applyPattern(timeFormat);
		}

		simpleDateFormat.format(date);
	}

	/**
	 * Get the actual date in the specific format.
	 *
	 * @param dateFormat - date format
	 * @return String
	 */
	public String getActualDateInSpecificFormat(String dateFormat){

		if (date == null) {

			date = new Date();
		}

		if (simpleDateFormat == null) {

			simpleDateFormat = new SimpleDateFormat();

			simpleDateFormat.applyPattern(dateFormat);
		}
		else{

			simpleDateFormat.applyPattern(dateFormat);
		}

		simpleDateFormat.format(date);
	}
	
	public String getApiPath() {
		
		return apiPath;
	}
}
