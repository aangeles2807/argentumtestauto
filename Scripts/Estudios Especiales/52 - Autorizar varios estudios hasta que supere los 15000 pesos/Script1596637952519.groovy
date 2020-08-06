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

int monto = 0;
boolean continuarIteracion = true;
CommonAction commonAction = CommonAction.getUniqueIntance();

while(continuarIteracion){
	
	if (monto == 0) {
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
			'ejecutarQueryCapturaAfiliadoMPP' : false,
			'ejecutarQueryCapturaAfiliadoPBS' : false,
			'consultarApiAutorizacionPortalAutorizar' : false,
			'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
			'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
			'servicioConsulta' : Keyword.SERVICIO_ESTUDIOS_ESPECIALES.value + Keyword.PRESTADOR_CENTRO_INST.value], FailureHandling.STOP_ON_FAILURE);
		
		monto += Integer.parseInt(commonAction.getMapStringObject().get("tarifaProcedimiento"));
	}
	else if (monto > 0.00 && monto < 15000) {
		
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPP", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryPrestadorServicio", false);
		commonAction.getMapStringObject().put("ejecutarQueryDiagnostico", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCobertura", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizar", false);
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), commonAction.getMapStringObject(), FailureHandling.STOP_ON_FAILURE);
		
		monto += Integer.parseInt(commonAction.getMapStringObject().get("tarifaProcedimiento"));
	}
	else if (monto >= 15000) {
		
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPP", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryPrestadorServicio", false);
		commonAction.getMapStringObject().put("ejecutarQueryDiagnostico", false);
		commonAction.getMapStringObject().put("ejecutarQueryProcedimientoPorPrestador", false);
		commonAction.getMapStringObject().put("consultarApiAfiliado", false);
		commonAction.getMapStringObject().put("consultarApiPrestadorSalud", false);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludServicios", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCobertura", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridos", false);
		commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticos", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresar", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientos", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizar", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarMensajeError", String.valueOf("<b>Permitio autorizar varios estudios que superaron los 15000 pesos, siendo el monto actual ${monto}</b>"));
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), commonAction.getMapStringObject(), FailureHandling.STOP_ON_FAILURE);
		
		continuarIteracion = false;
	}
}