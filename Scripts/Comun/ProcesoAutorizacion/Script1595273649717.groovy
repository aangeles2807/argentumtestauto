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
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import freemarker.core.BuiltInsForHashes.keysBI
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
//List<Map<String, String>> queryResultMultipleRows;
//Map<String, String> mapaPrestacionesIngresadas;
//Map<String, String> mapaPrestadorServicio;
double montoAcumulado = 0.00;
int randomValue;

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
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		QueryTemplate.afiliadoMPP.remove("fechaAutorizacion");
		QueryTemplate.afiliadoMPP.remove("condicionFechaAutorizacionMPP");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		QueryTemplate.afiliadoMPP.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoMPP.add("condicionFechaAutorizacionMPP", condicionFechaAutorizacionMPP);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados

		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
	}
	else if (ejecutarQueryCapturaAfiliadoPBS) {
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoPBS.remove("conditions");
		QueryTemplate.afiliadoPBS.remove("fechaAutorizacion");
		QueryTemplate.afiliadoPBS.remove("condicionFechaAutorizacionPBS");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
		QueryTemplate.afiliadoPBS.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoPBS.add("condicionFechaAutorizacionPBS", condicionFechaAutorizacionPBS);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());	
	}
	else if (ejecutarQueryCapturaAfiliadoMPPoPBS) {
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.afiliadoMPP.remove("conditions");
		QueryTemplate.afiliadoMPP.remove("fechaAutorizacion");
		QueryTemplate.afiliadoMPP.remove("condicionFechaAutorizacionMPP");		
		QueryTemplate.afiliadoPBS.remove("conditions");
		QueryTemplate.afiliadoPBS.remove("fechaAutorizacion");
		QueryTemplate.afiliadoPBS.remove("condicionFechaAutorizacionPBS");		
		QueryTemplate.afiliadoMPPoPBS.remove("afiliadoMPP");
		QueryTemplate.afiliadoMPPoPBS.remove("afiliadoPBS");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
		QueryTemplate.afiliadoMPP.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoMPP.add("condicionFechaAutorizacionMPP", condicionFechaAutorizacionMPP);
		QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
		QueryTemplate.afiliadoPBS.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.afiliadoPBS.add("condicionFechaAutorizacionPBS", condicionFechaAutorizacionPBS);
		
		
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
		QueryTemplate.afiliadoMPPoPBS.add("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPPoPBS", QueryTemplate.afiliadoMPPoPBS.render().toString());
	}
	
	/**
	 * Las variables del Script son cargadas con la informacion obtenida desde
	 * la base de datos.
	 *
	 * Observacion:
	 *
	 * Estas variables tambien pueden recibir valores desde el Script hijo
	 * que decida llamar este Script como padre.
	 */
	if (ejecutarQueryCapturaAfiliadoMPP || ejecutarQueryCapturaAfiliadoPBS || ejecutarQueryCapturaAfiliadoMPPoPBS) {
		
		numeroAfiliado = queryResult.get("NATIDE");
		
		if(!queryResult.get("NATPRINOM").trim().isEmpty()){nombreAfiliado += queryResult.get("NATPRINOM").trim() + " ";}
		
		if(!queryResult.get("NATSEGNOM").trim().isEmpty()){nombreAfiliado += queryResult.get("NATSEGNOM").trim() + " ";}
		
		if(!queryResult.get("NATPRIAPE").trim().isEmpty()){nombreAfiliado += queryResult.get("NATPRIAPE").trim() + " ";}
		
		if(!queryResult.get("NATSEGAPE").trim().isEmpty()){nombreAfiliado += queryResult.get("NATSEGAPE").trim() + " ";}
		
		codigoCobertura = queryResult.get("MPLCOD");
		generoAfiliado = queryResult.get("NATSEX");
		
		if (generoAfiliadoProcedimiento.toString().isEmpty()) {
			
			generoAfiliadoProcedimiento = String.valueOf("('A', '${generoAfiliado}')");
		}
		else {
			
			generoAfiliadoProcedimiento = String.valueOf("('${generoAfiliadoProcedimiento}')");
		}
		
		if (generoAfiliadoServicio.toString().isEmpty()) {
			
			generoAfiliadoServicio = String.valueOf("('A', '${generoAfiliado}')");
		}
		else {
			
			generoAfiliadoServicio = String.valueOf("('${generoAfiliadoServicio}')");
		}
		
		tipoAfiliado = queryResult.get("PRONOM");
		numeroDocumentoAfiliado = queryResult.get("NATNUMIDE");
		fecha = queryResult.get("Fecha Autorizacion"); 
	}
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	/*
	message = "\n\n";
	
	message += String.valueOf("Nombre Afiliado: ${nombreAfiliado}" + "\n");
	message += String.valueOf("Codigo Cobertura: ${codigoCobertura}" + "\n");
	message += String.valueOf("Numero Afiliado: ${numeroAfiliado}" + "\n");
	message += String.valueOf("Genero Afiliado: ${generoAfiliado}" + "\n");
	message += String.valueOf("Genero Afiliado Procedimiento: ${generoAfiliadoProcedimiento}" + "\n");
	message += String.valueOf("Tipo Afiliado: ${tipoAfiliado}" + "\n");
	message += String.valueOf("Numero Documento Afiliado: ${numeroDocumentoAfiliado}" + "\n");
	message += String.valueOf("Fecha: ${fecha}" + "\n");
	
	message += "\n\n";
	
	println message;
	*/
	
	/*
	 * Consulta Prestador Servicio
	 *
	 * IPSCODSUP, --Codigo de Prestador
	 * MPLCOD, -- Codigo de Plan del Afiliado
	 * SERIPSCOD -- Codigo de Servicio
	 *
	 * */
	if (ejecutarQueryPrestadorServicio || ejecutarQueryPrestadorFueradeRed) {
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.tablaPrestador.remove("codigoCobertura");
		QueryTemplate.tablaPrestador.remove("generoAfiliadoServicio");
		QueryTemplate.tablaPrestador.remove("estadoPrestador");
		QueryTemplate.tablaPrestador.remove("servicioConsulta");
		QueryTemplate.tablaPrestador.remove("camposTablaPrestador");
		QueryTemplate.tablaPrestador.remove("orderByRandom");
		QueryTemplate.prestadorServicio.remove("tablaPrestador");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.tablaPrestador.add("codigoCobertura", codigoCobertura);
		QueryTemplate.tablaPrestador.add("generoAfiliadoServicio", generoAfiliadoServicio);
		QueryTemplate.tablaPrestador.add("estadoPrestador", estadoPrestador);
		QueryTemplate.tablaPrestador.add("servicioConsulta", servicioConsulta);
		QueryTemplate.tablaPrestador.add("camposTablaPrestador", camposTablaPrestador);
		QueryTemplate.tablaPrestador.add("orderByRandom", orderByRandom);
		QueryTemplate.prestadorServicio.add("tablaPrestador", QueryTemplate.tablaPrestador.render().toString());
	}
	
	if (ejecutarQueryPrestadorServicio) {
		
		queryResult = null
		
		if (!mapaPrestadorServicio.isEmpty()) {
			
			while(queryResult == null){
				
				// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
				// Ejecutamos la consulta y obtenemos los resultados
				queryResult = dbConnection.executeQueryAndGetResult("prestadorServicio", QueryTemplate.prestadorServicio.render().toString());
				
				if (mapaPrestadorServicio.containsKey(queryResult.get("IPSCODSUP"))) {
					
					queryResult = null;
				}
			}
		}
		else{
			
			// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
			// Ejecutamos la consulta y obtenemos los resultados
			queryResult = dbConnection.executeQueryAndGetResult("prestadorServicio", QueryTemplate.prestadorServicio.render().toString());
			
			mapaPrestadorServicio.put(queryResult.get("IPSCODSUP"), queryResult.get("IPSNOM"));
		}
	}
	
	if(ejecutarQueryPrestadorFueradeRed){
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.prestadorFueraDeRed.remove("top10000Lineas");
		QueryTemplate.prestadorFueraDeRed.remove("tablaPrestador");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.prestadorFueraDeRed.add("top10000Lineas", top10000Lineas);
		QueryTemplate.prestadorFueraDeRed.add("tablaPrestador",  QueryTemplate.tablaPrestador.render().toString());
		
		queryResult = null;
		
		while(queryResult == null){
			
			// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
			// Ejecutamos la consulta y obtenemos los resultados
			queryResult = dbConnection.executeQueryAndGetResult("prestadorFueraDeRed", QueryTemplate.prestadorFueraDeRed.render().toString());
			
			//**********************************************
			// Consulta del Web Service: /api/PrestadorSalud
			//**********************************************
			
			// Consultamos el Web Service
			responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSalud', ['descripcion' : queryResult.get("IPSCODSUP")]), false, null, null, false);
			
			if (!responseContentMap.get(0).get("DescripcionTipoVinculacion").equals("INACTIVO")) {
				
				queryResult = null;
			}
		}
	}
	
	if (ejecutarQueryServicioConPrestacion) {
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.servicioConPrestacion.remove("servicioConsulta");
		QueryTemplate.servicioConPrestacion.remove("codigoCobertura");
		QueryTemplate.servicioConPrestacion.remove("codigoProcedimiento");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.servicioConPrestacion.add("servicioConsulta", servicioConsulta);
		QueryTemplate.servicioConPrestacion.add("codigoCobertura", codigoCobertura);
		QueryTemplate.servicioConPrestacion.add("codigoProcedimiento", codigoProcedimiento);
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados		
		queryResult = dbConnection.executeQueryAndGetResult("servicioConPrestacion", QueryTemplate.servicioConPrestacion.render().toString());
	}
	
	if (ejecutarQueryPrestadorServicio || ejecutarQueryPrestadorFueradeRed || ejecutarQueryServicioConPrestacion) {
		
		codigoPrestadorSalud = queryResult.get("IPSCODSUP");
		codigoServicioPrestadorSalud = queryResult.get("SERIPSCOD");
		nombrePrestador = queryResult.get("IPSNOM");
		nombreServicio = queryResult.get("SERIPSNOM");
		
		if (!queryResult.get("IPSSUCCOD").equals("null")) {
			
			codigoSucursal = queryResult.get("IPSSUCCOD");
		}
		else{
			
			codigoSucursal = "";
		}
		
		codigoCobertura = queryResult.get("MPLCOD");
		
		if (ejecutarQueryServicioConPrestacion) {
			
			codigoPrestacion = queryResult.get("CODIGO_PRESTACION");
			descripcionPrestacion = queryResult.get("DESCRIPCION_PRESTACION");
		}
	}
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	/*
	message = "\n\n";
	
	message += String.valueOf("Codigo Cobertura: ${codigoCobertura}" + "\n");
	message += String.valueOf("Codigo Prestador Salud: ${codigoPrestadorSalud}" + "\n");
	message += String.valueOf("Codigo De Servicio De Prestador Salud: ${codigoServicioPrestadorSalud}" + "\n");
	message += String.valueOf("Nombre Prestador: ${nombrePrestador}" + "\n");
	message += String.valueOf("Nombre Servicio: ${nombreServicio}" + "\n");
	
	if (!codigoSucursal.toString().isEmpty() &&  !codigoSucursal.toString().equals("null")) {message += String.valueOf("Codigo Sucursal: ${codigoSucursal}" + "\n");}
	
	message += String.valueOf("Codigo Prestacion: ${codigoPrestacion}" + "\n");
	message += String.valueOf("Descripcion Prestacion: ${descripcionPrestacion}" + "\n");
	
	message += "\n\n";
	
	println message;
	*/
	
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
					
					reportGenerator.setLogStatusPASS(message);
					
					break verifyCodeAndService;
				}
					
				if ( i == (responseContentMap.size() - 1) ) {
					
					message += String.valueOf("<br>El numero de documento del afiliado obtenido no es semejante al capturado desde la base de datos. El capturado desde la base de datos es: <b>${numeroDocumentoAfiliado}</b>");
					
					throw new RuntimeException(message);
				}
			}
		}
		else{
			
			// Consultamos el Web Service
			commonAction.getResponseContentIntoMapOrString(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]), true, null, null, true);
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
				 
				reportGenerator.setLogStatusPASS(message);
				  
			}
			else{
				
				message += String.valueOf("<br>El nombre del prestador obtenido no es semejante al capturado desde la base de datos. El capturado desde la base de datos es: <b>${nombrePrestador}</b>");
				 
				throw new RuntimeException(message);
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
					
					reportGenerator.setLogStatusPASS(message);
					
					break verifyCodeAndService;
				}
					
				if ( i == (responseContentMap.size() - 1) ) {
					
					message += String.valueOf("<br>El prestador con el codigo <b>${codigoPrestadorSalud}</b> en sus servicios no contiene el codigo de servicio <b>${codigoServicioPrestadorSalud}</b> con el nombre <b>${nombreServicio}</b>.");
					
					throw new RuntimeException(message);
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
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResultMultipleRows = dbConnection.executeQueryAndGetResultWithMultipleRows("doctor", QueryTemplate.doctor.render().toString());
		
		//*********************************************************
		// Consulta del Web Service: /api/PrestadorSalud/Remitentes
		//*********************************************************
		  
		// Si deseamos consultar este Web Service
		if (consultarApiPrestadorSaludDoctores) {
			
			// Si es un caso positivo
			if (consultarApiPrestadorSaludDoctoresCasoPositivo) {
				
				for(int i=0; i < queryResultMultipleRows.size(); i++){
					
					// Consultamos el Web Service
					responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSaludRemitentes', ['descripcion' : queryResultMultipleRows.get(i).get("IPSCODSUP")]));
					
					// Mensaje reporte/consola
					message = String.valueOf("En la consulta al servicio web: <b>${commonAction.getApiPath()}</b>:<br>");
					
					for(j=0; j < responseContentMap.size(); j++){
						
						if (responseContentMap.get(j).get(Keyword.KEY_CODIGO.value).equals(queryResultMultipleRows.get(i).get("IPSCODSUP"))
							&& responseContentMap.get(j).get(Keyword.KEY_NOMBRE.value).equals(queryResultMultipleRows.get(i).get("IPSNOM"))) {
							
							message += String.valueOf("<br>El doctor con el codigo <b>${queryResultMultipleRows.get(i).get("IPSCODSUP")}</b> tiene el nombre <b>${queryResultMultipleRows.get(i).get("IPSNOM")}</b>.");
							
							reportGenerator.setLogStatusPASS(message);
						}
						else{
							
							message += String.valueOf("<br>El doctor con el codigo <b>${queryResultMultipleRows.get(i).get("IPSCODSUP")}</b> no tiene el nombre <b>${queryResultMultipleRows.get(i).get("IPSNOM")}</b>.");
								
							throw new RuntimeException(message);
						}
					}
				}
			}
		}
			
		idDoctor = queryResultMultipleRows.get(0).get("IPSCODSUP");			
		nombreDoctor = queryResultMultipleRows.get(0).get("IPSNOM");
		codigoRemitente = queryResultMultipleRows.get(1).get("IPSCODSUP");
		
		queryResultMultipleRows.clear();
	}
		
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	/*
	message = "\n\n";
	
	message += String.valueOf("Codigo Doctor: ${idDoctor}" + "\n");
	message += String.valueOf("Nombre Doctor ${nombreDoctor}" + "\n");
	message += String.valueOf("Codigo Remitente ${codigoRemitente}" + "\n");
	
	message += "\n\n";
	
	println message;
	*/
	
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
		
		codigoDiagnostico = queryResult.get("DIA_DIA_CODIGO");		
		nombreDiagnostico = queryResult.get("DIA_DIA_DESCRIPCIO");
	}
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	
	/*
	message = "\n\n";
	
	message += String.valueOf("Codigo Diagnostico: ${codigoDiagnostico}" + "\n");
	message += String.valueOf("Nombre Diagnostico: ${nombreDiagnostico}" + "\n");
	
	message += "\n\n";
	
	println message;
	*/
	
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
				
				reportGenerator.setLogStatusPASS(message);				   
			}
			else{
				
				message += String.valueOf("<br>La descripcion del diagnostico obtenida no es semejante a la capturada desde la base de datos. La capturada desde la base de datos es: <b>${nombreDiagnostico}</b>");
				
				throw new RuntimeException(message);
			 }
		}
	}
	
	//************************************************************
	// Consulta del Web Service: /api/Autorizacion/Portal/Ingresar
	//************************************************************
	
	//SimpleDateFormat formatoFecha = new SimpleDateFormat("MM-dd-yyyy");
	//Date fecha = new Date();
	
	// Si deseamos consultar este Web Service
	if (consultarApiAutorizacionPortalIngresar) {
		
		// Si es un caso positivo
		if (consultarApiAutorizacionPortalIngresarCasoPositivo) {
			
			// Consultamos el Web Service
			responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalIngresar', [
				'codigoUsuario' : numeroAfiliado,
				'idInteraccion' : idInteraccion,
				'fecha' : fecha,
				'codigoRemitente' : codigoRemitente,
				'idDoctor' : idDoctor,
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
				'fecha' : fecha,
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
	
	if (ejecutarQueryProcedimientoPorPrestador || ejecutarQueryPrestacionNoContratada) {
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.procedimientos.remove("prePreDescripcion");
		QueryTemplate.procedimientos.remove("codigoServicioPrestadorSalud");
		QueryTemplate.procedimientos.remove("generoAfiliadoProcedimiento");
		QueryTemplate.procedimientos.remove("codigoPrestadorSalud");
		QueryTemplate.procedimientos.remove("codigoCobertura");
		QueryTemplate.procedimientos.remove("fechaAutorizacion");
		QueryTemplate.procedimientos.remove("condicionProcedimiento");
		QueryTemplate.procedimientos.remove("joinProcedimiento");
		QueryTemplate.procedimientos.remove("orderByRandom");
		QueryTemplate.procedimientos.remove("servicioConsulta");
		QueryTemplate.procedimientoPorPrestador.remove("procedimientos");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.procedimientos.add("prePreDescripcion", prePreDescripcion);
		QueryTemplate.procedimientos.add("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
		QueryTemplate.procedimientos.add("generoAfiliadoProcedimiento", generoAfiliadoProcedimiento);
		QueryTemplate.procedimientos.add("codigoPrestadorSalud", codigoPrestadorSalud);
		QueryTemplate.procedimientos.add("codigoCobertura", codigoCobertura);
		QueryTemplate.procedimientos.add("fechaAutorizacion", fechaAutorizacion);
		QueryTemplate.procedimientos.add("condicionProcedimiento", condicionProcedimiento);
		QueryTemplate.procedimientos.add("joinProcedimiento", joinProcedimiento);
		QueryTemplate.procedimientos.add("orderByRandom", orderByRandom);
		QueryTemplate.procedimientos.add("servicioConsulta", servicioConsulta);
		QueryTemplate.procedimientoPorPrestador.add("procedimientos", QueryTemplate.procedimientos.render().toString());
	}
	 
	if (ejecutarQueryProcedimientoPorPrestador) {
		
		if (queryResultMultipleRows.isEmpty()) {
			
			queryResultMultipleRows = dbConnection.executeQueryAndGetResultWithMultipleRows("procedimientos", QueryTemplate.procedimientos.render().toString());
			
			queryResultMultipleRows.get(0).put("codigoPrestadorSalud", codigoPrestadorSalud)
		}
		else{
			
			if (!queryResultMultipleRows.get(0).get("codigoPrestadorSalud").equals(codigoPrestadorSalud)) {
				
				queryResultMultipleRows = dbConnection.executeQueryAndGetResultWithMultipleRows("procedimientos", QueryTemplate.procedimientos.render().toString());
			}
		}
		
		queryResult = null;
		
		// Obtenemos todas las prestaciones con su tarifa
		buclePrestaciones:
		for(int i=0; i < queryResultMultipleRows.size(); i++){
			
			if (!obtenerTodasLasPrestaciones) {
				
				/*
				if (servicioConsulta.toString().equals(Keyword.SERVICIO_FARMACIA.value)) {
					
					queryResult = queryResultMultipleRows.get(i);
					
					break buclePrestaciones;
				}
				*/
				
				// ***************************************************************************************************************
				// Consulta del Web Service: /api/Autorizacion/Portal/TarifaProcedimiento para obtener la tarifa del procedimiento
				// ***************************************************************************************************************
				
				if (!queryResultMultipleRows.get(i).containsKey("MONTO_PRESTACION")) {
					
					// Consultamos el Web Service
					responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalTarifaProcedimiento', [
							'codigoUsuario' : numeroAfiliado,
							'idInteraccion' : idInteraccion,
							'codigoProcedimiento' : queryResultMultipleRows.get(i).get("CODIGO_PRESTACION")]));
					
					queryResultMultipleRows.get(i).put( "MONTO_PRESTACION", String.format( "%.2f", Double.parseDouble( responseContentString ) ) );
					
					montoAcumulado += Double.parseDouble(queryResultMultipleRows.get(i).get("MONTO_PRESTACION"));
				}
				
				if (Double.parseDouble(queryResultMultipleRows.get(i).get("MONTO_PRESTACION")) > 0.00) {
					
					if ( !mapaPrestacionesIngresadas.isEmpty() ) {
						
						if (!mapaPrestacionesIngresadas.containsValue(queryResultMultipleRows.get(i).get("DESCRIPCION_PRESTACION"))) {
							
							/*
							message = "\n\n";
							
							message += String.valueOf("Estas son los procedimientos ingresados\n\n");
							
							for(String key : mapaPrestacionesIngresadas.keySet()){
								
								message += String.valueOf("${mapaPrestacionesIngresadas.get(key)}\n");
							}
							
							message += String.valueOf("\nProcedimiento a ingresar: ${queryResultMultipleRows.get(i).get("DESCRIPCION_PRESTACION")}");
							
							message += "\n\n";
							
							println message;
							*/
							
							queryResult = queryResultMultipleRows.get(i);
							
							mapaPrestacionesIngresadas.put(queryResult.get("CODIGO_PRESTACION"), queryResult.get("DESCRIPCION_PRESTACION"));
							
							break buclePrestaciones;
						}
					}
					else{
						
						queryResult = queryResultMultipleRows.get(i);
						
						mapaPrestacionesIngresadas.put(queryResult.get("CODIGO_PRESTACION"), queryResult.get("DESCRIPCION_PRESTACION"));
						
						break buclePrestaciones;
					}
				}
				
				if ( i == ( queryResultMultipleRows.size() - 1) && montoAcumulado <= 0.00) {
					
					message = String.valueOf("Luego de la consulta al query:<br>");
					
					message += String.valueOf("<br><b>${QueryTemplate.procedimientos.render().toString()}</b><br>");
					
					message += String.valueOf("<br><b>Se obtuvieron ${queryResultMultipleRows.size()} prestacion(es).</b><br>");
					
					message += String.valueOf("<br><b>Todas las prestaciones presentaron un monto de RD\$0.00.</b><br>");
					
					throw new RuntimeException(message);
				}
			}
			else {
				
				// ***************************************************************************************************************
				// Consulta del Web Service: /api/Autorizacion/Portal/TarifaProcedimiento para obtener la tarifa del procedimiento
				// ***************************************************************************************************************
				
				// Consultamos el Web Service
				responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalTarifaProcedimiento', [
						'codigoUsuario' : numeroAfiliado,
						'idInteraccion' : idInteraccion,
						'codigoProcedimiento' : queryResultMultipleRows.get(i).get("CODIGO_PRESTACION")]));
				
				queryResultMultipleRows.get(i).put( "MONTO_PRESTACION", String.format( "%.2f", Double.parseDouble( responseContentString ) ) );
				
				if (queryResult == null) {
					
					if (Double.parseDouble(queryResultMultipleRows.get(i).get("MONTO_PRESTACION")) > 0.00) {
						
						queryResult = queryResultMultipleRows.get(i);
						
						mapaPrestacionesIngresadas.put(queryResult.get("CODIGO_PRESTACION"), queryResult.get("DESCRIPCION_PRESTACION"));
					}
				}
			}
		}
		
		if (validarProcedimientosSuperanMonto) {
			
			// Verifiquemos que contiene procedimientos que sumandolos superen los RD$15,000.00
			bucleVerificacionMonto:
			for(int i=0; i < queryResultMultipleRows.size(); i++){
				
				montoAcumulado += Double.parseDouble(queryResultMultipleRows.get(i).get("MONTO_PRESTACION"));
				
				if (montoAcumulado > 18000) {
					
					break bucleVerificacionMonto;
				}
				
				if ( i == ( queryResultMultipleRows.size() - 1) ) {
					
					message = String.valueOf("Luego de la consulta al query:<br>");
					
					message += String.valueOf("<br><b>${QueryTemplate.procedimientos.render().toString()}</b><br>");
					
					message += String.valueOf("<br><b>Se obtuvieron ${queryResultMultipleRows.size()} prestacion(es), que son las siguientes:</b><br>");
					
					for(Map<String, String> mapa : queryResultMultipleRows){
						
						for(String llave : mapa.keySet()){
							
							message += String.valueOf("<br>${llave}: ${mapa.get(llave)}");
						}
						
						message += String.valueOf("<br>");
					}
					
					message += String.valueOf("<br><b>La sumatoria del monto de todas las prestaciones no supera los RD\$15,000.00, siendo el monto acumulado RD\$${String.format("%,.2f", montoAcumulado)}.</b><br>");
					
					throw new RuntimeException(message);
				}
			}
		}
	}
	
	//****************************************
	// Consulta query Prestacion No Contratada
	//****************************************
	
	if(ejecutarQueryPrestacionNoContratada){
		
		// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
		QueryTemplate.prestacionNoContratada.remove("procedimientos");
		
		// Agregamos la(s) llave(s) y valor(es) al String Template
		QueryTemplate.prestacionNoContratada.add("procedimientos",  QueryTemplate.procedimientos.render().toString());
		
		// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
		// Ejecutamos la consulta y obtenemos los resultados
		queryResult = dbConnection.executeQueryAndGetResult("prestacionNoContratada", QueryTemplate.prestacionNoContratada.render().toString());
	}
	
	if (ejecutarQueryProcedimientoPorPrestador || ejecutarQueryPrestacionNoContratada) {
		
		codigoPrestacion = queryResult.get("CODIGO_PRESTACION");
		descripcionPrestacion = queryResult.get("DESCRIPCION_PRESTACION");
	}
	
	// ****************************
	// Imprimir estado de variables
	// ****************************
	/*
	message = "\n\n";
	
	message += String.valueOf("Codigo Prestacion: ${codigoPrestacion}" + "\n");
	message += String.valueOf("Descripcion Prestacion: ${descripcionPrestacion}" + "\n");
	
	message += "\n\n";
	
	println message;
	*/
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
				 
				reportGenerator.setLogStatusPASS(message);
			}
			else{
				
				message += String.valueOf("<br>La descripcion de la prestacion obtenida no es semejante a la capturada desde la base de datos. La capturada desde la base de datos es: <b>${descripcionPrestacion}</b>");
				
				throw new RuntimeException(message);
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
				 
				 reportGenerator.setLogStatusPASS(message);
			}
			else{
				
				message += String.valueOf("<br>Permitio seleccionar la prestacion <b>${descripcionPrestacion}</b> para el servicio <b>${nombreServicio}</b> y el prestador <b>${nombrePrestador}</b>");
				
				throw new RuntimeException(message);
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
				  
				  reportGenerator.setLogStatusPASS(message);
			 }
			 else{
				 
				 message += String.valueOf("<br>La descripcion de la prestacion obtenida no es semejante a la capturada desde la base de datos. La capturada desde la base de datos es: <b>${descripcionPrestacion}</b>");
				 
				throw new RuntimeException(message);
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
			  
			  // Cuerpo HTTP de la autorizacion
			  authorizationListHttpBodyContent.add(
				  String.format(
					  Keyword.AUTHORIZATION_HTTP_CONTENT_BODY.value,
					  codigoPrestacion,
					  habitacion,
					  String.valueOf(cantidad),
					  String.format("%.2f", cantidad * Double.parseDouble(tarifaProcedimiento)),
					  String.valueOf(valorConcesion),
					  String.valueOf(valorExcepcion)
				 )
			  );
			  
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
	  
	 //String codigoAutorizacion = null;
	  
	 // Si deseamos consultar este Web Service
	 if (consultarApiAutorizacionPortalAutorizar) {
		 
		 // Si es un caso positivo
		 if (consultarApiAutorizacionPortalAutorizarCasoPositivo) {
			 
			 responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAutorizar', [
				 'codigoUsuario' : numeroAfiliado,
				 'idInteraccion' : idInteraccion]), false, null, authorizationListHttpBodyContent, true);
			 
			 //codigoAutorizacion = responseContentString;
			  
			 println "\n\n" + "Codigo Autorizacion: " + responseContentString + "\n\n";
		 }
		 else{
			 
			 // En caso de querer agregar un mensaje adicional de error
			 if (!consultarApiAutorizacionPortalAutorizarMensajeError.toString().isEmpty()) {
				 
				 responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAutorizar', [
					 'codigoUsuario' : numeroAfiliado,
					 'idInteraccion' : idInteraccion]), true, consultarApiAutorizacionPortalAutorizarMensajeError.toString(), authorizationListHttpBodyContent, true);
			  	
			 }
			 else{
				 
				 responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalAutorizar', [
					 'codigoUsuario' : numeroAfiliado,
					 'idInteraccion' : idInteraccion]), true, null, authorizationListHttpBodyContent, true);
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
	  commonAction.getMapStringObject().put("servicioConsulta", servicioConsulta);
	  commonAction.getMapStringObject().put("numeroAfiliado", numeroAfiliado);
	  commonAction.getMapStringObject().put("tipoAfiliado", tipoAfiliado);
	  commonAction.getMapStringObject().put("nombreAfiliado", nombreAfiliado);
	  commonAction.getMapStringObject().put("codigoCobertura", codigoCobertura);
	  commonAction.getMapStringObject().put("generoAfiliado", generoAfiliado);
	  commonAction.getMapStringObject().put("numeroDocumentoAfiliado", numeroDocumentoAfiliado);
	  commonAction.getMapStringObject().put("estadoPrestador", estadoPrestador);
	  commonAction.getMapStringObject().put("codigoPrestadorSalud", codigoPrestadorSalud);
	  commonAction.getMapStringObject().put("codigoServicioPrestadorSalud", codigoServicioPrestadorSalud);
	  commonAction.getMapStringObject().put("nombrePrestador", nombrePrestador);
	  commonAction.getMapStringObject().put("nombreServicio", nombreServicio);
	  commonAction.getMapStringObject().put("codigoSucursal", codigoSucursal);
	  commonAction.getMapStringObject().put("codigoDiagnostico", codigoDiagnostico);
	  commonAction.getMapStringObject().put("nombreDiagnostico", nombreDiagnostico);
	  commonAction.getMapStringObject().put("codigoPrestacion", codigoPrestacion);
	  commonAction.getMapStringObject().put("descripcionPrestacion", descripcionPrestacion);
	  commonAction.getMapStringObject().put("fechaAutorizacion", fechaAutorizacion);
	  commonAction.getMapStringObject().put("tarifaProcedimiento", tarifaProcedimiento);
	  commonAction.getMapStringObject().put("idInteraccion", idInteraccion);
	  commonAction.getMapStringObject().put("fecha", fecha);
	  commonAction.getMapStringObject().put("cantidad", cantidad);
	  commonAction.getMapStringObject().put("mapaPrestacionesIngresadas", mapaPrestacionesIngresadas);
	  commonAction.getMapStringObject().put("prePreDescripcion", prePreDescripcion);
	  commonAction.getMapStringObject().put("orderByRandom", orderByRandom);
	  commonAction.getMapStringObject().put("idDoctor", idDoctor);
	  commonAction.getMapStringObject().put("nombreDoctor", nombreDoctor);
	  commonAction.getMapStringObject().put("validarProcedimientosSuperanMonto", validarProcedimientosSuperanMonto);
	  commonAction.getMapStringObject().put("montoAcumulado", montoAcumulado);
	  commonAction.getMapStringObject().put("queryResultMultipleRows", queryResultMultipleRows);
	  commonAction.getMapStringObject().put("authorizationListHttpBodyContent", authorizationListHttpBodyContent);
	  commonAction.getMapStringObject().put("generoAfiliadoProcedimiento", generoAfiliadoProcedimiento);
	  commonAction.getMapStringObject().put("generoAfiliadoServicio", generoAfiliadoServicio);
	  commonAction.getMapStringObject().put("mapaPrestadorServicio", mapaPrestadorServicio);
	  commonAction.getMapStringObject().put("codigoRemitente", codigoRemitente);
	  
	  return commonAction.getMapStringObject();
	  
} catch (Exception e) {

	reportGenerator.setLogStatusFAIL(e.getMessage());
	KeywordUtil.markErrorAndStop(e.getMessage());
}