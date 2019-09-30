package testcore.controls.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import control.WebControl;
import page.IPage;

public class GridControl extends WebControl {

	/* These identifiers are added based on BaseListing ag-grid, which can be updated for generic usage across the application*/
	String COLUMNS_HEADERS_IDENTIFIER = "div.rt-th.rt-resizable-header div.rt-resizable-header-content span";
	String COLUMNS_IDS_IDENTIFIER = "div.ag-header-viewport div.ag-header-row div.ag-header-cell , div.ant-table-header tr th:not(.ant-table-selection-column)";
	String ROWS_IDENTIFIER = "div[class=rt-tr-group][role='rowgroup']";
	String COLUMNS_IDENTIFIER = "div[role='gridcell']";
	String COLUMNS_SEARCH_ROW_IDENTIFIER = "div.rt-thead.-filters div.rt-tr div.rt-th input";

	public GridControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	/*Column headers is nothing but Column names*/
	@Override
	public ArrayList<String> column_headers() throws Exception {
		ArrayList<String> column_names = new ArrayList<String>();
		WebElement grid_element = this.getRawWebElement();
		List<WebElement> headers = grid_element.findElements(By.cssSelector(COLUMNS_HEADERS_IDENTIFIER));
		headers.forEach(header -> column_names.add(header.getText()));
		return column_names;
	}

	public List<WebElement> rows() {
		WebElement grid_element = this.getRawWebElement();
		return grid_element.findElements(By.cssSelector(ROWS_IDENTIFIER));
	}

	public List<WebElement> columns(WebElement row) {
		return row.findElements(By.cssSelector(COLUMNS_IDENTIFIER));
	}

	private WebElement get_row(List<WebElement> rows, ArrayList<Integer> column_positions ,List<String> expected_column_values) {
		WebElement expected_row = null;

		/*Iterate over all the rows in the grid and verify any row has the expected column values*/
		for(int row_num = 0; row_num < rows.size(); row_num++) {
			List<String> values;
			values = get_column_values_based_on_column_positions(rows.get(row_num), column_positions);

			if(values.equals(expected_column_values)) { return rows.get(row_num); }
		}

		return expected_row;
	}

	private List<String> get_column_values_based_on_column_positions(WebElement identified_row, ArrayList<Integer> column_positions) {
		List<String> values = new ArrayList<String>();

		List<WebElement> columns = identified_row.findElements(By.cssSelector(COLUMNS_IDENTIFIER));
		for(int i = 0; i < column_positions.size(); i++) {
			values.add(columns.get(column_positions.get(i)).getText());
		}

		return values;
	}

	@Override
	public void verifyValues(HashMap<String, String> unique_column_name_and_values,
			HashMap<String, String> expected_column_name_and_values) throws Exception {

		WebElement expected_row = getRow_BasedOnUniqueColumnValues(unique_column_name_and_values);
		HashMap<String, String> actual_column_name_and_values = get_column_values(expected_row);

		compare_expected_vs_actual(expected_column_name_and_values, actual_column_name_and_values);
	}

	public ArrayList<String> column_ids() throws Exception {
		ArrayList<String> column_ids = new ArrayList<String>();
		WebElement grid_element = this.getRawWebElement();
		List<WebElement> temp_ids = grid_element.findElements(By.cssSelector(COLUMNS_IDS_IDENTIFIER));
		temp_ids.forEach(header -> column_ids.add(header.getAttribute("col-id")));
		return column_ids;
	}

	private HashMap<String, String> get_column_values(WebElement identified_row) throws Exception {
		/*Store all column ids available in the grid*/
		ArrayList<String> all_columns_ids = column_ids();

		WebElement grid_element = this.getRawWebElement();
		List<WebElement> headers = grid_element.findElements(By.cssSelector(COLUMNS_HEADERS_IDENTIFIER));

		List<WebElement> headers_values = identified_row.findElements(By.cssSelector(COLUMNS_IDENTIFIER));

		if(grid_element.getAttribute("id").contains("borderLayout_eGridPanel")) {
			/*Clicks on the second cell of the identified row*/
			headers_values.get(1).click();
		}

		HashMap<String, String> actual_column_name_and_values = new HashMap<String, String>();

		for(int id = 0; id < all_columns_ids.size(); id++){
			String this_column_name = headers.get(id).getText();
			String this_column_value = headers_values.get(id).getText();

			if(grid_element.getAttribute("id").contains("borderLayout_eGridPanel")) {
				/* hack to navigate to columns to get visibility and read the value since it is dynamically loading */
				Actions builder = new Actions(this.getAgent().getWebDriver());
				builder.sendKeys(Keys.ARROW_RIGHT).release().build().perform();
			}

			actual_column_name_and_values.put(this_column_name, this_column_value);
		}

		/*Scroll back to start of the grid*/
		if(grid_element.getAttribute("id").contains("borderLayout_eGridPanel")) {
			for (int id = 0; id < all_columns_ids.size(); id++) {
				Actions builder = new Actions(this.getAgent().getWebDriver());
				builder.sendKeys(Keys.ARROW_LEFT).release().build().perform();
			}
		}

		return actual_column_name_and_values;
	}

	private void compare_expected_vs_actual(HashMap<String, String> expected_column_name_and_values,
			HashMap<String, String> actual_column_name_and_values) {

		SoftAssert softAssert = new SoftAssert();

		for(Entry<String, String> entry : expected_column_name_and_values.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			softAssert.assertEquals(actual_column_name_and_values.get(key).trim(), value.trim());
		}

		/*Ensures all values are validated instead of failing in the first incorrect value*/
		softAssert.assertAll();
	}
	
	/*This method may needs to be added to IControl inorder to access via getGridControl*/
	public WebElement getRow_BasedOnUniqueColumnValues(HashMap<String, String> unique_column_name_and_values) throws Exception{
		/*Has all the row elements from ag-grid*/
		List<WebElement> rows = rows();		

		/*Store all column names available in the grid*/
		ArrayList<String> all_columns_names = column_headers();

		/*Store positions of required_column_names*/
		ArrayList<Integer> column_positions = new ArrayList<Integer>();	

		ArrayList<String> unique_column_values = new ArrayList<String>();

		/*Identify the position of the columns, so we can retrieve only values for those columns*/
		for(Entry<String, String> entry : unique_column_name_and_values.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			column_positions.add(all_columns_names.indexOf(key));
			unique_column_values.add(value);

		}

		/* find the row, to verify its column value */
		WebElement expected_row = get_row(rows, column_positions, unique_column_values);
		
		if(expected_row == null) {
			Assert.fail("Unable to find the expected rows based on the given column values");
		}
		
		return expected_row;
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

	@Override
	public WebElement thisControlElement() throws Exception {
		return this.getRawWebElement();
	}

	public void setGridPageSize(String value) throws Exception {
	    WebElement element = thisControlElement().findElement(By.xpath("parent::*/preceding-sibling::*//li[contains(@class, 'ant-pagination-options')] | .//li[contains(@class, 'ant-pagination-options')]"));
	    DropdownControl dropdownControl = new DropdownControl(getName(), getPage(), element);
	    dropdownControl.enterText(value);
    }
	
	public void searchWithColumnHeader(String columnHeader, String value) throws Exception{
		WebElement grid_element = this.getRawWebElement();
		List<WebElement> column_search_input = grid_element.findElements(By.cssSelector(COLUMNS_SEARCH_ROW_IDENTIFIER));
		int indexOfHeader = column_headers().indexOf(columnHeader.toUpperCase().trim());
		WebElement col_input = column_search_input.get(indexOfHeader);
		col_input.sendKeys(value);
	}
}


