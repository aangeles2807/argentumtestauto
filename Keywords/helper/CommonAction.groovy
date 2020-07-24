package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import javax.json.Json
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event

import org.stringtemplate.v4.compiler.STParser.ifstat_return

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class CommonAction {

	// Attribute

	// To get response API content in a Mao
	private String llave = null;
	private String valor = null;
	private Map<String, String> mapResponseBodyKeyAndValue = null;
	private ArrayList<Map<String, String>> mapResponseBody = null;
	private URL url;
	private HttpURLConnection httpURLConnection;
	private JsonParser jsonParser;
	private Event jasonParserEvent;
	private int responseCode;

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
	 *  Get an ArrayList of Map<String, String>, where each Map is the
	 *  set of Keys and Value of response body of the URL consulted from API.
	 * 
	 * @param endPoint - The Rest API URL.
	 * @param httpRestApiMethod - The http rest method (GET, POST, PUT, DELETE, etc.).
	 * @return
	 */
	public ArrayList<Map<String, String>> getResponseContentIntoMap(String endPoint, String httpRestApiMethod){

		// Pass the desired URL as an object
		url = new URL(String.valueOf(endPoint));

		/*
		 * Type cast the URL object into a HttpURLConnection object.
		 * The benefit of doing this is that we will be able to harness
		 * the properties of the HttpURLConnection class to validate features.
		 *
		 * For example, set the request type or check the status of the response code:
		 * */
		httpURLConnection = (HttpURLConnection) url.openConnection();

		//  Set the request type, as in, whether the request to the API is a GET request or a POST request.
		httpURLConnection.setRequestMethod(httpRestApiMethod);

		// Open a connection stream to the corresponding API.
		httpURLConnection.connect();

		// Get the corresponding response code.
		responseCode = httpURLConnection.getResponseCode();

		/*
		 * Now we need to perform a check so that if the response code is not 200, we throw a runtime exception,
		 * or otherwise carry on the rest of the procedure.
		 *
		 * The structure would be like this:
		 * */
		if (responseCode == 200) {

			jsonParser = Json.createParser(httpURLConnection.getInputStream());

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
		}
		else{

			throw new RuntimeException("HttpResponseCode: " + responseCode);
		}

		return mapResponseBody;
	}

	/**
	 * Get the rest URL from request object.
	 * 
	 * @param requestObject
	 * @return the API's URL.
	 */
	public String getRequestObjectURL(RequestObject requestObject){

		return requestObject.getRestUrl();
	}
}
