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

try {
	
	for(int indice=0; indice <= cantidadIteracion; indice++){
		
		dbConnection = DBConnection.getDBConnectionUniqueIntance();
		reportGenerator = ReportGenerator.getUniqueIntance();
		
		/*
		 * Obtencion de la informacion del afiliado segun el tipo
		 * desde la base de datos.
		 * 
		 * Las cuales son:
		 * 
		 * MPP
		 * PBS
		 * MPP o PBS (Aleatoriamente se toma uno de los dos)
		 * 
		 */
		if (queryTipoAfiliado.toString().equals(Keyword.AFILIADO_MPP.value)) {
			
			// Agregamos la(s) llave(s) y valor(es) al String Template
			QueryTemplate.afiliadoMPP.add("conditions", condicionAfiliadoMPP);
			
			// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
			// Ejecutamos la consulta y obtenemos los resultados
			queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPP", QueryTemplate.afiliadoMPP.render().toString());
			
			// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
			QueryTemplate.afiliadoMPP.remove("conditions");
		}
		else if (queryTipoAfiliado.toString().equals(Keyword.AFILIADO_PBS.value)) {
			
			// Agregamos la(s) llave(s) y valor(es) al String Template
			QueryTemplate.afiliadoPBS.add("conditions", condicionAfiliadoPBS)
			
			// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
			// Ejecutamos la consulta y obtenemos los resultados
			queryResult = dbConnection.executeQueryAndGetResult("afiliadoPBS", QueryTemplate.afiliadoPBS.render().toString());
			
			// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
			QueryTemplate.afiliadoPBS.remove("conditions");
		
		}
		else if (queryTipoAfiliado.toString().equals(Keyword.AFILIADO.value)) {
			
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
		if (queryTipoAfiliado.toString().equals(Keyword.AFILIADO_MPP.value) ||
			queryTipoAfiliado.toString().equals(Keyword.AFILIADO_PBS.value) ||
			queryTipoAfiliado.toString().equals(Keyword.AFILIADO.value)) {
			
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
		
		if (queryPrestadorServicio.toString().equals(Keyword.PRESTADOR_SERVICIO.value)) {
			
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
		
		//*********************************
		// API de consulta de # de afiliado
		//*********************************
		
		// Consultamos el API
		responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]));
		
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
				
				KeywordUtil.markPassed(String.valueOf("El API /api/Afiliado presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
				reportGenerator.setLogStatusPASS(String.valueOf("El API /api/Afiliado presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
				
				break verifyCodeAndService;
			}
				
			if ( i == (responseContentMap.size() - 1) ) {
				
				KeywordUtil.markFailed(String.valueOf("El API /api/Afiliado no presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
				reportGenerator.setLogStatusFAIL(String.valueOf("El API /api/Afiliado no presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
			}
		}
		
		//*******************************
		// API de consulta de # Prestador
		//*******************************
		
		String nombrePrestadorAPI = null;
		
		responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSalud', ['descripcion' : codigoPrestadorSalud]));
		
		for (Map<String, String> mapKeyAndValue : responseContentMap) {
			
			nombrePrestadorAPI = mapKeyAndValue.get(Keyword.KEY_NOMBRE.value);
		}
		
		// Validamos el nombre, desde el API Vs Base de Datos
		if (nombrePrestadorAPI.equals(nombrePrestadorBD)) {
			
			KeywordUtil.markPassed(String.valueOf("El API presento el nombre del prestador ${nombrePrestadorAPI} y la Base de Datos ${nombrePrestadorBD}"));
			reportGenerator.setLogStatusPASS(String.valueOf("El API presento el nombre del prestador ${nombrePrestadorAPI} y la Base de Datos ${nombrePrestadorBD}"));
			 
		}else{
		
			KeywordUtil.markFailed(String.valueOf("El API presento el nombre del prestador ${nombrePrestadorAPI} y la Base de Datos ${nombrePrestadorBD}"));
			reportGenerator.setLogStatusFAIL(String.valueOf("El API presento el nombre del prestador ${nombrePrestadorAPI} y la Base de Datos ${nombrePrestadorBD}"));
		}
		
		//******************************************
		// API de consulta de # Prestador Servicios
		//******************************************
		
		responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('HealthProvider/PrestadorSaludServicios', ['codigoPrestadorSalud' : codigoPrestadorSalud]));
		
		verifyCodeAndService:
		for(int i = 0; responseContentMap.size(); i++){
			
			if (responseContentMap.get(i).get(Keyword.KEY_CODIGO.value).equals(codigoServicioPrestadorSalud)
				&& responseContentMap.get(i).get(Keyword.KEY_DESCRIPCION.value).equals(nombreServicioBD)) {
				
				KeywordUtil.markPassed(String.valueOf("El API /api/PrestadorSalud/Servicios mediante la consulta del codigo prestador ${codigoPrestadorSalud} contiene el el codigo ${codigoServicioPrestadorSalud} con el servicio ${nombreServicioBD}."));
				reportGenerator.setLogStatusPASS(String.valueOf("El API /api/PrestadorSalud/Servicios mediante la consulta del codigo prestador ${codigoPrestadorSalud} contiene el el codigo ${codigoServicioPrestadorSalud} con el servicio ${nombreServicioBD}."));
				
				break verifyCodeAndService;
			}
				
			if ( i == (responseContentMap.size() - 1) ) {
				
				KeywordUtil.markFailed(String.valueOf("El API /api/PrestadorSalud/Servicios mediante la consulta del codigo prestador ${codigoPrestadorSalud} no contiene el el codigo ${codigoServicioPrestadorSalud} con el servicio ${nombreServicioBD}."));
				reportGenerator.setLogStatusFAIL(String.valueOf("El API /api/PrestadorSalud/Servicios mediante la consulta del codigo prestador ${codigoPrestadorSalud} no contiene el el codigo ${codigoServicioPrestadorSalud} con el servicio ${nombreServicioBD}."));
			}
		}
		
		
		//**********************************************************
		// API de consulta de Autorizacion Portal Validar Cobertura
		//**********************************************************
		
		responseContentString = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalValidarCobertura', [
			'codigoUsuario' : numeroAfiliado,
			'idInteraccion' : '0',
			'codigoPrestadorSalud' : codigoPrestadorSalud,
			'codigoSucursalPrestadorSalud' : codigoSucursal,
			'numeroAfiliado' : numeroAfiliado,
			'codigoServicio' : codigoServicioPrestadorSalud]));
	}
	  
} catch (Exception e) {

	KeywordUtil.markError(e.getMessage());
	
	reportGenerator.setLogStatusFAIL(e.getMessage());
}