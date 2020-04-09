package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import testcore.pages.Connect.SegmentDetailsPage;
import testcore.pages.Connect.SegmentsPage;
import testcore.pages.libraries.FilterForQueryBuilder;
import utils.RandomData;

import java.util.List;
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

	@Step("Enter basic details for create segment")
	public SegmentDetailsPageSteps enterBasicDetails() throws Exception {
		String newSegmentName = "MT-Automation" + RandomData.dateTime_yyyyMMddHHmmss();
		getTestData().put("SegmentName", newSegmentName);
		getTextboxControl("segmentName").enterValue(getTestData().get("SegmentName"));
		return this;
	}

	@Step("Choose data collections")
	public SegmentDetailsPageSteps chooseDataCollections() throws Exception {
		getDropdownControl("Choose Data Collections to inc").enterValue(getTestData().get("DataCollections"));
		assertPageLoad();
		return this;
	}

	@Step("Choose input identifiers")
	public SegmentDetailsPageSteps chooseInputIdentifiers() throws Exception {
		getDropdownControl("Select your Input Identifiers").enterValue(getTestData().get("InputIdentifiers"));
		clickNextButton();
		assertPageLoad();
		return this;
	}

	@Step("Add filter for data collections")
	public SegmentDetailsPageSteps addFiltersToDataCollections() throws Exception {
		getButtonControl("FILTER DATA COLLECTIONS").click();

		FilterForQueryBuilder filter = filter();

		WebElement queryBuilder = filter.getTopQueryBuilder();
		filter.addRule(queryBuilder);
		WebElement queryBuilder_rule1 = filter.getAllRulesFromRuleSet(queryBuilder).get(0);
		WebElement queryBuilder_rule2 = filter.getAllRulesFromRuleSet(queryBuilder).get(1);

		filter.setRuleValues(queryBuilder_rule1, "Gender in mt_dc_esp", "In", "Male", "Dropdown");
		filter.setRuleValues(queryBuilder_rule2, "Model in mt_dc_esp", "Equal", "GTI", "Dropdown");
		filter.setRuleSetCondition(queryBuilder, "AND");

		filter.addRuleSet(queryBuilder);
		WebElement queryBuilder_ruleSet1 = filter.getAllRuleSetsFromRuleSet(queryBuilder).get(0);

		filter.addRule(queryBuilder_ruleSet1);
		filter.addRule(queryBuilder_ruleSet1);
		WebElement queryBuilder_ruleSet1_rule1 = filter.getAllRulesFromRuleSet(queryBuilder_ruleSet1).get(0);
		WebElement queryBuilder_ruleSet1_rule2 = filter.getAllRulesFromRuleSet(queryBuilder_ruleSet1).get(1);

		filter.setRuleValues(queryBuilder_ruleSet1_rule1, "Price in mt_dc_esp", "Equal", "10", "Textbox");
		filter.setRuleValues(queryBuilder_ruleSet1_rule2, "Price in mt_dc_esp", "Equal", "20", "Textbox");
		filter.setRuleSetCondition(queryBuilder_ruleSet1, "OR");

		clickNextButton();
		return this;
	}


	@Step("Extend first party audience")
	public SegmentDetailsPageSteps extendFirstPartyAudience() throws Exception {
		String[] valuesToSelect = getTestData().get("ExtendFirstPartyAudience").split(";"); //Delimit data with ";"
		for ( String value: valuesToSelect) {
			getCheckboxControl(value).checkboxCheck();
		}
		clickNextButton();
		return this;
	}


	@Step("Verify options of input identifiers")
	public SegmentDetailsPageSteps verifyInputIdentifiersOptions() throws Exception {
		List<String> allOptions = getDropdownControl("Select your Input Identifiers").allDropdownOptions();
		String[] optionsToCheck = getTestData().get("OptionsOfInputIdentifiers").split(";");

		SoftAssert softAssert = new SoftAssert();
		for(String option: optionsToCheck){
			softAssert.assertTrue(allOptions.contains(option), option + " is not available in input identifier " + allOptions);
		}

		softAssert.assertAll();
		return this;
	}

	@Step("Verify options of output identifiers")
	public SegmentDetailsPageSteps verifyOutputIdentifiersOptions() throws Exception {
		List<String> allOptions = getDropdownControl("Choose Output Identifiers").allDropdownOptions();
		String[] optionsToCheck = getTestData().get("OptionsOfOutputIdentifiers").split(";");

		SoftAssert softAssert = new SoftAssert();
		for(String option: optionsToCheck){
			softAssert.assertTrue(allOptions.contains(option), option + " is not available in output identifiers " + allOptions);
		}

		softAssert.assertAll();
		return this;
	}


	@Step("Choose desired output identifiers")
	public SegmentDetailsPageSteps chooseOutputIdentifiers() throws Exception {
		getDropdownControl("Choose Output Identifiers").enterValue(getTestData().get("OutputIdentifiers"));
		return this;
	}

	@Step("Click proceed")
	public SegmentDistributionPageSteps proceed() throws Exception {
		getButtonControl("PROCEED").click();
		assertPageLoad();
		return new SegmentDistributionPageSteps(getConfig(), getAgent(), getTestData());
	}
}
