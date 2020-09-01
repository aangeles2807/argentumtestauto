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

import helper.Keyword
import internal.GlobalVariable as GlobalVariable

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
	'ejecutarQueryCapturaAfiliadoMPP' : false,
	'ejecutarQueryCapturaAfiliadoPBS' : false,
	'ejecutarQueryDiagnostico' : false,
	'ejecutarQueryProcedimientoPorPrestador' : false,
	'consultarApiAutorizacionPortalValidarCoberturaCasoPositivo' : false,
	'consultarApiAutorizacionPortalCamposRequeridos' : false,
	'consultarApiConsultarDiagnosticos' : false,
	'consultarApiAutorizacionPortalIngresar' : false,
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientos' : false,
	'consultarApiConsultarProcedimientos' : false,
	'consultarApiAutorizacionPortalTarifaProcedimiento' : false,
	'consultarApiAutorizacionPortalAutorizar' : false,
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_INACTIVO_SIN_PBS.value,
	'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_INACTIVO_SIN_MPP.value,
	'servicioConsulta' : Keyword.SERVICIO_CONSULTA_AMBULATORIA.value], FailureHandling.STOP_ON_FAILURE);