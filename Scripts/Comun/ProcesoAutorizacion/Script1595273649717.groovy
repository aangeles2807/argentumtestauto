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
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		
		// Ejecutamos la consulta y obtenemos los resultados
		//resultSet = statement.executeQuery(tipoAfiliado);
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
		//resultSet = statement.executeQuery(tipoAfiliado);
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
		//resultSet = statement.executeQuery(tipoAfiliado);
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPPoPBS", tipoAfiliado);
	}
	
	String nombreAfiliadoBD = "";
	String codigoCobertura = "";
	String generoAfiliado = "";
	String numeroDocumentoAfiliado = "";
	
	numeroAfiliado = queryResult.get("NATIDE").trim();
	nombreAfiliadoBD = queryResult.get("NATPRINOM").trim() + " " + queryResult.get("NATSEGNOM").trim() + " " + queryResult.get("NATPRIAPE").trim() + " " + queryResult.get("NATSEGAPE").trim();
	codigoCobertura = queryResult.get("MPLCOD").trim();
	generoAfiliado = queryResult.get("NATSEX").trim();
	tipoAfiliadoBD = queryResult.get("PRONOM").trim();
	numeroDocumentoAfiliado = queryResult.get("NATNUMIDE").trim();
	
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
	//resultSet = statement.executeQuery(QueryTemplate.prestadorServicio.render().toString());
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
	
	
	//IPSCODSUP
	codigoPrestadorSalud = queryResult.get("IPSCODSUP").trim();
	
	//SERIPSCOD
	codigoServicioPrestadorSalud = queryResult.get("SERIPSCOD").trim();
	
	//IPSNOM
	nombrePrestadorBD = queryResult.get("IPSNOM").trim();
	
	// SERIPSNOM
	nombreServicioBD = queryResult.get("SERIPSNOM").trim();
	
	println "\n\n" + 
			"Codigo Cobertura: " + codigoCobertura + "\n" +
			"Codigo Prestador Salud: " + codigoPrestadorSalud + "\n" +
			"Codigo De Servicio De Prestador Salud: " + codigoServicioPrestadorSalud +  "\n" +
			"Nombre Prestador: " + nombrePrestadorBD + "\n" + 
			"Nombre Servicio: " + nombreServicioBD +
			"\n\n";
	
	//*********************************
	// API de consulta de # de afiliado
	//*********************************
	
	// Consultamos el API
	responseObject = WS.sendRequestAndVerify(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]), FailureHandling.STOP_ON_FAILURE);
	
	responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado])), Keyword.METHOD_GET.value);
	
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
		'codigoSucursalPrestadorSalud' : '0',
		'numeroAfiliado' : numeroAfiliado,
		'codigoServicio' : codigoServicioPrestadorSalud]), FailureHandling.STOP_ON_FAILURE);
	
	String idInteraccion = responseObject.getResponseText().trim();
	
	println "\n\n" + "idInteraccion: " + idInteraccion + "\n\n";
	
	/*
	 * Errores:
	 * 
	 * ORA-20001: Prestador no está activo.
	 * 
	 * */
	
	//**********************************************************
	// API de consulta de Autorizacion Portal Campos Requeridos
	//**********************************************************
	 
	responseObject = WS.sendRequestAndVerify( findTestObject('Authorization/AutorizacionPortalCamposRequeridos', [
		'codigoUsuario' : numeroAfiliado,
		'idInteraccion' : idInteraccion]) , FailureHandling.STOP_ON_FAILURE);
	
	// Validamos el codigo de respuesta (debe ser 200)
	if (responseObject.getStatusCode().equals(200)) {
		
		KeywordUtil.markPassed(String.valueOf("El API dio codigo de estado 200"));
		 
	}else{
	
		KeywordUtil.markFailed(String.valueOf("El API dio codigo de estado ${responseObject.getStatusCode()}"));
	}
	
	// Consulta Diagnostico
	
	// Agregamos la(s) llave(s) y valor(es) al String Template
	QueryTemplate.diagnostico.add("generoAfiliado", generoAfiliado);
	
	// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
	// Ejecutamos la consulta y obtenemos los resultados
	//resultSet = statement.executeQuery(QueryTemplate.diagnostico.render().toString());
	queryResult = dbConnection.executeQueryAndGetResult("diagnostico", QueryTemplate.diagnostico.render().toString());
	
	// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
	QueryTemplate.diagnostico.remove("generoAfiliado");
	
	String codigoDiagnosticoBD = null;
	String nombreDiagnosticoBD = null;
	
	// DIA_DIA_CODIGO
	codigoDiagnosticoBD = queryResult.get("DIA_DIA_CODIGO").trim();
	
	// DIA_DIA_DESCRIPCIO
	nombreDiagnosticoBD = queryResult.get("DIA_DIA_DESCRIPCIO").trim();
		
	println "\n\n" + "Codigo Diagnostico: " + codigoDiagnosticoBD + ", Nombre Diagnostico: " + nombreDiagnosticoBD + "\n\n";
	
	//******************************
	// API de Consultar Diagnosticos
	//******************************
	
	responseObject = WS.sendRequestAndVerify(findTestObject('Consult/ConsultarDiagnosticos', ['descripcion' : codigoDiagnosticoBD]), FailureHandling.STOP_ON_FAILURE);
	
	String nombreDiagnosticoAPI = null;
	
	responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('Consult/ConsultarDiagnosticos', ['descripcion' : codigoDiagnosticoBD])), Keyword.METHOD_GET.value);
	
	for (Map<String, String> mapKeyAndValue : responseContent) {
		
		nombreDiagnosticoAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
	}
	
	// Comparacion de descriipcion de diagnosticos
	
	if (nombreDiagnosticoAPI.equals(nombreDiagnosticoBD)) {
		  
		  KeywordUtil.markPassed(String.valueOf("El API presento la descripcion del diagnostico ${nombreDiagnosticoAPI} y la Base de Datos ${nombreDiagnosticoBD}"));
		   
	 }else{
	  
		  KeywordUtil.markFailed(String.valueOf("El API presento la descripcion del diagnostico ${nombreDiagnosticoAPI} y la Base de Datos ${nombreDiagnosticoBD}"));
	 }
	
	//**********************************************************
	// API de Autorizacion Portal Ingresar
	//**********************************************************
	
	SimpleDateFormat formatoFecha = new SimpleDateFormat("MM-dd-yyyy");	
	Date fecha = new Date();
	
	responseObject = WS.sendRequestAndVerify(findTestObject('Authorization/AutorizacionPortalIngresar', [
		'codigoUsuario' : numeroAfiliado,
		'idInteraccion' : idInteraccion,
		'fecha' : formatoFecha.format(fecha),
		'idDoctor' : '0',
		'telefono' : '8091112222',
		'email' : 'prueba@prueba.com',
		'codigoDiagnostico' : codigoDiagnosticoBD,
		'observacion' : nombreDiagnosticoBD]), FailureHandling.STOP_ON_FAILURE);
	 
	println "\n\n" + "API de Autorizacion Portal Ingresar: " + responseObject.getResponseText() + "\n\n";
	 
	 // Validamos el codigo de respuesta (debe ser 200)
	 if (responseObject.getStatusCode().equals(200)) {
		 
		 KeywordUtil.markPassed(String.valueOf("API de Autorizacion Portal Ingresar dio codigo de estado 200"));
		  
	 }else{
	 
		 KeywordUtil.markFailed(String.valueOf("API de Autorizacion Portal Ingresar dio codigo de estado ${responseObject.getStatusCode()}"));
	 }
	 
	 /*
	  * Errores:
	  * 
	  * ORA-20003: Prestacion no es apropiada para este paciente ORA-06512: at "EPS.AUTORIZACION_MEDICA_PKG", line 930
	  * 
	  * */
	 
	 /*
	  * Consulta Procedimiento Por Prestador
	  * 
	  * AND preser.seripscod = '11' --CODIGO DE SERVICIO
	  * AND ips.ipscodsup = '00188' -- CODIGO DE PRESTADOR
	  * AND mpl.mplcod = 'E0302' -- CODIGO DE PLAN
	  * 
	  * */
	 
	 //String codigoPrestacion = "890202";
	 //String descripcionPrestacionBD = "CONSULTA DE PRIMERA VEZ POR MEDICINA ESPECIALIZADA";
	 String codigoPrestacion = "";
	 String descripcionPrestacionBD = "";
	 
	 // Agregamos la(s) llave(s) y valor(es) al String Template
	 QueryTemplate.procedimientoPorPrestador.add("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
	 QueryTemplate.procedimientoPorPrestador.add("generoAfiliado", generoAfiliado);
	 QueryTemplate.procedimientoPorPrestador.add("codigoPrestadorSalud", codigoPrestadorSalud);
	 QueryTemplate.procedimientoPorPrestador.add("codigoCobertura", codigoCobertura);
	 
	 // Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
	 // Ejecutamos la consulta y obtenemos los resultados
	 //resultSet = statement.executeQuery(QueryTemplate.procedimientoPorPrestador.render().toString());
	 queryResult = dbConnection.executeQueryAndGetResult("procedimientoPorPrestador", QueryTemplate.procedimientoPorPrestador.render().toString());
	 
	 // Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
	 QueryTemplate.procedimientoPorPrestador.remove("codigoServicioPrestadorSalud");
	 QueryTemplate.procedimientoPorPrestador.remove("generoAfiliado");
	 QueryTemplate.procedimientoPorPrestador.remove("codigoPrestadorSalud");
	 QueryTemplate.procedimientoPorPrestador.remove("codigoCobertura");
	 
	 // CODIGO_PRESTACION
	 codigoPrestacion = queryResult.get("CODIGO_PRESTACION").trim();
	 
	 // DESCRIPCION_PRESTACION
	 descripcionPrestacionBD = queryResult.get("DESCRIPCION_PRESTACION").trim();
	 
	 println "\n\n" + "Codigo Prestacion: " + codigoPrestacion + ", Descripcion Prestacion: " + descripcionPrestacionBD + "\n\n";
	 
	 //**********************************************************************
	 // API de consulta de Autorizacion Portal Prestador Salud Procedimientos
	 //**********************************************************************
	  
	 responseObject = WS.sendRequestAndVerify(findTestObject('Authorization/AutorizacionPortalPrestadorSaludProcedimientos', [
		  'codigoUsuario' : numeroAfiliado,
		  'idInteraccion' : idInteraccion,
		  'descripcion' : codigoPrestacion]), FailureHandling.STOP_ON_FAILURE);
	  
	  String descripcionPrestacionAPI = null;
	  
	  responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('Authorization/AutorizacionPortalPrestadorSaludProcedimientos', [
		  'codigoUsuario' : numeroAfiliado,
		  'idInteraccion' : idInteraccion,
		  'descripcion' : codigoPrestacion])), Keyword.METHOD_GET.value);
	  
	  for (Map<String, String> mapKeyAndValue : responseContent) {
		  
		  descripcionPrestacionAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
	  }
	  
	  // Comparacion descripcion prestacion de base de datos Vs. API
	  if (descripcionPrestacionAPI.equals(descripcionPrestacionBD)) {
		  
		  KeywordUtil.markPassed(String.valueOf("El API presento la descripcion de la prestacion ${descripcionPrestacionAPI} y la Base de Datos ${descripcionPrestacionBD}"));
		   
	  }else{
	  
		  KeywordUtil.markFailed(String.valueOf("El API presento la descripcion de la prestacion ${descripcionPrestacionAPI} y la Base de Datos ${descripcionPrestacionBD}"));
	  }
	  
	  //********************************
	  // API de Consultar Procedimientos
	  //********************************
	  
	  responseObject = WS.sendRequestAndVerify(findTestObject('Consult/ConsultarProcedimientos', ['descripcion' : codigoPrestacion]), FailureHandling.STOP_ON_FAILURE);
	  
	  descripcionPrestacionAPI = null;
	  
	  responseContent = commonAction.getResponseContentIntoMap(commonAction.getRequestObjectURL(findTestObject('Consult/ConsultarProcedimientos', ['descripcion' : codigoPrestacion])), Keyword.METHOD_GET.value);
	  
	  for (Map<String, String> mapKeyAndValue : responseContent) {
		  
		  descripcionPrestacionAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
	  }
	  
	  // Comparacion de descriipcion de Procedimiento
	  if (descripcionPrestacionAPI.equals(descripcionPrestacionBD)) {
		  
		  KeywordUtil.markPassed(String.valueOf("El API presento la descripcion de la prestacion ${descripcionPrestacionAPI} y la Base de Datos ${descripcionPrestacionBD}"));
		   
	  }else{
	  
		  KeywordUtil.markFailed(String.valueOf("El API presento la descripcion de la prestacion ${descripcionPrestacionAPI} y la Base de Datos ${descripcionPrestacionBD}"));
	  }
	  
	  //************************************************
	  // API de Autorizacion Portal Tarifa Procedimiento
	  //************************************************
	  
	  String montoProcedimiento = null;
	  
	  responseObject = WS.sendRequestAndVerify(findTestObject('Authorization/AutorizacionPortalTarifaProcedimiento', [
		  'codigoUsuario' : numeroAfiliado,
		  'idInteraccion' : idInteraccion,
		  'codigoProcedimiento' : codigoPrestacion]), FailureHandling.STOP_ON_FAILURE);
	  
	  montoProcedimiento = responseObject.getResponseText();
	  
	  println "\n\n" + "Monto Procedimiento: " + montoProcedimiento + "\n\n";	  
	  
	  //**********************************************************
	  // API de Autorizacion Portal Autorizar: Investigar cuando APIs no responde con cuerpo JSON
	  //**********************************************************
	   
	  responseObject = WS.sendRequestAndVerify(findTestObject('Authorization/AutorizacionPortalAutorizar', [
		  'codigoUsuario' : numeroAfiliado,
		  'idInteraccion' : idInteraccion,
		  'Codigo' : codigoPrestacion,
		  'Valor' : montoProcedimiento]), FailureHandling.STOP_ON_FAILURE);
	  
	  String codigoAutorizacion = responseObject.getResponseText();
	  
	  println "\n\n" + "Codigo Autorizacion: " + codigoAutorizacion + "\n\n";
	  
} catch (Exception e) {

	e.printStackTrace(System.out)
	
}
finally{
	
	dbConnection.close(dbConnection.getResultSet());
	dbConnection.close(dbConnection.getStatement());
	dbConnection.close(dbConnection.getConnection());
}