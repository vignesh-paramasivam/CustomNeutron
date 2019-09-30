package testcore.controls.common;

import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class TextareaControl extends WebControl {
	
	public TextareaControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}
	
	
	@Override
	public void enterText(String text) throws Exception {
		try {
			this.waitUntilClickable();
			WebElement element = this.getRawWebElement();
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}


	@Override
	public void enterValue(String text) throws Exception {
		enterText(text);
	}
}
