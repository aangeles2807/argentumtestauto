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
		QueryTemplate.afiliadoMPP.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoMPP.add("condicionFechaAutorizacionMPP", condicionFechaAutorizacionMPP);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados

		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		QueryTemplate.afiliadoMPP.remove("fechaAutorizacion");
		QueryTemplate.afiliadoMPP.remove("condicionFechaAutorizacionMPP");
		
	}
	else if (ejecutarQueryCapturaAfiliadoPBS) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoMPP);
		QueryTemplate.afiliadoPBS.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoPBS.add("condicionFechaAutorizacionPBS", condicionFechaAutorizacionPBS);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoPBS.remove("conditions");
		QueryTemplate.afiliadoPBS.remove("fechaAutorizacion");
		QueryTemplate.afiliadoPBS.remove("condicionFechaAutorizacionPBS");
	
	}
	else if (ejecutarQueryCapturaAfiliadoMPPoPBS) {
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		QueryTemplate.afiliadoMPP.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoMPP.add("condicionFechaAutorizacionMPP", condicionFechaAutorizacionMPP);
		
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS);
		QueryTemplate.afiliadoPBS.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoPBS.add("condicionFechaAutorizacionPBS", condicionFechaAutorizacionPBS);
		
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPPoPBS", QueryTemplate.afiliadoMPPoPBS.render().toString());
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		QueryTemplate.afiliadoMPP.remove("fechaAutorizacion");
		QueryTemplate.afiliadoMPP.remove("condicionFechaAutorizacionMPP");
		
		QueryTemplate.afiliadoPBS.remove("conditions");
		QueryTemplate.afiliadoPBS.remove("fechaAutorizacion");
		QueryTemplate.afiliadoPBS.remove("condicionFechaAutorizacionPBS");
				
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
		
		if (generoAfiliado.toString().isEmpty()) {
			
			generoAfiliado = queryResult.get("NATSEX");
		}
		
		tipoAfiliado = queryResult.get("PRONOM");
		numeroDocumentoAfiliado = queryResult.get("NATNUMIDE");
	}
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	message = "\n\n";
	
	if (!nombreAfiliado.toString().isEmpty()) {
		
		message += String.valueOf("Nombre Afiliado: ${nombreAfiliado}" + "\n");
	}
	
	if (!numeroAfiliado.toString().isEmpty()) {
		
		message += String.valueOf("Numero Afiliado: ${numeroAfiliado}" + "\n");
	}
	
	if (!generoAfiliado.toString().isEmpty()) {
		
		message += String.valueOf("Genero Afiliado: ${generoAfiliado}" + "\n");
	}
	
	if (!tipoAfiliado.toString().isEmpty()) {
		
		message += String.valueOf("Tipo Afiliado: ${tipoAfiliado}" + "\n");
	}
	
	if (!numeroDocumentoAfiliado.toString().isEmpty()) {
		
		message += String.valueOf("Numero Documento Afiliado: ${numeroDocumentoAfiliado}" + "\n");
	}
	
	message += "\n\n";
	
	println message;
	
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
		if (codigoPrestadorSalud.toString().isEmpty()) {
			
			codigoPrestadorSalud = queryResult.get("IPSCODSUP");
		}
		
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
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	message = "\n\n";
	
	if (!codigoCobertura.toString().isEmpty()) {
		
		message += String.valueOf("Codigo Cobertura: ${codigoCobertura}" + "\n");
	}
	
	if (!codigoPrestadorSalud.toString().isEmpty()) {
		
		message += String.valueOf("Codigo Prestador Salud ${codigoPrestadorSalud}" + "\n");
	}
	
	if (!codigoServicioPrestadorSalud.toString().isEmpty()) {
		
		message += String.valueOf("Codigo De Servicio De Prestador Salud: ${codigoServicioPrestadorSalud}" + "\n");
	}
	
	if (!nombrePrestador.toString().isEmpty()) {
		
		message += String.valueOf("Nombre Prestador: ${nombrePrestador}" + "\n");
	}
	
	if (!nombreServicio.toString().isEmpty()) {
		
		message += String.valueOf("Nombre Servicio: ${nombreServicio}" + "\n");
	}
	
	if (!codigoSucursal.toString().isEmpty()) {
		
		message += String.valueOf("Codigo Sucursal: ${codigoSucursal}" + "\n");
	}
	
	message += "\n\n";
	
	println message;
	
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
	
	
	/*
		 *
		 * Consulta Doctor
		 *
		 * IPSCODSUP, --Codigo de Doctor
		 * IPSNOM, -- Nombre del Doctor
		 *
	 * */
	
		if (ejecutarQueryDoctor) {
			
			queryResult = dbConnection.executeQueryAndGetResult("doctor", QueryTemplate.doctor.render().toString());
			
			//IPSCODSUP
			codigoDoctor = queryResult.get("IPSCODSUP");
			
			//IPSNOM
			nombreDoctor = queryResult.get("IPSNOM");
			
		}
		
		// ****************************
		// Imprimir estado de variables
		// ****************************
		
		message = "\n\n";
		
		if (!codigoDoctor.toString().isEmpty()) {
			
			message += String.valueOf("Codigo Doctor: ${codigoDoctor}" + "\n");
		}
		
		if (!nombreDoctor.toString().isEmpty()) {
			
			message += String.valueOf("Nombre Doctor ${nombreDoctor}" + "\n");
		}
		
		
		
		message += "\n\n";
		
		println message;
	
	
	
	 //********************************************************
	 // Consulta del Web Service: /api/PrestadorSalud/Doctores
	 //********************************************************
	 
	 // Si deseamos consultar este Web Service
	 if (consultarApiPrestadorSaludDoctores) {
		 
		 // Si es un caso positivo
		 if (consultarApiPrestadorSaludDoctoresCasoPositivo) {
			 
			 // Consultamos el Web Service
			 responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSaludDoctores', ['descripcion' : nombreDoctor]));
			 
			 // Mensaje reporte/consola
			 message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
			 
			 verifyCodeAndService:
			 for(int i = 0; responseContentMap.size(); i++){
				 
				 if (responseContentMap.get(i).get(Keyword.KEY_CODIGO.value).equals(codigoDoctor)
					 && responseContentMap.get(i).get(Keyword.KEY_NOMBRE.value).equals(nombreDoctor)) {
					 
					 message += String.valueOf("<br>El doctor con el codigo <b>${codigoDoctor}</b> tiene el nombre <b>${nombreDoctor}</b>.");
					 
					 //KeywordUtil.markPassed(message);
					 reportGenerator.setLogStatusPASS(message);
					 
					 break verifyCodeAndService;
				 }
					 
				 if ( i == (responseContentMap.size() - 1) ) {
					 
					 message += String.valueOf("<br>El doctor con el codigo <b>${codigoDoctor}</b> no tiene el nombre <b>${nombreDoctor}</b>.");
					 
					 KeywordUtil.markFailed(message);
					 reportGenerator.setLogStatusFAIL(message);
				 }
			 }
		 }
	 }
	
	
	//********************************************************************
	// Consulta del Web Service: /api/Autorizacion/Portal/ValidarCobertura
	//********************************************************************
	
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
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.diagnostico.remove("generoAfiliado");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.diagnostico.add("generoAfiliado", generoAfiliado);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("diagnostico", QueryTemplate.diagnostico.render().toString());
		
		// DIA_DIA_CODIGO
		codigoDiagnostico = queryResult.get("DIA_DIA_CODIGO");
		
		// DIA_DIA_DESCRIPCIO
		nombreDiagnostico = queryResult.get("DIA_DIA_DESCRIPCIO");
	}
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	message = "\n\n";
	
	if (!codigoDiagnostico.toString().isEmpty()) {
		
		message += String.valueOf("Codigo Diagnostico: ${codigoDiagnostico}" + "\n");
	}
	
	if (!nombreDiagnostico.toString().isEmpty()) {
		
		message += String.valueOf("Nombre Diagnostico: ${nombreDiagnostico}" + "\n");
	}
	
	message += "\n\n";
	
	println message;
	
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
		 QueryTemplate.procedimientos.add("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
		 QueryTemplate.procedimientos.add("generoAfiliado", generoAfiliado);
		 QueryTemplate.procedimientos.add("codigoPrestadorSalud", codigoPrestadorSalud);
		 QueryTemplate.procedimientos.add("codigoCobertura", codigoCobertura);
		 QueryTemplate.procedimientos.add("fechaAutorizacion", fechaAutorizacion);
		 QueryTemplate.procedimientos.add("prePreDescripcion", prePreDescripcion);
		 QueryTemplate.procedimientos.add("orderByRandom", orderByRandom);
		 QueryTemplate.procedimientos.add("condicionProcedimiento", condicionProcedimiento);
		 QueryTemplate.procedimientos.add("joinProcedimiento", joinProcedimiento);
		 
		 QueryTemplate.procedimientoPorPrestador.add("procedimientos",  QueryTemplate.procedimientos.render().toString());
		 
		 // Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		 // Ejecutamos la consulta y obtenemos los resultados
		 queryResult = dbConnection.executeQueryAndGetResult("procedimientoPorPrestador", QueryTemplate.procedimientoPorPrestador.render().toString());
		 
		 if(ejecutarQueryPrestacionNoContratada){
			 
			 QueryTemplate.prestacionNoContratada.add("procedimientos",  QueryTemplate.procedimientos.render().toString());
			 
			 // Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
			 // Ejecutamos la consulta y obtenemos los resultados
			 queryResult = dbConnection.executeQueryAndGetResult("prestacionNoContratada", QueryTemplate.prestacionNoContratada.render().toString());
			 
			 QueryTemplate.prestacionNoContratada.remove("procedimientos");
		 
		 }
		 // Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		 QueryTemplate.procedimientos.remove("codigoServicioPrestadorSalud");
		 QueryTemplate.procedimientos.remove("generoAfiliado");
		 QueryTemplate.procedimientos.remove("codigoPrestadorSalud");
		 QueryTemplate.procedimientos.remove("codigoCobertura");
		 QueryTemplate.procedimientos.remove("fechaAutorizacion");
		 QueryTemplate.procedimientos.remove("orderByRandom");
		 QueryTemplate.procedimientos.remove("prePreDescripcion");
		 QueryTemplate.procedimientos.remove("condicionProcedimiento");
		 QueryTemplate.procedimientos.remove("joinProcedimiento");
		 QueryTemplate.procedimientoPorPrestador.remove("procedimientos");
		 
		 // CODIGO_PRESTACION
		 codigoPrestacion = queryResult.get("CODIGO_PRESTACION");
		 
		 // DESCRIPCION_PRESTACION
		 descripcionPrestacion = queryResult.get("DESCRIPCION_PRESTACION");
	 }
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	message = "\n\n";
	
	if (!codigoPrestacion.toString().isEmpty()) {
		
		message += String.valueOf("Codigo Prestacion: ${codigoPrestacion}" + "\n");
	}
	
	if (!descripcionPrestacion.toString().isEmpty()) {
		
		message += String.valueOf("Descripcion Prestacion: ${descripcionPrestacion}" + "\n");
	}
	
	message += "\n\n";
	
	println message;
	 
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
	  
	  // Si deseamos consultar este Web Service
	  if (consultarApiAutorizacionPortalTarifaProcedimiento) {
		  
		  // Si es un caso positivo
		  if (consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo) {
			  
			  // Consultamos el Web Service
			  responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalTarifaProcedimiento', [
				  'codigoUsuario' : numeroAfiliado,
				  'idInteraccion' : idInteraccion,
				  'codigoProcedimiento' : codigoPrestacion]));
			  
			  tarifaProcedimiento = responseContentString;
			  
			  println "\n\n" + "Monto Procedimiento: " + tarifaProcedimiento + "\n\n";
		  }else{
		  
			  // Consultamos el Web Service
			  responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalTarifaProcedimiento', [
				  'codigoUsuario' : numeroAfiliado,
				  'idInteraccion' : idInteraccion,
				  'codigoProcedimiento' : codigoPrestacion]), true);
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
				  'Valor' : (cantidad * Double.parseDouble(tarifaProcedimiento)),
				  'Cantidad' : cantidad]));
			  
			  codigoAutorizacion = responseContentString;
			  
			  println "\n\n" + "Codigo Autorizacion: " + codigoAutorizacion + "\n\n";
		  }
		  else{
			  
			  // En caso de querer agregar un mensaje adicional de error
			  if (!consultarApiAutorizacionPortalAutorizarMensajeError.toString().isEmpty()) {
				  
				  responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAutorizar', [
				  'codigoUsuario' : numeroAfiliado,
				  'idInteraccion' : idInteraccion,
				  'Codigo' : codigoPrestacion,
				  'Valor' : (cantidad * Double.parseDouble(tarifaProcedimiento)),
				  'Cantidad' : cantidad]), true, consultarApiAutorizacionPortalAutorizarMensajeError.toString());
			  }
			  else{
				  
				  responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAutorizar', [
				  'codigoUsuario' : numeroAfiliado,
				  'idInteraccion' : idInteraccion,
				  'Codigo' : codigoPrestacion,
				  'Valor' : (cantidad * Double.parseDouble(tarifaProcedimiento)),
				  'Cantidad' : cantidad]), true);
			  }
		  }
	  }
	  
	  
	  //*************************************************************
	   // Consulta del Web Service: /api/Autorizacion/Portal/Anular
	   //*************************************************************
	   
	   String respuestaAnulacion = null;
	   
	   // Si deseamos consultar este Web Service
	   if (consultarApiAutorizacionPortalAnular) {
		   
		   // Si es un caso positivo
		   if (consultarApiAutorizacionPortalAnularCasoPositivo) {
			   
			   // Consultamos el Web Service
			   responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAnular', [
				   'codigoUsuario' : numeroAfiliado,
				   'idInteraccion' : idInteraccion,
				   'codigoMotivo' : '1',
				   'observacion' : 'Prueba API Anulacion de Autorizacion Completada']));
			   
			   respuestaAnulacion = responseContentString;
			   
			   println "\n\n" + "Respuesta de Anulacion: " + respuestaAnulacion + "\n\n";
		   }
		   else{
			   
			   responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAnular', [
				   'codigoUsuario' : numeroAfiliado,
				   'idInteraccion' : idInteraccion,
				   'codigoMotivo' : '1',
				   'observacion' : 'Prueba API Anulacion de Autorizacion Completada']), true);
		   }
	   }
	  
	  /**
	   * Agregamos todas las variables con sus valores de este Scrip,
	   * para ser retornadas en caso de ser necesarias para otro escenario.
	   */
	  
	  commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPP", ejecutarQueryCapturaAfiliadoMPP);
	  commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoPBS", ejecutarQueryCapturaAfiliadoPBS);
	  commonAction.getMapStringObject().put("ejecutarQueryCapturaAfiliadoMPPoPBS", ejecutarQueryCapturaAfiliadoMPPoPBS);
	  commonAction.getMapStringObject().put("condicionAfiliadoMPP", condicionAfiliadoMPP);
	  commonAction.getMapStringObject().put("condicionAfiliadoPBS", condicionAfiliadoPBS);
	  commonAction.getMapStringObject().put("servicioConsulta", servicioConsulta);
	  commonAction.getMapStringObject().put("numeroAfiliado", numeroAfiliado);
	  commonAction.getMapStringObject().put("tipoAfiliado", tipoAfiliado);
	  commonAction.getMapStringObject().put("nombreAfiliado", nombreAfiliado);
	  commonAction.getMapStringObject().put("codigoCobertura", codigoCobertura);
	  commonAction.getMapStringObject().put("generoAfiliado", generoAfiliado);
	  commonAction.getMapStringObject().put("numeroDocumentoAfiliado", numeroDocumentoAfiliado);
	  commonAction.getMapStringObject().put("ejecutarQueryPrestadorServicio", ejecutarQueryPrestadorServicio);
	  commonAction.getMapStringObject().put("estadoPrestador", estadoPrestador);
	  commonAction.getMapStringObject().put("codigoPrestadorSalud", codigoPrestadorSalud);
	  commonAction.getMapStringObject().put("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
	  commonAction.getMapStringObject().put("nombrePrestador", nombrePrestador);
	  commonAction.getMapStringObject().put("nombreServicio", nombreServicio);
	  commonAction.getMapStringObject().put("codigoSucursal", codigoSucursal);
	  commonAction.getMapStringObject().put("ejecutarQueryDiagnostico", ejecutarQueryDiagnostico);
	  commonAction.getMapStringObject().put("codigoDiagnostico", codigoDiagnostico);
	  commonAction.getMapStringObject().put("nombreDiagnostico", nombreDiagnostico);
	  commonAction.getMapStringObject().put("ejecutarQueryProcedimientoPorPrestador", ejecutarQueryProcedimientoPorPrestador);
	  commonAction.getMapStringObject().put("codigoPrestacion", codigoPrestacion);
	  commonAction.getMapStringObject().put("descripcionPrestacion", descripcionPrestacion);
	  commonAction.getMapStringObject().put("consultarApiAfiliado", consultarApiAfiliado);
	  commonAction.getMapStringObject().put("consultarApiAfiliadoCasoPositivo", consultarApiAfiliadoCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiPrestadorSalud", consultarApiPrestadorSalud);
	  commonAction.getMapStringObject().put("consultarApiPrestadorSaludCasoPositivo", consultarApiPrestadorSaludCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiPrestadorSaludServicios", consultarApiPrestadorSaludServicios);
	  commonAction.getMapStringObject().put("consultarApiPrestadorSaludServiciosCasoPositivo", consultarApiPrestadorSaludServiciosCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCobertura", consultarApiAutorizacionPortalValidarCobertura);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalValidarCoberturaCasoPositivo", consultarApiAutorizacionPortalValidarCoberturaCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridos", consultarApiAutorizacionPortalCamposRequeridos);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalCamposRequeridosCasoPositivo", consultarApiAutorizacionPortalCamposRequeridosCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticos", consultarApiConsultarDiagnosticos);
	  commonAction.getMapStringObject().put("consultarApiConsultarDiagnosticosCasoPositivo", consultarApiConsultarDiagnosticosCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresar", consultarApiAutorizacionPortalIngresar);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalIngresarCasoPositivo", consultarApiAutorizacionPortalIngresarCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientos", consultarApiAutorizacionPortalPrestadorSaludProcedimientos);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo", consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiConsultarProcedimientos", consultarApiConsultarProcedimientos);
	  commonAction.getMapStringObject().put("consultarApiConsultarProcedimientosCasoPositivo", consultarApiConsultarProcedimientosCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimiento", consultarApiAutorizacionPortalTarifaProcedimiento);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo", consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizar", consultarApiAutorizacionPortalAutorizar);
	  commonAction.getMapStringObject().put("consultarApiAutorizacionPortalAutorizarCasoPositivo", consultarApiAutorizacionPortalAutorizarCasoPositivo);
	  commonAction.getMapStringObject().put("fechaAutorizacion", fechaAutorizacion);
	  commonAction.getMapStringObject().put("tarifaProcedimiento", tarifaProcedimiento);
	  commonAction.getMapStringObject().put("idInteraccion", idInteraccion);
	  commonAction.getMapStringObject().put("cantidad", cantidad);
	  
	   return commonAction.getMapStringObject();
	  
} catch (Exception e) {

	reportGenerator.setLogStatusFAIL(e.getMessage());
	KeywordUtil.markErrorAndStop(e.getMessage());
}