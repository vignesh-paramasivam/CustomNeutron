package testcore.controls.common;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import control.WebControl;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import page.IPage;

public class DropdownControl extends WebControl {

	public DropdownControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	//CTMS - Dropdown
	@Override
	public void enterValue(String value) throws Exception {
		try {
			WebElement inputBox = this.getRawWebElement();
			inputBox.clear();
			inputBox.sendKeys(value);

			String options_xpath = "//ul[not(contains(@style,'display:none'))]//li[@class='ui-menu-item']/a";
			List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(options_xpath));

			for (WebElement option : options) {
				//TODO: Value should be verified with equals instead of contains(modified - as the text box prints [] during sendKeys)
				if (option.getText().contains(value)) {
					this.getAgent().getWaiter().until(ExpectedConditions.visibilityOf(option));
					option.click();
					return;
				}
			}
			Assert.fail("Unable to find the desired dropdown value:" + value);
		} catch (Exception e) {
			throwControlActionException(e);
		}

	}
}
