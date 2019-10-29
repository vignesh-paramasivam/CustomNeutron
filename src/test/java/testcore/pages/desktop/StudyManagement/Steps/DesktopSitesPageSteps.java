package testcore.pages.desktop.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import testcore.pages.StudyManagement.StudyPage;

import java.util.Map;

public class DesktopSitesPageSteps extends StudyPage {


	public DesktopSitesPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return DesktopSitesPageSteps.class.getSimpleName();
	}

}
