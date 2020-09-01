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

// Condiciones de servicios
List<String> servicios = Arrays.asList(
	//Keyword.SERVICIO_TERAPIAS_FISICAS.value,
	//Keyword.SERVICIO_SONOGRAFIA.value,
	Keyword.SERVICIO_RAYOS_X.value,
	//Keyword.SERVICIO_PATOLOGIA.value,
	//Keyword.SERVICIO_ODONTOLOGIA.value,
	Keyword.SERVICIO_LABORATORIO.value,
	Keyword.SERVICIO_ESTUDIOS_ESPECIALES.value,
	Keyword.SERVICIO_EMERGENCIA_49.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_1.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_2.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_3.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_4.value,
	//Keyword.SERVICIO_PSIQUIATRIA.value
);

// Valor aleatorio
int valorAleatorio = CommonAction.getUniqueIntance().getRandomNumber( servicios.size() );

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
	'ejecutarQueryCapturaAfiliadoMPP' : false,
	'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
	'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value  + Keyword.AFILIADO_RECIEN_NACIDO_PBS.value,
	'servicioConsulta' : servicios.get(valorAleatorio)], FailureHandling.STOP_ON_FAILURE);