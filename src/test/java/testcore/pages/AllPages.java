package testcore.pages;

import agent.IAgent;
import central.Configuration;
import testcore.pages.SiteManagement.SitesPage;
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

	public StudyPage studyPage() throws Exception {
		return new StudyPage(getConfig(), getAgent(), getTestData()).createInstance();
	}

	public SitesPage sitesPage() throws Exception {
		return new SitesPage(getConfig(), getAgent(), getTestData()).createInstance();
	}

}
