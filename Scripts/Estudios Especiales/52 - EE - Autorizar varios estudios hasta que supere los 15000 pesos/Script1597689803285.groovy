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

double monto = 0.00;
boolean continuarIteracion = true;
CommonAction commonAction = CommonAction.getUniqueIntance();
Map<String, String> mapaPrestacionesIngresadas = new HashMap<String, String>();

while(continuarIteracion){
	
	if (monto == 0.00) {
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
			// Querys
			'ejecutarQueryCapturaAfiliadoMPP' : false,
			'ejecutarQueryCapturaAfiliadoPBS' : false,
			'ejecutarQueryCapturaAfiliadoMPPoPBS' : true,
			'ejecutarQueryPrestadorServicio' : true,			
			// APIs
			'consultarApiAfiliado' : true,
			'consultarApiAfiliadoCasoPositivo' : true,
			'consultarApiPrestadorSalud' : true,
			'consultarApiPrestadorSaludCasoPositivo' : true,
			'consultarApiPrestadorSaludServicios' : true,
			'consultarApiPrestadorSaludServiciosCasoPositivo' : true,
			'consultarApiAutorizacionPortalValidarCobertura' : true,
			'consultarApiAutorizacionPortalValidarCoberturaCasoPositivo' : true,
			'consultarApiAutorizacionPortalCamposRequeridos' : true,
			'consultarApiAutorizacionPortalCamposRequeridosCasoPositivo' : true,			
			// Querys
			'ejecutarQueryDiagnostico' : true,			
			// APIs
			'consultarApiConsultarDiagnosticos' : true,
			'consultarApiConsultarDiagnosticosCasoPositivo' : true,
			'consultarApiAutorizacionPortalIngresar' : true,
			'consultarApiAutorizacionPortalIngresarCasoPositivo' : true,			
			// Querys
			'ejecutarQueryProcedimientoPorPrestador' : true,			
			// APIs
			'consultarApiAutorizacionPortalPrestadorSaludProcedimientos' : true,
			'consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo' : true,
			'consultarApiConsultarProcedimientos' : true,
			'consultarApiConsultarProcedimientosCasoPositivo' : true,
			'consultarApiAutorizacionPortalTarifaProcedimiento' : true,
			'consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo' : true,
			'consultarApiAutorizacionPortalAutorizar' : false,
			'consultarApiAutorizacionPortalAutorizarCasoPositivo' : false,
			'consultarApiAutorizacionPortalAnular' : false,
			'consultarApiAutorizacionPortalAnularCasoPositivo' : false,
			'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
			'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
			'servicioConsulta' : Keyword.SERVICIO_ESTUDIOS_ESPECIALES.value + Keyword.PRESTADOR_CENTRO_INST.value], FailureHandling.STOP_ON_FAILURE);
		
		// Obtenemos la prestacion
		mapaPrestacionesIngresadas.put(commonAction.getMapStringObject().get("descripcionPrestacion"), commonAction.getMapStringObject().get("descripcionPrestacion"));
		
		// Obtenemos el monto de la prestacion
		monto += Double.parseDouble(commonAction.getMapStringObject().get("tarifaProcedimiento"));
	}
	else if (monto > 0.00 && monto < 15000) {
		
		// Querys
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPP", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryPrestadorServicio", false);		
		// APIs
		commonAction.getMapStringObject().put("consultarApiAfiliado", true);
		commonAction.getMapStringObject().put("consultarApiAfiliadoCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiPrestadorSalud", true);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludServicios", true);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludServiciosCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCobertura", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridos", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", true);		
		// Querys
		commonAction.getMapStringObject().put("ejecutarQueryDiagnostico", false);		
		// APIs
		commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticos", true);
		commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticosCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresar", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresarCasoPositivo", true);		
		// Querys
		commonAction.getMapStringObject().put("ejecutarQueryProcedimientoPorPrestador", true);		
		// APIs
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientos", true);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientosCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimiento", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizar", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAnular", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAnularCasoPositivo", false);
		// Prestaciones Ingresadas
		commonAction.getMapStringObject().put("mapaPrestacionesIngresadas", mapaPrestacionesIngresadas);
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), commonAction.getMapStringObject(), FailureHandling.STOP_ON_FAILURE);
		
		// Obtenemos la prestacion
		mapaPrestacionesIngresadas.put(commonAction.getMapStringObject().get("descripcionPrestacion"), commonAction.getMapStringObject().get("descripcionPrestacion"));
		
		// Obtenemos el monto de la prestacion
		monto += Double.parseDouble(commonAction.getMapStringObject().get("tarifaProcedimiento"));
	}
	else if (monto >= 15000) {
		
		// Querys
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPP", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
		commonAction.getMapStringObject().put("ejecutarQueryPrestadorServicio", false);		
		// APIs
		commonAction.getMapStringObject().put("consultarApiAfiliado", false);
		commonAction.getMapStringObject().put("consultarApiAfiliadoCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiPrestadorSalud", false);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludServicios", false);
		commonAction.getMapStringObject().put("consultarApiPrestadorSaludServiciosCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCobertura", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridos", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", false);		
		// Querys
		commonAction.getMapStringObject().put("ejecutarQueryDiagnostico", false);		
		// APIs
		commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticos", false);
		commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticosCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresar", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);		
		// Querys
		commonAction.getMapStringObject().put("ejecutarQueryProcedimientoPorPrestador", false);		
		// APIs
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientos", false);
		commonAction.getMapStringObject().put("consultarApiConsultarProcedimientosCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizar", true);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarMensajeError", String.valueOf("<b>Permitio autorizar varios estudios que superaron los 15000 pesos, siendo el monto actual ${monto}</b>"));
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAnular", false);
		commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAnularCasoPositivo", false);
		// Prestaciones Ingresadas
		commonAction.getMapStringObject().put("mapaPrestacionesIngresadas", mapaPrestacionesIngresadas);
		commonAction.getMapStringObject().put("tarifaProcedimiento", String.valueOf(monto));
		
		WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), commonAction.getMapStringObject(), FailureHandling.STOP_ON_FAILURE);
		
		continuarIteracion = false;
	}
}