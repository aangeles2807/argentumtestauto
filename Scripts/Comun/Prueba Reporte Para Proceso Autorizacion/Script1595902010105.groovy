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
ArrayList<Map<String, String>> responseContent = null;
ResponseObject responseObject = null;
Map<String, String> queryResult = null;

try {
	
	dbConnection = DBConnection.getDBConnectionUniqueIntance();
	reportGenerator = ReportGenerator.getUniqueIntance();
	
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
	/*
	responseContent = commonAction.getResponseContentIntoMap(findTestObject('Affiliate/Afiliado', ["descripcion" : numeroAfiliado]));
	
	println "\n" + "Informacion del Afiliado Obtenida de API" + "\n";
	
	for(Map<String, String> APIsconsultContent : responseContent){
		
		for(String key : APIsconsultContent.keySet()){
			
			println key + " : " + APIsconsultContent.get(key);
		}
	}
	
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
	*/
	responseContent = commonAction.getResponseContentIntoMap(findTestObject('Authorization/AutorizacionPortalValidarCobertura', [
		'codigoUsuario' : numeroAfiliado,
		'idInteraccion' : '0',
		'codigoPrestadorSalud' : codigoPrestadorSalud,
		'codigoSucursalPrestadorSalud' : '0',
		'numeroAfiliado' : numeroAfiliado,
		'codigoServicio' : codigoServicioPrestadorSalud]));
	
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