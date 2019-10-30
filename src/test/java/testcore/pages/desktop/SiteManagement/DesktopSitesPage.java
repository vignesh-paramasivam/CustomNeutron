package testcore.pages.desktop.SiteManagement;

import agent.IAgent;
import central.Configuration;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.StudyManagement.StudyPage;

import java.util.Map;

public class DesktopSitesPage extends SitesPage {

	public DesktopSitesPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}
}
