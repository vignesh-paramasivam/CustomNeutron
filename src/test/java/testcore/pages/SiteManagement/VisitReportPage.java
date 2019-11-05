package testcore.pages.SiteManagement;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.SiteManagement.DesktopSiteVisitsPage;
import testcore.pages.desktop.SiteManagement.DesktopVisitReportPage;

import java.util.Map;

public class VisitReportPage extends BasePage {


	public VisitReportPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public VisitReportPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		VisitReportPage derivedVisitReportPage;
		switch(getPlatform()){
			case DESKTOP_WEB:
				derivedVisitReportPage = new DesktopVisitReportPage(getConfig(),getAgent(),getTestData());
				break;
			default:
				throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedVisitReportPage;
	}

	@Override
	public String pageName() {
		return VisitReportPage.class.getSimpleName();
	}
	
}
