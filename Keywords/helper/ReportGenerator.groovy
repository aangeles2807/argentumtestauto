package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.jsoup.Jsoup

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
	private ArrayList<Object> messageType;
	private ArrayList<String> messageContent;

	public ReportGenerator(){

		messageType = new ArrayList<String>();

		messageContent = new ArrayList<String>();
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

	/**
	 *
	 * Set the name that will be shown in report.
	 *
	 * @param testName - Test name.
	 */
	public void setTestName(String testName){

		messageType.add(helper.Keyword.TEST_NAME.value);
		messageContent.add(testName);
	}

	public void setLogStatusINFO(String infoMessage){

		infoMessage.replace("\n", "<br>");

		messageType.add(LogStatus.INFO);
		messageContent.add(infoMessage);
	}

	public void setLogStatusPASS(String passMessage){

		passMessage.replace("\n", "<br>");

		messageType.add(LogStatus.PASS);
		messageContent.add(passMessage);
	}

	public void setLogStatusWARNING(String warningMessage){

		warningMessage.replace("\n", "<br>");

		messageType.add(LogStatus.WARNING);
		messageContent.add(warningMessage);
	}

	public void setLogStatusSKIP(String skipMessage){

		skipMessage.replace("\n", "<br>");

		messageType.add(LogStatus.SKIP);
		messageContent.add(skipMessage);
	}

	public void setLogStatusERROR(String errorMessage){

		errorMessage.replace("\n", "<br>");

		messageType.add(LogStatus.ERROR);
		messageContent.add(errorMessage);
	}

	public void setLogStatusFAIL(String failMessage){

		failMessage.replace("\n", "<br>");

		messageType.add(LogStatus.FAIL);
		messageContent.add(failMessage);
	}

	public void setLogStatusFATAL(String fatalMessage){

		fatalMessage.replace("\n", "<br>");

		messageType.add(LogStatus.FATAL);
		messageContent.add(fatalMessage);
	}

	public void setLogStatusUNKNOWN(String unknownMessage){

		unknownMessage.replace("\n", "<br>");

		messageType.add(LogStatus.UNKNOWN);
		messageContent.add(unknownMessage);
	}

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

		for(int i = 0; i < messageType.size(); i++){

			if (messageType.get(i) == helper.Keyword.TEST_NAME.value) {

				extentTest = extentReports.startTest(messageContent.get(i));
			}
			else if (messageType.get(i) == LogStatus.INFO) {

				extentTest.log(LogStatus.INFO, messageContent.get(i));
			}
			else if (messageType.get(i) == LogStatus.PASS) {

				extentTest.log(LogStatus.PASS, messageContent.get(i));

			}else if (messageType.get(i) == LogStatus.WARNING) {

				extentTest.log(LogStatus.WARNING, messageContent.get(i));

			}else if (messageType.get(i) == LogStatus.SKIP) {

				extentTest.log(LogStatus.SKIP, messageContent.get(i));

			}else if (messageType.get(i) == LogStatus.ERROR) {

				extentTest.log(LogStatus.ERROR, messageContent.get(i));

			}else if (messageType.get(i) == LogStatus.FAIL) {

				extentTest.log(LogStatus.FAIL, messageContent.get(i));

			}else if (messageType.get(i) == LogStatus.FATAL) {

				extentTest.log(LogStatus.FATAL, messageContent.get(i));

			}else if (messageType.get(i) == LogStatus.UNKNOWN) {

				extentTest.log(LogStatus.UNKNOWN, messageContent.get(i));
			}
		}

		extentReports.endTest(extentTest);

		extentReports.flush();

		extentReports = null;
		extentTest = null;
	}

	public static void main(String[] args){

		try {

			String content = "";
			
			content = Jsoup.parse(new File("C:\\Katalon_Studio_Projects\\Universal Apolo\\Reports\\091146326_29-07-2020.html"), "UTF-8").toString();
			
			//println content;

			// content = content.replaceAll("", "");

			content = content.replaceAll("ExtentReports 2.0", "Reporte de Pruebas");
			
			content = content.replaceAll("<a class=\"logo-content\" href=\"http:\\/\\/extentreports.relevantcodes.com\"> <span>ExtentReports<\\/span> <\\/a>",
					"<a class=\"logo-content\" href=\"#\"> <span>Universal (Apolo)<\\/span> <\\/a>");

			content = content.replaceAll("<span class=\"report-name\">Automation Report<\\/span>",
					"<span class=\"report-name\">Reporte De Pruebas Automatizadas (APIs-Web)<\\/span>");

			content = content.replaceAll("<th>Status<\\/th>", "<th>Estado<\\/th>");
			content = content.replaceAll("<th>Timestamp<\\/th>", "<th><center>Tiempo</center><\\/th>");
			content = content.replaceAll("<th>Details<\\/th>", "<th>Detalles<\\/th>");

			FileWriter fileWriter = new FileWriter("C:\\Katalon_Studio_Projects\\Universal Apolo\\Reports\\prueba.html");

			fileWriter.write(content);

			fileWriter.close();

		}
		catch (IOException e) {

			e.printStackTrace();
		}

		println "FIN";
	}
}
