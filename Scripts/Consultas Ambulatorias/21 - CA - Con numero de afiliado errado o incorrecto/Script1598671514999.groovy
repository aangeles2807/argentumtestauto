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
	'ejecutarQueryCapturaAfiliadoMPP' : false,
	'ejecutarQueryCapturaAfiliadoPBS' : false,
	'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
	'ejecutarQueryPrestadorServicio' : false,
	'ejecutarQueryPrestadorFueradeRed' : false,
	'ejecutarQueryServicioConPrestacion' : false,
	// APIs
	'consultarApiAfiliado' : true,
	'consultarApiAfiliadoCasoPositivo' : false,
	'consultarApiPrestadorSalud' : false,
	'consultarApiPrestadorSaludCasoPositivo' : false,
	'consultarApiPrestadorSaludServicios' : false,
	'consultarApiPrestadorSaludServiciosCasoPositivo' : false,
	// Querys
	'ejecutarQueryDoctor' : false,
	// APIs
	'consultarApiPrestadorSaludDoctores' : false,
	'consultarApiPrestadorSaludDoctoresCasoPositivo' : false,
	'consultarApiAutorizacionPortalValidarCobertura' : false,
	'consultarApiAutorizacionPortalValidarCoberturaCasoPositivo' : false,
	'consultarApiAutorizacionPortalCamposRequeridos' : false,
	'consultarApiAutorizacionPortalCamposRequeridosCasoPositivo' : false,
	// Querys
	'ejecutarQueryDiagnostico' : false,
	// APIs
	'consultarApiConsultarDiagnosticos' : false,
	'consultarApiConsultarDiagnosticosCasoPositivo' : false,
	'consultarApiAutorizacionPortalIngresar' : false,
	'consultarApiAutorizacionPortalIngresarCasoPositivo' : false,
	// Querys
	'ejecutarQueryProcedimientoPorPrestador' : false,
	'ejecutarQueryPrestacionNoContratada' : false,
	// APIs
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientos' : false,
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo' : false,
	'consultarApiConsultarProcedimientos' : false,
	'consultarApiConsultarProcedimientosCasoPositivo' : false,
	'consultarApiAutorizacionPortalTarifaProcedimiento' : false,
	'consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo' : false,
	'consultarApiAutorizacionPortalAutorizar' : false,
	'consultarApiAutorizacionPortalAutorizarCasoPositivo' : false,
	'consultarApiAutorizacionPortalAnular' : false,
	'consultarApiAutorizacionPortalAnularCasoPositivo' : false,
	// Parametros Caso Modelo
	'numeroAfiliado' : '1234567X'], FailureHandling.STOP_ON_FAILURE);