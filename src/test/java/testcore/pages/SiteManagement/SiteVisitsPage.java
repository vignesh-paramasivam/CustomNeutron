package testcore.pages.SiteManagement;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.SiteManagement.DesktopSiteVisitsPage;

import java.util.Map;

public class SiteVisitsPage extends BasePage {


	public SiteVisitsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public SiteVisitsPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		SiteVisitsPage derivedSiteVisitsPage;
		switch(getPlatform()){
			case DESKTOP_WEB:
				derivedSiteVisitsPage = new DesktopSiteVisitsPage(getConfig(),getAgent(),getTestData());
				break;
			default:
				throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedSiteVisitsPage;
	}
	@Override
	public String pageName() {
		return SiteVisitsPage.class.getSimpleName();
	}

	protected SitesPage clickSave() throws Exception {
		getButtonControl("save2").click();
		assertPageLoad();
		return new SitesPage(getConfig(), getAgent(), getTestData());
	}

	protected SitesPage clickSaveAndClose() throws Exception {
		getButtonControl("save1").click();
		assertPageLoad();
		return new SitesPage(getConfig(), getAgent(), getTestData());
	}
}
