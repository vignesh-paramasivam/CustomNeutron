package testcore.controls.common;

import java.util.ArrayList;
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
		if(this.getRawWebElement().getAttribute("class").contains("ng-select-multiple")) {
			selectMultiDropdownOptions(value);
		} else {
			selectDropdownValue(value);
		}
	}


	public ArrayList<String> allDropdownOptions() throws Exception {
		this.getRawWebElement().findElement(By.xpath(".//input[@role='combobox']")).click();
		String optionsRoot = "//div[(contains(@class,'ng-option'))][@role='option']";
		String options_xpath = optionsRoot + "//span[contains(@class, 'ng-option-label')] | " +optionsRoot + "//span |" + optionsRoot;
		List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(options_xpath));

		ArrayList<String> allOptions = new ArrayList<>();
		for (WebElement option : options) {
			allOptions.add(option.getText().trim());
		}
		closeDropdownOptions();
		return allOptions;
	}


	private void selectDropdownValue(String value) throws Exception {
		int count = 0;
		int maxTries = 3;
		while (true) {
			try {
				closeDropdownOptions();
				WebElement inputBox = this.getRawWebElement().findElement(By.xpath(".//input[@role='combobox']"));
				this.getAgent().scrollIntoView(inputBox);
				inputBox.clear();
				inputBox.click();

				for(int i = 0; i < 4 && i < value.toCharArray().length; i++){
					String charVal = new StringBuilder().append(value.charAt(i)).toString();
					inputBox.sendKeys(charVal);
				}

				openDropdownOptions();
				String options_xpath = "//div[(contains(@class,'ng-option'))][@role='option']";
				List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(options_xpath));

				for (WebElement option : options) {
					//If the option has extra spaces when inspecting it, we will remove it and compare
					if (option.getText().trim().replaceAll("\\s+", " ").equalsIgnoreCase(value)) {
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

	private void selectMultiDropdownOptions(String value) throws Exception {
		String[] values = value.split(";");

		for (String valueToSelect: values) {
			int count = 0;
			int maxTries = 3;
			while (true) {
				try {
					closeDropdownOptions();
					WebElement inputBox = this.getRawWebElement().findElement(By.xpath(".//input[@role='combobox']"));
					this.getAgent().scrollIntoView(inputBox);
					inputBox.click();

					for(int i = 0; i < 4 && i < value.toCharArray().length; i++){
						String charVal = new StringBuilder().append(valueToSelect.charAt(i)).toString();
						inputBox.sendKeys(charVal);
					}

					String options_xpath = "//div[(contains(@class,'ng-option'))][@role='option']";
					List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(options_xpath));

					boolean optionAvailable = true;
					for (WebElement option : options) {
						String optionTxt = option.getText().trim().replaceAll("\\s+", " ");
						if (optionTxt.equalsIgnoreCase(valueToSelect)) {
							option.click();
							optionAvailable = false;
							break;
						}
					}
					if(optionAvailable) { throw new Exception("Retry on multi dropdown");}
					break;
				}
				catch (Exception e) {
					Thread.sleep(500);
					if (++count == maxTries) {
						waitForPageLoad();
						Assert.fail(valueToSelect + " option is not available in dropdown" + e);
						throwControlActionException(e);
					}
				}
			}
		}
		closeDropdownOptions();

	}

	private void closeDropdownOptions() throws Exception {
		if(this.getRawWebElement().findElements(By.xpath(".//ng-dropdown-panel[contains(@class, 'ng-select-multiple')]")).size() > 0 || this.getAgent().getWebDriver().findElements(By.xpath("//body//ng-dropdown-panel[contains(@class, 'ng-select-multiple')]")).size() > 0) {
			//Dropdown options list is displayed - close it
			this.getRawWebElement().findElement(By.xpath(".//span[contains(@class, 'ng-arrow-wrapper')]")).click();
		}
	}

	private void openDropdownOptions(){
		if(this.getRawWebElement().findElements(By.xpath(".//ng-dropdown-panel")).size() < 1 && this.getRawWebElement().findElements(By.xpath("//body//ng-dropdown-panel")).size() < 1) {
			//Dropdown options list is not displayed - open it
			this.getRawWebElement().findElement(By.xpath(".//span[contains(@class, 'ng-arrow-wrapper')]")).click();
		};
	}
}
