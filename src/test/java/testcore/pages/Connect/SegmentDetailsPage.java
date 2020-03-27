package testcore.pages.Connect;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.Connect.DesktopSegmentDetailsPage;

import java.util.Map;

public class SegmentDetailsPage extends BasePage {


	public SegmentDetailsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public SegmentDetailsPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		SegmentDetailsPage derivedSegmentDetailsPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedSegmentDetailsPage = new DesktopSegmentDetailsPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedSegmentDetailsPage;
	}

	@Override
	public String pageName() {
		return SegmentDetailsPage.class.getSimpleName();
	}
	
}
