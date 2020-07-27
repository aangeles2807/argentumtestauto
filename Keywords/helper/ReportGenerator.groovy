package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.stringtemplate.v4.ST
import org.stringtemplate.v4.compiler.STParser.ifstat_return

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
import com.relevantcodes.extentreports.ExtentReports
import com.relevantcodes.extentreports.ExtentTest
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable

public class ReportGenerator {

	private ExtentReports extentReports;
	private ExtentTest extentTest;
	private CommonAction commonAction;
	private String actualTime;
	private String actualDate;

	public ReportGenerator(){
	}

	// Singleton Pattern
	//******************************************************************************
	private static ReportGenerator commonReportGenerator = null;

	public static ReportGenerator getUniqueIntance() {

		if (commonReportGenerator == null) {

			commonReportGenerator = new ReportGenerator();
		}

		return commonReportGenerator;
	}
	//******************************************************************************

	public void generateReport(){

		commonAction = CommonAction.getUniqueIntance();

		actualTime = commonAction.getActualTimeInSpecificFormat("HH:mm:ss:SSS").replace(":", "");
		actualDate = commonAction.getActualDateInSpecificFormat("dd-MM-yyyy");

		if (extentReports == null) {

			extentReports = new ExtentReports(String.valueOf(commonAction.getProjectpath() + "\\Reports\\${actualTime}_${actualDate}.html"));
		}
		else{

			extentReports.setFilePath(String.valueOf(commonAction.getProjectpath() + "\\Reports\\${actualTime}_${actualDate}.html"))
		}

		// Caso Prueba 1

		extentTest = extentReports.startTest("Prueba 1");

		extentTest.log(LogStatus.INFO, "El Querys: <br >" + QueryTemplate.afiliadoMPP.render().toString());

		extentTest.log(LogStatus.PASS, "Prueba Exitosa 1");

		extentTest.log(LogStatus.PASS, "Prueba Exitosa 2");

		// Caso Prueba 2

		extentTest = extentReports.startTest("Prueba 2");

		extentTest.log(LogStatus.INFO, "El Querys: <br >" + QueryTemplate.afiliadoMPPoPBS.render().toString());

		extentTest.log(LogStatus.PASS, "Prueba Exitosa 1");

		extentTest.log(LogStatus.PASS, "Prueba Exitosa 2");

		extentTest.log(LogStatus.ERROR, "Prueba Error 1");

		extentReports.endTest(extentTest);

		extentReports.flush();
	}

	public static void main(String[] args) {

		ReportGenerator reportGenerator = ReportGenerator.getUniqueIntance();

		reportGenerator.generateReport();
	}
}
