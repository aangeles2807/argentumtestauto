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

import helper.Keyword
import internal.GlobalVariable as GlobalVariable

Map<String, String> mapaVariablesScript = null;

// Iteraciones de autorizaciones
for(int i=1; i <= 2; i++){
	
	// Se aprueba la autorizacion
	if (i == 1) {
		
		mapaVariablesScript = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
			'ejecutarQueryCapturaAfiliadoPBS' : false,
			'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
			'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
			'servicioConsulta' : Keyword.SERVICIO_CONSULTA.value], FailureHandling.STOP_ON_FAILURE);
	}
	else if (i == 2) {
		
		mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPP", false);
		mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoPBS", false);
		mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		mapaVariablesScript.put("ejecutarQueryPrestadorServicio", false);
		mapaVariablesScript.put("ejecutarQueryDiagnostico", false);
		mapaVariablesScript.put("ejecutarQueryProcedimientoPorPrestador", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
		mapaVariablesScript.put("consultarApiConsultarProcedimientos", false);
		mapaVariablesScript.put("consultarApiConsultarProcedimientosCasoPositivo", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);
	}
}