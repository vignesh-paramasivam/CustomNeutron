package testcore.controls.common;

import control.WebControl;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import page.IPage;

public class NotificationControl extends WebControl {

	public NotificationControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public String getValue() throws Exception {
		return this.getRawWebElement().findElement(By.cssSelector("div.notification_text > span.info")).getText();
	}
	
}

	

