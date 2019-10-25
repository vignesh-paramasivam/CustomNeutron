package testcore.pages.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.StudyManagement.StudyPage;
import testcore.pages.desktop.StudyManagement.DesktopStudyPage;
import utils.RandomData;

import java.util.Map;

public class StudyPageSteps extends BasePage {


	public StudyPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}


	//TEST STEPS RELATED TO STUDY PAGE NEEDS TO BE ADDED BELOW

	@Step("Add a new study")
	public void addNewStudy() throws Exception {
		//TODO: All sleeps will be removed after adding the page wait strategy

		getLinkControl("Add record(s)").click();
		assertPageLoad();

		String studyName = "Test25Oct - " + RandomData.alpha_numeric_string(3);
		getTextboxControl("name").enterValue(studyName);
		logger.info(studyName);

		getDropdownControl("phase_cb").enterValue(getTestData().get("Study Phase"));
		getDropdownControl("status_cb").enterValue(getTestData().get("Status"));
		getButtonControl("save2").click();
		assertPageLoad();
	}

}
