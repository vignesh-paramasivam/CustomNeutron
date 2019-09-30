package testcore.controls.common;

import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class ButtonControl extends WebControl {

	public ButtonControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void click() throws InterruptedException {
		this.waitUntilClickable();
		this.getRawWebElement().click();
	}
}