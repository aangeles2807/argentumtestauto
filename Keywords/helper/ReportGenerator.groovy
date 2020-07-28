package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

		messageType.add(LogStatus.INFO);
		messageContent.add(infoMessage);
	}

	public void setLogStatusPASS(String passMessage){

		messageType.add(LogStatus.PASS);
		messageContent.add(passMessage);
	}

	public void setLogStatusWARNING(String warningMessage){

		messageType.add(LogStatus.WARNING);
		messageContent.add(warningMessage);
	}

	public void setLogStatusSKIP(String skipMessage){

		messageType.add(LogStatus.SKIP);
		messageContent.add(skipMessage);
	}

	public void setLogStatusERROR(String errorMessage){

		messageType.add(LogStatus.ERROR);
		messageContent.add(errorMessage);
	}

	public void setLogStatusFAIL(String failMessage){

		messageType.add(LogStatus.FAIL);
		messageContent.add(failMessage);
	}

	public void setLogStatusFATAL(String fatalMessage){

		messageType.add(LogStatus.FATAL);
		messageContent.add(fatalMessage);
	}

	public void setLogStatusUNKNOWN(String unknownMessage){

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

		Map<Object, String> mapObjectString = new HashMap<Object, String>();

		mapObjectString.put(LogStatus.INFO, "Hola");
		mapObjectString.put(LogStatus.INFO, "Buen dia");

		System.out.println(mapObjectString.size());

		System.out.println(mapObjectString.get(LogStatus.INFO));
	}
}
