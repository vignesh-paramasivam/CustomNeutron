package testcore.pages;

import agent.IAgent;
import central.Configuration;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testcore.controls.common.MenuControl;
import testcore.pages.desktop.DesktopHomePage;

import java.util.Map;

public class HomePage extends BasePage {



	public HomePage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public HomePage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		HomePage derivedHomePage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedHomePage = new DesktopHomePage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedHomePage;
	}

	@Override
	public String pageName() {
		return HomePage.class.getSimpleName();
	}

	public void navigateTo(String menuName) throws Exception {
		WebElement menuTopElement = this.getAgent().getWebDriver().findElement(By.id("jsm"));
		new MenuControl("menucontrol", this, menuTopElement).selectMenu(menuName);

		//TODO: The below items needs to be placed in respective page steps;
		//TODO: All sleeps will be removed after adding the page wait strategy

		getLinkControl("Add record(s)").click();
		Thread.sleep(5000);

		getTextboxControl("name").enterValue("Test23Oct - 01");
		getDropdownControl("phase_cb").enterValue("Phase II/III");
		getDropdownControl("status_cb").enterValue("Planning");
		getButtonControl("save2").click();
		Thread.sleep(10000);




	}
	
}
