package control;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import page.IPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebControl extends Control {


	String CONFIRMATION_DIALOG_IDENTIFIER = "div.ant-modal-content";

	public WebControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public String getValue() throws Exception {
		return null;
	}

	@Override
	public ArrayList<String> column_headers() throws Exception {
		return null;
	}

	@Override
	public void verifyValues(HashMap<String, String> unique_column_name_and_values, HashMap<String, String> expected_column_name_and_values) throws Exception {

	}

	@Override
	public boolean isMandatoryField() throws Exception {
		return false;
	}

	@Override
	public void enterDate(String string) throws Exception {

	}

	@Override
	public void enterValue(String text) throws Exception {

	}

	@Override
	public String getErrorMessage() throws Exception {
		return null;
	}

	@Override
	public boolean isSelected() throws Exception {
		return false;
	}

	@Override
	public boolean isReadOnly() throws Exception {
		return false;
	}

	@Override
	public boolean isDropdown() throws Exception {
		return false;
	}

	@Override
	public void clearfield() throws Exception {

	}

	@Override
	public boolean isElementPresent() throws Exception {
		return false;
	}

	@Override
	public WebElement label() throws Exception {
		return null;
	}

	@Override
	public WebElement getTooltip() throws Exception {
		return null;
	}

	@Override
	public void checkboxCheck() throws Exception {

	}

	@Override
	public void checkboxUnCheck() throws Exception {

	}

	@Override
	public List<WebElement> rows() throws Exception {
		return null;
	}

	@Override
	public void scrollDownAndUp() throws Exception {

	}

	@Override
	public void pageUpAndDown() throws Exception {

	}

	@Override
	public WebElement thisControlElement() throws Exception {
		return null;
	}

	@Override
	public void click() throws Exception {
		try {
			String msg = String.format("Clicking on %s", this.getControlBasicInfoString());
			logger.debug(msg);
			this.waitUntilVisible();
			this.waitUntilClickable();
			this.getRawWebElement().click();
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}

	@Override
	public void enterText(String text) throws Exception {
		try {
			String msg = String.format("Entering text %s in %s", text, this.getControlBasicInfoString());
			logger.debug(msg);
			//			this.waitUntilVisible();
			this.getRawWebElement().sendKeys(text);
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}

	@Override
	public void uploadFile(String text) throws Exception {
		try {
			String msg = String.format("Entering text %s in %s", text, this.getControlBasicInfoString());
			logger.debug(msg);
			this.getRawWebElement().sendKeys(text);
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}


	@Override
	public void clear() throws Exception {
		try {
			String msg = String.format("Clear data from %s", this.getControlBasicInfoString());
			logger.debug(msg);
			this.getRawWebElement().clear();
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}


	@Override
	public void enterText(Keys key) throws Exception {
		try {
			String msg = String.format("Entering keys %s in %s", key, this.getControlBasicInfoString());
			logger.debug(msg);
			this.waitUntilVisible();
			this.getRawWebElement().sendKeys(key);
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}

	@Override
	public String getText() throws Exception {
		try {
			String msg = String.format("Getting text for %s", this.getControlBasicInfoString());
			logger.debug(msg);
			this.waitUntilVisible();
			String text = this.getRawWebElement().getText();
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
			return text;
		} catch (Exception e) {
			throwControlActionException(e);
		}

		return null;
	}

	@Override
	public void assertText(String text) throws Exception {
		Assert.assertEquals(this.getText(), text);
	}

	@Override
	public boolean isVisible() throws Exception {
		//	logger.info("ISVISIBLE - START");
		String msg = String.format("Checking visibility of %s", this.getControlBasicInfoString());
		//	logger.info("ISVISIBLE - STEP1");
		logger.debug(msg);
		boolean condition = getRawWebElement().isDisplayed();
		logger.debug(String.format("Success in %s. Control is visible: %s", msg, condition));
		//	logger.info("ISVISIBLE - END");
		return condition;

	}

	@Override
	public void assertVisible() throws Exception {
		Assert.assertTrue(this.isVisible(), this.getControlBasicInfoString());
	}

	@Override
	public boolean isEnabled() throws Exception {
		try {
			String msg = String.format("Checking clickablity of %s", this.getControlBasicInfoString());
			logger.debug(msg);
			boolean condition = getRawWebElement().isEnabled();
			logger.debug(String.format("Success in %s. Control is enabled: %s", msg, condition));
			return condition;
		} catch (Exception e) {
			throwControlActionException(e);
		}
		return false;
	}

	@Override
	public void assertEnabled() throws Exception {
		Assert.assertTrue(this.isEnabled());
	}

	@Override
	public void scrollTo() throws Exception {
		String msg = String.format("Scrolling till element - ", this.getControlBasicInfoString());
		try {
			logger.debug(msg);
			JavascriptExecutor jse = (JavascriptExecutor) this.getAgent().getWebDriver();
			for (int i = 0; i < 10; i++) {
				try {
					this.getRawWebElement().isDisplayed();
					this.getRawWebElement().click();
					break;
				} catch (Exception e) {
					jse.executeScript(("window.scrollBy(0, 40)"));
				}

			}
			this.getAgent().takeConditionalSnapShot();
		} catch (Exception e) {
			logger.debug(String.format("Failure in %s", msg));
		}
	}

	@Override
	public void selectDropDownByValue(String value) throws Exception {
		try {
			String msg = String.format("Select Value from drop down list - %s", this.getControlBasicInfoString());
			logger.debug(msg);
			Select drpElement = new Select(this.getRawWebElement());
			drpElement.selectByValue(value);
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}

	@Override
	public void waitUntilVisible() {
		this.getAgent().getWaiter().until(ExpectedConditions.visibilityOf(this.getRawWebElement()));
	}

	@Override
	public void waitUntilClickable() {
		this.getAgent().getWaiter().until(ExpectedConditions.elementToBeClickable(this.getRawWebElement()));
	}

	@Override
	public void hover() throws Exception {
		Actions action = new Actions(getAgent().getWebDriver());
		action.moveToElement(this.getRawWebElement()).build().perform();
	}

}
