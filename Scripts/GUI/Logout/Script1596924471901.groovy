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

// Click en el boton de usuario en sesion
commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "#button-animated", "Boton de Usuario en Sesión", false, true, false);

// Click en el boton Salir
commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "#dropdown-animated button", "Salir", false, true, true);

// Espera que el logo de carga deje de presentarse
if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
	
	message = String.valueOf("<b>Final de Sesión exitosa<b><br>");
	
	message += String.valueOf("<a href=\"${WebUI.takeScreenshot()}\" target=\"_blank\"><img src=\"${WebUI.takeScreenshot()}\"  alt=\"Imagen de evidencia\" style=\"width: 100%; border: solid 1px blue;\">></a><br>");

	reportGenerator = ReportGenerator.getUniqueIntance();
	reportGenerator.setLogStatusPASS(message);
}