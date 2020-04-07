package testcore.pages.libraries;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testcore.pages.AllPages;
import testcore.pages.BasePage;

import java.util.List;
import java.util.Map;

public class FilterForQueryBuilder extends BasePage {

    public FilterForQueryBuilder(Configuration config, IAgent agent, Map<String, String> testData) throws Exception {
        super(config, agent, testData);
    }

    @Override
    public String pageName() {
        return AllPages.class.getSimpleName();
    }


    public WebElement getTopQueryBuilder() throws Exception {
        assertPageLoad();
        return this.getAgent().getWebDriver().findElement(By.cssSelector("section.query-builder query-builder"));
    }

    public List<WebElement> getAllRulesFromRuleSet(WebElement ruleSetElement) throws Exception {
        return ruleSetElement.findElements(By.xpath(".//ul/li[contains(@class, 'q-rule')]"));
    }

    public List<WebElement> getAllRuleSetsFromRuleSet(WebElement ruleSetElement) throws Exception {
        return ruleSetElement.findElements(By.xpath(".//query-builder[@ng-reflect-allow-ruleset='true']"));
    }

    public void setRuleSetCondition(WebElement ruleSetElement, String condition) throws Exception {
        WebElement ruleSetToggle = ruleSetElement.findElement(By.xpath(".//zap-toggle-window//div[contains(@class, 'option-text')][text()='"+condition.toUpperCase()+"']"));
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
        getButtonControl("add_circle_outline", ruleSetElement).click();
        assertPageLoad();
    }

    public void setRuleValues(WebElement ruleSetElement, String columnFromDC, String operator, String value, String valueType) throws Exception {
        getDropdownControl("Select Column from Data Collec",ruleSetElement).enterValue(columnFromDC);
        getDropdownControl("Select Operator", ruleSetElement).enterValue(operator);
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
