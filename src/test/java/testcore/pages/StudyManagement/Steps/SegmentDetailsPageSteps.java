package testcore.pages.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import testcore.pages.BasePage;
import testcore.pages.StudyManagement.SegmentsPage;

import java.util.Map;

public class SegmentDetailsPageSteps extends BasePage {


	public SegmentDetailsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}

}
