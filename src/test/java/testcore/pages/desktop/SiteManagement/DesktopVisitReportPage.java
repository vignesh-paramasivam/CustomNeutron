package testcore.pages.desktop.SiteManagement;

import agent.IAgent;
import central.Configuration;
import testcore.pages.SiteManagement.SiteVisitsPage;
import testcore.pages.SiteManagement.VisitReportPage;

import java.util.Map;

public class DesktopVisitReportPage extends VisitReportPage {

	public DesktopVisitReportPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return DesktopVisitReportPage.class.getSimpleName();
	}
}
