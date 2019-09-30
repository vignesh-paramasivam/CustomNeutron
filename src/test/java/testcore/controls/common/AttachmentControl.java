package testcore.controls.common;

import control.WebControl;
import org.openqa.selenium.WebElement;
import page.IPage;

public class AttachmentControl extends WebControl {

	public AttachmentControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	@Override
	public void enterValue(String filePath) throws Exception{
		this.getRawWebElement().sendKeys(filePath);
		assertPageLoad();
	}
}