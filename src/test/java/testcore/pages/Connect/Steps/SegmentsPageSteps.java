package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.testng.Assert;
import testcore.pages.Connect.SegmentsPage;
import utils.RandomData;
import java.util.HashMap;
import java.util.Map;

public class SegmentsPageSteps extends SegmentsPage {


	public SegmentsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}


	public SegmentDetailsPageSteps gotoCreateSegmentPage() throws Exception {
		getButtonControl("CREATE SEGMENT").click();
		assertPageLoad();
		return new SegmentDetailsPageSteps(this.getConfig(), this.getAgent(), this.getTestData());
	}

	public SegmentsPageSteps selectOrgAndCountry() throws Exception {
		assertPageLoad();
		getDropdownControl("Select Organization").enterValue(getTestData().get("Organization"));
		assertPageLoad();
		getDropdownControl("Select Country").enterValue(getTestData().get("Country"));
		assertPageLoad();
		return this;
	}

	public SegmentsPageSteps verifyNewSegmentIsCreated() throws Exception {
		String createdSegmentName = getTestData().get("SegmentName");
		getTextboxControl("Search Segments").enterValue(createdSegmentName);
		Thread.sleep(1000); // Static wait of 1s is added to get the loading spinner to display
		assertPageLoad();
		Assert.assertTrue(getAllPostersNames().contains(createdSegmentName), createdSegmentName + " is not available. Available posters:: " + getAllPostersNames());
		return this;
	}

}
