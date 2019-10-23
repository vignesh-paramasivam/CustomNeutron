package testcore.controls.common;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import control.WebControl;
import page.IPage;

public class DropdownControl extends WebControl {

	private String create_dropdown_value_identifier = "li.react-autosuggest__suggestion--first";
	String dropdown_input_identifier = ".//div[@role='combobox']//input[@type='text'] | .//input[@role='combobox']";
	String select_menu_outer = "//div[@class='Select-menu-outer']";

	public DropdownControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void enterText(String text) throws Exception{
		int count = 0;
		int maxTries = 2;
		while(true) {
			try {
			this.waitUntilClickable();
			WebElement dropdown_element = this.getRawWebElement();
			List<WebElement> elements = dropdown_element.findElements(By.xpath(dropdown_input_identifier));

			if(elements.size() < 1){
				dropdown_element.click();
				List<WebElement> listOfOptions = dropdown_element.findElements(By.cssSelector("ul li"));

				/*Dropdown values are detached from dropdown input box - used in edit filters page*/
				if (listOfOptions.size() < 1){
					listOfOptions = this.getAgent().getWebDriver().findElements(By.cssSelector("div:not([ant-select-dropdown-hidden]) ul li.ant-select-dropdown-menu-item"));
				}
				boolean setIfOptionAvailable = false;

				for (WebElement options : listOfOptions) {
					if (text.equals(options.getText())) {
						setIfOptionAvailable = true;
						this.getAgent().scrollIntoView(options);
						options.click();
						getPage().assertPageLoad();
						break; 	}
				}

				if (!setIfOptionAvailable){
				    throw new Exception("Dropdown option " + text + " is not available. retrying...");
				}
				return;
			}
			else {
				WebElement dropdown = dropdown_element.findElement(By.xpath(dropdown_input_identifier));
				/*dropdown.clear() is not clearing the value, so clearing it using close icon*/
				if(!dropdown.getAttribute("value").equals("")) {
					dropdown.findElement(By.xpath(".//ancestor::div[contains(@class, 'ant-form-item-control-wrapper')]//i[contains(@class, 'close-icon')]")).click();
				}
				dropdown.sendKeys(text);
				selectValueFromDropDown();
				return;
			}
			} catch (Exception e) {
				if (++count == maxTries) {
					logger.error("*****Failure in dropdown selection******");
					logger.error(e.getStackTrace());
					throwControlActionException(e);
				}
			}
		}
	}


	@Override
	public void enterValue(String text) throws Exception{
		enterText(text);
	}

	/**
	 * select the value from auto-polulated suggestions
	 */

	/*TODO: Here, the first option is getting selected - instead we need to identify the element
	 * based on the absolute name */
	public void selectValueFromDropDown() throws Exception {
		List<WebElement> selectElements = this.getAgent().getWebDriver().findElements(By.cssSelector(create_dropdown_value_identifier));

		if (selectElements.size() > 0) {
			selectElements.get(0).click();
			getPage().assertPageLoad();
			return;
		}

		WebElement selectElement = this.getAgent().getWebDriver().findElement(By.xpath(select_menu_outer));
		String locator = "//div[contains(@class,'Select-option')]";
		List<WebElement> dropDownItem = selectElement.findElements(By.xpath(locator));
		dropDownItem.get(0).click();
		getPage().assertPageLoad();
	}

	@Override
	public String getValue() throws Exception {
		try {
			this.waitUntilVisible();
			List<WebElement> valueElement = this.getRawWebElement().findElements(By.cssSelector(".ant-select-selection-selected-value , .Select-value-label , .Select-placeholder"));
			if (valueElement.size() > 0) {
				return valueElement.get(0).getText();
			}
			return this.getRawWebElement().findElement(By.xpath(dropdown_input_identifier)).getAttribute("value");
		} catch (Exception e) {
			throwControlActionException(e);
		}

		return null;
	}


	@Override
	public boolean isElementPresent() {
		boolean result = false;
		this.waitUntilVisible();
		try{
			if(this.getRawWebElement().findElement(By.cssSelector(".ant-select-selection-selected-value , .Select-value-label , .Select-placeholder")).isDisplayed()) {
				return true;
			}
		} catch(NoSuchElementException e){
			return this.getRawWebElement().findElement(By.xpath(dropdown_input_identifier)).getAttribute("value").equals("");
		}
		return result;
	}

	@Override
	public boolean isDropdown() {
		boolean result = false;
		WebElement dropdown_element = this.getRawWebElement();
		List<WebElement> elements = dropdown_element.findElements(By.xpath(".//input[@role='combobox']"));
		if(elements.size() < 1){
			dropdown_element.click();
			List<WebElement> listOfOptions = dropdown_element.findElements(By.cssSelector("ul li"));
			if(listOfOptions.size() > 0)
				result = true;
		}
		return result;
	}


	@Override
	public void clearfield() throws Exception {
		try {
			this.waitUntilVisible();
			this.getRawWebElement().findElement(By.cssSelector(".ant-select-selection__clear")).click();
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}

	public boolean checkDuplicateValueInDropDownbox() throws Exception {
		boolean isElement = true;
		List<String> list = new ArrayList<String>();

		List<WebElement> eList = getAllOptionsFromDropDownbox();
		for(int i = 0; i < eList.size(); i++) {
			list.add(eList.get(i).getText());
		}

		for(int j = 0; j < list.size()-1; j++) {
			for(int k = j + 1; k < list.size(); k++) {
				if(list.get(j).equals(list.get(k))) {
					isElement = false;
					break;
				}
			}
			break;
		}
		return isElement;
	}

	private List<WebElement> getAllOptionsFromDropDownbox() throws Exception {
		return this.getAgent().getWebDriver().findElements(By.cssSelector(".react-autosuggest__suggestions-list > li[role='option'] > div"));
	}
}
