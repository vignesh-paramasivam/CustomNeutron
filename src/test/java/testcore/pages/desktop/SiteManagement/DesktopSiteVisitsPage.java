package testcore.pages.desktop.SiteManagement;

import agent.IAgent;
import central.Configuration;
import testcore.pages.SiteManagement.SiteVisitsPage;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.StudyManagement.StudyPage;

import java.util.Map;

public class DesktopSiteVisitsPage extends SiteVisitsPage {

	public DesktopSiteVisitsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return DesktopSiteVisitsPage.class.getSimpleName();
	}
}
