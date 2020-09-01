package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.sql.CallableStatement
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException
import java.sql.Statement
import java.time.Duration
import java.time.Instant

import javax.swing.JOptionPane

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import oracle.jdbc.driver.OracleResultSet

public class DBConnection {

	private final String JDBC_URL = "jdbc:oracle:thin:@ars01desdb:1521:arsx9";
	private final String JDBC_USER = "eps";
	private final String JDBC_PASS = "eps99";
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private ResultSetMetaData resultSetMetaData = null;
	private Instant start = null;
	private Instant end = null;
	private Duration interval = null;
	private Map<String, String> queryResultInMap = null;
	private List<Map<String, String>> queryResultInArrayListOfMap;
	private Map<String, String> mapStringStringTemp;
	private String message;
	private ReportGenerator reportGenerator;

	// Singleton Pattern
	//*************************************************************
	private static DBConnection commonDBConnection = null;

	public static DBConnection getDBConnectionUniqueIntance() {

		if (commonDBConnection == null) {

			commonDBConnection = new DBConnection();
		}

		return commonDBConnection;
	}
	//*************************************************************

	public Connection getConnectionDB() throws SQLException {

		return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
	}

	/**
	 * Execute the specific query and return its results in a Map.
	 * 
	 * Note: for result of one row.
	 * 
	 * @param queryToExecute
	 * @return - A Map of String (Key or Column) and String (Value or Cell). 
	 */
	public Map<String, String> executeQueryAndGetResult(String queryName, String queryToExecute){

		reportGenerator = ReportGenerator.getUniqueIntance();
		
		System.out.println(String.valueOf("\n\nEjecucion de query ${queryName}\n\n"));

		//println "\n\n" + queryToExecute + "\n\n";

		if(queryResultInMap == null){

			queryResultInMap = new HashMap<String, String>();
		}
		else{

			queryResultInMap.clear();
		}

		// Nos conectamos a la Base de Datos
		if(connection == null){

			connection = getConnectionDB();
		}

		// Creamos la sentencia
		if(statement == null){

			statement = connection.createStatement();
		}

		// Inicio de tiempo
		//****************************
		start = Instant.now();
		//****************************

		// Ejecutamos la consulta y obtenemos los resultados
		resultSet = statement.executeQuery(queryToExecute);

		//***************************************************************************************************************
		// Fin de tiempo
		end = Instant.now();

		// Calculo de tiempo
		interval = Duration.between(start, end);

		// Impresion de duracion del query en ejecutarse
		System.out.println(String.valueOf("\n\nEl query ${queryName} ejecutandose se demoro: " + interval.getSeconds() + " segundo(s) \n\n"));
		//***************************************************************************************************************

		resultSetMetaData = resultSet.getMetaData();

		while (resultSet.next()) {

			for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){

				queryResultInMap.put(resultSetMetaData.getColumnName(i).trim(), resultSet.getObject(i).toString().trim());
			}
		}

		resultSetMetaData = null;
		
		// **************
		// Report message
		// **************
		
		if (queryResultInMap.isEmpty()) {
			
			message = String.valueOf("El query <b>${queryName}</b>, el cual ejecuta la siguiente sentencia en la Base de Datos:<br>");
			
			message += String.valueOf("<br>${queryToExecute}<br>");
	
			message += String.valueOf("<br><b>No obtuvo información</b><br>");
			
			//message += String.valueOf("<b>Observación: Este tiempo es medido desde que se ejecuta la consulta/query hasta que se recibe la respuesta.</b>");
	
			throw new RuntimeException(message);
		}
		else{
			
			message = String.valueOf("El query <b>${queryName}</b>, el cual ejecuta la siguiente sentencia en la Base de Datos:<br>");
			
			message += String.valueOf("<br>${queryToExecute}<br>");
	
			message += String.valueOf("<br>Obtuvo la siguiente información:<br><br>");
	
			for(String key : queryResultInMap.keySet()){
	
				message += key + " : " + queryResultInMap.get(key) + "<br>";
			}
	
			message += String.valueOf("<br>En un lapso de tiempo de <b>${interval.getSeconds()} segundo(s)</b>.<br><br>");
	
			//message += String.valueOf("<b>Observación: Este tiempo es medido desde que se ejecuta la consulta/query hasta que se recibe la respuesta.</b>");
	
			reportGenerator.setLogStatusINFO(message);
		}

		return queryResultInMap;
	}
	
	/**
	 * Execute the specific query and return its results in a Map.
	 * 
	 * Note: for result of multiple rows.
	 * 
	 * @param queryToExecute
	 * @return - A Map of String (Key or Column) and String (Value or Cell). 
	 */
	public List<Map<String, String>> executeQueryAndGetResultWithMultipleRows(String queryName, String queryToExecute){
		
		reportGenerator = ReportGenerator.getUniqueIntance();
		
		System.out.println(String.valueOf("\n\nEjecucion de query ${queryName}\n\n"));

		//println "\n\n" + queryToExecute + "\n\n";

		if(queryResultInArrayListOfMap == null){

			queryResultInArrayListOfMap = new ArrayList<Map<String, String>>();
		}
		else{

			queryResultInArrayListOfMap.clear();
		}

		// Nos conectamos a la Base de Datos
		if(connection == null){

			connection = getConnectionDB();
		}

		// Creamos la sentencia
		if(statement == null){

			statement = connection.createStatement();
		}

		// Inicio de tiempo
		//****************************
		start = Instant.now();
		//****************************

		// Ejecutamos la consulta y obtenemos los resultados
		resultSet = statement.executeQuery(queryToExecute);
		
		//***************************************************************************************************************
		// Fin de tiempo
		end = Instant.now();
 
		// Calculo de tiempo
		interval = Duration.between(start, end);
 
		// Impresion de duracion del query en ejecutarse
		System.out.println(String.valueOf("\n\nEl query ${queryName} ejecutandose se demoro: " + interval.getSeconds() + " segundo(s) \n\n"));
		//***************************************************************************************************************

		resultSetMetaData = resultSet.getMetaData();
		
		whileQueryResult:
		while (resultSet.next()) {
			
			mapStringStringTemp = new HashMap<String, String>();

			for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){

				mapStringStringTemp.put(resultSetMetaData.getColumnName(i).trim(), resultSet.getObject(i).toString().trim());
			}
			
			queryResultInArrayListOfMap.add(mapStringStringTemp);
			
			if (queryResultInArrayListOfMap.size() == 50) {
				
				break whileQueryResult;
			}
		}

		resultSetMetaData = null;
		
		// **************
		// Report message
		// **************
		
		if (queryResultInArrayListOfMap.isEmpty()) {
			
			message = String.valueOf("El query <b>${queryName}</b>, el cual ejecuta la siguiente sentencia en la Base de Datos:<br>");
			
			message += String.valueOf("<br>${queryToExecute}<br>");
	
			message += String.valueOf("<br><b>No obtuvo información</b><br>");
			
			//message += String.valueOf("<b>Observación: Este tiempo es medido desde que se ejecuta la consulta/query hasta que se recibe la respuesta.</b>");
	
			throw new RuntimeException(message);
		}
		else{
			
			message = String.valueOf("El query <b>${queryName}</b>, el cual ejecuta la siguiente sentencia en la Base de Datos:<br>");
			
			message += String.valueOf("<br>${queryToExecute}<br>");
	
			message += String.valueOf("<br>Obtuvo la siguiente información:<br><br>");
			
			for(int i = 0; i < queryResultInArrayListOfMap.size(); i++){
				
				for(String key : queryResultInArrayListOfMap.get(i).keySet()){
					
					message += key + " : " + queryResultInArrayListOfMap.get(i).get(key) + "<br>";
				}
				
				message += "<br>";
			}
	
			message += String.valueOf("<br>En un lapso de tiempo de <b>${interval.getSeconds()} segundo(s)</b>.<br><br>");
	
			//message += String.valueOf("<b>Observación: Este tiempo es medido desde que se ejecuta la consulta/query hasta que se recibe la respuesta.</b>");
	
			reportGenerator.setLogStatusINFO(message);
		}

		return queryResultInArrayListOfMap;
	}
	

	public Statement getStatement(){

		return statement;
	}

	public ResultSet getResultSet(){

		return resultSet;
	}

	public ResultSetMetaData getResultSetMetaData(){

		return ResultSetMetaData;
	}

	public Connection getConnection(){

		return connection;
	}
}
