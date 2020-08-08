import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import helper.CommonAction
import helper.GuiApoloComonAction
import helper.Keyword
import helper.ReportGenerator
import internal.GlobalVariable as GlobalVariable

CommonAction commonAction = CommonAction.getUniqueIntance();
ReportGenerator reportGenerator = ReportGenerator.getUniqueIntance();
GuiApoloComonAction guiApoloComonAction = GuiApoloComonAction.getUniqueIntance();

// Desplegamos el navegador
WebUI.openBrowser(null, FailureHandling.STOP_ON_FAILURE);

// Maximizamos el nagevador
WebUI.maximizeWindow(FailureHandling.STOP_ON_FAILURE);

// Accedemos a la URL deseada
WebUI.navigateToUrl(Keyword.PORTAL_AUTORIZACIONES_URL.value, FailureHandling.STOP_ON_FAILURE);

commonAction.javaScriptExecuter(
	findTestObject('GUI/Comun/campoUsuario').getProperties().get(0).getName(),
	findTestObject('GUI/Comun/campoUsuario'),
	String.valueOf("var myElement = document.querySelector('${findTestObject('GUI/Comun/campoUsuario').getProperties().get(0).getValue()}'); myElement.style.border = 'solid 1px red';")
);

//document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;

//var paragraphCount = document.evaluate('count(//p)', document, null, XPathResult.ANY_TYPE, null );



// Suplimos el usuario
commonAction.setTextToWebElement(findTestObject('GUI/Comun/campoUsuario').getProperties().get(0).getName(), findTestObject('GUI/Comun/campoUsuario'), Keyword.PORTAL_AUTORIZACIONES_USUARIO.value, "en el campo para suplir el usuario", false, false, true);
///WebUI.setText(findTestObject('GUI/Comun/campoUsuario'), Keyword.PORTAL_AUTORIZACIONES_USUARIO.value, FailureHandling.STOP_ON_FAILURE);

// Suplimos la contraseña
WebUI.setText(findTestObject('GUI/Comun/campoContrasena'), Keyword.PORTAL_AUTORIZACIONES_CONTRASENA.value, FailureHandling.STOP_ON_FAILURE);

// Click en el boton Iniciar Sesión
WebUI.click(findTestObject('GUI/Comun/botonIniciarSesion'), FailureHandling.STOP_ON_FAILURE);

// Espera que el logo de carga deje de presentarse
if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
	
	WebUI.delay(commonAction.getTimeout3Seconds())
}