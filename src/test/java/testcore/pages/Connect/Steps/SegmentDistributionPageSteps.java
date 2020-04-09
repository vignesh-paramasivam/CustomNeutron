package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import testcore.pages.Connect.SegmentDistributionPage;

import java.util.Map;

public class SegmentDistributionPageSteps extends SegmentDistributionPage {


	public SegmentDistributionPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentDistributionPage.class.getSimpleName();
	}

	@Step("Choose destinations")
	public SegmentDistributionPageSteps chooseDestinations() throws Exception {
		String[] destinationsToSelect = getTestData().get("Destinations").split(";");
		for (String destination: destinationsToSelect) {
			selectDestination(destination);
		}
		return this;
	}

	@Step("Click create and distribute")
	public SegmentsPageSteps createAndDistribute() throws Exception {
		getButtonControl("CREATE AND DISTRIBUTE").click();
		assertPageLoad();
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}

}
