package testcore.pages.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.testng.Assert;
import testcore.pages.StudyManagement.SegmentsPage;
import utils.RandomData;
import java.util.HashMap;
import java.util.Map;

public class SegmentsPageSteps extends SegmentsPage {


	public SegmentsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}


	//TEST STEPS RELATED TO STUDY PAGE NEEDS TO BE ADDED BELOW

	@Step("Add new study details and save")
	public SegmentsPageSteps addNewStudyDetailsAndSave() throws Exception {
		getLinkControl("Add record(s)").click();
		assertPageLoad();

		String studyName = "AA_TestStudy - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("studyName", studyName);
		getTextboxControl("name").enterValue(studyName);
		logger.info(studyName);

		getDropdownControl("phase_cb").enterValue(getTestData().get("Segments Phase"));
		getDropdownControl("status_cb").enterValue(getTestData().get("Status"));
		getButtonControl("save2").click();

		assertPageLoad();

		getNotificationControl("").waitUntilVisible();
		String actualMessage = getNotificationControl("").getValue();
		String expectedMessage = getTestData().get("SuccessMsgForRecordCreation");
		Assert.assertEquals(expectedMessage, actualMessage);
		assertPageLoad();

		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SegmentsPageSteps verifyValuesInGrid() throws Exception {
		waitForVisibilityByClass("Gentable");
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Segments Name", getTestData().get("studyName"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Segments Name", getTestData().get("studyName"));

		getGridControl("Gentable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SegmentsPageSteps searchNewlyAddedStudy() throws Exception {
		getTextboxControl("drugtrialNameSearch").enterValue(getTestData().get("studyName"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SegmentsPageSteps addNewStudy() throws Exception {
		addNewStudyDetailsAndSave();
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}
}
