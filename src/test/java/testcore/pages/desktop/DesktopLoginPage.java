package testcore.pages.desktop;

import agent.IAgent;
import central.Configuration;
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

    @Override
    public HomePage login() throws Exception {
        getTextboxControl("user").enterText(getTestData().get("User"));
        getTextboxControl("pwd").enterText(getTestData().get("Password"));
        getButtonControl("btnLogin").click();
        this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#leftPaneDiv")));
        return new HomePage(getConfig(), getAgent(), getTestData());
    }
}
