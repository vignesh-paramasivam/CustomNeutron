package testcore.controls.common;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class TextboxControl extends WebControl {

	public TextboxControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public String placeHolderName() {
		return this.getRawWebElement().getAttribute("placeholder");
	}

	@Override
	public void enterText(String text) throws Exception {
		try {
			this.waitUntilClickable();
			WebElement element = this.getRawWebElement();
			element.click();
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

	@Override
	public String getValue() throws Exception {
		try {
			String msg = String.format("Getting text for %s", this.getControlBasicInfoString());
			logger.debug(msg);
			this.waitUntilVisible();
			String text = this.getRawWebElement().getAttribute("value");
			this.getAgent().takeConditionalSnapShot();
			logger.debug(String.format("Success in %s", msg));
			return text;
		} catch (Exception e) {
			throwControlActionException(e);
		}

		return null;
	}

	public void clear_via_javascript() throws Exception {
		this.getRawWebElement().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
	}


	@Override
	public void sendKeysAfterRemovingExistingValue(String text) throws Exception {
		this.getRawWebElement().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
		this.getRawWebElement().sendKeys(text);
	}
	
}

	

