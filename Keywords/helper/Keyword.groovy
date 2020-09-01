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
	KEY_FEMENINO("F"),
	KEY_MASCULINO("M"),
	KEY_ID("Id"),

	// ****************
	// Query Conditions
	// ****************

	AFILIADO_MPP_ACTIVO("AND trim(ben.afibenestcod) = 'A' "),
	AFILIADO_MPP_INACTIVO("AND trim(ben.afibenestcod) <> ('A') AND NOT EXISTS (select hij2.afihijestcod from TABHIJ hij2 where trim(hij2.afihijestcod) = ('A') AND ben.natide = hij2.natide) "),
	AFILIADO_PBS_ACTIVO("AND trim(hij.afihijestcod) = 'A' "),
	AFILIADO_PBS_INACTIVO("AND trim(hij.afihijestcod) <> ('A') "),
	AFILIADO_MPP_CON_PBS("AND nat.natide IN (SELECT natide FROM tabhij pbs WHERE pbs.natide = nat.natide) "),
	AFILIADO_MENOR_EDAD("AND round(( sysdate - nat.natfecnac) /365.242199,0) < 18 "),
	AFILIADO_MENOR_7_AÑOS("AND round(( sysdate - nat.natfecnac) /365.242199,0) < 7 "),
	AFILIADO_MAYOR_EDAD("AND round(( sysdate - nat.natfecnac) /365.242199,0) >= 18 "),
	AFILIADO_MPP_CON_COBERTURA_INMEDIATA("AND crt.crtcobinm = 1 "),
	AFILIADO_MPP_SIN_COBERTURA_INMEDIATA("AND crt.crtcobinm <> 1 "),
	AFILIADO_MPP_SUSPENDIDO_PBS_ACTIVO("AND hij.natide not in (SELECT ben.natide FROM tabcrt crt, tabsbc sbc, tabben ben WHERE 1=1 AND crt.crtcon = sbc.crtcon AND sbc.sbccon = ben.sbccon AND ben.natide = hij.natide AND trim(crt.AFICRTESTCOD) in ('14', '8') ) "),
	AFILIADO_AUTORIZACION_1_ANO("ADD_MONTHS(sysdate,-13)"),
	AFILIADO_AUTORIZACION_6_MESES("ADD_MONTHS(sysdate,-6)"),
	AFILIADO_AUTORIZACION_30_DIAS("(SYSDATE - 30)"),
	AFILIADO_AUTORIZACION_5_DIAS("(SYSDATE - 5)"),
	AFILIADO_AUTORIZACION_1_DIAS("(SYSDATE - 1)"),
	AFILIADO_AUTORIZACION_SYSDATE("sysdate"),
	AFILIADO_EXCLUSIVO("AND mpl.mplcod like 'EX%' "),
	AFILIADO_MASCULINO("AND nat.NATSEX = 'M' "),
	AFILIADO_FEMENINO("AND nat.NATSEX = 'F' "),
	AFILIADO_RECIEN_NACIDO_PBS("AND hij.hijesrn = 1 "),
	AFILIADO_RECIEN_NACIDO_MPP_CON_PBS("AND nat.natide IN (SELECT natide FROM tabhij pbs WHERE pbs.natide = nat.natide AND pbs.hijesrn = 1) "),
	AFILIADO_MPP_COVERTURA_VENCIDA("AND EXISTS ( SELECT * FROM  tabben ben2, tabcrt crt2 WHERE 1 = 1 AND crt2.crtcon = ben2.crtcon AND ben2.natide = ben.natide AND SYSDATE not BETWEEN crt2.crtinivig and crt2.crtfinvig )"),
	AFILIADO_PBS_COVERTURA_VENCIDA("AND EXISTS ( SELECT * FROM  tabhij hij2, tabcon con2 WHERE 1 = 1 AND con2.concon = hij2.concon AND hij2.natide = hij.natide AND SYSDATE NOT BETWEEN con2.coninivig AND con2.confinvig )"),
	SERVICIO_NO_LABORATORIO("AND trim(tips.seripscod) NOT IN ('31', '32', '33') "),
	SERVICIO_LABORATORIO("AND trim(tips.seripscod) = '33' AND trim(IPS.SER_ESP_CODIGO) = '89' and trim(ips.tipinscod) = '16'"),
	SERVICIO_ESTUDIOS_ESPECIALES("AND trim(tips.seripscod) = '20' AND trim(IPS.SER_ESP_CODIGO) = '888' AND trim(ips.tipinscod) = '02'"),
	SERVICIO_RAYOS_X("AND trim(tips.seripscod) = '46' AND trim(IPS.SER_ESP_CODIGO) = '888' AND trim(ips.tipinscod) = '09'"),
	SERVICIO_PSIQUIATRIA("AND trim(tips.seripscod) = '11' AND trim(IPS.SER_ESP_CODIGO) IN ('146', '951') AND trim(ips.tipinscod) IN ('18')"),
	SERVICIO_ODONTOLOGIA("AND trim(tips.seripscod) = '65' AND trim(IPS.SER_ESP_CODIGO) IN ('111','112','113','114') AND trim(ips.tipinscod) = '05'"),
	SERVICIO_TERAPIAS_FISICAS("AND trim(tips.seripscod) = '72' AND trim(IPS.SER_ESP_CODIGO) IN ('03','96') AND ips.tipinscod in('18','02')"),
	SERVICIO_VACUNAS("AND trim(tips.seripscod) = '108' AND trim(ips.tipinscod) = '25'"),
	SERVICIO_EMERGENCIA_49("AND trim(tips.seripscod) IN ('49') AND trim(IPS.SER_ESP_CODIGO) = '972' AND trim(ips.tipinscod) = '08'"),
	//SERVICIO_EMERGENCIA("AND trim(tips.seripscod) IN (select trim(seripscod) from tabserips where serclacod = 1) AND trim(IPS.SER_ESP_CODIGO) = '972' AND trim(ips.tipinscod) = '08'"),
	SERVICIO_EMERGENCIA_TRIAGE_1("AND trim(tips.seripscod) = '126' AND trim(IPS.SER_ESP_CODIGO) = '972' AND trim(ips.tipinscod) = '08'"),
	SERVICIO_EMERGENCIA_TRIAGE_2("AND trim(tips.seripscod) = '127' AND trim(IPS.SER_ESP_CODIGO) = '972' AND trim(ips.tipinscod) = '08'"),
	SERVICIO_EMERGENCIA_TRIAGE_3("AND trim(tips.seripscod) = '128' AND trim(IPS.SER_ESP_CODIGO) = '972' AND trim(ips.tipinscod) = '08'"),
	SERVICIO_EMERGENCIA_TRIAGE_4("AND trim(tips.seripscod) = '129' AND trim(IPS.SER_ESP_CODIGO) = '972' AND trim(ips.tipinscod) = '08'"),
	SERVICIO_NO_FARMACIA("AND trim(tips.seripscod) NOT IN ('35') "),
	SERVICIO_FARMACIA("AND trim(tips.seripscod) IN ('35') AND trim(IPS.SER_ESP_CODIGO) = '09' "),
	SERVICIO_PATOLOGIA("AND trim(tips.seripscod) = '32' AND trim(IPS.SER_ESP_CODIGO) = '130' AND trim(ips.tipinscod) = '18'"),
	PRESTADOR_CENTRO_INSTITUCION("AND Trim(ips.EMPTIPEMP) = 'J' "),
	PRESTADOR_CENTRO_NO_INSTITUCION("AND Trim(ips.EMPTIPEMP) = 'N' "),
	//PRESTADOR_NO_VIGENTE("AND Trim(ips.ipsestado) <> '1' "),
	PRESTADOR_EXCLUSIVO ("AND trim(ips.ipsnomcor)  = 'EXC'"),
	PRESTADOR_DIFERENTE_AL_ULTIMO("AND ser.IPSCODSUP <> "),
	PROCEDIMIENTO_MATERNIDAD("AND TRIM(TATE.TIPORICOD) = '2' "),
	PROCEDIMIENTO_MATERNIDAD_JOIN("JOIN Tabate TATE ON mpl.MPLCOD = TATE.MPLCOD "),
	PROCEDIMIENTO_MA("AND TRIM(PRE.pre_pre_codigo) like 'MA%' "),
	PROCEDIMIENTO_CODIGO("AND TRIM(PRE.pre_pre_codigo) = "),
	CONDICION_FECHA_AUTORIZACION_CONTRATO_INACTIVO_MPP("NOT BETWEEN crt.crtinivig AND crt.crtfinvig AND NOT EXISTS (select hij2.afihijestcod from TABHIJ hij2 where trim(hij2.afihijestcod) = ('A') AND ben.natide = hij2.natide) "),
	CONDICION_FECHA_AUTORIZACION_CONTRATO_INACTIVO_PBS("NOT BETWEEN con.coninivig AND con.confinvig"),
	SERVICIO_CONSULTA_AMBULATORIA("AND trim(tips.seripscod) = '11' AND trim(IPS.SER_ESP_CODIGO) = '13' AND trim(ips.tipinscod) = '18' "),
	SERVICIO_SONOGRAFIA("AND trim(tips.seripscod) = '20' AND trim(IPS.SER_ESP_CODIGO) = '888' AND trim(ips.tipinscod) = '02'"),
	AFILIADO_PBS_CON_MPP_VENCIDA("AND EXISTS ( SELECT * FROM  tabben ben2, tabcrt crt2 WHERE 1 = 1 AND crt2.crtcon = ben2.crtcon AND ben2.natide = hij.natide AND SYSDATE not BETWEEN crt2.crtinivig and crt2.crtfinvig )"),
	AFILIADO_MPP_ACTIVO_SIN_PBS("AND trim(ben.afibenestcod) = 'A' AND NOT EXISTS (SELECT hij2.afihijestcod FROM TABHIJ hij2 where trim(hij2.afihijestcod) = ('A') AND ben.natide = hij2.natide) "),
	AFILIADO_MPP_INACTIVO_SIN_PBS("AND trim(ben.afibenestcod) <> 'A' AND NOT EXISTS (SELECT hij2.afihijestcod FROM TABHIJ hij2 where trim(hij2.afihijestcod) = ('A') AND ben.natide = hij2.natide) "),
	AFILIADO_PBS_ACTIVO_SIN_MPP("AND trim(hij.afihijestcod) = 'A' AND NOT EXISTS (SELECT natide FROM tabben ben WHERE ben.natide = hij.natide) "),
	AFILIADO_PBS_INACTIVO_SIN_MPP("AND trim(hij.afihijestcod) <> 'A' AND NOT EXISTS (SELECT natide FROM tabben ben WHERE ben.natide = hij.natide) "),
	AFILIADO_MPP_CON_MAS_DE_2_COBERTURAS("AND EXISTS( SELECT COUNT(ben2.natide), ben2.natide FROM tabben ben2, tabcrt crt2 WHERE 1 = 1 AND trim(ben.afibenestcod) = 'A' AND ben2.crtcon = crt2.crtcon AND ben2.natide = ben.natide GROUP BY ben2.natide HAVING COUNT(*) > 1 )"),
	AFILIADO_PBS_CON_MAS_DE_2_COBERTURAS("AND EXISTS ( SELECT COUNT(hij2.natide), hij2.natide FROM tabhij hij2, tabcon con2 WHERE 1 = 1 AND hij2.concon = con2.concon AND hij2.natide = hij.natide GROUP BY hij2.natide HAVING COUNT(*) > 1 )"),
	SERVICIO_PyP("AND Trim(tips.seripscod) IN ('01','32','39','76','99','100','103','104','105','106','107','108','113','118','119') AND trim(ips.ser_esp_codigo) = '972' AND ips.tipinscod = '08' "),

	// *********************
	// Portal Autorizaciones
	// *********************

	PORTAL_AUTORIZACIONES_URL("https://appenlinea-qa.azurewebsites.net/Autorizaciones"),
	PORTAL_AUTORIZACIONES_USUARIO("PS18815"),
	PORTAL_AUTORIZACIONES_CONTRASENA("arielDJ*123"),
	ATTRIBUTE_STYLE("style"),
	ATTRIBUTE_VALUE("value"),
	ATTRIBUTE_HEIGHT("height"),
	ATTRIBUTE_WIDTH("width"),
	XPATH_SELECTOR("xpath"),
	CSS_SELECTOR("css"),
	JAVASCRIPT_CLICK("JavaScript Click"),
	JAVASCRIPT_SCROLL("JavaScript Scroll"),
	JAVASCRIPT_HIGHLIGHT("JavaScript Highlight"),

	// ******
	// Report
	// ******

	TEST_NAME("Test Name"),
	
	// ****************************************
	// Authorization Http Body Content Template 
	// ****************************************

	AUTHORIZATION_HTTP_CONTENT_BODY("{\"Codigo\": \"%s\", \"Habitacion\": \"%s\", \"Cantidad\": %s, \"Valor\": %s, \"ValorConcesion\": %s, \"ValorExcepcion\": %s}"),

	// Attribute
	public String value;

	// Constructor
	private Keyword(String value){

		this.value = value;
	}
}