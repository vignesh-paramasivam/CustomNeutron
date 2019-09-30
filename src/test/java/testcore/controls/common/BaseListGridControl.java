package testcore.controls.common;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import page.IPage;

public class BaseListGridControl extends GridControl {

	String COLUMNS_SANDWICH_ICON1_IDENTIFIER="ancestor::div[contains(@class, 'ag-header-cell')]//span[contains(@class, 'ag-icon-menu')]";
	String COLUMNS_SANDWICH_ICON2_IDENTIFIER="//span[@class='ag-icon ag-icon-columns']";
	String SECOND_SANDWICH_lIST="//span[@class='ag-column-select-label']";
	String FIRST_SANDWICH_LIST="//div[@class='ag-menu-list']/div[@class='ag-menu-option']//span[@id='eName']";
	String PIN_COLUMN_LIST="(//div[@class='ag-menu-list'])[2]//span[@id='eName']";

	String LEFT_PINNED_COLUMNS = "ancestor::div[contains(@class, 'ag-body-viewport-wrapper')]/preceding-sibling::div[contains(@class, 'ag-pinned-left-cols-viewport')]";

	public BaseListGridControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	/** Selecting value from first tab**/
	public void open_firstsandwichtab(String colunm_name) throws Exception{
		try{
			this.waitUntilClickable();
			this.getAgent().getWaiter().until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@col-id='0' and @class='ag-header-cell']")));
			WebElement grid_element = this.getRawWebElement();
			List<WebElement> headers = grid_element.findElements(By.cssSelector(COLUMNS_HEADERS_IDENTIFIER));
			for (WebElement colunm_names : headers){
				if(colunm_name.equals(colunm_names.getText())){
					/*Hover to column header name */
					Actions action = new Actions(this.getAgent().getWebDriver());
					WebElement column_to_select = colunm_names;
					action.moveToElement(column_to_select).build().perform();
					column_to_select.findElement(By.xpath(COLUMNS_SANDWICH_ICON1_IDENTIFIER)).click();
				}
			}    		
		}
		catch (Exception e) {
			throwControlActionException(e);
		}

	}

	/** Selecting value from first pop-up **/
	public void select_firstsandwichtab_option(String option_name) throws Exception{
		try {
			this.waitUntilClickable();
			List<WebElement> options = this.getAgent().getWebDriver().findElements(By.xpath(FIRST_SANDWICH_LIST));
			for (int i=0; i<options.size(); i++){
				if(options.get(i).getText().equals(option_name)){
					options.get(i).click();
					break;
				}
			}    		
		}
		catch (Exception e) {
			throwControlActionException(e);
		}
	}
	/** Selecting value from second pop-up **/
	public void column_pin(String option) throws Exception{
		try {
			this.waitUntilClickable();
			WebElement secondtable_dropdown = this.getRawWebElement();
			List<WebElement> first_sandwich_options = secondtable_dropdown.findElements(By.xpath(FIRST_SANDWICH_LIST));
			for (WebElement option_names : first_sandwich_options){
				if(option_names.getText().equals("Pin Column")){
					option_names.click();
					break;
				}
			}
			List<WebElement> pincoloumn_options = secondtable_dropdown.findElements(By.xpath(PIN_COLUMN_LIST));
			for (WebElement pincoloumn_options_names : pincoloumn_options){
				if(pincoloumn_options_names.getText().equals(option)){
					pincoloumn_options_names.click();
					break;
				}
			}    		
		}
		catch (Exception e) {
			throwControlActionException(e);
		}

	}

	public void check_secondsandwichtab_options(String[] option_names) throws Exception{
		secondsandwichtab_options("check", option_names);
	}

	public void uncheck_secondsandwichtab_options(String[] option_names) throws Exception{
		secondsandwichtab_options("uncheck", option_names);
	}

	private void secondsandwichtab_options(String condition, String[] option_names) throws Exception{
		try {
			this.waitUntilClickable();
			WebElement sandwich_item = this.getAgent().getWebDriver().findElement(By.xpath(COLUMNS_SANDWICH_ICON2_IDENTIFIER));
			sandwich_item.click();
			for (String option_name : option_names) {
				boolean isChecked = false;

				WebElement this_option_element = this.getAgent().getWebDriver().findElement(By.xpath("//span[@class='ag-column-select-label'][text()='" + option_name + "']"));

				if(condition == "check") {
					isChecked = this_option_element.findElements(By.xpath("preceding-sibling::span[contains(@class, 'g-checkbox-checked')][contains(@class, 'ag-hidden')]")).size() > 0;
				}
				else {
					isChecked = this_option_element.findElements(By.xpath("preceding-sibling::span[contains(@class, 'g-checkbox-unchecked')][contains(@class, 'ag-hidden')]")).size() > 0;

				}

				if(isChecked) {
					this_option_element.click();
				}
			}
			sandwich_item.click();
		}
		catch (Exception e) {
			throwControlActionException(e);
		}
	}

	/*This method is to select the checkbox in the baselist grid based on identifying the row using its unique column values.
	 * Its input will be as hashmap having column name and its value e.g. map_ary.put("Asset number", "1234")*/
	public void select_row_checkbox(HashMap<String, String> unique_column_name_and_values) throws Exception {
		WebElement row_to_select_checkbox = getRow_BasedOnUniqueColumnValues(unique_column_name_and_values);

		String row_id = row_to_select_checkbox.getAttribute("row-id");
		String identifier_checkbox_container = ".//div[@row-index='" + row_id + "']//div[@col-id='0']//span[contains(@class, 'ag-selection-checkbox')]";

		WebElement checkbox_columns_container = row_to_select_checkbox.findElement(By.xpath(LEFT_PINNED_COLUMNS));
		WebElement checkbox_container = checkbox_columns_container.findElement(By.xpath(identifier_checkbox_container));
		WebElement element_checkbox_checked = checkbox_container.findElement(By.xpath("span[contains(@class, 'ag-icon-checkbox-checked')]"));

		/*If the checkbox is already selected, ignore it, else check it*/
		if((element_checkbox_checked.getAttribute("class")).contains("ag-hidden")) {
			checkbox_container.click();
		}		
	}


	public void  scrollDownAndUp() throws Exception{
		List<WebElement> rowList = rows();
		this.getRawWebElement().click();
		for(int i =0 ; i < rowList.size() ;i++){			
			Actions builder = new Actions(this.getAgent().getWebDriver());
			builder.sendKeys(Keys.ARROW_DOWN).release().build().perform();
		}
		for(int i =0 ; i < rowList.size() ;i++){			
			Actions builder = new Actions(this.getAgent().getWebDriver());
			builder.sendKeys(Keys.ARROW_UP).release().build().perform();
		}	
	}

	public void  pageUpAndDown() throws Exception{
		this.getRawWebElement().click();	
		Actions builder = new Actions(this.getAgent().getWebDriver());
		builder.sendKeys(Keys.PAGE_DOWN).release().build().perform();
		builder = new Actions(this.getAgent().getWebDriver());
		builder.sendKeys(Keys.PAGE_UP).release().build().perform();

	}
	
	/*This method is intended to click equipment number cell based on
	 * row identified. Similar function "select_row_checkbox()"*/
	public void select_row_and_click(String equiment_number) throws Exception {
		List<WebElement>  rows =  rows();
		List<String> columnsHeaders =  column_headers();
		int columnIndex = -1;
		int flag = 0;
		for(WebElement row : rows){
			List<WebElement> columns = columns(row);
			columnIndex = columnsHeaders.indexOf("EQUIPMENT NUMBER");
			WebElement column = columns.get(columnIndex+1);	
			if(column.getText().contains(equiment_number)){
				flag = 1;
				columnIndex = columnsHeaders.indexOf("MODEL");
				column = columns.get(columnIndex+1);
				column.click();
				break;
			}
		}
		
		Assert.assertTrue(flag > 0 ? true : false, "Unable to search the row");
	}




}
