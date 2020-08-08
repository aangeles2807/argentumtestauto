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
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import helper.CommonAction
import helper.Keyword
import helper.ReportGenerator
import internal.GlobalVariable as GlobalVariable

//*******
// Helper
//*******
CommonAction commonAction = CommonAction.getUniqueIntance();
ReportGenerator reportGenerator = ReportGenerator.getUniqueIntance();

// *******************
// Test Case variables
// *******************
String xpath;
 
String datosDelAfiliado = "1677288";
String servicio = "CONSULTAS MEDICAS CUALQUIER ESPECIALIDAD";

try {
	
	// Iniciamos Sesion
	WebUI.callTestCase(findTestCase('GUI/Login'), null, FailureHandling.STOP_ON_FAILURE);
	
	// Click en la opcion Creación de Autorizaciones
	WebUI.click(findTestObject('GUI/Comun/accesoCreacionDeAutorizaciones'), FailureHandling.STOP_ON_FAILURE);
	
	// Espera que el logo de carga deje de presentarse
	if (commonAction.verifyTestObjectExistence(Keyword.XPATH_SELECTOR.value, findTestObject('GUI/Comun/logoCargando'), commonAction.getTimeout1Second(), commonAction.getTimeout5Seconds(), false, false) == null) {
		
		WebUI.delay(commonAction.getTimeout3Seconds())
	}
	else{
		
		reportGenerator.setLogStatusFAIL("")
	}
	
	WebUI.delay(5);
	
}
catch (Exception e) {
	
	reportGenerator.setLogStatusFAIL(e.getMessage());
	KeywordUtil.markErrorAndStop(e.getMessage());
}