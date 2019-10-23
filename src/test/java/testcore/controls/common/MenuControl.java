package testcore.controls.common;

import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class MenuControl  extends WebControl {

	public MenuControl(String name, IPage page, WebElement element) {
		super(name, page, element);
		
	}

	@Override
	public void click() throws Exception {
		this.getRawWebElement().click();
		getPage().assertPageLoad();
	}
}
