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
import internal.GlobalVariable as GlobalVariable

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

mapaVariablesScript = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
	// Querys
	'ejecutarQueryCapturaAfiliadoMPP' : true,
	'ejecutarQueryCapturaAfiliadoPBS' : false,
	'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
	'ejecutarQueryPrestadorServicio' : false,
	'ejecutarQueryServicioConPrestacion' : true,
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
	'ejecutarQueryProcedimientoPorPrestador' : false,
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
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO_SIN_PBS.value,
	//'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value + Keyword.AFILIADO_FEMENINO.value,
	'servicioConsulta' : Keyword.SERVICIO_LABORATORIO.value,
	// Parametros Caso Modelo
	'codigoProcedimiento': "906850"], FailureHandling.STOP_ON_FAILURE);

// Querys
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPP", false);
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoPBS", false);
mapaVariablesScript.put("ejecutarQueryCapturaAfiliadoMPPoPBS", false);
mapaVariablesScript.put("ejecutarQueryPrestadorServicio", false);
mapaVariablesScript.put("ejecutarQueryServicioConPrestacion", true);
// APIs
mapaVariablesScript.put("consultarApiAfiliado", true);
mapaVariablesScript.put("consultarApiAfiliadoCasoPositivo", true);
mapaVariablesScript.put("consultarApiPrestadorSalud", true);
mapaVariablesScript.put("consultarApiPrestadorSaludCasoPositivo", true);
mapaVariablesScript.put("consultarApiPrestadorSaludServicios", true);
mapaVariablesScript.put("consultarApiPrestadorSaludServiciosCasoPositivo", true);
// Querys
mapaVariablesScript.put("ejecutarQueryDoctor", false);
// APIs
mapaVariablesScript.put("consultarApiPrestadorSaludDoctores", false);
mapaVariablesScript.put("consultarApiPrestadorSaludDoctoresCasoPositivo", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCobertura", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridos", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", true);
// Querys
mapaVariablesScript.put("ejecutarQueryDiagnostico", false);
// APIs
mapaVariablesScript.put("consultarApiConsultarDiagnosticos", true);
mapaVariablesScript.put("consultarApiConsultarDiagnosticosCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresar", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalIngresarCasoPositivo", true);
// Querys
mapaVariablesScript.put("ejecutarQueryProcedimientoPorPrestador", false);
mapaVariablesScript.put("ejecutarQueryPrestacionNoContratada", false);
// APIs
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", true);
mapaVariablesScript.put("consultarApiConsultarProcedimientos", true);
mapaVariablesScript.put("consultarApiConsultarProcedimientosCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimiento", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizar", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalAutorizarCasoPositivo", true);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnular", false);
mapaVariablesScript.put("consultarApiAutorizacionPortalAnularCasoPositivo", false);
mapaVariablesScript.put("codigoProcedimiento", "906850");
mapaVariablesScript.put("authorizationListHttpBodyContent", new ArrayList<>());

mapaVariablesScript = WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), mapaVariablesScript, FailureHandling.STOP_ON_FAILURE);