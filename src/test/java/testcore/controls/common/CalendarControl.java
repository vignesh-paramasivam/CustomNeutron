package testcore.controls.common;


import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import control.WebControl;
import org.testng.Assert;
import page.IPage;

public class CalendarControl extends WebControl {

	String CALENDAR_IDENTIFIER = "//div[@class='ant-calendar-date-panel']//td";
	String CALENDAR_TEXTBOX= "//label[@for='ReplacementDate']//parent::div//following-sibling::div//input[@class='ant-calendar-picker-input ant-input']";
	String YEAR_IDENTIFIER =  "//a[@class='ant-calendar-year-select']";
	String MONTH_IDENTIFIER= "//a[@title='Choose a month']";
	String PREVIOUS_YEAR_IDENTIFIER= "//a[@class='ant-calendar-prev-year-btn']";
	String NEXT_YEAR_IDENTIFIER= "//a[@class='ant-calendar-next-year-btn']";
	String PREVIOUS_MONTH_IDENTIFIER="//a[@title='Previous month (PageUp)']";
	String NEXT_MONTH_IDENTIFIER="//a[@title='Next month (PageDown)']";

	public CalendarControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void click() throws InterruptedException {
		this.getRawWebElement().click();
	}

	@Override
	public void enterValue(String date_to_select) throws Exception {
			this.waitUntilClickable();
			WebElement element = this.getRawWebElement();
			element.click();

			String [] dateParts = date_to_select.split("-");
			String year = dateParts[0];
			String month = dateParts[1];
			String date = dateParts[2];

			// Substring to remove '0' from date.  
			if(date.charAt(0) == '0') {
				date = date.substring(1);
			}

			// Read value from calendar
			String currentYear = element.findElement(By.xpath(YEAR_IDENTIFIER)).getText();
			String currentMonth = element.findElement(By.xpath(MONTH_IDENTIFIER)).getText();

			if(currentMonth.equals("") || currentYear.equals("")) {
				currentYear = element.findElement(By.xpath(YEAR_IDENTIFIER)).getText();
				currentMonth = element.findElement(By.xpath(MONTH_IDENTIFIER)).getText();
			}

			move_to_year(element, year, currentYear);
			move_to_month(element, month, currentMonth );
			move_to_date( element, date);
	}


	private void move_to_year(WebElement date_element, String navigate_to_year, String displayed_year) {

		Assert.assertTrue(!navigate_to_year.equalsIgnoreCase(""));
		Assert.assertTrue(!displayed_year.equalsIgnoreCase(""));
		int int_value_navigate_to_year, int_value_displayed_year;
		int_value_navigate_to_year = Integer.parseInt(navigate_to_year);
		int_value_displayed_year = Integer.parseInt(displayed_year);
		int subractYear = int_value_displayed_year - int_value_navigate_to_year;
		String string_value_subractYear = Integer.toString(subractYear);
		String positive_subractYear = string_value_subractYear.replaceAll("[-+.^:,]","");

		if(subractYear > 0.0)
			for (int yr = 0; yr < subractYear; yr++){
				date_element.findElement(By.xpath(PREVIOUS_YEAR_IDENTIFIER)).click();
			}
		else{
			subractYear=Integer.parseInt(positive_subractYear);
			for (int yr = 0; yr < subractYear; yr++){
				date_element.findElement(By.xpath(NEXT_YEAR_IDENTIFIER)).click();
			}
		}
	}

	private void move_to_month(WebElement date_element, String navigate_to_month, String displayed_month){

		HashMap<String,String> month_name = new HashMap<String, String>();
		month_name.put("jan", "01");
		month_name.put("Feb", "02");
		month_name.put("Mar", "03");
		month_name.put("Apr", "04");
		month_name.put("May", "05");
		month_name.put("Jun", "06");
		month_name.put("Jul", "07");
		month_name.put("Aug", "08");
		month_name.put("Sep", "09");
		month_name.put("Oct", "10");
		month_name.put("Nov", "11");
		month_name.put("Dec", "12");

		String monthValue = month_name.get(displayed_month);
		int int_Month_Value, int_navigate_to_month;
		int_Month_Value = Integer.parseInt(monthValue);
		int_navigate_to_month = Integer.parseInt(navigate_to_month);

		int subMonth = int_Month_Value-int_navigate_to_month;
		String string_value_subMonth = Integer.toString(subMonth);
		String positive_subMonth = string_value_subMonth.replaceAll("[-+.^:,]","");
		if(subMonth > 0.0) {

			for (int mon = 0; mon < subMonth; mon++){
				date_element.findElement(By.xpath(PREVIOUS_MONTH_IDENTIFIER)).click();
			}
		}
		else {
			subMonth=Integer.parseInt(positive_subMonth);
			for(int mon = 0; mon < subMonth; mon++){
				date_element.findElement(By.xpath(NEXT_MONTH_IDENTIFIER)).click();
			}
		}
	}

	private void move_to_date(WebElement date_element, String date){
		List<WebElement> dates = date_element.findElements(By.xpath(CALENDAR_IDENTIFIER));

		for (int dat = 0; dat < dates.size(); dat++){
			String CalenderDates = dates.get(dat).getText();

			if(CalenderDates.equals(date)){
				dates.get(dat).click();
				break;							
			}
		}
	}
	
}

