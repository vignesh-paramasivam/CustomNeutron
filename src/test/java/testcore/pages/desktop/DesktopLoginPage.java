package testcore.pages.desktop;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testcore.pages.HomePage;
import testcore.pages.LoginPage;
import utils.RandomData;

import java.util.Map;

public class DesktopLoginPage extends LoginPage {

    public DesktopLoginPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
        super(conf, agent, testData);
        assertPageLoad();
    }

    @Step("Login")
    @Override
    public HomePage login() throws Exception {
        assertPageLoad();
        this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='email']")));
        getTextboxControl("email").enterText(getTestData().get("Email"));
        getTextboxControl("password").enterText(getTestData().get("Password"));
        getButtonControl("LOGIN").click();
        assertPageLoad();
        //Try again if the login click is not performed properly
        if(driver().findElements(By.xpath("//zui-button[@ng-reflect-label='LOGIN'][@ng-reflect-disabled='false']")).size() > 0) {
            getButtonControl("LOGIN").click();
        }
        this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.navBarContainer")));
        return new HomePage(getConfig(), getAgent(), getTestData()).createInstance();
    }
}

