package testcore.controls.common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import control.WebControl;
import org.testng.Assert;
import page.IPage;

public class DropdownControl extends WebControl {

	public DropdownControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void enterValue(String value) throws Exception {
		int count = 0;
		int maxTries = 3;
		while (true) {
			try {
				WebElement inputBox = this.getRawWebElement().findElement(By.xpath(".//input[@role='combobox']"));
				inputBox.clear();
				inputBox.click();

				for(int i = 0; i < 4; i++){
					String charVal = new StringBuilder().append(value.charAt(i)).toString();
					inputBox.sendKeys(charVal);
				}

				String optionsRoot = "//div[(contains(@class,'ng-option'))][@role='option']";
				String options_xpath = optionsRoot + "//span[contains(@class, 'ng-option-label')] | " +optionsRoot + "//span";
				List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(options_xpath));

				for (WebElement option : options) {
					if (option.getText().trim().equalsIgnoreCase(value)) {
						option.click();
						return;
					}
				}
				throw new Exception("Retry");
			} catch (Exception e) {
				Thread.sleep(500);
				if (++count == maxTries) {
					waitForPageLoad();
					Assert.fail("Unable to select dropdown value: " + value + "; Failed due to " + e);
					throwControlActionException(e);
				}
			}
		}

	}
}
