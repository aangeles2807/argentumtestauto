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

Map<String, String> mapaVariablesScript = null;
CommonAction commonAction = CommonAction.getUniqueIntance();
/*
// Iteraciones de autorizaciones
for(int i=1; i <= 2; i++){
	
	//Se aprueba la autorizacion
	if (i == 1) {
		
		mapaVariablesScript = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
			'ejecutarQueryCapturaAfiliadoMPP' : false,
			'ejecutarQueryCapturaAfiliadoPBS' : false,
			'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
			'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
			'fechaAutorizacion' : Keyword.AFILIADO_AUTORIZACION_1_DIAS.value,
			'servicioConsulta' : Keyword.SERVICIO_LABORATORIO.value], FailureHandling.STOP_ON_FAILURE);
	}
	else if (i == 2) {
		
		mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPP", false);
		mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoPBS", false);
		mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		//mapaVariablesScript.put("fechaAutorizacion", Keyword.AFILIADO_AUTORIZACION_SYSDATE.value);
		mapaVariablesScript.put("fecha", commonAction.getActualDateInSpecificFormat("MM-dd-yyyy"));
		mapaVariablesScript.put("ejecutarQueryPrestadorServicio", true);
		mapaVariablesScript.put("ejecutarQueryDiagnostico", false);
		mapaVariablesScript.put("ejecutarQueryProcedimientoPorPrestador", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalIngresarCasoPositivo", true);
		mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
		mapaVariablesScript.put("consultarApiConsultarProcedimientos", false);
		mapaVariablesScript.put("consultarApiConsultarProcedimientosCasoPositivo", false);
		mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimiento", true);
		mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", true);
		mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		mapaVariablesScript.put("authorizationListHttpBodyContent", new ArrayList<>());
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);
	}
}
*/

mapaVariablesScript = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
	'ejecutarQueryCapturaAfiliadoMPP' : false,
	'ejecutarQueryCapturaAfiliadoPBS' : false,
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
	'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
	'servicioConsulta' : Keyword.SERVICIO_LABORATORIO.value], FailureHandling.STOP_ON_FAILURE);

// Querys
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPP", false);
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoPBS", false);
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
mapaVariablesScript.put("ejecutarQueryPrestadorServicio", false);
// APIs
mapaVariablesScript.put("consultarApiAfiliado", false);
mapaVariablesScript.put("consultarApiAfiliadoCasoPositivo", false);
mapaVariablesScript.put("consultarApiPrestadorSalud", false);
mapaVariablesScript.put("consultarApiPrestadorSaludCasoPositivo", false);
mapaVariablesScript.put("consultarApiPrestadorSaludServicios", false);
mapaVariablesScript.put("consultarApiPrestadorSaludServiciosCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCobertura", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridos", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", false);
// Querys
mapaVariablesScript.put("ejecutarQueryDiagnostico", true);
// APIs
mapaVariablesScript.put("consultarApiConsultarDiagnosticos", true);
mapaVariablesScript.put("consultarApiConsultarDiagnosticosCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresar", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresarCasoPositivo", true);
// Querys
mapaVariablesScript.put("ejecutarQueryProcedimientoPorPrestador", false);
// APIs
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", true);
mapaVariablesScript.put("consultarApiConsultarProcedimientos", true);
mapaVariablesScript.put("consultarApiConsultarProcedimientosCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimiento", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizar", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnular", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnularCasoPositivo", false);
mapaVariablesScript.put("authorizationListHttpBodyContent", new ArrayList<>());

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);

// Querys
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPP", false);
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoPBS", false);
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
mapaVariablesScript.put("ejecutarQueryPrestadorServicio", false);
// APIs
mapaVariablesScript.put("consultarApiAfiliado", false);
mapaVariablesScript.put("consultarApiAfiliadoCasoPositivo", false);
mapaVariablesScript.put("consultarApiPrestadorSalud", false);
mapaVariablesScript.put("consultarApiPrestadorSaludCasoPositivo", false);
mapaVariablesScript.put("consultarApiPrestadorSaludServicios", false);
mapaVariablesScript.put("consultarApiPrestadorSaludServiciosCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCobertura", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridos", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", false);
// Querys
mapaVariablesScript.put("ejecutarQueryDiagnostico", false);
// APIs
mapaVariablesScript.put("consultarApiConsultarDiagnosticos", false);
mapaVariablesScript.put("consultarApiConsultarDiagnosticosCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresar", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);
// Querys
mapaVariablesScript.put("ejecutarQueryProcedimientoPorPrestador", true);
// APIs
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", true);
mapaVariablesScript.put("consultarApiConsultarProcedimientos", true);
mapaVariablesScript.put("consultarApiConsultarProcedimientosCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimiento", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizar", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarMensajeError", "<b>Permitio autorizar una prestación reciente.</b>");
mapaVariablesScript.put("consultarApiAutorizacionPortalAnular", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnularCasoPositivo", false);

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);