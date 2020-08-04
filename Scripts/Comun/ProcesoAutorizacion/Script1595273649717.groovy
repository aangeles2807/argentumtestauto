import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Map

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
import helper.ReportGenerator
import internal.GlobalVariable as GlobalVariable

//********************
// Data Base Variables
//********************
DBConnection dbConnection = null;
ReportGenerator reportGenerator = null;

//*****************
// Helper Variables
//*****************
CommonAction commonAction = CommonAction.getUniqueIntance();

//***************
// APIs Variables
//***************
ArrayList<Map<String, String>> responseContentMap = null;
String responseContentString = null;
ResponseObject responseObject = null;
Map<String, String> queryResult = null;
String message = null;
Map<String, String> mapaVariablesScript = null;

try {
	
	dbConnection = DBConnection.getDBConnectionUniqueIntance();
	reportGenerator = ReportGenerator.getUniqueIntance();
	
	/*
	 * Obtencion de la informacion del afiliado segun el tipo desde la base de datos.
	 *
	 * Las cuales son:
	 *
	 * MPP
	 * PBS
	 * MPP o PBS (Aleatoriamente se toma uno de los dos)
	 *
	 */
	if (ejecutarQueryCapturaAfiliadoMPP) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
	}
	else if (ejecutarQueryCapturaAfiliadoPBS) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoPBS.remove("conditions");
	
	}
	else if (ejecutarQueryCapturaAfiliadoMPPoPBS) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPPoPBS", QueryTemplate.afiliadoMPPoPBS.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		QueryTemplate.afiliadoPBS.remove("conditions");
		QueryTemplate.afiliadoMPPoPBS.remove("afiliadoMPP");
		QueryTemplate.afiliadoMPPoPBS.remove("afiliadoPBS");
	}
	
	/**
	 * Las variables del Script son cargadas con la informacion obtenida desde
	 * la base de datos.
	 *
	 * Observacion:
	 *
	 * Estas variables tambien pueden recibir valores desde el Script padre
	 * que decida llamar este Script como hijo.
	 */
	if (ejecutarQueryCapturaAfiliadoMPP || ejecutarQueryCapturaAfiliadoPBS || ejecutarQueryCapturaAfiliadoMPPoPBS) {
		
		numeroAfiliado = queryResult.get("NATIDE");
		
		// Primer Nombre
		if(!queryResult.get("NATPRINOM").trim().isEmpty()){
			
			nombreAfiliado += queryResult.get("NATPRINOM").trim() + " ";
		}
		
		// Segundo Nombre
		if(!queryResult.get("NATSEGNOM").trim().isEmpty()){
			
			nombreAfiliado += queryResult.get("NATSEGNOM").trim() + " ";
		}
		
		// Primer Aprellido
		if(!queryResult.get("NATPRIAPE").trim().isEmpty()){
			
			nombreAfiliado += queryResult.get("NATPRIAPE").trim() + " ";
		}
		
		// Segundo Apellido
		if(!queryResult.get("NATSEGAPE").trim().isEmpty()){
			
			nombreAfiliado += queryResult.get("NATSEGAPE").trim() + " ";
		}
		
		if (codigoCobertura.toString().isEmpty()) {
			
			codigoCobertura = queryResult.get("MPLCOD");
		}
		
		generoAfiliado = queryResult.get("NATSEX");
		tipoAfiliado = queryResult.get("PRONOM");
		numeroDocumentoAfiliado = queryResult.get("NATNUMIDE");
	}
	
	println "\n\n" + "Nombre Afiliado: " + nombreAfiliado +
			"\n" + "Numero Afiliado: " + numeroAfiliado +
			"\n" + "Genero Afiliado: " + generoAfiliado +
			"\n" + "Tipo Afiliado: " + tipoAfiliado  +
			"\n" + "Numero Documento Afiliado: " + numeroDocumentoAfiliado +
			"\n\n";
	
	/*
	 *
	 * Consulta Prestador Servicio
	 *
	 * IPSCODSUP, --Codigo de Prestador
	 * MPLCOD, -- Codigo de Plan del Afiliado
	 * SERIPSCOD -- Codigo de Servicio
	 *
	 * */
	
	if (ejecutarQueryPrestadorServicio) {
		
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
		
		//IPSCODSUP
		codigoPrestadorSalud = queryResult.get("IPSCODSUP");
		
		//SERIPSCOD
		codigoServicioPrestadorSalud = queryResult.get("SERIPSCOD");
		
		//IPSNOM
		nombrePrestador = queryResult.get("IPSNOM");
		
		// SERIPSNOM
		nombreServicio = queryResult.get("SERIPSNOM");
	
		//IPSSUCCOD
		codigoSucursal = queryResult.get("IPSSUCCOD");
		
		// MPLCOD
		codigoCobertura = queryResult.get("MPLCOD");
	}
	
	println "\n\n" +
			"Codigo Cobertura: " + codigoCobertura + "\n" +
			"Codigo Prestador Salud: " + codigoPrestadorSalud + "\n" +
			"Codigo De Servicio De Prestador Salud: " + codigoServicioPrestadorSalud +  "\n" +
			"Nombre Prestador: " + nombrePrestador + "\n" +
			"Nombre Servicio: " + nombreServicio + "\n" +
			"Codigo Sucursal: " + codigoSucursal +
			"\n\n";
	
	//***************************************
	// Consulta del Web Service /api/Afiliado
	//***************************************
	
	// Si deseamos consultar este Web Service
	if (consultarApiAfiliado) {
		
		// Si es un caso positivo
		if (consultarApiAfiliadoCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]));
			
			// Mensaje reporte/consola
			message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			
			println "\n" + "Informacion del Afiliado Obtenida de API" + "\n";
			
			for(Map<String, String> APIsconsultContent : responseContentMap){
				
				for(String key : APIsconsultContent.keySet()){
					
					println key + " : " + APIsconsultContent.get(key);
				}
			}
			
			println "\n";
			
			verifyCodeAndService:
			for(int i = 0; responseContentMap.size(); i++){
				
				if (responseContentMap.get(i).get(Keyword.KEY_IDENTIFICACION.value).equals(numeroDocumentoAfiliado)) {
					
					message += String.valueOf("<br>El numero de documento del afiliado obtenido es semejante al capturado desde la base de datos, siendo este: <b>${numeroDocumentoAfiliado}</b>");
					
					//KeywordUtil.markPassed(message);
					reportGenerator.setLogStatusPASS(message);
					
					break verifyCodeAndService;
				}
					
				if ( i == (responseContentMap.size() - 1) ) {
					
					message += String.valueOf("<br>El numero de documento del afiliado obtenido no es semejante al capturado desde la base de datos. El capturado desde la base de datos es: <b>${numeroDocumentoAfiliado}</b>");
					
					KeywordUtil.markFailed(message);
					
					reportGenerator.setLogStatusFAIL(message);
				}
			}
		}
	}
			
	//**********************************************
	// Consulta del Web Service: /api/PrestadorSalud
	//**********************************************
	 
	String nombrePrestadorAPI = null;
	
	 // Si deseamos consultar este Web Service
	 if (consultarApiPrestadorSalud) {
		 
		 // Si es un caso positivo
		 if (consultarApiPrestadorSaludCasoPositivo) {
			 
			 // Consultamos el Web Service
			 responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSalud', ['descripcion' : codigoPrestadorSalud]));
			 
			 // Mensaje reporte/consola
			 message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			 
			 for (Map<String, String> mapKeyAndValue : responseContentMap) {
				 
				 nombrePrestadorAPI = mapKeyAndValue.get(Keyword.KEY_NOMBRE.value);
			 }
			 
			 // Validamos el nombre, desde el API Vs Base de Datos
			 if (nombrePrestadorAPI.equals(nombrePrestador)) {
				 
				 message += String.valueOf("<br>El nombre del prestador obtenido es semejante al capturado desde la base de datos, siendo este: <b>${nombrePrestador}</b>");
				 
				 //KeywordUtil.markPassed(message);
				 reportGenerator.setLogStatusPASS(message);
				  
			 }
			 else{
				 
				 message += String.valueOf("<br>El nombre del prestador obtenido no es semejante al capturado desde la base de datos. El capturado desde la base de datos es: <b>${nombrePrestador}</b>");
				 
				 KeywordUtil.markFailed(message);
				 reportGenerator.setLogStatusFAIL(message);
			 }
		 }
	 }
	
	//********************************************************
	// Consulta del Web Service: /api/PrestadorSalud/Servicios
	//********************************************************
	
	// Si deseamos consultar este Web Service
	if (consultarApiPrestadorSaludServicios) {
		
		// Si es un caso positivo
		if (consultarApiPrestadorSaludServiciosCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSaludServicios', ['codigoPrestadorSalud' : codigoPrestadorSalud]));
			
			// Mensaje reporte/consola
			message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			
			verifyCodeAndService:
			for(int i = 0; responseContentMap.size(); i++){
				
				if (responseContentMap.get(i).get(Keyword.KEY_CODIGO.value).equals(codigoServicioPrestadorSalud)
					&& responseContentMap.get(i).get(Keyword.KEY_DESCRIPCION.value).equals(nombreServicio)) {
					
					message += String.valueOf("<br>El prestador con el codigo <b>${codigoPrestadorSalud}</b> en sus servicios contiene el codigo de servicio <b>${codigoServicioPrestadorSalud}</b> con el nombre <b>${nombreServicio}</b>.");
					
					//KeywordUtil.markPassed(message);
					reportGenerator.setLogStatusPASS(message);
					
					break verifyCodeAndService;
				}
					
				if ( i == (responseContentMap.size() - 1) ) {
					
					message += String.valueOf("<br>El prestador con el codigo <b>${codigoPrestadorSalud}</b> en sus servicios no contiene el codigo de servicio <b>${codigoServicioPrestadorSalud}</b> con el nombre <b>${nombreServicio}</b>.");
					
					KeywordUtil.markFailed(message);
					reportGenerator.setLogStatusFAIL(message);
				}
			}
		}
	}
	
	
	//********************************************************************
	// Consulta del Web Service: /api/Autorizacion/Portal/ValidarCobertura
	//********************************************************************
	
	String idInteraccion = null;
	
	// Si deseamos consultar este Web Service
	if (consultarApiAutorizacionPortalValidarCobertura) {
		
		// Si es un caso positivo
		if (consultarApiAutorizacionPortalValidarCoberturaCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalValidarCobertura', [
				'codigoUsuario' : numeroAfiliado,
				'idInteraccion' : '0',
				'codigoPrestadorSalud' : codigoPrestadorSalud,
				'codigoSucursalPrestadorSalud' : codigoSucursal,
				'numeroAfiliado' : numeroAfiliado,
				'codigoServicio' : codigoServicioPrestadorSalud]));
			
			idInteraccion = responseContentString;
			
			println "\n\n" + "idInteraccion: " + idInteraccion + "\n\n";
		}
		// Si es un caso negativo
		else{
			
			// Consultamos el Web Service
			responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalValidarCobertura', [
				'codigoUsuario' : numeroAfiliado,
				'idInteraccion' : '0',
				'codigoPrestadorSalud' : codigoPrestadorSalud,
				'codigoSucursalPrestadorSalud' : codigoSucursal,
				'numeroAfiliado' : numeroAfiliado,
				'codigoServicio' : codigoServicioPrestadorSalud]), true);
		}
	}
	
	//********************************************************************
	// Consulta del Web Service: /api/Autorizacion/Portal/CamposRequeridos
	//********************************************************************
	
	// Si deseamos consultar este Web Service
	if (consultarApiAutorizacionPortalCamposRequeridos) {
		
		// Si es un caso positivo
		if (consultarApiAutorizacionPortalCamposRequeridosCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalCamposRequeridos', [
				'codigoUsuario' : numeroAfiliado,
				'idInteraccion' : idInteraccion]));
		}
	}
	
	
	//***************************
	// Consulta query Diagnostico
	//***************************
	
	if (ejecutarQueryDiagnostico) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.diagnostico.add("generoAfiliado", generoAfiliado);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("diagnostico", QueryTemplate.diagnostico.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.diagnostico.remove("generoAfiliado");
		
		// DIA_DIA_CODIGO
		codigoDiagnostico = queryResult.get("DIA_DIA_CODIGO");
		
		// DIA_DIA_DESCRIPCIO
		nombreDiagnostico = queryResult.get("DIA_DIA_DESCRIPCIO");
	}
		
	println "\n\n" + "Codigo Diagnostico: " + codigoDiagnostico + ", Nombre Diagnostico: " + nombreDiagnostico + "\n\n";
	
	//******************************************************
	// Consulta del Web Service: /api/Consultar/Diagnosticos
	//******************************************************
	
	String nombreDiagnosticoAPI = null;
	
	// Si deseamos consultar este Web Service
	if (consultarApiConsultarDiagnosticos) {
		
		// Si es un caso positivo
		if (consultarApiConsultarDiagnosticosCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Consult/ConsultarDiagnosticos', ['descripcion' : codigoDiagnostico]));
			
			// Mensaje reporte/consola
			message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			
			for (Map<String, String> mapKeyAndValue : responseContentMap) {
				
				nombreDiagnosticoAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
			}
			
			// Comparacion de descriipcion de diagnosticos
			
			if (nombreDiagnosticoAPI.equals(nombreDiagnostico)) {
				  
				message += String.valueOf("<br>La descripcion del diagnostico obtenida es semejante a la capturada desde la base de datos, siendo esta: <b>${nombreDiagnostico}</b>");
				
				//KeywordUtil.markPassed(message);
				reportGenerator.setLogStatusPASS(message);
				   
			}
			else{
				
				message += String.valueOf("<br>La descripcion del diagnostico obtenida no es semejante a la capturada desde la base de datos. La capturada desde la base de datos es: <b>${nombreDiagnostico}</b>");
				
				KeywordUtil.markFailed(message);
				reportGenerator.setLogStatusFAIL(message);
			 }
		}
	}
	
	//************************************************************
	// Consulta del Web Service: /api/Autorizacion/Portal/Ingresar
	//************************************************************
	
	SimpleDateFormat formatoFecha = new SimpleDateFormat("MM-dd-yyyy");
	Date fecha = new Date();
	
	// Si deseamos consultar este Web Service
	if (consultarApiAutorizacionPortalIngresar) {
		
		// Si es un caso positivo
		if (consultarApiAutorizacionPortalIngresarCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalIngresar', [
				'codigoUsuario' : numeroAfiliado,
				'idInteraccion' : idInteraccion,
				'fecha' : formatoFecha.format(fecha),
				'idDoctor' : '0',
				'telefono' : '8091112222',
				'email' : 'prueba@prueba.com',
				'codigoDiagnostico' : codigoDiagnostico,
				'observacion' : nombreDiagnostico]));
			
			println "\n\n" + "API de Autorizacion Portal Ingresar: " + responseContentString + "\n\n";
		}
		// Si es un caso positivo
		else{
			
			// Consultamos el Web Service
			responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalIngresar', [
				'codigoUsuario' : numeroAfiliado,
				'idInteraccion' : idInteraccion,
				'fecha' : formatoFecha.format(fecha),
				'idDoctor' : '0',
				'telefono' : '8091112222',
				'email' : 'prueba@prueba.com',
				'codigoDiagnostico' : codigoDiagnostico,
				'observacion' : nombreDiagnostico]), true);
		}
	}
	
	//*******************************************
	// Consulta query Procedimiento Por Prestador
	//*******************************************
	 
	if (ejecutarQueryProcedimientoPorPrestador) {
		
		 // Agregamos la(s) llave(s) y valor(es) al String Template
		 QueryTemplate.procedimientoPorPrestador.add("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
		 QueryTemplate.procedimientoPorPrestador.add("generoAfiliado", generoAfiliado);
		 QueryTemplate.procedimientoPorPrestador.add("codigoPrestadorSalud", codigoPrestadorSalud);
		 QueryTemplate.procedimientoPorPrestador.add("codigoCobertura", codigoCobertura);
		 QueryTemplate.procedimientoPorPrestador.add("fechaAutorizacion", fechaAutorizacion);
		 
		 // Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		 // Ejecutamos la consulta y obtenemos los resultados
		 queryResult = dbConnection.executeQueryAndGetResult("procedimientoPorPrestador", QueryTemplate.procedimientoPorPrestador.render().toString());
		 
		 // Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		 QueryTemplate.procedimientoPorPrestador.remove("codigoServicioPrestadorSalud");
		 QueryTemplate.procedimientoPorPrestador.remove("generoAfiliado");
		 QueryTemplate.procedimientoPorPrestador.remove("codigoPrestadorSalud");
		 QueryTemplate.procedimientoPorPrestador.remove("codigoCobertura");
		 QueryTemplate.procedimientoPorPrestador.remove("fechaAutorizacion");
		 
		 // CODIGO_PRESTACION
		 codigoPrestacion = queryResult.get("CODIGO_PRESTACION");
		 
		 // DESCRIPCION_PRESTACION
		 descripcionPrestacion = queryResult.get("DESCRIPCION_PRESTACION");
	 }
	 
	 println "\n\n" + "Codigo Prestacion: " + codigoPrestacion + ", Descripcion Prestacion: " + descripcionPrestacion + "\n\n";
	 
	 //*********************************************************************************
	 // Consulta del Web Service: /api/Autorizacion/Portal/PrestadorSalud/Procedimientos
	 //*********************************************************************************
	  
	 String descripcionPrestacionAPI = null;
	 
	 // Si deseamos consultar este Web Service
	 if (consultarApiAutorizacionPortalPrestadorSaludProcedimientos) {
		 
		 // Si es un caso positivo
		 if (consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo) {
			 
			 // Consultamos el Web Service
			 responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalPrestadorSaludProcedimientos', [
				 'codigoUsuario' : numeroAfiliado,
				 'idInteraccion' : idInteraccion,
				 'descripcion' : codigoPrestacion]));
			 
			 // Mensaje reporte/consola
			 message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			 
			 for (Map<String, String> mapKeyAndValue : responseContentMap) {
				 
				 descripcionPrestacionAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
			 }
			 
			 // Comparacion descripcion prestacion de base de datos Vs. API
			 if (descripcionPrestacionAPI.equals(descripcionPrestacion)) {
				 
				 message += String.valueOf("<br>La descripcion de la prestacion obtenida es semejante a la capturada desde la base de datos, siendo esta: <b>${descripcionPrestacion}</b>");
				 
				 //KeywordUtil.markPassed(message);
				 reportGenerator.setLogStatusPASS(message);
			}
			else{
				
				message += String.valueOf("<br>La descripcion de la prestacion obtenida no es semejante a la capturada desde la base de datos. La capturada desde la base de datos es: <b>${descripcionPrestacion}</b>");
				
				KeywordUtil.markFailed(message);
				reportGenerator.setLogStatusFAIL(message);
			}
		 }
		 // Si es un caso negativo
		 else{
			 
			 // Consultamos el Web Service
			 responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalPrestadorSaludProcedimientos', [
				 'codigoUsuario' : numeroAfiliado,
				 'idInteraccion' : idInteraccion,
				 'descripcion' : codigoPrestacion]));
			 
			 // Mensaje reporte/consola
			 message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			 
			 for (Map<String, String> mapKeyAndValue : responseContentMap) {
				 
				 descripcionPrestacionAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
			 }
			 
			 // Comparacion descripcion prestacion de base de datos Vs. API
			 if (!descripcionPrestacionAPI.equals(descripcionPrestacion)) {
				 
				 message += String.valueOf("<br>No permitio seleccionar la prestacion <b>${descripcionPrestacion}</b> para el servicio <b>${nombreServicio}</b> y el prestador <b>${nombrePrestador}</b>");
				 
				 //KeywordUtil.markPassed(message);
				 reportGenerator.setLogStatusPASS(message);
			}
			else{
				
				message += String.valueOf("<br>Permitio seleccionar la prestacion <b>${descripcionPrestacion}</b> para el servicio <b>${nombreServicio}</b> y el prestador <b>${nombrePrestador}</b>");
				
				KeywordUtil.markFailed(message);
				reportGenerator.setLogStatusFAIL(message);
			}
		 }
	 }
	  
	  //********************************************************
	  // Consulta del Web Service: /api/Consultar/Procedimientos
	  //********************************************************
	  
	  descripcionPrestacionAPI = null;
	  
	  // Si deseamos consultar este Web Service
	  if (consultarApiConsultarProcedimientos) {
		  
		  // Si es un caso positivo
		  if (consultarApiConsultarProcedimientosCasoPositivo) {
			  
			  // Consultamos el Web Service
			  responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Consult/ConsultarProcedimientos', ['descripcion' : codigoPrestacion]));
			  
			  // Mensaje reporte/consola
			  message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			  
			  for (Map<String, String> mapKeyAndValue : responseContentMap) {
				  
				  descripcionPrestacionAPI = mapKeyAndValue.get(Keyword.KEY_DESCRIPCION.value);
			  }
			  
			  // Comparacion descripcion prestacion de base de datos Vs. API
			  if (descripcionPrestacionAPI.equals(descripcionPrestacion)) {
				  
				  message += String.valueOf("<br>La descripcion de la prestacion obtenida es semejante a la capturada desde la base de datos, siendo esta: <b>${descripcionPrestacion}</b>");
				  
				  //KeywordUtil.markPassed(message);
				  reportGenerator.setLogStatusPASS(message);
			 }
			 else{
				 
				 message += String.valueOf("<br>La descripcion de la prestacion obtenida no es semejante a la capturada desde la base de datos. La capturada desde la base de datos es: <b>${descripcionPrestacion}</b>");
				 
				 KeywordUtil.markFailed(message);
				 reportGenerator.setLogStatusFAIL(message);
			 }
		  }
	  }
	  
	  //***********************************************************************
	  // Consulta del Web Service: /api/Autorizacion/Portal/TarifaProcedimiento
	  //***********************************************************************
	  
	  String montoProcedimiento = null;
	  
	  // Si deseamos consultar este Web Service
	  if (consultarApiAutorizacionPortalTarifaProcedimiento) {
		  
		  // Si es un caso positivo
		  if (consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo) {
			  
			  // Consultamos el Web Service
			  responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalTarifaProcedimiento', [
				  'codigoUsuario' : numeroAfiliado,
				  'idInteraccion' : idInteraccion,
				  'codigoProcedimiento' : codigoPrestacion]));
			  
			  montoProcedimiento = responseContentString;
			  
			  println "\n\n" + "Monto Procedimiento: " + montoProcedimiento + "\n\n";
		  }
	  }
	  
	  //*************************************************************
	  // Consulta del Web Service: /api/Autorizacion/Portal/Autorizar
	  //*************************************************************
	  
	  String codigoAutorizacion = null;
	  
	  // Si deseamos consultar este Web Service
	  if (consultarApiAutorizacionPortalAutorizar) {
		  
		  // Si es un caso positivo
		  if (consultarApiAutorizacionPortalAutorizarCasoPositivo) {
			  
			  // Consultamos el Web Service
			  responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAutorizar', [
				  'codigoUsuario' : numeroAfiliado,
				  'idInteraccion' : idInteraccion,
				  'Codigo' : codigoPrestacion,
				  'Valor' : montoProcedimiento]));
			  
			  codigoAutorizacion = responseContentString;
			  
			  println "\n\n" + "Codigo Autorizacion: " + codigoAutorizacion + "\n\n";
		  }
	  }
	  
	  mapaVariablesScript = new HashMap<String, String>();
	  
	  mapaVariablesScript.put("numeroAfiliado", numeroAfiliado);
	  mapaVariablesScript.put("tipoAfiliado", tipoAfiliado);
	  mapaVariablesScript.put("nombreAfiliado", nombreAfiliado);
	  mapaVariablesScript.put("codigoCobertura", codigoCobertura);
	  mapaVariablesScript.put("generoAfiliado", generoAfiliado);
	  mapaVariablesScript.put("numeroDocumentoAfiliado", numeroDocumentoAfiliado);
	  mapaVariablesScript.put("estadoPrestador", estadoPrestador);
	  mapaVariablesScript.put("codigoPrestadorSalud", codigoPrestadorSalud);
	  mapaVariablesScript.put("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
	  mapaVariablesScript.put("nombrePrestador", nombrePrestador);
	  mapaVariablesScript.put("nombreServicio", nombreServicio);
	  mapaVariablesScript.put("codigoSucursal", codigoSucursal);
	  mapaVariablesScript.put("codigoDiagnostico", codigoDiagnostico);
	  mapaVariablesScript.put("nombreDiagnostico", nombreDiagnostico);
	  mapaVariablesScript.put("codigoPrestacion", codigoPrestacion);
	  mapaVariablesScript.put("descripcionPrestacion", descripcionPrestacion);
	  mapaVariablesScript.put("fechaAutorizacion", fechaAutorizacion);
	  
	  return mapaVariablesScript;
	  
} catch (Exception e) {

	KeywordUtil.markError(e.getMessage());
	reportGenerator.setLogStatusFAIL(e.getMessage());
}