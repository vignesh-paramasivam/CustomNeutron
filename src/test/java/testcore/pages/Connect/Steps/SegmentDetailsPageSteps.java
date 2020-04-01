package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.WebElement;
import testcore.pages.AllPages;
import testcore.pages.BasePage;
import testcore.pages.Connect.SegmentDetailsPage;
import testcore.pages.Connect.SegmentsPage;

import java.util.Map;

public class SegmentDetailsPageSteps extends SegmentDetailsPage {


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
		clickNextButton();
		assertPageLoad();
		return this;
	}

	public SegmentDetailsPageSteps addFiltersToDataCollections() throws Exception {
		getButtonControl("FILTER DATA COLLECTIONS").click();

		//Todo: Filter steps - Validate once application slowness is addressed; Add test data in dataSheet instead of hardcode
		Thread.sleep(2000); // Handle wait for filter options to load
		AllPages aP = new AllPages(getConfig(), getAgent(), getTestData());
		AllPages filter = aP.filter();

		WebElement ruleSet1 = filter.getTopRuleSet();
		WebElement ruleSet1_rule1 = filter.getAllRulesFromRuleSet(ruleSet1).get(0);
		filter.setRuleValues(ruleSet1_rule1, "Gender in test-ms-01", "Contains", "Male", "Textbox");
		//filter.addRule(ruleSet1);
		//filter.setRuleSetCondition(ruleSet1, "AND");


		clickNextButton();
		return this;
	}


	public SegmentDetailsPageSteps extendFirstPartyAudience() throws Exception {
		String[] valuesToSelect = getTestData().get("ExtendFirstPartyAudience").split(";"); //Delimit data with ";"
		for ( String value: valuesToSelect) {
			getCheckboxControl(value).checkboxCheck();
		}
		clickNextButton();
		return this;
	}


	public SegmentDetailsPageSteps chooseOutputIdentifiers() throws Exception {
		getDropdownControl("Choose Output Identifiers").enterValue(getTestData().get("OutputIdentifiers"));
		return this;
	}

	public SegmentDistributionPageSteps proceed() throws Exception {
		getButtonControl("PROCEED").click();
		assertPageLoad();
		return new SegmentDistributionPageSteps(getConfig(), getAgent(), getTestData());
	}
}
