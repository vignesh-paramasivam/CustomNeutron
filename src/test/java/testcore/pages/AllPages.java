package testcore.pages;

import agent.IAgent;
import central.Configuration;
import testcore.pages.StudyManagement.Steps.SegmentsPageSteps;

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
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}

}
