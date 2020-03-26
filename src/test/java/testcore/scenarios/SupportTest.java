package testcore.scenarios;

import agent.AgentFactory;
import agent.IAgent;
import central.AutomationCentral;
import central.Configuration;
import io.qameta.allure.Attachment;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import testcore.pages.*;
import utils.DataTable;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportTest {
	protected static Logger logger = AutomationCentral.getLogger();
	private Configuration conf = null;
	protected HomePage home;
	protected LoginPage unity;
	private IAgent agent;
	private ITestContext context = null;
	private String testName = null;
	private DataTable dataTable = null;
	private int Testcount = 0;
	private String testCase=null;
	private Map<String, String> testData =  new HashMap<>();
	private List<HashMap<String, String>> listOfHashMap = new ArrayList<HashMap<String,String>>();


	@BeforeSuite(alwaysRun = true)
	public void runOncePerSuite(ITestContext context) throws Exception {
		AutomationCentral.INSTANCE.init();
		logger = AutomationCentral.getLogger();
		logger.info("Central setup completed.");
	}

	@BeforeTest(alwaysRun = true)
	public void runOncePerContext(ITestContext context) throws Exception {
		logger.info(String.format("Test context setup started for %s test.", context.getName()));
		AutomationCentral.INSTANCE.registerContext(context);
		logger.info(String.format("Test context setup completed for %s test.", context.getName()));
	}

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void runOncePerClass(@Optional("browser") String browser, ITestContext context) throws Exception {
		if(!browser.equals("browser")) { System.setProperty("browser", browser); }
		this.context = context;
		this.conf = AutomationCentral.INSTANCE.getContextConfig(context);
		/*DataSheet name will be retrieved from the scenarios package name*/
		String rawPath = this.getClass().getName().replace("testcore.scenarios.", "");
		int index = rawPath.lastIndexOf(".");
		String dataSheetPath = rawPath.substring(0, index).replace(".", "/");


		String currentClassName = this.getClass().getSimpleName();
		dataTable = new DataTable(dataSheetPath);
		dataTable.capturesRowOfTestCasesInSheet(currentClassName);
	}

	@BeforeMethod(alwaysRun = true)
	public void runOncePerMethod(ITestContext context, Method method) throws Exception {
		testName = method.getName();
		if (!testName.equals(testCase)) {
			Testcount = 0;
			if(!listOfHashMap.isEmpty()){
				listOfHashMap.clear();
			}
			listOfHashMap = dataTable.preProcessAllTestData(testName);
		}
		logger.info(String.format("Set up for test method [%s] started.", testName));
		logger.debug(String.format("Creating agent for %s", this.conf.getPlatform()));
		agent = AgentFactory.createAgent(this.conf);
		SessionId sessionId = ((RemoteWebDriver)agent.getWebDriver()).getSessionId();
		logger.debug("Session ID for test: " + testName + " -----> " + sessionId);
		agent.getWebDriver().manage().window().maximize();
		logger.debug(String.format("Test Method Name Started :: %s", testName));
		//<String, String> testData = AutomationCentral.INSTANCE.getTestData(context, testName);
		if(!testData.isEmpty()){
			testData.clear();
		}
		testData.putAll(listOfHashMap.get(Testcount++));
		this.testData.put("testName", testName);
		home = new HomePage(this.conf, agent, testData);
		unity = new LoginPage(this.conf, agent, testData);
		logger.info(String.format("Set up for test method [%s] ended.", testName));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result, ITestContext context) throws Exception {
		logger.info(String.format("Tear down for test method [%s] started.", testName));
		testCase = testName;
		if (ITestResult.FAILURE == result.getStatus()) {
			File scrShotFile = agent.takeSnapShot(testName);
			byte[] scrShot = Files.readAllBytes(scrShotFile.toPath());
			attachScreenshotAllure(scrShot);
		}
		try {
			//Agent logger can be added here
		} catch(Exception e) {
			e.printStackTrace();
		}
		agent.quit();
		logger.info(String.format("Tear down for test method [%s] ended.", testName));
	}

	protected String getTestInfoMessage(String stage, String method) {
		return String.format("Test method [%s] %s", method, stage);
	}

	protected String getTestStartInfoMessage(String method) {
		return getTestInfoMessage("start", method);
	}

	protected String getTestEndInfoMessage(String method) {
		return getTestInfoMessage("end", method);
	}
	
	@Attachment(value = "failure screenshot", type = "image/jpg")
	public byte[] attachScreenshotAllure(byte[] screenShot) {
	    return screenShot;
	}
}
