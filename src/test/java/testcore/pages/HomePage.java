package testcore.pages;

import agent.IAgent;
import central.Configuration;

import io.qameta.allure.Step;
import org.apache.commons.lang3.NotImplementedException;
import org.testng.Assert;
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

	@Step("Navigate to {menuName}")
	public HomePage navigateTo(String menuName) throws Exception {
		Assert.fail("Method implementation done in desktop page class");
		return new HomePage(getConfig(), getAgent(), getTestData());
	}

	public AllPages on() throws Exception {
		return new AllPages(getConfig(), getAgent(), getTestData());
	}
}
