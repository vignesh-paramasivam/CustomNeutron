package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import testcore.pages.BasePage;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.StudyManagement.StudyPage;
import utils.RandomData;

import java.util.Map;

public class SitesPageSteps extends BasePage {


	public SitesPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}

	public StudyPageSteps addNewSiteForStudy() throws Exception {
		String studyName = getTestData().get("StudyName");
		getDropdownControl("drugtrialIdSrch_cb").enterValue("AK_03oct_Study_01");
		getButtonControl("btnSelect").click();
		assertPageLoad();

		getLinkControl("Add a new site").click();
		assertPageLoad();

		getTextboxControl("Study Site Number").enterValue("TestSite30-1");

		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}
}
