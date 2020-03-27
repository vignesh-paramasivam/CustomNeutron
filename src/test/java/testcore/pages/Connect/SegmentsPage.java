package testcore.pages.Connect;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.Connect.DesktopSegmentsPage;

import java.util.Map;

public class SegmentsPage extends BasePage {


	public SegmentsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public SegmentsPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		SegmentsPage derivedSegmentsPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedSegmentsPage = new DesktopSegmentsPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedSegmentsPage;
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}

}
