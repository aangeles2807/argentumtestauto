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

mapaVariablesScript = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
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
	// Querys
	'ejecutarQueryDoctor' : false,
	// APIs
	'consultarApiPrestadorSaludDoctores' : false,
	'consultarApiPrestadorSaludDoctoresCasoPositivo' : false,
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
	'ejecutarQueryPrestacionNoContratada' : false,
	// APIs
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientos' : true,
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo' : true,
	'consultarApiConsultarProcedimientos' : true,
	'consultarApiConsultarProcedimientosCasoPositivo' : true,
	'consultarApiAutorizacionPortalTarifaProcedimiento' : true,
	'consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo' : true,
	'consultarApiAutorizacionPortalAutorizar' : true,
	'consultarApiAutorizacionPortalAutorizarCasoPositivo' : true,
	'consultarApiAutorizacionPortalAnular' : false,
	'consultarApiAutorizacionPortalAnularCasoPositivo' : false,
	// Condiciones de los Querys
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value,
	'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
	'servicioConsulta' : Keyword.SERVICIO_TERAPIAS_FISICAS.value], FailureHandling.STOP_ON_FAILURE);

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
// Querys
mapaVariablesScript.put("ejecutarQueryDoctor", false);
// APIs
mapaVariablesScript.put("consultarApiPrestadorSaludDoctores", false);
mapaVariablesScript.put("consultarApiPrestadorSaludDoctoresCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCobertura", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridos", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", false);
// Querys
mapaVariablesScript.put("ejecutarQueryDiagnostico", false);
// APIs
mapaVariablesScript.put("consultarApiConsultarDiagnosticos", true);
mapaVariablesScript.put("consultarApiConsultarDiagnosticosCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresar", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresarCasoPositivo", false);
// Querys
mapaVariablesScript.put("ejecutarQueryProcedimientoPorPrestador", false);
mapaVariablesScript.put("ejecutarQueryPrestacionNoContratada", false);
// APIs
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", false);
mapaVariablesScript.put("consultarApiConsultarProcedimientos", false);
mapaVariablesScript.put("consultarApiConsultarProcedimientosCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimiento", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizar", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnular", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnularCasoPositivo", false);
// Parametros Caso Modelo
mapaVariablesScript.put("authorizationListHttpBodyContent", new ArrayList<>());

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);