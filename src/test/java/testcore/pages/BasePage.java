package testcore.pages;

import agent.IAgent;
import central.Configuration;
import control.Control;
import control.IControl;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import page.Page;
import pagedef.Identifier;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testcore.controls.common.MenuControl;
import testcore.pages.SiteManagement.SitesPage;

public abstract class BasePage extends Page {


	public BasePage(Configuration config, IAgent agent, Map<String, String> testData) throws Exception {
		super(config, agent, testData);
	}

	@Step("Navigate to {menuName}")
	public AllPages navigateTo(String menuName) throws Exception {
		WebElement menuTopElement = this.getAgent().getWebDriver().findElement(By.id("jsm"));
		new MenuControl("menucontrol", this, menuTopElement).selectMenu(menuName);
		assertPageLoad();
		return new AllPages(getConfig(), getAgent(), getTestData());
	}

	//Wrapper methods for wait for element: Begins
	protected void waitForVisibilityById(String id) throws Exception {
		waiter().until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		Thread.sleep(2000); //TODO: Page loading in splits, to be fixed
	}

	protected void waitForVisibilityByClass(String className) throws Exception {
		waiter().until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
		Thread.sleep(2000); //TODO: Page loading in splits, to be fixed
	}

	protected void waitUntilElementVisible(WebElement element) throws Exception {
		waiter().until(ExpectedConditions.visibilityOf(element));
	}
	//Wrapper methods for wait for element: Ends

	@Override
	public IControl getControl(String name) throws Exception {
		IControl control = Control.createControl(this, name, getIdentifier(name));
		return control;
	}

	public String rootDirPath(){
		return System.getProperty("user.dir");
	}


	public WebDriver driver() throws Exception {
		return this.getAgent().getWebDriver();
	}

	public WebDriverWait waiter() throws Exception {
		return this.getAgent().getWaiter();
	}

	public void waitUntilVisible(WebElement element) {
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOf(element));
	}

	public IControl getTextboxControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getButtonControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getLinkControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getGridControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getLinkControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getTextboxControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getCheckboxControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getCheckboxControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}
	public IControl getCalendarControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getCalendarControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getButtonControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	private IControl generateDynamicControl(String locatorVariable, String controlType) throws Exception {
		Identifier identifier = getDynamicIdentifier(controlType);
		IControl control = (IControl) Control.createDynamicControl(this, locatorVariable, identifier, controlType, null);		
		if(control == null) {
			throw new Error("Unable to identify " + controlType + " " + locatorVariable);
		}
		return control;
	}

	private IControl generateDynamicControlFromParent(String locatorVariable, String controlType, WebElement parentElement) throws Exception {
		Identifier identifier = getDynamicIdentifier(controlType);
		IControl control = (IControl) Control.createDynamicControl(this, locatorVariable, identifier, controlType, parentElement);
		if(control == null) {
			throw new Error("Unable to identify " + controlType + " " + locatorVariable);
		}
		return control;
	}

	public IControl getTextareaControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getDropdownControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getDropdownControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public WebElement elementNotificationDialog() throws Exception {
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-notification-notice-message, .ant-notification-notice-with-icon")));
		if(this.getAgent().getWebDriver().findElements(By.cssSelector(".ant-notification-notice-message, .ant-notification-notice-with-icon")).size() < 0){
			Assert.fail("Notification message box is not displayed");
		};
		return this.getAgent().getWebDriver().findElement(By.cssSelector(".ant-notification"));
	}

	public IControl getMenuControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getSectionControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getSectionControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getTextareaControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getRadioControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getRadioControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getNotificationControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl("", controlType);
	}

	public IControl getNotificationControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent("", controlType, parentElement);
	}

	public void logScreenShot(String logScreenShotName) throws Exception {
		//Disabling this log for now
		//getAgent().takeSnapShot(getTestData().get("testName") + "#groupName_" + getTestData().get("_groupName") + "#" + logScreenShotName);
	}
}
