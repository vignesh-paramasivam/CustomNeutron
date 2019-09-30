package testcore.controls.common;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import control.WebControl;
import page.IPage;

public class SectionControl extends WebControl {

	public SectionControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}
	
	/**
	*Returns the rows
	*/
	public List<WebElement> rows(){
	return this.getRawWebElement().findElements(By.cssSelector(".ant-row"));
	}
		
}
