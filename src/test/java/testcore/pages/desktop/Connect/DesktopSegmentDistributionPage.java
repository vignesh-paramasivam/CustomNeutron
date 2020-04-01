package testcore.pages.desktop.Connect;

import agent.IAgent;
import central.Configuration;
import testcore.pages.Connect.SegmentDistributionPage;

import java.util.Map;

public class DesktopSegmentDistributionPage extends SegmentDistributionPage {

	public DesktopSegmentDistributionPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentDistributionPage.class.getSimpleName();
	}
}
