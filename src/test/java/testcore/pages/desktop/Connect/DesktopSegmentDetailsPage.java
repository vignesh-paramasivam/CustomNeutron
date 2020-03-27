package testcore.pages.desktop.Connect;

import agent.IAgent;
import central.Configuration;
import testcore.pages.Connect.SegmentsPage;
import testcore.pages.Connect.SegmentDetailsPage;

import java.util.Map;

public class DesktopSegmentDetailsPage extends SegmentDetailsPage {

	public DesktopSegmentDetailsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}

}
