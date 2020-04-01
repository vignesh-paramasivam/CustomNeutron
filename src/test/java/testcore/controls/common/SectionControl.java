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

	//Usage in Destinations page - to check whether it is selected or not
	public boolean isSelected (){
		return this.getRawWebElement().getAttribute("class").contains("active");
	}

}
