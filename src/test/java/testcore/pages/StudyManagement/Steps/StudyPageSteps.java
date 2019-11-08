package testcore.pages.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.testng.Assert;
import testcore.pages.StudyManagement.StudyPage;
import utils.RandomData;
import java.util.HashMap;
import java.util.Map;

public class StudyPageSteps extends StudyPage {


	public StudyPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}


	//TEST STEPS RELATED TO STUDY PAGE NEEDS TO BE ADDED BELOW

	@Step("Add new study details and save")
	public StudyPageSteps addNewStudyDetailsAndSave() throws Exception {
		getLinkControl("Add record(s)").click();
		assertPageLoad();

		String studyName = "AA_TestStudy - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("studyName", studyName);
		getTextboxControl("name").enterValue(studyName);
		logger.info(studyName);

		getDropdownControl("phase_cb").enterValue(getTestData().get("Study Phase"));
		getDropdownControl("status_cb").enterValue(getTestData().get("Status"));
		getButtonControl("save2").click();

		assertPageLoad();

		getNotificationControl("").waitUntilVisible();
		String actualMessage = getNotificationControl("").getValue();
		String expectedMessage = getTestData().get("SuccessMsgForRecordCreation");
		Assert.assertEquals(expectedMessage, actualMessage);
		assertPageLoad();

		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}

	public StudyPageSteps verifyValuesInGrid() throws Exception {
		waitForVisibilityByClass("Gentable");
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Name", getTestData().get("studyName"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Name", getTestData().get("studyName"));

		getGridControl("Gentable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}


	public StudyPageSteps searchNewlyAddedStudy() throws Exception {
		getTextboxControl("drugtrialNameSearch").enterValue(getTestData().get("studyName"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}

	public StudyPageSteps addNewStudy() throws Exception {
		addNewStudyDetailsAndSave();
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}
}
