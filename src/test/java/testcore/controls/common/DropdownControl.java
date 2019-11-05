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
		int count = 0;
		int maxTries = 3;
		while (true) {
			try {
				WebElement inputBox = this.getRawWebElement();
				inputBox.clear();
				inputBox.click();
				inputBox.sendKeys(value);

				String options_xpath = "//ul[not(contains(@style,'display:none'))]//li[@class='ui-menu-item']/a";
				List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(options_xpath));

				for (WebElement option : options) {
					if (option.getText().equalsIgnoreCase(value)) {
						this.getAgent().getWaiter().until(ExpectedConditions.visibilityOf(option));
						option.click();
						return;
					}
				}
				throw new Exception("Retry");
			} catch (Exception e) {
				Thread.sleep(2000);
				if (++count == maxTries) {
					Assert.fail("Unable to select dropdown value: " + value + "; Failed due to " + e);
					throwControlActionException(e);
				}
			}
		}

	}
}
