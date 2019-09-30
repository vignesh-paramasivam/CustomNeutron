package testcore.controls.common;

import control.WebControl;
import org.openqa.selenium.WebElement;
import page.IPage;

public class RadioControl extends WebControl {

	public RadioControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void click() {
		this.getRawWebElement().click();
	}
}