package testcore.controls.common;

import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class LinkControl extends WebControl {
	
	public LinkControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}
	
	@Override
	public void click() {
		this.waitUntilClickable();
		this.getRawWebElement().click();
	}

}