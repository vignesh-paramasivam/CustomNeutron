package testcore.pages.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.testng.Assert;
import testcore.pages.BasePage;
import testcore.pages.HomePage;
import testcore.pages.StudyManagement.StudyPage;
import testcore.pages.desktop.StudyManagement.DesktopStudyPage;
import utils.RandomData;

import java.util.HashMap;
import java.util.Map;

public class StudyPageSteps extends HomePage {


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

		String studyName = "Test30Oct - " + RandomData.alpha_numeric_string(3);

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
				.on().studyPage()
				.addNewStudyDetailsAndSave();
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}
}
