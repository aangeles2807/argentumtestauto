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
		
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPP", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryPrestadorServicio", false);
		//commonAction.getMapStringObject().put("consultarApiAfiliado", false);
		//commonAction.getMapStringObject().put("consultarApiAfiliadoCasoPositivo", false);
		//commonAction.getMapStringObject().put("consultarApiPrestadorSalud", false);
		//commonAction.getMapStringObject().put("consultarApiPrestadorSaludCasoPositivo", false);
		//commonAction.getMapStringObject().put("consultarApiPrestadorSaludServicios", false);
		//commonAction.getMapStringObject().put("consultarApiPrestadorSaludServiciosCasoPositivo", false);
		//commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCobertura", false);
		//commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", false);
		//commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridos", false);
		//commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", false);
		commonAction.getMapStringObject().put("ejecutarQueryDiagnostico", false);
		//commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticos", false);
		//commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticosCasoPositivo", false);
		//commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresar", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);
		commonAction.getMapStringObject().put("ejecutarQueryProcedimientoPorPrestador", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientos", false);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientosCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizar", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAnular", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAnularCasoPositivo", false);
		
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), commonAction.getMapStringObject(), FailureHandling.STOP_ON_FAILURE);
	}
}