package testcore.controls.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import control.WebControl;
import page.IPage;

public class MenuControl extends WebControl {

	public MenuControl(String name, IPage page, WebElement element) {
		super(name, page, element);
		
	}

	@Override
	public void click() throws Exception {
		this.getRawWebElement().click();
		getPage().assertPageLoad();
	}

	public void selectMenu(String menuName) throws Exception {
		String[] menus = menuName.split(";");
		String mainMenu = menus[0];
		String childMenu = menus[1];

		//TODO: Handle menu selection in multiple levels dynamically
		WebElement menuParent = this.getRawWebElement();
		menuParent.findElement(By.xpath(".//table[@class='TMenuItemChild0']//td[text()='" + mainMenu + "']")).click();
		menuParent.findElement(By.xpath(".//div[@class='TPopUp0']//td[text()='" + childMenu + "']")).click();

		if(menus.length > 2) {
			String subChild = menus[2];
			menuParent.findElement(By.xpath(".//div[@class='TPopUpItem0']//td[text()='" + subChild + "']")).click();
		}

	}
}
