package testcore.pages.SiteManagement;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.SiteManagement.Steps.SitesPageSteps;
import testcore.pages.StudyManagement.StudyPage;
import testcore.pages.desktop.SiteManagement.DesktopSitesPage;
import testcore.pages.desktop.StudyManagement.DesktopStudyPage;

import java.util.Map;

public class SitesPage extends SitesPageSteps {


	public SitesPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public SitesPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		SitesPage derivedSitesPage;
		switch(getPlatform()){
			case DESKTOP_WEB:
				derivedSitesPage = new DesktopSitesPage(getConfig(),getAgent(),getTestData());
				break;
			default:
				throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedSitesPage;
	}
	@Override
	public String pageName() {
		return SitesPage.class.getSimpleName();
	}
	
}
