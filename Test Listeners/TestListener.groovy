import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import javax.swing.JOptionPane

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import helper.DBConnection
import helper.ReportGenerator

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TestListener {
	
	private DBConnection dbConnection;
	private ReportGenerator reportGenerator;
	private String testCaseName;
	private String testSuiteName;
	
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseVariables()
		
		dbConnection = DBConnection.getDBConnectionUniqueIntance();
		reportGenerator = ReportGenerator.getUniqueIntance();
		
		// Get the Test Case Name
		testCaseName = testCaseContext.getTestCaseId().split("/")[ ( testCaseContext.getTestCaseId().split("/").length - 1 ) ];
		
		reportGenerator.setTestName(testCaseName);
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseStatus()
		
		//if(dbConnection.getConnectionvariable() == null){dbConnection.close(dbConnection.getConnectionvariable());}
		
		if (testSuiteName == null) {
			
			if (dbConnection.getResultSet() != null) {
				
				dbConnection.getResultSet().close()
			}
			
			if (dbConnection.getStatement() != null) {
				
				dbConnection.getConnection().close()
			}
			
			if (dbConnection.getConnection() != null) {
				
				dbConnection.getConnection().close()
			}
			
			reportGenerator.setTestScriptName(testCaseName);
			
			reportGenerator.generateReport();
		}
	}

	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		
		dbConnection = DBConnection.getDBConnectionUniqueIntance();
		reportGenerator = ReportGenerator.getUniqueIntance();
		
		// Get the Test Suite Name
		testSuiteName = testSuiteContext.getTestSuiteId().split("/")[ ( testSuiteContext.getTestSuiteId().split("/").length - 1 ) ];
		
		println testSuiteContext.getTestSuiteId()
		println testSuiteContext.getTestSuiteId()
	}

	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
		
		if (dbConnection.getResultSet() != null) {
			
			dbConnection.getResultSet().close()
		}
		
		if (dbConnection.getStatement() != null) {
			
			dbConnection.getConnection().close()
		}
		
		if (dbConnection.getConnection() != null) {
			
			dbConnection.getConnection().close()
		}
		
		reportGenerator.setTestScriptName(testSuiteName);
		
		reportGenerator.generateReport();
	}
}