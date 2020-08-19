package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class GuiApoloComonAction {
	
	private CommonAction commonAction;
	private String message;
	private String fileNameAndPathOfScreenshot;
	private String webElementPath;
	private TestObject testObject;
	
	public GuiApoloComonAction(){
		
	}
	
	// Singleton Pattern
	// *******************************************************************
	private static GuiApoloComonAction guiApoloComonActionVariable = null;
	
	public static  getUniqueIntance() {
	
		if(guiApoloComonActionVariable == null) {
	
			guiApoloComonActionVariable = new GuiApoloComonAction();
		}
	
		return guiApoloComonActionVariable;
	}
	// *******************************************************************
	
	public boolean waitLoadingLogoNotShowing(){
		
		commonAction = CommonAction.getUniqueIntance();
		webElementPath = findTestObject('GUI/Comun/logoCargando').getProperties().get(0).getName();
		testObject = findTestObject('GUI/Comun/logoCargando');
		
		while(commonAction.verifyTestObjectExistence(webElementPath, testObject, commonAction.getTimeout1Second(), commonAction.getTimeout1Second(), false, false) != null)
		
		WebUI.delay(commonAction.getTimeout1Second());
		
		return true;
		/*
		for(int i=0; i < commonAction.getTimeout5Seconds();){
			
			if (commonAction.verifyTestObjectExistence(webElementPath, testObject, commonAction.getTimeout1Second(), commonAction.getTimeout5Seconds(), false, false) == null) {
				
				return true;
			}
			else{
				
				WebUI.delay(commonAction.getTimeout1Second());
			}
		}
		
		message = "<b>El logo de carga del Portal de Autorizaciones se mostro por un lapso de tiempo de 5 segundos.</b>";
		
		message += String.valueOf("<img src=\"${WebUI.takeScreenshot(FailureHandling.OPTIONAL)}\" alt=\"Imagen de evidencia\" style=\"width: 100%; border: solid 1px blue;\"><br>");
		
		throw new RuntimeException(message);
		*/
	}
}
