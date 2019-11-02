package testcore.pages;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testcore.controls.common.MenuControl;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.SiteManagement.Steps.SitesPageSteps;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.StudyManagement.StudyPage;

import java.util.Map;

public class AllPages extends BasePage {


	public AllPages(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return AllPages.class.getSimpleName();
	}


	public StudyPageSteps studyPage() throws Exception {
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SitesPageSteps sitesPage() throws Exception {
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

}
