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

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
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
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value + Keyword.AFILIADO_FEMENINO.value,
	//'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value + Keyword.AFILIADO_FEMENINO.value,
	'servicioConsulta' : Keyword.SERVICIO_SONOGRAFIA.value,
	// Parametros Caso Modelo
	'codigoProcedimiento': "881401"/*,
	'codigoServicioPrestadorSalud' : '20'*/], FailureHandling.STOP_ON_FAILURE);