package testcore.pages.desktop.Connect;

import agent.IAgent;
import central.Configuration;
import testcore.pages.Connect.SegmentsPage;

import java.util.Map;

public class DesktopSegmentsPage extends SegmentsPage {

	public DesktopSegmentsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}
}
