package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.WebElement;
import testcore.pages.AllPages;
import testcore.pages.BasePage;
import testcore.pages.Connect.SegmentDetailsPage;
import testcore.pages.Connect.SegmentsPage;
import testcore.pages.libraries.FilterForQueryBuilder;

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

		FilterForQueryBuilder filter = filter();

		WebElement queryBuilder = filter.getTopQueryBuilder();
		filter.addRule(queryBuilder);
		WebElement queryBuilder_rule1 = filter.getAllRulesFromRuleSet(queryBuilder).get(0);
		WebElement queryBuilder_rule2 = filter.getAllRulesFromRuleSet(queryBuilder).get(1);

		filter.setRuleValues(queryBuilder_rule1, "Gender in test-ms-01", "In", "Male", "Dropdown");
		filter.setRuleValues(queryBuilder_rule2, "Currency in test-ms-01", "Equal", "Pound", "Dropdown");
		filter.setRuleSetCondition(queryBuilder, "AND");

		filter.addRuleSet(queryBuilder);
		WebElement queryBuilder_ruleSet1 = filter.getAllRuleSetsFromRuleSet(queryBuilder).get(0);

		filter.addRule(queryBuilder_ruleSet1);
		filter.addRule(queryBuilder_ruleSet1);
		WebElement queryBuilder_ruleSet1_rule1 = filter.getAllRulesFromRuleSet(queryBuilder_ruleSet1).get(0);
		WebElement queryBuilder_ruleSet1_rule2 = filter.getAllRulesFromRuleSet(queryBuilder_ruleSet1).get(1);

		filter.setRuleValues(queryBuilder_ruleSet1_rule1, "Name in e2e-QA-Test-01", "Equal", "Teacher", "Dropdown");
		filter.setRuleValues(queryBuilder_ruleSet1_rule2, "Name in e2e-QA-Test-01", "Equal", "Editor", "Dropdown");
		filter.setRuleSetCondition(queryBuilder_ruleSet1, "OR");

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
