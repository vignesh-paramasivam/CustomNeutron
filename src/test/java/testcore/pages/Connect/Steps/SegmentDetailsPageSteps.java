package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import testcore.pages.BasePage;
import testcore.pages.Connect.SegmentsPage;

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

	public SegmentDetailsPageSteps enterBasicDetails() throws Exception {
		getTextboxControl("segmentName").enterValue(getTestData().get("SegmentName"));
		return this;
	}

	public SegmentDetailsPageSteps enterBuildSegmentDetails() throws Exception {
		getDropdownControl("Choose Data Collections to inc").enterValue(getTestData().get("DataCollections"));
		getDropdownControl("Select your Input Identifiers").enterValue(getTestData().get("InputIdentifiers"));
		getButtonControl("NEXT").click();
		assertPageLoad();
		return this;
	}

	public SegmentDetailsPageSteps addFiltersToDataCollections() throws Exception {
		getButtonControl("FILTER DATA COLLECTIONS").click();
		return this;
	}


}
