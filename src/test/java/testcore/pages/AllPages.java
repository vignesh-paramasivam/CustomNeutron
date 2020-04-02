package testcore.pages;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testcore.pages.Connect.Steps.SegmentDetailsPageSteps;
import testcore.pages.Connect.Steps.SegmentsPageSteps;

import java.util.List;
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
		Thread.sleep(10000);
		assertPageLoad();
		return new SegmentsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SegmentDetailsPageSteps onSegmentDetailsPage() throws Exception {
		assertPageLoad();
		return new SegmentDetailsPageSteps(getConfig(), getAgent(), getTestData());
	}


	//Filter function

	public AllPages filter() throws Exception {
		return this;
	}

	public WebElement getTopRuleSet() throws Exception {
		assertPageLoad();
		return this.getAgent().getWebDriver().findElement(By.cssSelector("section.query-builder query-builder"));
	}

	public WebElement getRuleSetUnderParentRuleSet(WebElement parentRuleSetElement) throws Exception {
		assertPageLoad();
		return parentRuleSetElement.findElement(By.cssSelector("li.q-rule"));
	}

	public List<WebElement> getAllRulesFromRuleSet(WebElement ruleSetElement) throws Exception {
		return ruleSetElement.findElements(By.xpath("//ul/li[contains(@class, 'q-rule')]"));
	}

	public void setRuleSetCondition(WebElement ruleSetElement, String condition) throws Exception {
		WebElement ruleSetToggle = ruleSetElement.findElement(By.xpath("//zap-toggle-window//div[contains(@class, 'option-text')][text()='"+condition.toUpperCase()+"']"));
		if(ruleSetToggle.getAttribute("class").contains("activeOption")) {
			return;
		}
		ruleSetToggle.click();
		assertPageLoad();
	}

	public void addRule(WebElement ruleSetElement) throws Exception {
		getButtonControl("add", ruleSetElement).click();
		assertPageLoad();
	}

	public void addRuleSet(WebElement ruleSetElement) throws Exception {
		getButtonControl("add_circle_outline", ruleSetElement);
		assertPageLoad();
	}

	public void setRuleValues(WebElement ruleSetElement, String columnFromDC, String operator, String value, String valueType) throws Exception {
		getDropdownControl("Select Column from Data Collec",ruleSetElement).enterValue(columnFromDC);
		getDropdownControl("Select Operator", ruleSetElement).enterValue("Contains"); // Dummy step to invoke action
		getDropdownControl("Select Operator", ruleSetElement).enterValue(operator);
		Thread.sleep(2000);
		switch (valueType.toLowerCase()) {
			case "dropdown":
				getDropdownControl("Select Values", ruleSetElement).enterValue(value);
				break;
			case "textbox" :
				getTextboxControl("Enter Value", ruleSetElement).enterValue(value);
				break;
			default:
				Assert.fail(valueType + "is not a valid type");
		}
		assertPageLoad();
	}

}
