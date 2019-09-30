package testcore.pages;

import agent.IAgent;
import central.Configuration;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import testcore.controls.dialog.ConfirmActionDialog;
import testcore.controls.dialog.ForgotPasswordDialog;
import testcore.pages.desktop.DesktopHomePage;

import java.util.Map;

public class HomePage extends FullPage {



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


	
}
