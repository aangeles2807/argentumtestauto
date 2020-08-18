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
	'ejecutarQueryCapturaAfiliadoPBS' : false,
	'ejecutarQueryCapturaAfiliadoMPP' : false,
	'consultarApiAutorizacionPortalAutorizarCasoPositivo': false,
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value + Keyword.AFILIADO_MASCULINO.value,
	'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value + Keyword.AFILIADO_MASCULINO.value,
	'generoAfiliado' : Keyword.KEY_FEMENINO.value,
	'codigoPrestadorSalud': '04912',
	//'servicioConsulta' : Keyword.SERVICIO_PATOLOGIA.value,
	'condicionProcedimiento': Keyword.PROCEDIMIENTO_CODIGO.value + "'881401'"], FailureHandling.STOP_ON_FAILURE);