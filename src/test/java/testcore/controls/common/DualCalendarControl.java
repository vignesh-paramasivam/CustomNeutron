package testcore.controls.common;


import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import control.WebControl;
import page.IPage;

public class DualCalendarControl extends WebControl {

	public DualCalendarControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void click() throws InterruptedException {
		this.getRawWebElement().click();
	}

	@Override
	public void enterDateRange(String start_date, String end_date) throws Exception {
		this.waitUntilClickable();
		WebElement element = this.getRawWebElement();
		Thread.sleep(1000);
		element.click();

		String [] start_date_parts = start_date.split("-");
		String startYear = start_date_parts[0];
		String startMonth = start_date_parts[1];
		String startDate = start_date_parts[2];

		String [] end_date_parts = end_date.split("-");
		String endYear = end_date_parts[0];
		String endMonth = end_date_parts[1];
		String endDate = end_date_parts[2];

		// Substring to remove '0' from date.  
		if(startDate.charAt(0) == '0') {
			startDate = startDate.substring(1);
		}

		if(endDate.charAt(0) == '0') {
			endDate = endDate.substring(1);
		}
		// Read value from calendar
		String currentStartYear = element.findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//a[contains(@class, 'ant-calendar-year-select')]")).getText();
		String currentStartMonth = element.findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//a[contains(@class,'ant-calendar-month-select')]")).getText();

		String currentEndYear = element.findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//a[contains(@class, 'ant-calendar-year-select')]")).getText();
		String currentEndMonth = element.findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//a[contains(@class,'ant-calendar-month-select')]")).getText();
		move_to_year(element, startYear, currentStartYear, endYear,currentEndYear );
		move_to_month_and_date(element, currentStartMonth, startMonth, currentEndMonth , endMonth , startDate , endDate);
	}

	private void move_to_year(WebElement date_element, String navigate_to_start_year, String displayed_start_year, String navigate_to_end_year, String displayed_end_year) throws Exception {
		int int_navigate_to_start_year = Integer.parseInt(navigate_to_start_year); 
		int int_displayed_start_year = Integer.parseInt(displayed_start_year);
		int int_navigate_to_end_year = Integer.parseInt(navigate_to_end_year); 
		int int_displayed_end_year = Integer.parseInt(displayed_end_year);
		int difference_in_start_year = int_displayed_start_year - int_navigate_to_start_year ;
		int difference_in_end_year = int_displayed_end_year - int_navigate_to_end_year ;

		String string_value_start_year = Integer.toString(difference_in_start_year);
		String positive_startYear = string_value_start_year.replaceAll("[-]","");
		String string_value_end_year = Integer.toString(difference_in_end_year);
		String positive_endYear = string_value_end_year.replaceAll("[-]","");

		/*Selecting year from left hand table*/
		if(difference_in_start_year > 0.0)
			for (int yr = 0; yr < difference_in_start_year; yr++){
				this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//a[contains(@class,'ant-calendar-prev-year-btn')]")).click();
			}
		else{
			difference_in_start_year=Integer.parseInt(positive_startYear);
			for (int yr = 0; yr < difference_in_start_year; yr++){
				this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//a[contains(@class,'ant-calendar-next-year-btn')]")).click();
			}

		}

		/*Selecting year from right hand table*/
		if(difference_in_end_year > 0.0)
			for (int yr = 0; yr < difference_in_end_year; yr++){
				this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//a[contains(@class,'ant-calendar-prev-year-btn')]")).click();
			}
		else{
			difference_in_end_year=Integer.parseInt(positive_endYear);
			for (int yr = 0; yr < difference_in_end_year; yr++){
				this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//a[contains(@class,'ant-calendar-next-year-btn')]")).click();
			}
		}

	}

	private void move_to_month_and_date(WebElement date_element, String displayed_start_mon_value, String navigate_to_start_month, String displayed_end_mon_value, String navigate_to_end_month, String start_date, String end_date) throws Exception{

		HashMap<String,String> month_value = new HashMap<String, String>();

		month_value.put("jan", "01");
		month_value.put("Feb", "02");
		month_value.put("Mar", "03");
		month_value.put("Apr", "04");
		month_value.put("May", "05");
		month_value.put("Jun", "06");
		month_value.put("Jul", "07");
		month_value.put("Aug", "08");
		month_value.put("Sep", "09");
		month_value.put("Oct", "10");
		month_value.put("Nov", "11");
		month_value.put("Dec", "12");

		HashMap<String,String> month_name = new HashMap<String, String>();

		month_name.put("01", "jan");
		month_name.put("02", "Feb");
		month_name.put("03", "Mar");
		month_name.put("04", "Apr");
		month_name.put("05", "May");
		month_name.put("06", "Jun");
		month_name.put("07", "Jul");
		month_name.put("08", "Aug");
		month_name.put("09", "Sep");
		month_name.put("10", "Oct");
		month_name.put("11", "Nov");
		month_name.put("12", "Dec");

		/*String value for the months*/
		String string_navigate_to_start_month_name = month_name.get(navigate_to_start_month);
		String String_navigate_to_end_month_name = month_name.get(navigate_to_end_month);
		String displayedstartMonthvalue = month_value.get(displayed_start_mon_value);

		int int_displayedstartMonthvalue ; 
		int int_navigate_to_start_month;
		int int_navigate_to_end_month;

		/*Integer value of start and end months*/
		int_displayedstartMonthvalue = Integer.parseInt(displayedstartMonthvalue);
		int_navigate_to_start_month = Integer.parseInt(navigate_to_start_month);
		int_navigate_to_end_month =  Integer.parseInt(navigate_to_end_month);

		/* if start month is less than displayed date, navigate the left calendar to desired month */
		if(int_navigate_to_start_month <= int_displayedstartMonthvalue && int_navigate_to_start_month == int_navigate_to_end_month ){
			select_month_if_less_than_displayed(string_navigate_to_start_month_name, start_date, end_date);
		}

		else if( int_navigate_to_start_month > int_displayedstartMonthvalue && int_navigate_to_start_month == int_navigate_to_end_month ){
			select_month_if_greater_than_displayed(date_element, string_navigate_to_start_month_name, start_date, end_date);
		}

		else {
			select_date_for_different_start_end_months(date_element, string_navigate_to_start_month_name, String_navigate_to_end_month_name, start_date, end_date);
		}

	}

	private void select_month_if_less_than_displayed(String string_navigate_to_start_month_name, String start_date, String end_date) throws Exception{
		/*Select Month*/
		this.getAgent().getWebDriver().findElement(By.xpath("(//div[contains(@class, 'ant-calendar-range-left')]//span[contains(@class,'ant-calendar-my-select')]//a)[1]")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//table[contains(@class,'ant-calendar-month-panel-table')]//td//a[text()='" + string_navigate_to_start_month_name + "']")).click();
		/*Select date*/
		this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//td[not(contains(@class, 'ant-calendar-next-month-btn-day'))][not(contains(@class, 'ant-calendar-last-month-cell'))]//div[text()='" + start_date + "']")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//td[not(contains(@class, 'ant-calendar-next-month-btn-day'))][not(contains(@class, 'ant-calendar-last-month-cell'))]//div[text()='" + end_date + "']")).click();
	}

	private void select_month_if_greater_than_displayed(WebElement date_element, String string_navigate_to_start_month_name, String start_date, String end_date) throws Exception{
		/*Select Month*/
		this.getAgent().getWebDriver().findElement(By.xpath("(//div[contains(@class, 'ant-calendar-range-right')]//span[contains(@class,'ant-calendar-my-select')]//a)[1]")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//table[contains(@class,'ant-calendar-month-panel-table')]//td//a[text()='" + string_navigate_to_start_month_name + "']")).click();
		/*Select date*/
		this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//td[not(contains(@class, 'ant-calendar-next-month-btn-day'))][not(contains(@class, 'ant-calendar-last-month-cell'))]//div[text()='" + start_date + "']")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//td[not(contains(@class, 'ant-calendar-next-month-btn-day'))][not(contains(@class, 'ant-calendar-last-month-cell'))]//div[text()='" + end_date + "']")).click();

	}

	private void select_date_for_different_start_end_months(WebElement date_element, String string_navigate_to_start_month_name, String string_navigate_to_end_month_name, String start_date, String end_date) throws Exception{
		/*Select Month*/
		this.getAgent().getWebDriver().findElement(By.xpath("(//div[contains(@class, 'ant-calendar-range-right')]//span[contains(@class,'ant-calendar-my-select')]//a)[1]")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//table[contains(@class,'ant-calendar-month-panel-table')]//td//a[text()='" + string_navigate_to_end_month_name + "']")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("(//div[contains(@class, 'ant-calendar-range-left')]//span[contains(@class,'ant-calendar-my-select')]//a)[1]")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//table[contains(@class,'ant-calendar-month-panel-table')]//td//a[text()='" + string_navigate_to_start_month_name + "']")).click();

		/*Select date*/
		this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-left')]//td[not(contains(@class, 'ant-calendar-next-month-btn-day'))][not(contains(@class, 'ant-calendar-last-month-cell'))]//div[text()='" + start_date + "']")).click();
		this.getAgent().getWebDriver().findElement(By.xpath("//div[contains(@class, 'ant-calendar-range-right')]//td[not(contains(@class, 'ant-calendar-next-month-btn-day'))][not(contains(@class, 'ant-calendar-last-month-cell'))]//div[text()='" + end_date + "']")).click();
	}		

}


