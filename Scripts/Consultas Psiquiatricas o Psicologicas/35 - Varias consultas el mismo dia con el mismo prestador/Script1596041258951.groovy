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
import helper.Keyword
import internal.GlobalVariable as GlobalVariable

CommonAction commonAction = CommonAction.getUniqueIntance();

// Iteraciones de autorizaciones
for(int i=1; i <= 2; i++){
	
	// Se aprueba la autorizacion
	if (i == 1) {
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
			'ejecutarQueryCapturaAfiliadoPBS' : false,
			'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
			'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
			'servicioConsulta' : Keyword.SERVICIO_CONSULTA.value], FailureHandling.STOP_ON_FAILURE);
	}
	else if (i == 2) {
		
		commonAction.getMapStringString().put("ejecutarQueryCapturaAfiliadoMPP", false);
		commonAction.getMapStringString().put("ejecutarQueryCapturaAfiliadoPBS", false);
		commonAction.getMapStringString().put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		commonAction.getMapStringString().put("ejecutarQueryPrestadorServicio", false);
		commonAction.getMapStringString().put("ejecutarQueryDiagnostico", false);
		commonAction.getMapStringString().put("ejecutarQueryProcedimientoPorPrestador", false);
		commonAction.getMapStringString().put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);
		commonAction.getMapStringString().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		commonAction.getMapStringString().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
		commonAction.getMapStringString().put("consultarApiConsultarProcedimientos", false);
		commonAction.getMapStringString().put("consultarApiConsultarProcedimientosCasoPositivo", false);
		commonAction.getMapStringString().put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
		commonAction.getMapStringString().put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", false);
		commonAction.getMapStringString().put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), commonAction.getMapStringString(), FailureHandling.STOP_ON_FAILURE);
	}
}