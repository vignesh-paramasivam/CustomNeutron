package testcore.pages.desktop.StudyManagement;

import agent.IAgent;
import central.Configuration;
import testcore.pages.BasePage;
import testcore.pages.StudyManagement.StudyPage;
import utils.RandomData;

import java.util.Map;

public class DesktopStudyPage extends StudyPage {

	public DesktopStudyPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}


	@Override
	public void addStudy() throws Exception {
		//TODO: The below items needs to be placed in respective page steps;
		//TODO: All sleeps will be removed after adding the page wait strategy

		getLinkControl("Add record(s)").click();
		Thread.sleep(5000);

		String studyName = "Test24Oct - " + RandomData.alpha_numeric_string(3);
		getTextboxControl("name").enterValue(studyName);
		logger.info(studyName);

		getDropdownControl("phase_cb").enterValue(getTestData().get("Study Phase"));
		getDropdownControl("status_cb").enterValue(getTestData().get("Status"));
		//getButtonControl("save2").click();
		Thread.sleep(10000);
	}

}
