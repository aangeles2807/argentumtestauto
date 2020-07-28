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

//*******************************
// Data Base and Report Variables
//*******************************
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
String responseContentText = null;
ResponseObject responseObject = null;
Map<String, String> queryResult = null;

try {
	
	dbConnection = DBConnection.getDBConnectionUniqueIntance();
	reportGenerator = ReportGenerator.getUniqueIntance();
	
	// Agregamos la(s) llave(s) y valor(es) al String Template
	QueryTemplate.afiliadoMPP.add("conditions", Keyword.AFILIADO_MPP_ACTIVO.value);
	
	// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
	tipoAfiliado = QueryTemplate.afiliadoMPP.render().toString();
	
	// Eliminamos la(s) llave(s) y valor(es) para dejar el template en su estado original
	QueryTemplate.afiliadoMPP.remove("conditions");
	
	// Ejecutamos la consulta y obtenemos los resultados
	queryResult = dbConnection.executeQueryAndGetResult("afiliadoMPP", tipoAfiliado);
	
	String numeroAfiliado = "639070";
	String tipoAfiliadoBD = "";
	String nombreAfiliadoBD = "MARCIA MERCEDES MARTINEZ TEJADA";
	String codigoCobertura = "VT400";
	String generoAfiliado = "F";
	String numeroDocumentoAfiliado = "05600812282";
	
	String codigoPrestadorSalud = "07327";
	String codigoServicioPrestadorSalud = "85";
	String nombrePrestadorBD = "HECTOR DE JESUS PEÑA GARABITO";
	String nombreServicioBD = "CIRUGÍAS GINECOLÓGICAS";
	
	//*********************************
	// API de consulta de # de afiliado
	//*********************************
	
	// Consultamos el API
	
	responseContentMap = commonAction.getResponseContentIntoMapOrString(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]));
	
	verifyCodeAndService:
	for(int i = 0; responseContentMap.size(); i++){
		
		if (responseContentMap.get(i).get(Keyword.KEY_IDENTIFICACION.value).equals(numeroDocumentoAfiliado)) {
			
			KeywordUtil.markPassed(String.valueOf("El API: ${commonAction.getApiPath()} presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
			reportGenerator.setLogStatusPASS(String.valueOf("El API: ${commonAction.getApiPath()} presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"))
			
			break verifyCodeAndService;
		}
			
		if ( i == (responseContentMap.size() - 1) ) {
			
			KeywordUtil.markFailed(String.valueOf("El API: ${commonAction.getApiPath()} no presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"));
			reportGenerator.setLogStatusFAIL(String.valueOf("El API: ${commonAction.getApiPath()} no presento el numero Documento Afiliado ${numeroDocumentoAfiliado}"))
		}
	}
	
	responseContentText = commonAction.getResponseContentIntoMapOrString(findTestObject('Authorization/AutorizacionPortalValidarCobertura', [
		'codigoUsuario' : numeroAfiliado,
		'idInteraccion' : '0',
		'codigoPrestadorSalud' : codigoPrestadorSalud,
		'codigoSucursalPrestadorSalud' : '0',
		'numeroAfiliado' : numeroAfiliado,
		'codigoServicio' : codigoServicioPrestadorSalud]));
	
	String idInteraccion = responseContentText;
	
	println "\n\n" + "idInteraccion: " + idInteraccion + "\n\n";
	
	idInteraccion = null;
} 
catch (Exception e) {
	
	KeywordUtil.markError(e.getMessage());

	reportGenerator.setLogStatusERROR(e.getMessage());
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