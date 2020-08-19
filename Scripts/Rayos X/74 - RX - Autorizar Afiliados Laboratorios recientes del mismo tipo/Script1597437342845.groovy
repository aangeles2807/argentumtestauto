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

Map<String, String> mapaVariablesScript1 = null;

// Iteraciones de autorizaciones
for(int i=1; i <= 2; i++){
	
	// Se aprueba la autorizacion
	if (i == 1) {
		
		mapaVariablesScript1 = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
			'ejecutarQueryCapturaAfiliadoMPP' : false,
			'ejecutarQueryCapturaAfiliadoPBS' : false,
			'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
			'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
			'servicioConsulta' : Keyword.SERVICIO_RAYOS_X.value], FailureHandling.STOP_ON_FAILURE);
	}
	else if (i == 2) {
		
		mapaVariablesScript1.put("ejecutarQueryCapturaAfiliadoMPP", false);
		mapaVariablesScript1.put("ejecutarQueryCapturaAfiliadoPBS", false);
		mapaVariablesScript1.put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		mapaVariablesScript1.put("ejecutarQueryPrestadorServicio", false);
		mapaVariablesScript1.put("ejecutarQueryDiagnostico", false);
		mapaVariablesScript1.put("ejecutarQueryProcedimientoPorPrestador", false);
		mapaVariablesScript1.put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);
		mapaVariablesScript1.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		mapaVariablesScript1.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
		mapaVariablesScript1.put("consultarApiConsultarProcedimientos", false);
		mapaVariablesScript1.put("consultarApiConsultarProcedimientosCasoPositivo", false);
		mapaVariablesScript1.put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
		mapaVariablesScript1.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", false);
		mapaVariablesScript1.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);
	}
}