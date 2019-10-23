package testcore.controls.common;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import control.WebControl;
import org.openqa.selenium.support.ui.Select;
import page.IPage;

public class DropdownControl extends WebControl {

	public DropdownControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void enterValue(String value) throws Exception{
		try {
			WebElement inputBox = this.getRawWebElement();
			inputBox.sendKeys(value);
			inputBox.sendKeys(Keys.TAB);
		} catch (Exception e) {
			throwControlActionException(e);
		}
	}


}
