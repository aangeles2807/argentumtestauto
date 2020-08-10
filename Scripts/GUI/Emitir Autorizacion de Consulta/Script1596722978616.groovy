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
import helper.GuiApoloComonAction
import helper.Keyword
import helper.ReportGenerator
import internal.GlobalVariable as GlobalVariable

//*******
// Helper
//*******
CommonAction commonAction = CommonAction.getUniqueIntance();
ReportGenerator reportGenerator = ReportGenerator.getUniqueIntance();
GuiApoloComonAction guiApoloComonAction = GuiApoloComonAction.getUniqueIntance();

// *******************
// Test Case variables
// *******************
String message = null;
String selector = null;
TestObject testObject = null;
String datosDelAfiliado = "1677288";
String servicio = "CONSULTAS MEDICAS CUALQUIER ESPECIALIDAD";
String origen = "ENFERMEDAD GENERAL";
String diagnostico = "R509";
String telefonoDelAfiliado = "8091112222";
String correoElectronicoAfiliado = "prueba@universal.com.do";
String prestacion = "890202";


try {
	
	// Iniciamos Sesion
	WebUI.callTestCase(findTestCase('GUI/Login'), null, FailureHandling.STOP_ON_FAILURE);
	
	// Click en la opcion Creación de Autorizaciones
	selector = findTestObject('GUI/Comun/accesoCreacionDeAutorizaciones').getProperties().get(0).getName();
	testObject = findTestObject('GUI/Comun/accesoCreacionDeAutorizaciones');
	commonAction.highlightTestObject(Keyword.CSS_SELECTOR.value, "a[href='/Autorizaciones/Nueva']>div.text");
	commonAction.clickToWebElement(selector, testObject, "Creación de Autorizaciones", false, false, true);
	
	// Espera que el logo de carga deje de presentarse
	if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
		
		// Suplimos los datos del afiliado y lo seleccionamos
		selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoDatosDelAfiliado').getProperties().get(0).getName();
		testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoDatosDelAfiliado');
		commonAction.setTextToWebElement(selector, testObject, datosDelAfiliado, "Datos del Afiliado", false, false, false);
		commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "ng-select[name='identificacion-afiliado'] ng-dropdown-panel div[class='ng-option ng-option-marked']", String.valueOf("Afiliado ${datosDelAfiliado}"), false, true, true);
		
		// Suplimos los datos del servicio y lo seleccionamos
		selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoServicio').getProperties().get(0).getName();
		testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoServicio');
		commonAction.setTextToWebElement(selector, testObject, servicio, "Sservicio", false, false, false);
		commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "ng-select[name='tipo-servicio'] ng-dropdown-panel div[class='ng-option ng-option-marked']", String.valueOf("Servicio ${servicio}"), false, true, true);
		
		// Click en boton Continuar
		selector = findTestObject('GUI/Emitir Autorizacion de Consulta/botonContinuarDatosDelAfiliado').getProperties().get(0).getName();
		testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/botonContinuarDatosDelAfiliado');
		commonAction.clickToWebElement(selector, testObject, "Continuar", false, true, true);
		
		// Espera que el logo de carga deje de presentarse
		if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
			
			// Suplimos los datos del origen y lo seleccionamos
			selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoOrigen').getProperties().get(0).getName();
			testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoOrigen');
			commonAction.setTextToWebElement(selector, testObject, origen, "Origen", false, false, false);
			commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "ng-select[name='origen'] ng-dropdown-panel div[class='ng-option ng-option-marked']", String.valueOf("Origen ${origen}"), false, true, true);
			
			// Suplimos los datos del diagnostico y lo seleccionamos
			selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoDiagnostico').getProperties().get(0).getName();
			testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoDiagnostico');
			commonAction.setTextToWebElement(selector, testObject, diagnostico, "Diagnóstico", false, false, false);
			commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "ng-select[name='diagnostico'] ng-dropdown-panel div[class='ng-option ng-option-marked']", String.valueOf("Diagnóstico ${diagnostico}"), false, true, true);
			
			// Suplimos los datos del Teléfono del Afiliado
			selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoTelefonoDelAfiliado').getProperties().get(0).getName();
			testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoTelefonoDelAfiliado');
			commonAction.setTextToWebElement(selector, testObject, telefonoDelAfiliado, "Teléfono del Afiliado", false, false, false);
			
			// Suplimos los datos del Correo Electrónico Afiliado
			selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoCorreoElectronicoAfiliado').getProperties().get(0).getName();
			testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoCorreoElectronicoAfiliado');
			commonAction.setTextToWebElement(selector, testObject, correoElectronicoAfiliado, "Correo Electrónico Afiliado", false, false, false);
			
			// Click en boton Continuar
			selector = findTestObject('GUI/Emitir Autorizacion de Consulta/botonContinuarDatosDeAutorizacion').getProperties().get(0).getName();
			testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/botonContinuarDatosDeAutorizacion');
			commonAction.clickToWebElement(selector, testObject, "Continuar", false, true, true);
			
			// Espera que el logo de carga deje de presentarse
			if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
				
				// Suplimos los datos de la Prestacion y la seleccionamos
				selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoPrestacion').getProperties().get(0).getName();
				testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoPrestacion');
				commonAction.setTextToWebElement(selector, testObject, prestacion, "Prestación", false, false, false);
				commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "ng-select[name='prestacion'] ng-dropdown-panel div[class='ng-option ng-option-marked']", String.valueOf("Prestación ${prestacion}"), false, true, true);
				
				// Click en boton Continuar
				selector = findTestObject('GUI/Emitir Autorizacion de Consulta/botonCrearAutorizacion').getProperties().get(0).getName();
				testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/botonCrearAutorizacion');
				commonAction.clickToWebElement(selector, testObject, "Crear Autorización", false, true, true);
				
				// Espera que el logo de carga deje de presentarse
				if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
					
					if (commonAction.verifyTestObjectExistence(Keyword.CSS_SELECTOR.value, "section#resumen div.m-alert-success.icon", commonAction.getTimeout1Second(), commonAction.getTimeout3Seconds(), false, true) != null) {
						
						message = String.valueOf("<b>La autorizacion fue realizada exitosamente<b><br>");
						
						message += String.valueOf("<a href=\"${WebUI.takeScreenshot()}\" target=\"_blank\"><img src=\"${WebUI.takeScreenshot()}\"  alt=\"Imagen de evidencia\" style=\"width: 100%; border: solid 1px blue;\">></a><br>");
			
						reportGenerator = ReportGenerator.getUniqueIntance();
						reportGenerator.setLogStatusPASS(message);
						
						// ******************************************************************
						// Anulamos la transaccion para que no interfiera en el caso negativo
						// ******************************************************************
						
						// Click en boton Continuar
						selector = findTestObject('GUI/Emitir Autorizacion de Consulta/botonAnularAutorizacion').getProperties().get(0).getName();
						testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/botonAnularAutorizacion');
						commonAction.clickToWebElement(selector, testObject, "Crear Autorización", false, false, false);
						
						String razon = "ERROR DE DIGITACION";
						String comentario = "Prueba Robot Katalon Studio";
						
						// Suplimos los datos de la Razón y la seleccionamos
						selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoRazonCancelacionAutorizacion').getProperties().get(0).getName();
						testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoRazonCancelacionAutorizacion');
						commonAction.setTextToWebElement(selector, testObject, razon, "Razón", false, false, false);
						commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, "ng-select[name='razon'] ng-dropdown-panel div[class='ng-option ng-option-marked']", String.valueOf("Razón ${razon}"), false, false, false);
						
						// Suplimos los datos de la Comentario
						selector = findTestObject('GUI/Emitir Autorizacion de Consulta/campoComentarioCancelacionAutorizacion').getProperties().get(0).getName();
						testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/campoComentarioCancelacionAutorizacion');
						commonAction.setTextToWebElement(selector, testObject, comentario, "Comentario", false, false, false);
						
						// Click en boton Anular
						selector = findTestObject('GUI/Emitir Autorizacion de Consulta/botonAnular').getProperties().get(0).getName();
						testObject = findTestObject('GUI/Emitir Autorizacion de Consulta/botonAnular');
						commonAction.clickToWebElement(selector, testObject, "Anular", false, false, false);
						
						// Espera que el logo de carga deje de presentarse
						if (guiApoloComonAction.waitLoadingLogoNotShowing()) {
							
							// Click en boton Cerrar
							commonAction.clickToWebElement(Keyword.CSS_SELECTOR.value, ".modal-content button.btn-primary", "Cerrar", false, false, false);
							
							// Cerramos Sesion
							WebUI.callTestCase(findTestCase('GUI/Logout'), null, FailureHandling.STOP_ON_FAILURE);
						}
					}
					else{
						
						message = String.valueOf("<b>La autorizacion no fue realizada exitosamente<b><br>");
						
						message += String.valueOf("<a href=\"${WebUI.takeScreenshot()}\" target=\"_blank\"><img src=\"${WebUI.takeScreenshot()}\"  alt=\"Imagen de evidencia\" style=\"width: 100%; border: solid 1px blue;\">></a><br>");
			
						reportGenerator = ReportGenerator.getUniqueIntance();
						reportGenerator.setLogStatusFAIL(message);
					}
				}
			}
		}
	}
	
}
catch (Exception e) {
	
	reportGenerator.setLogStatusFAIL(e.getMessage());
	KeywordUtil.markErrorAndStop(e.getMessage());
}