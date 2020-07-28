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
import java.util.Map

import javax.swing.JOptionPane

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.RequestObject
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
import helper.QueryTemplate
import internal.GlobalVariable as GlobalVariable

//********************
// Data Base Variables
//********************
DBConnection dbConnection = null;

//*****************
// Helper Variables
//*****************
CommonAction commonAction = CommonAction.getUniqueIntance();

//***************
// APIs Variables
//***************
String numeroAfiliado = null;
String tipoAfiliadoBD = null;
ArrayList<Map<String, String>> responseContent = null;
ResponseObject responseObject = null;
Map<String, String> queryResult = null;

try {
	
	// Instanciamos la clase de conexion a la base de datos
	dbConnection = DBConnection.getDBConnectionUniqueIntance();
	
	if (tipoAfiliado.toString().equals(Keyword.AFILIADO_MPP.value)) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s) 
		tipoAfiliado = QueryTemplate.afiliadoMPP.render().toString();
		
		println tipoAfiliado;
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPP", tipoAfiliado);
	}
	else if (tipoAfiliado.toString().equals(Keyword.AFILIADO_PBS.value)) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		tipoAfiliado = QueryTemplate.afiliadoPBS.render().toString();
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoPBS.remove("conditions");
		
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoPBS", tipoAfiliado);
	
	}
	else if (tipoAfiliado.toString().equals(Keyword.AFILIADO.value)) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		tipoAfiliado = QueryTemplate.afiliadoMPPoPBS.render().toString();
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		QueryTemplate.afiliadoPBS.remove("conditions");
		QueryTemplate.afiliadoMPPoPBS.remove("afiliadoMPP");
		QueryTemplate.afiliadoMPPoPBS.remove("afiliadoPBS");
		
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPPoPBS", tipoAfiliado);
	}
	
	String nombreAfiliadoBD = "";
	String codigoCobertura = "";
	String generoAfiliado = "";
	String numeroDocumentoAfiliado = "";
	
	numeroAfiliado = queryResult.get("NATIDE");
	
	// Primer Nombre
	if(!queryResult.get("NATPRINOM").trim().isEmpty()){
		
		nombreAfiliadoBD += queryResult.get("NATPRINOM").trim() + " ";
	}
	
	// Segundo Nombre
	if(!queryResult.get("NATSEGNOM").trim().isEmpty()){
		
		nombreAfiliadoBD += queryResult.get("NATSEGNOM").trim() + " ";
	}
	
	// Primer Aprellido
	if(!queryResult.get("NATPRIAPE").trim().isEmpty()){
		
		nombreAfiliadoBD += queryResult.get("NATPRIAPE").trim() + " ";
	}
	
	// Segundo Apellido
	if(!queryResult.get("NATSEGAPE").trim().isEmpty()){
		
		nombreAfiliadoBD += queryResult.get("NATSEGAPE").trim() + " ";
	}
	
	codigoCobertura = queryResult.get("MPLCOD");
	generoAfiliado = queryResult.get("NATSEX");
	tipoAfiliadoBD = queryResult.get("PRONOM");
	numeroDocumentoAfiliado = queryResult.get("NATNUMIDE");
	
	println "\n\n" + "Numero Afiliado: " + numeroAfiliado + "\n" + "Genero Afiliado: " + generoAfiliado + "\n" + "Tipo Afiliado: " + tipoAfiliadoBD  + "\n" + "Numero Documento Afiliado: " + numeroDocumentoAfiliado + "\n\n";
	
	/*
	 * 
	 * Consulta Prestador Servicio
	 * 
	 * IPSCODSUP, --Codigo de Prestador
	 * MPLCOD, -- Codigo de Plan del Afiliado
	 * SERIPSCOD -- Codigo de Servicio
	 * 
	 * */
	
	String estadoPrestador = 1;
	
	// Agregamos la(s) llave(s) y valor(es) al String Template
	QueryTemplate.prestadorServicio.add("codigoCobertura", codigoCobertura);
	QueryTemplate.prestadorServicio.add("generoAfiliado", generoAfiliado);
	QueryTemplate.prestadorServicio.add("estadoPrestador", estadoPrestador);
	QueryTemplate.prestadorServicio.add("servicioConsulta", servicioConsulta);
	
	// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
	// Ejecutamos la consulta y obtenemos los resultados
	queryResult = dbConnection.executeQueryAndGetResult("prestadorServicio", QueryTemplate.prestadorServicio.render().toString());
	
	// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
	QueryTemplate.prestadorServicio.remove("codigoCobertura");
	QueryTemplate.prestadorServicio.remove("generoAfiliado");
	QueryTemplate.prestadorServicio.remove("estadoPrestador");
	QueryTemplate.prestadorServicio.remove("servicioConsulta");
	
	String codigoPrestadorSalud = null;
	String codigoServicioPrestadorSalud;
	String nombrePrestadorBD = null;
	String nombreServicioBD = null;
	String codigoSucursal = null;
	
	//IPSCODSUP
	codigoPrestadorSalud = queryResult.get("IPSCODSUP");
	
	//SERIPSCOD
	codigoServicioPrestadorSalud = queryResult.get("SERIPSCOD");
	
	//IPSNOM
	nombrePrestadorBD = queryResult.get("IPSNOM");
	
	// SERIPSNOM
	nombreServicioBD = queryResult.get("SERIPSNOM");
	
	//IPSSUCCOD
	codigoSucursal = queryResult.get("IPSSUCCOD");
	
	println "\n\n" + 
			"Codigo Cobertura: " + codigoCobertura + "\n" +
			"Codigo Prestador Salud: " + codigoPrestadorSalud + "\n" +
			"Codigo De Servicio De Prestador Salud: " + codigoServicioPrestadorSalud +  "\n" +
			"Nombre Prestador: " + nombrePrestadorBD + "\n" + 
			"Nombre Servicio: " + nombreServicioBD + "\n" + 
			"Codigo Sucursal: " + codigoSucursal +
			"\n\n";
	
	//*********************************
	// API de consulta de # de afiliado
	//*********************************
	
	// Consultamos el API
	responseObject = WS.sendRequestAndVerify(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]), FailureHandling.STOP_ON_FAILURE);
	
	responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado])), Keyword.METHOD_GET.value);
	
	println "\n" + "Informacion del Afiliado" + "\n";
	
	for(Map<String, String> APIsconsultContent : responseContent){
		
		for(String key : APIsconsultContent.keySet()){
			
			println key + " : " + APIsconsultContent.get(key);
		}
	}
	
	println "\n";
	
	verifyCodeAndService:
	for(int i = 0; responseContent.size(); i++){
		
		if (responseContent.get(i).get(Keyword.KEY_IDENTIFICACION.value).equals(numeroDocumentoAfiliado)) {
			
			KeywordUtil.markPassed(String.valueOf("El API /api/Afiliado presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
			
			break verifyCodeAndService;
		}
			
		if ( i == (responseContent.size() - 1) ) {
			
			KeywordUtil.markFailed(String.valueOf("El API /api/Afiliado no presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
		}
	}
	
	//*******************************
	// API de consulta de # Prestador
	//*******************************
	
	responseObject = WS.sendRequestAndVerify(findTestObject('HealthProvider/PrestadorSalud', ['descripcion' : codigoPrestadorSalud]), FailureHandling.STOP_ON_FAILURE);
	
	String nombrePrestadorAPI = null;
	
	responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('HealthProvider/PrestadorSalud', ['descripcion' : codigoPrestadorSalud])), Keyword.METHOD_GET.value);
	
	for (Map<String, String> mapKeyAndValue : responseContent) {
		
		nombrePrestadorAPI = mapKeyAndValue.get(Keyword.KEY_NOMBRE.value);
	}
	
	// Validamos el nombre, desde el API Vs Base de Datos
	if (nombrePrestadorAPI.equals(nombrePrestadorBD)) {
		
		KeywordUtil.markPassed(String.valueOf("El API presento el nombre del prestador ${nombrePrestadorAPI} y la Base de Datos ${nombrePrestadorBD}"));
		 
	}else{
	
		KeywordUtil.markFailed(String.valueOf("El API presento el nombre del prestador ${nombrePrestadorAPI} y la Base de Datos ${nombrePrestadorBD}"));
	}
	
	//******************************************
	// API de consulta de # Prestador Servicios
	//******************************************
	
	responseObject = WS.sendRequestAndVerify(findTestObject('HealthProvider/PrestadorSaludServicios', ['codigoPrestadorSalud' : codigoPrestadorSalud]), FailureHandling.STOP_ON_FAILURE);
	
	responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('HealthProvider/PrestadorSaludServicios', ['codigoPrestadorSalud' : codigoPrestadorSalud])), Keyword.METHOD_GET.value);
	
	verifyCodeAndService:
	for(int i = 0; responseContent.size(); i++){
		
		if (responseContent.get(i).get(Keyword.KEY_CODIGO.value).equals(codigoServicioPrestadorSalud)
			&& responseContent.get(i).get(Keyword.KEY_DESCRIPCION.value).equals(nombreServicioBD)) {
			
			KeywordUtil.markPassed(String.valueOf("El API /api/PrestadorSalud/Servicios mediante la consulta del codigo prestador ${codigoPrestadorSalud} contiene el el codigo ${codigoServicioPrestadorSalud} con el servicio ${nombreServicioBD}."));
			
			break verifyCodeAndService;
		}
			
		if ( i == (responseContent.size() - 1) ) {
			
			KeywordUtil.markFailed(String.valueOf("El API /api/PrestadorSalud/Servicios mediante la consulta del codigo prestador ${codigoPrestadorSalud} no contiene el el codigo ${codigoServicioPrestadorSalud} con el servicio ${nombreServicioBD}."));
		}
	}
	
	
	//**********************************************************
	// API de consulta de Autorizacion Portal Validar Cobertura
	//**********************************************************
	
	responseObject = WS.sendRequestAndVerify(findTestObject('Authorization/AutorizacionPortalValidarCobertura', [
		'codigoUsuario' : numeroAfiliado,
		'idInteraccion' : '0',
		'codigoPrestadorSalud' : codigoPrestadorSalud,
		'codigoSucursalPrestadorSalud' : codigoSucursal,
		'numeroAfiliado' : numeroAfiliado,
		'codigoServicio' : codigoServicioPrestadorSalud]), FailureHandling.STOP_ON_FAILURE);
	
	String idInteraccion = responseObject.getResponseText().trim();
	
	println "\n\n" + "idInteraccion: " + idInteraccion + "\n\n";
	
	println "\n\n" + "Status API Code: " + responseObject.getStatusCode() + "\n\n";
	
	if (responseObject.getStatusCode() == 200) {
		
		KeywordUtil.markFailed(String.valueOf("El Web Service: ​/api​/Autorizacion​/Portal​/ValidarCobertura genero el Id De Interaccion ${idInteraccion} y el API mostro el codigo de respuesta ${responseObject.getStatusCode()}."));
	}
	else{
		
		KeywordUtil.markPassed(String.valueOf("La cobertura lanzo el mensaje: ${idInteraccion} y el API mostro el codigo de respuesta ${responseObject.getStatusCode()}."));
	}
	
} catch (Exception e) {

	//KeywordUtil.markError(e.getMessage());
}
finally{
	
	if (dbConnection.getResultSet() != null) {
		
		dbConnection.getResultSet().close()
	}
	
	if (dbConnection.getStatement() != null) {
		
		dbConnection.getConnection().close()
	}
	
	if (dbConnection.getConnection() != null) {
		
		dbConnection.getConnection().close()
	}
}