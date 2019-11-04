package testcore.pages;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.desktop.DesktopLoginPage;

import java.util.Map;

public class LoginPage extends BasePage {



	public LoginPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public LoginPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		LoginPage derivedLoginPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedLoginPage = new DesktopLoginPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedLoginPage;
	}

	@Override
	public String pageName() {
		return LoginPage.class.getSimpleName();
	}

	public HomePage login() throws Exception {
		return new HomePage(getConfig(), getAgent(), getTestData()).createInstance();
	}
}
