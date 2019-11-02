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
		//TODO: All sleeps will be removed after adding the page wait strategy

		getLinkControl("Add record(s)").click();
		assertPageLoad();

		String studyName = "Test - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("StudyName", studyName);
		getTextboxControl("name").enterValue(studyName);
		logger.info(studyName);

		getDropdownControl("phase_cb").enterValue(getTestData().get("Study Phase"));
		getDropdownControl("status_cb").enterValue(getTestData().get("Status"));
		getButtonControl("save2").click();

		assertPageLoad();

		String actualMessage = getNotificationControl("").getValue();
		String expectedMessage = "1 record(s) successfully entered.";
		Assert.assertEquals(expectedMessage, actualMessage);
		assertPageLoad();

		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}

	public StudyPageSteps verifyValuesInGrid() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Name", getTestData().get("StudyName"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Name", getTestData().get("StudyName"));

		getGridControl("Gentable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}


	public StudyPageSteps searchNewlyAddedStudy() throws Exception {
		getTextboxControl("drugtrialNameSearch").enterValue(getTestData().get("StudyName"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}

	public StudyPageSteps addNewStudy() throws Exception {
		navigateTo("Study Management;Study")
				.studyPage()
				.addNewStudyDetailsAndSave();
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}
}
