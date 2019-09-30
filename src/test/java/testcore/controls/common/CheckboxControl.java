package testcore.controls.common;

import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class CheckboxControl extends WebControl {
	
	public CheckboxControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}
	
	@Override
	public void click() throws InterruptedException {
		this.getRawWebElement().click();
	}
	
	@Override
	public void checkboxCheck() throws Exception {
		if(!(this.getRawWebElement().isSelected())){
			this.getRawWebElement().click();
		}
	}

	@Override
	public void checkboxUnCheck() throws Exception {
		if(this.getRawWebElement().isSelected()){
			this.getRawWebElement().click();
		}
	}

}