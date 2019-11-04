package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testcore.controls.common.GridControl;
import testcore.pages.SiteManagement.SiteVisitsPage;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.StudyManagement.StudyPage;
import utils.RandomData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiteVisitsPageSteps extends SiteVisitsPage {


	public SiteVisitsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SiteVisitsPage.class.getSimpleName();
	}

}
