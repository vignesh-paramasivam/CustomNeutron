package testcore.pages;

import agent.IAgent;
import central.Configuration;
import testcore.pages.Connect.Steps.SegmentDetailsPageSteps;
import testcore.pages.Connect.Steps.SegmentsPageSteps;

import java.util.Map;

public class AllPages extends BasePage {


	public AllPages(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return AllPages.class.getSimpleName();
	}


	public SegmentsPageSteps onSegmentsPage() throws Exception {
		Thread.sleep(10000); //Todo: to be addressed
		assertPageLoad();
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SegmentDetailsPageSteps onSegmentDetailsPage() throws Exception {
		assertPageLoad();
		return new SegmentDetailsPageSteps(getConfig(), getAgent(), getTestData());
	}
}
