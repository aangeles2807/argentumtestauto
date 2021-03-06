import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.*;
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.Date

import javax.swing.JOptionPane

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.JsonSlurper
import helper.CommonAction
import helper.DBConnection
import helper.Keyword
import internal.GlobalVariable as GlobalVariable

// Condiciones de servicios
List<String> servicios = Arrays.asList(
	Keyword.SERVICIO_TERAPIAS_FISICAS.value,
	Keyword.SERVICIO_SONOGRAFIA.value,
	Keyword.SERVICIO_RAYOS_X.value,
	Keyword.SERVICIO_PATOLOGIA.value,
	Keyword.SERVICIO_ODONTOLOGIA.value,
	Keyword.SERVICIO_ESTUDIOS_ESPECIALES.value,
	Keyword.SERVICIO_PSIQUIATRIA.value
);

// Valor aleatorio
int valorAleatorio = CommonAction.getUniqueIntance().getRandomNumber( servicios.size() );

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
	// Querys
	'ejecutarQueryCapturaAfiliadoMPP' : true,
	'ejecutarQueryCapturaAfiliadoPBS' : false,
	'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
	'ejecutarQueryPrestadorServicio' : true,
	'ejecutarQueryPrestadorFueradeRed' : false,
	'ejecutarQueryServicioConPrestacion' : false,
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
	// Condiciones de los Querys
	'condicionAfiliadoMPP' : Keyword.AFILIADO_MPP_ACTIVO.value + Keyword.AFILIADO_MPP_SIN_COBERTURA_INMEDIATA.value,
	'servicioConsulta' : servicios.get(valorAleatorio)], FailureHandling.STOP_ON_FAILURE);