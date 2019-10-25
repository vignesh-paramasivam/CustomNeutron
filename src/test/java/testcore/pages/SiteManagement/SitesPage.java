package testcore.pages.SiteManagement;

import agent.IAgent;
import central.Configuration;
import testcore.pages.BasePage;

import java.util.Map;

public class SitesPage extends BasePage {


	public SitesPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SitesPage.class.getSimpleName();
	}
	
}
