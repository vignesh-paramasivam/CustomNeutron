package testcore.pages;

import agent.IAgent;
import central.Configuration;
import control.Control;
import control.IControl;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import page.Page;
import pagedef.Identifier;
import testcore.controls.dialog.ConfirmActionDialog;
import testcore.controls.dialog.NotificationDialog;
import testcore.controls.dialog.UserSettingsDialog;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class FullPage extends Page {

	String CONFIRMATION_DIALOG_IDENTIFIER = "div.ant-modal-content";

	public FullPage(Configuration config, IAgent agent, Map<String, String> testData) throws Exception {
		super(config, agent, testData);
	}

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

	public WebElement commonModalDialogElement() throws Exception {
		String DIALOG_IDENTIFIER = "div.ant-modal-content";
		WebElement dialogElement = this.getAgent().getWebDriver()
				.findElement(By.cssSelector(DIALOG_IDENTIFIER));
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOf(dialogElement));
		return dialogElement;
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
		System.out.println(controlType);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getDropdownControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public IControl getCascadingDropdownControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getCascadingDropdownControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	protected WebElement elementConfirmActionDialog() throws Exception {
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CONFIRMATION_DIALOG_IDENTIFIER)));
		WebElement dialogElement = this.getAgent().getWebDriver().findElement(By.cssSelector(CONFIRMATION_DIALOG_IDENTIFIER));
		waitUntilVisible(dialogElement);
		return dialogElement;
	}

	public ConfirmActionDialog confirmActionDialog() throws Exception {
		WebElement dialog = elementConfirmActionDialog();
		ConfirmActionDialog confirmActiondialog = new ConfirmActionDialog(null, this, dialog);
		return confirmActiondialog;
	}

	public WebElement elementNotificationDialog() throws Exception {
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ant-notification-notice-message, .ant-notification-notice-with-icon")));
		if(this.getAgent().getWebDriver().findElements(By.cssSelector(".ant-notification-notice-message, .ant-notification-notice-with-icon")).size() < 0){
			Assert.fail("Notification message box is not displayed");
		};
		return this.getAgent().getWebDriver().findElement(By.cssSelector(".ant-notification"));
	}

	public NotificationDialog notificationDialog() throws Exception {
		WebElement dialog = elementNotificationDialog();
		NotificationDialog NotificationDialog = new NotificationDialog(null, this, dialog);
		return NotificationDialog;
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

	public IControl getDualCalendarControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getDualCalendarControl(String locatorVariable, WebElement parentElement) throws Exception {
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

	public IControl getAttachmentControl(String locatorVariable) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControl(locatorVariable, controlType);
	}

	public IControl getAttachmentControl(String locatorVariable, WebElement parentElement) throws Exception {
		String controlType = new Object(){}.getClass().getEnclosingMethod().getName().substring(3);
		return generateDynamicControlFromParent(locatorVariable, controlType, parentElement);
	}

	public  void click_popup_yes_button() throws Exception{
		getButtonControl("Yes", driver().findElement(By.cssSelector("div.ant-popover-content div.ant-popover-buttons"))).click();
	}

	public  void click_popup_no_button() throws Exception{
		getButtonControl("No", driver().findElement(By.cssSelector("div.ant-popover-content div.ant-popover-buttons"))).click();
	}

	private WebElement elementUserSettingsDialog() throws Exception {
		String DIALOG_IDENTIFIER = "//div[contains(@class, 'modal-content')]";
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DIALOG_IDENTIFIER)));
		return this.getAgent().getWebDriver().findElement(By.xpath(DIALOG_IDENTIFIER));
	}

	public UserSettingsDialog userSettingsDialog() throws Exception {
		WebElement dialog = elementUserSettingsDialog();
		return new UserSettingsDialog(pageName(), this, dialog);
	}

	public void logScreenShot(String logScreenShotName) throws Exception {
		//Disabling this log for now
		//getAgent().takeSnapShot(getTestData().get("testName") + "#groupName_" + getTestData().get("_groupName") + "#" + logScreenShotName);
	}
}
