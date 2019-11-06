package testcore.pages.desktop;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testcore.pages.HomePage;
import testcore.pages.LoginPage;

import java.util.Map;

public class DesktopLoginPage extends LoginPage {

    public DesktopLoginPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
        super(conf, agent, testData);
        assertPageLoad();
    }

    @Step("Enter the username and password to login")
    @Override
    public HomePage login() throws Exception {
        assertPageLoad();
        waitForVisibilityById("user");
        getTextboxControl("user").enterText(getTestData().get("User"));
        getTextboxControl("pwd").enterText(getTestData().get("Password"));
        getButtonControl("btnLogin").click();
        this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#leftPaneDiv")));
        return new HomePage(getConfig(), getAgent(), getTestData()).createInstance();
    }
}
