package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.Date
import java.awt.GraphicsEnvironment
import java.awt.Robot
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.text.SimpleDateFormat

import javax.imageio.ImageIO
import javax.json.Json
import javax.json.stream.JsonParser
import javax.json.stream.JsonParser.Event
import javax.swing.JOptionPane

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.stringtemplate.v4.compiler.STParser.ifstat_return

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class CommonAction {

	private static final String projectPath = new File("").getAbsolutePath();
	private String llave;
	private String valor;
	private Map<String, String> mapResponseBodyKeyAndValue;
	private ArrayList<Map<String, String>> mapResponseBody;
	private JsonParser jsonParser;
	private Event jasonParserEvent;
	private Date date;
	private SimpleDateFormat simpleDateFormat;
	private ReportGenerator reportGenerator;
	private ResponseObject responseObject;
	private String apiPath;
	private String message;
	private Map<String, Object> mapStringObject;

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

	// ***
	// API
	// ***

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

		return getResponseContentIntoMapOrString(requestObject, false, null);
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
	public Object getResponseContentIntoMapOrString(RequestObject requestObject, boolean isInverseCase){

		return getResponseContentIntoMapOrString(requestObject, isInverseCase, null);
	}

	/**
	 * Can get one of two objects types:
	 * 
	 * 1-) Get an ArrayList of Map<String, String>, where each Map is the set of Keys and Value of response body of the URL consulted from API.
	 * 2-) When the response content is not a JSon structure then return the text.
	 * 
	 * @param requestObject - Object with all request data to consult the API.
	 * @param ifInverseCase - true is case is inverse, otherwise false.
	 * @param otherMessage - Other message that is not the common used. 
	 * @return Object - can be an ArrayList<Map<String, String>> or String.
	 */
	public Object getResponseContentIntoMapOrString(RequestObject requestObject, boolean isInverseCase, String addMoreInformationMessage){

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

		// *****************************************
		// Continuation of Generate template message
		// *****************************************

		message += String.valueOf("<br>Lanzo el codigo de respuesta HTTP: <b>${responseObject.getStatusCode()}</b>.<br>");

		message += String.valueOf("<br>En un lapso de tiempo de: <b>${responseObject.getElapsedTime()} ms</b>.<br>");

		// ********************
		// Verify response code
		// ********************

		if (responseObject.getStatusCode() == 200) {

			// *******************************************************
			// Verify if response body is in JSon format or plain text
			// *******************************************************

			if (apiPath.equals("/api/Autorizacion/Portal/ValidarCobertura") ||
			apiPath.equals("/api/Autorizacion/Portal/Ingresar") ||
			apiPath.equals("/api/Autorizacion/Portal/TarifaProcedimiento") ||
			apiPath.equals("/api/Autorizacion/Portal/Autorizar")) {

				// *****************************************
				// Continuation of Generate template message
				// *****************************************

				message += String.valueOf("<br>Obtuvo la respuesta: ${responseObject.getResponseText().trim()}.<br>");

				if (addMoreInformationMessage != null) {

					message += String.valueOf("<br>${addMoreInformationMessage}<br>");
				}

				if (isInverseCase) {

					throw new RuntimeException(message);
				}
				else{

					reportGenerator.setLogStatusINFO(message);

					return String.valueOf(responseObject.getResponseText().trim());
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

			// *****************************************
			// Continuation of Generate template message
			// *****************************************

			// If response body is empty
			if(mapResponseBody.isEmpty() && !apiPath.equals("/api/Autorizacion/Portal/CamposRequeridos")){

				message += String.valueOf("<br><b><font color=\"orange\">El Web Service no devolvio ningun resultado.</font></b><br>");
			}
			else{

				message += String.valueOf("<br>Obtuvo respuesta:<br><br>");

				for(Map<String, String> contentBodyMap : mapResponseBody){

					for(String key : contentBodyMap.keySet()){

						message += key + " : " + contentBodyMap.get(key) + "<br>";
					}
				}
			}

			if (addMoreInformationMessage != null) {

				message += String.valueOf("<br>${addMoreInformationMessage}<br>");
			}

			//message += String.valueOf("<b>Observación: Este tiempo es medido desde que se envía la solicitud hasta que se recibe el último byte de la respuesta.</b>");

			// If response body is empty
			if(mapResponseBody.isEmpty() && !apiPath.equals("/api/Autorizacion/Portal/CamposRequeridos")){

				reportGenerator.setLogStatusWARNING(message);
			}
			else{

				reportGenerator.setLogStatusINFO(message);
			}
		}
		else{

			// *****************************************
			// Continuation of Generate template message
			// *****************************************

			message += String.valueOf("<br>Mostrando el mensaje: <b>${responseObject.getResponseText()}</b>.");

			if (addMoreInformationMessage != null) {

				message += String.valueOf("<br>${addMoreInformationMessage}<br>");
			}

			if (isInverseCase) {

				reportGenerator.setLogStatusPASS(message);
			}
			else{

				throw new RuntimeException(message);
			}
		}

		return mapResponseBody;
	}

	// ***
	// GUI
	// ***

	private TestObject testObject;
	private WebElement webElement;
	private String style;
	private String scriptToExecute;
	private String screenshotFileNameAndPath;
	private int x;
	private int y;
	private int width;
	private int height;
	private Robot robot = null;
	private BufferedImage bufferedImage;
	private static final int TIMEOUT_1_SECOND = 1;
	private static final int TIMEOUT_3_SECONDS = 3;
	private static final int TIMEOUT_5_SECONDS = 5;
	private static final int TIMEOUT_10_SECONDS = 10;

	/**
	 * Crea un objeto TestObject, si el objeto no es nulo, entonces remplaza sus propiedades
	 * con las recibidas en los parametros selectorName and testObjectOrXpathString evitando
	 * crear una nueva instancoa del objeto de tipo TestObject.
	 * 
	 * @param selectorName - possible values are: xpath, css, id, name, title and class 
	 * @param testObjectOrXpathString - the types allowed are TestObject or String
	 */
	private void defineVariableTestObject(String selectorName, Object testObjectOrXpathString){

		if (testObjectOrXpathString instanceof String) {

			if (testObject == null) {

				testObject = new TestObject('TestObject')
				testObject.addProperty(selectorName, ConditionType.EQUALS, testObjectOrXpathString.toString(), true);

				return;
			}

			if (testObject != null) {

				if(selectorName != null && !selectorName.isEmpty()){

					if (!testObject.getProperties().get(0).getName().equals(selectorName)) {

						testObject.getProperties().get(0).setName(selectorName);
					}
				}

				if (!testObject.getProperties().get(0).getValue().equals(testObjectOrXpathString.toString())) {

					testObject.getProperties().get(0).setValue(testObjectOrXpathString.toString());
				}

				return;
			}
		}
		else if (testObjectOrXpathString instanceof TestObject) {

			testObject = testObjectOrXpathString;

			testObject.getProperties().get(0).setName(testObjectOrXpathString.getProperties().get(0).getName());
			testObject.getProperties().get(0).setValue(testObjectOrXpathString.getProperties().get(0).getValue());

			return;
		}
	}

	/**
	 * Verifica que un elemento web este presente en el DOM (Document Object Model) y visible al usuario en la aplicacion web.
	 *
	 * @param testObjectOrXpathString - web element que se verificara su existencia en el DOM y visibilidad en la aplicacion web.
	 * @param loopHints - Cantidad de repeticiones de intentos para encontrar el web elemnt.
	 * @param timeWaitingToVerifyAndVisualizedTestObject - tiempo (segundos) de espera para encontrar el web elemnt.
	 * @param testObjectHighlight - especificar si deseamos aplicar estillo css al web element.
	 * @param testObjectScroll - especificar si deseamos hacer scroll al web element.
	 * @return TestObject
	 */
	public TestObject verifyTestObjectExistence(String selectorName, Object testObjectOrXpathString, int loopHints, int timeWaitingToVerifyAndVisualizedTestObject, boolean testObjectScroll, boolean testObjectHighlight){

		defineVariableTestObject(selectorName, testObjectOrXpathString);

		for(int i=1; i <= loopHints; i++){

			if (WebUI.waitForElementPresent(testObject, timeWaitingToVerifyAndVisualizedTestObject, FailureHandling.OPTIONAL) &&
			WebUI.waitForElementVisible(testObject, timeWaitingToVerifyAndVisualizedTestObject, FailureHandling.OPTIONAL)) {

				if (testObjectScroll) {

					WebUI.scrollToElement(testObject, timeWaitingToVerifyAndVisualizedTestObject, FailureHandling.OPTIONAL)
				}

				if (testObjectHighlight) {

					highlightTestObject(testObject);
				}

				return testObject;
			}
		}

		return null;
	}

	/**
	 * Metodo que aplica el estilo css (border-color: red;) a los elementos web recibidos como
	 * argumento por parametro.
	 *
	 * @param testObjectOrXpathString - web element que se le aplicara el estilo.
	 */
	public Object highlightTestObject(String selectorName, Object testObjectOrXpathString){

		commonJavaScriptSentenceExecuter(selectorName, testObjectOrXpathString, helper.Keyword.JAVASCRIPT_HIGHLIGHT.value, null);
	}

	/**
	 * Metodo que aplica el estilo css (border-color: red;) a los elementos web recibidos como
	 * argumento por parametro, como tambien se puede aplicar un estilo personalizado en caso
	 * de desearlo.
	 *
	 * @param testObjectOrXpathString - web element que se le aplicara el estilo.
	 * @param otherStyle - estilo personalizado, poniendola null en caso de no querer estilo personalizado.
	 */
	public Object highlightTestObject(String selectorName, Object testObjectOrXpathString, String otherStyle){

		commonJavaScriptSentenceExecuter(selectorName, testObjectOrXpathString, helper.Keyword.JAVASCRIPT_HIGHLIGHT.value, otherStyle);
	}

	/**
	 * click en elemento web utilizando directamente la sentencia JavaScript. ejemplo: arguments[0].click();.
	 *
	 * @param testObjectOrXpathString  - web element que se le aplicara el click.
	 */
	public Object javaScriptClick(String selectorName, Object testObjectOrXpathString){

		commonJavaScriptSentenceExecuter(selectorName, testObjectOrXpathString, helper.Keyword.JAVASCRIPT_CLICK.value, null);
	}

	/**
	 * scroll a elemento web utilizando directamente la sentencia JavaScript. ejemplo: arguments[0].scrollIntoView();.
	 *
	 * @param testObjectOrXpathString - web element que se le aplicara el scroll.
	 */
	@Keyword
	public Object javaScriptScroll(String selectorName, Object testObjectOrXpathString){

		commonJavaScriptSentenceExecuter(selectorName, testObjectOrXpathString, helper.Keyword.JAVASCRIPT_SCROLL.value, null);
	}

	/**
	 *
	 * @param testObjectOrXpathString- web element que se le aplicara directamente logica JavaScript.
	 * @param javaScriptSentence - sentencia JavaScript a ser ejecutada.
	 */
	public Object javaScriptExecuter(String selectorName, Object testObjectOrXpathString, String javaScriptSentence){

		commonJavaScriptSentenceExecuter(selectorName, testObjectOrXpathString, javaScriptSentence, null);
	}

	/**
	 * Ofrece logica comun para ser utilizada por todos los metodos que desees ejecutar sentencias JavaScript.
	 *
	 * @param testObjectOrXpathString - elemento web que se aplicara direntamente la sentencia JavaScript
	 * @param accionToExecute - accion a ser ejecutada utilizando JavaScript (highlight, clicl y scroll).
	 * @param otherStyleInCaseOfHighlight - en caso de la accion a ejecutar sea highlight y se desee aplicar otro estilo al definido por defecto.
	 */
	private Object commonJavaScriptSentenceExecuter(String selectorName, Object testObjectOrXpathString, String accionToExecute, String otherStyleInCaseOfHighlight){

		defineVariableTestObject(selectorName, testObjectOrXpathString);

		if (verifyTestObjectExistence(selectorName, testObject, TIMEOUT_3_SECONDS, TIMEOUT_5_SECONDS, false, false) != null) {

			webElement = WebUiCommonHelper.findWebElement(testObject, TIMEOUT_3_SECONDS);

			// In case of highlighting
			if (accionToExecute.equals(helper.Keyword.JAVASCRIPT_HIGHLIGHT.value)) {

				if (WebUI.verifyElementHasAttribute(testObject, helper.Keyword.ATTRIBUTE_STYLE.value, TIMEOUT_5_SECONDS, FailureHandling.OPTIONAL)) {

					style = WebUI.getAttribute(testObject, helper.Keyword.ATTRIBUTE_STYLE.value);

					if (otherStyleInCaseOfHighlight != null && !otherStyleInCaseOfHighlight.isEmpty()) {

						style += otherStyleInCaseOfHighlight;

					}else{

						style += "border: solid 1px red !important;"

					}

				}else{

					if (otherStyleInCaseOfHighlight != null && !otherStyleInCaseOfHighlight.isEmpty()) {

						style = otherStyleInCaseOfHighlight;
					}

					else{

						style = "border: solid 1px red !important;"
					}
				}

				scriptToExecute = String.valueOf("arguments[0].setAttribute('${helper.Keyword.ATTRIBUTE_STYLE.value}', '${style}')");
			}

			// In case of clicking
			if (accionToExecute.equals(helper.Keyword.JAVASCRIPT_CLICK.value)) {

				scriptToExecute = "arguments[0].click();";
			}

			// In case of clicking
			if (accionToExecute.equals(helper.Keyword.JAVASCRIPT_SCROLL.value)) {

				scriptToExecute = "arguments[0].scrollIntoView();";
			}

			// In case of execute personal JavaScript sentence
			if (!accionToExecute.equals(helper.Keyword.JAVASCRIPT_HIGHLIGHT.value) &&
			!accionToExecute.equals(helper.Keyword.JAVASCRIPT_CLICK.value) &&
			!accionToExecute.equals(helper.Keyword.JAVASCRIPT_SCROLL.value)) {

				scriptToExecute = accionToExecute;
			}

			WebUI.executeJavaScript(scriptToExecute, Arrays.asList(webElement));
		}
	}

	/**
	 * Nos da la cantidad de elementos web con con la misma referencia especificada.
	 *
	 * @param testObjectOrXpathString - web element que contiene la referencia.
	 * @param hint - Cantidad de repeticiones de intentos para encontrar el web elemnt (con un segundo de espera en cada intento).
	 * @return Integer
	 */
	public int getQuantityOfWebElement(String selectorName, Object testObjectOrXpathString, int hint){

		defineVariableTestObject(selectorName, testObjectOrXpathString);

		for(int i=1; i <= hint; i++){

			if (testObject.getProperties().get(0).getName().equals(helper.Keyword.XPATH_SELECTOR.value)) {

				if (DriverFactory.getWebDriver().findElements(By.xpath(testObject.getProperties().get(0).getValue())).size() > 0) {

					return DriverFactory.getWebDriver().findElements(By.xpath(testObject.getProperties().get(0).getValue())).size();
				}
			}

			if (testObject.getProperties().get(0).getName().equals(helper.Keyword.CSS_SELECTOR.value)) {

				if (DriverFactory.getWebDriver().findElements(By.cssSelector(testObject.getProperties().get(0).getValue())).size() > 0) {

					return DriverFactory.getWebDriver().findElements(By.cssSelector(testObject.getProperties().get(0).getValue())).size();

				}
			}

			WebUI.delay(TIMEOUT_1_SECOND);
		}

		return 0;
	}

	/**
	 * nos retorna el texto visible de un web element.
	 *
	 * @param testObjectOrXpath - elemento web que contiene el texto.
	 * @return String
	 */
	public String getWebElementText(String selectorName, Object testObjectOrXpathString, boolean testObjectScroll, boolean testObjectHighlight){

		defineVariableTestObject(selectorName, testObjectOrXpathString);

		if (verifyTestObjectExistence(testObject, TIMEOUT_3_SECONDS, TIMEOUT_5_SECONDS, testObjectScroll, testObjectHighlight) != null) {

			if (testObject.getProperties().get(0).getValue().contains("input") || testObject.getProperties().get(0).getValue().contains("INPUT")) {

				return WebUI.getAttribute(testObject, helper.Keyword.ATTRIBUTE_VALUE.value).trim();
			}

			else{

				return WebUI.getText(testObject).trim();
			}
		}

		return null;
	}

	/**
	 * Suple texto en un web element (especificament eun campo de texto).
	 *
	 * @param testObjectOrXpathh - elemento web que se le suplira el texto.
	 * @param text - testo a ser suplido.
	 * @param shortDescriptionInCaseOfFail - Breve descripcion en caso de falla.
	 */
	public void setTextToWebElement(String selectorName, Object testObjectOrXpathString, String textToSet, String shortDescriptionInCaseOfFail, boolean testObjectScroll, boolean testObjectHighlight, boolean isTakeScreenshot){

		defineVariableTestObject(selectorName, testObjectOrXpathString);

		if (verifyTestObjectExistence(selectorName, testObject, TIMEOUT_1_SECOND, TIMEOUT_5_SECONDS, testObjectScroll, testObjectHighlight) != null) {

			WebUI.setText(testObject, textToSet);

			message = String.valueOf("Fue suplido el texto <b>${textToSet}<b> <b>${shortDescriptionInCaseOfFail}<b>.<br>");

			if (isTakeScreenshot) {

				screenshotFileNameAndPath = WebUI.takeScreenshot();

				message += String.valueOf("<img src=\"${screenshotFileNameAndPath}\" alt=\"Imagen de evidencia\" style=\"width: 100%; border: solid 1px blue;\"><br>");
			}

			reportGenerator = ReportGenerator.getUniqueIntance();
			reportGenerator.setLogStatusPASS(message);
		}
	}

	/**
	 * Click en web element.
	 *
	 * Observacion: en caso de desplegarse una alerta, le tomara un screenshot y le dara
	 * click a la alerta cerrandola.
	 *
	 * @param testObjectOrXpathString - Web element sobre el cual se dara click.
	 * @param shortDescriptionInCaseOfFail - Breve descripcion en caso de falla.
	 */
	public void clickToWebElement(String selectorName, Object testObjectOrXpathString, String shortDescriptionInCaseOfFail, boolean testObjectScroll, boolean testObjectHighlight) {

		defineVariableTestObject(selectorName, testObjectOrXpathString);

		if (verifyTestObjectExistence(testObject, TIMEOUT_1_SECOND, TIMEOUT_5_SECONDS, testObjectScroll, testObjectHighlight) != null) {

			if (WebUI.waitForElementClickable(testObject, TIMEOUT_5_SECONDS, FailureHandling.OPTIONAL)) {

				// Screenshot before click to be replace with alert screenshot
				//fileNameAndPathOfScreenshot = WebUI.takeScreenshot();

				WebUI.click(testObject);
				/*
				 if (WebUI.waitForAlert(TIMEOUT_1_SECOND, FailureHandling.OPTIONAL)) {
				 x = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration().getBounds().getMinX();
				 y = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration().getBounds().getMinX();
				 width = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration().getBounds().getWidth();
				 height = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDefaultConfiguration().getBounds().getHeight();
				 if (robot == null) {
				 robot = new Robot();
				 }
				 WebUI.delay(TIMEOUT_1_SECOND);
				 bufferedImage = robot.createScreenCapture(new Rectangle(x, y, width, (height - 50)));
				 ImageIO.write(bufferedImage, "png", new File(fileNameAndPathOfScreenshot));
				 WebUI.delay(TIMEOUT_1_SECOND);
				 //reportGenerator.screenVerification(String.valueOf("se presento la alerta mostrando el mensaje ${WebUI.getAlertText(FailureHandling.OPTIONAL)}."), true, true);
				 WebUI.acceptAlert();
				 }
				 fileNameAndPathOfScreenshot = null;
				 //reportGenerator.screenVerification(String.valueOf("Fue presionado el boton ${shortDescriptionInCaseOfFail}."), true, true);
				 */
				return;
			}
		}

		//reportGenerator.screenVerification(String.valueOf("Luego de la espera de 30 segundos para dar click. ${shortDescriptionInCaseOfFail}"), false, true);
	}

	// ****************
	// Common Utilities
	// ****************

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

	public Map<String, Object> getMapStringObject() {

		if (mapStringObject == null) {

			mapStringObject = new HashMap<String, Object>();
		}

		return mapStringObject;
	}

	public static int getTimeout1Second() {
		return TIMEOUT_1_SECOND;
	}

	public static int getTimeout3Seconds() {
		return TIMEOUT_3_SECONDS;
	}

	public static int getTimeout5Seconds() {
		return TIMEOUT_5_SECONDS;
	}

	public static int getTimeout10Seconds() {
		return TIMEOUT_10_SECONDS;
	}
}
