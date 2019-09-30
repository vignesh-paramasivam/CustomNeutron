package testcore.controls.common;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import control.WebControl;
import org.testng.Assert;
import page.IPage;

public class CascadingDropdownControl extends WebControl {

	String CASCADING_MENU_INDENTIFIER = "../following-sibling::div//ul[@class='ant-cascader-menu']";
	String CASCADINGDROPDOWN_DELETE_IDENTIFIER     = "following-sibling::i[contains(@class,'anticon anticon-cross-circle')][contains(@class,'ant-cascader-picker-clear')]";
	String CASCADINGDROPDOWN_IDENTIFIER            = "preceding-sibling::span[@class='ant-cascader-picker-label']";

	public CascadingDropdownControl(String name, IPage page, WebElement element) {
		super(name, page, element);
	}

	/**
	Select the value from the cascading menu
	 * @throws Exception 
	 */
	public void selectCascadingOption(String value) throws Exception {
		this.waitUntilClickable();
		WebElement inputTextBox = this.getRawWebElement();
		String[] splittedValue = value.split("/");
		inputTextBox.click();
		int count = 0;

		boolean isOptionSelected = false;
		for (String selectValue : splittedValue) {
			List<WebElement> currentMenu = inputTextBox.findElements(By.xpath(CASCADING_MENU_INDENTIFIER));
			List<WebElement> optionsInCurrentMenu = currentMenu.get(count++).findElements(By.xpath("li"));
			for (WebElement option : optionsInCurrentMenu) {
				if (selectValue.equals(option.getText())) {
					option.click();

					/*Hack to select parent value of a dropdowns items list*/
					if(hackToSelectParentValue(option, optionsInCurrentMenu)){
						return;
					}
					isOptionSelected = true;
					break;
				}

			}
		}

		if(!isOptionSelected){
			Assert.fail("Cascading dropdown option " + value + " not selected/ available");
		}
	}

	private boolean hackToSelectParentValue(WebElement option, List<WebElement> optionsInCurrentMenu) throws Exception {
		boolean val = false;
		if(option.getText() == optionsInCurrentMenu.get(optionsInCurrentMenu.size()-1).getText()){
			if (option.getAttribute("class").contains("ant-cascader-menu-item-expand")){
				this.getAgent().getWebDriver().findElement(By.cssSelector("span.createAssetLabel")).click();
				val = true;
			}
		}
		return val;
	}


	@Override
	public boolean isbreadcrumDropDown() {
		boolean result = false;
		WebElement inputTextBox = this.getRawWebElement();
		inputTextBox.click();
		WebElement currentMenu = inputTextBox.findElement(By.xpath(CASCADING_MENU_INDENTIFIER));
		WebElement optionsInCurrentMenu = currentMenu.findElement(By.xpath("(li)[1]"));
		WebElement option = optionsInCurrentMenu;
		String breadcrumDropDownClassIdentifier = "ant-cascader-menu-item";
		String classValue = option.getAttribute("class");
		if(classValue.contains(breadcrumDropDownClassIdentifier)){
			result = true;
		}
		inputTextBox.click();
		return result;		
	}

	@Override
	public void clearfield() throws Exception{
		try {
			this.waitUntilVisible();
			this.getRawWebElement().findElement(By.xpath(CASCADINGDROPDOWN_DELETE_IDENTIFIER)).click();	
		}catch (Exception e) {
			throwControlActionException(e);
		}
	}

	@Override
	public String getValue() throws Exception {
		try {
			this.waitUntilVisible();
			String text = this.getRawWebElement().findElement(By.xpath(CASCADINGDROPDOWN_IDENTIFIER)).getText();
			return text;
		} catch (Exception e) {
			throwControlActionException(e);
		}
		return null;
	}


}