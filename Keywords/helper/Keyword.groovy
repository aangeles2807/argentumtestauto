package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public enum Keyword {

	// ************
	// HTTP Methods
	// ************

	METHOD_GET("GET"),

	// *****************
	// API Responses Key
	// *****************

	KEY_CODIGO("Codigo"),
	KEY_DESCRIPCION("Descripcion"),
	KEY_NUMERO("Numero"),
	KEY_NOMBRE("Nombre"),
	KEY_CODIGOTIPOIDENTIFICACION("CodigoTipoIdentificacion"),
	KEY_DESCRIPCIONTIPOIDENTIFICACION("DescripcionTipoIdentificacion"),
	KEY_IDENTIFICACION("Identificacion"),
	KEY_NUMEROSEGURIDADSOCIAL("NumeroSeguridadSocial"),
	KEY_CODIGOTIPOCLIENTE("CodigoTipoCliente"),
	KEY_DESCRIPCIONTIPOCLIENTE("DescripcionTipoCliente"),
	KEY_COLORTIPOCLIENTE("ColorTipoCliente"),
	KEY_CODIGOSEGMENTOCLIENTE("CodigoSegmentoCliente"),
	KEY_DESCRIPCIONSEGMENTOCLIENTE("DescripcionSegmentoCliente"),
	KEY_CODIGOPARENTESCO("CodigoParentesco"),
	KEY_DESCRIPCIONPARENTESCO("DescripcionParentesco"),
	KEY_EDAD("Edad"),
	KEY_SEXO("Sexo"),
	KEY_VIENEDETRASPASO("VieneDeTraspaso"),
	KEY_TIENEPBS("TienePBS"),

	// ****************
	// Query Conditions
	// ****************

	AFILIADO_MPP_ACTIVO("AND Trim(ben.afibenestcod) IN ('A') "),
	AFILIADO_MPP_INACTIVO("AND Trim(ben.afibenestcod) <> ('A') "),
	AFILIADO_PBS_ACTIVO("AND trim(hij.afihijestcod) IN ('A') "),
	AFILIADO_PBS_INACTIVO("AND trim(hij.afihijestcod) <> ('A') "),
	AFILIADO_MPP_CON_PBS("AND nat.natide IN (SELECT natide FROM tabhij pbs WHERE pbs.natide = nat.natide) "),
	AFILIADO_MENOR_EDAD("AND round(( sysdate - nat.natfecnac) /365.242199,0) < 18 "),
	AFILIADO_MENOR_7("AND round(( sysdate - nat.natfecnac) /365.242199,0) < 18 "),
	AFILIADO_MAYOR_EDAD("AND round(( sysdate - nat.natfecnac) /365.242199,0) >= 18 "),
	AFILIADO_CONTRATO_ACTIVO("AND trim(crt.AFICRTESTCOD) not in ('14', '8') "),
	AFILIADO_CONTRATO_INACTIVO("AND trim(crt.AFICRTESTCOD) in ('14', '8') "),
	AFILIADO_MPP_CON_COBERTURA_INMEDIATA("AND crt.crtcobinm = 1 "),
	AFILIADO_MPP_SIN_COBERTURA_INMEDIATA("AND crt.crtcobinm <> 1 "),
	AFILIADO_MPP_SUSPENDIDO_PBS_ACTIVO("AND hij.natide not in (SELECT ben.natide FROM tabcrt crt, tabsbc sbc, tabben ben WHERE 1=1 AND crt.crtcon = sbc.crtcon AND sbc.sbccon = ben.sbccon AND ben.natide = hij.natide AND  trim(crt.AFICRTESTCOD) in ('14','8')) "),
	SERVICIO_CONSULTA("AND trim(tips.seripscod) IN ('08', '09', '10', '11', '12', '40') "),
	SERVICIO_LABORATORIO("AND trim(tips.seripscod) IN ('31', '32', '33') "),
	SERVICIO_ESTUDIOS_ESPECIALES("AND trim(tips.seripscod) IN ('20') "),
	SERVICIO_RAYOS_X("AND trim(tips.seripscod) IN ('46') "),
	SERVICIO_PSIQUIATRIA("AND trim(tips.seripscod) IN ('43') "),
	SERVICIO_ODONTOLOGIA("AND trim(tips.seripscod) IN ('65') "),
	SERVICIO_TERAPIAS_FISICAS("AND trim(tips.seripscod) IN ('72') "),
	SERVICIO_VACUNAS("AND trim(tips.seripscod) IN ('71') "),
	
	// *******************
	// Query to be execute
	// *******************

	AFILIADO_MPP("MPP"),
	AFILIADO_PBS("PBS"),
	AFILIADO("MPP o PBS"),
	PRESTADOR_SERVICIO("Prestador Servicio"),
	DIAGNOSTICO("Diagnostico"),
	PROCEDIMIENTO_POR_PRESTADOR("Procedimiento Por Prestador"),

	// ******
	// Report
	// ******

	TEST_NAME("Test Name"),

	// Attribute
	public String value;

	// Constructor
	private Keyword(String value){

		this.value = value;
	}
}