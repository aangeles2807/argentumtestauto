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
	private Map<String, String> queryResult = null;

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

	public void close(ResultSet resultSet) {

		try {

			if (resultSet != null) {

				resultSet.close();

				resultSet = null;
			}

		} catch (SQLException sqlException) {

			sqlException.printStackTrace(System.out);
		}
	}

	public void close(OracleResultSet oracleResultSet) {

		try {

			if (oracleResultSet != null) {

				oracleResultSet.close();

				oracleResultSet = null;
			}

		} catch (SQLException sqlException) {

			sqlException.printStackTrace(System.out);
		}
	}

	public void close(Statement statement) {

		try {

			if (statement != null) {

				statement.close();

				statement = null;
			}

		} catch (SQLException sqlException) {

			sqlException.printStackTrace(System.out);
		}
	}

	public void close(PreparedStatement preparedStatement) {

		try {

			if (preparedStatement != null) {

				preparedStatement.close();

				preparedStatement = null;
			}

		} catch (SQLException sqlException) {

			sqlException.printStackTrace(System.out);
		}
	}

	public void close(CallableStatement callableStatement) {

		try {

			if (callableStatement != null) {

				callableStatement.close();

				callableStatement = null;
			}

		} catch (SQLException sqlException) {

			sqlException.printStackTrace(System.out);
		}
	}

	public void close(Connection connection) {

		try {

			if (connection != null) {

				connection.close();

				connection = null;
			}

		} catch (SQLException sqlException) {

			sqlException.printStackTrace(System.out);
		}
	}

	/**
	 * Execute the specific query and return its results in a Map.
	 * 
	 * @param queryToExecute
	 * @return - A Map of String (Key or Column) and String (Value or Cell). 
	 */
	public Map<String, String> executeQueryAndGetResult(String queryName, String queryToExecute){

		try {

			if(queryResult == null){

				queryResult = new HashMap<String, String>();
			}
			else{

				queryResult.clear();
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
					
					queryResult.put(resultSetMetaData.getColumnName(i).trim(), resultSet.getObject(i).toString().trim());
				}	
			}

		}
		catch (Exception e) {

			e.printStackTrace(System.out);
		}

		return queryResult;
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
