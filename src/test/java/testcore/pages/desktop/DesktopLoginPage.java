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

    @Step("Enter the username and password to login")
    @Override
    public HomePage login() throws Exception {

        //Temp test data: To be added in excel file under resources
        getTestData().put("Organization", "ZEOTAP");
        getTestData().put("Country", "Spain");
        getTestData().put("SegmentName", "MT-Automation" + RandomData.dateTime_yyyyMMddHHmmss());
        getTestData().put("DataCollections", "test-ms-01;e2e-QA-Test-01");
        getTestData().put("InputIdentifiers", "Email;Mobile");
        getTestData().put("OutputIdentifiers", "Google Cookie;MAID");
        getTestData().put("ExtendFirstPartyAudience", "Activate Zeotap Graph");
        getTestData().put("Destinations", "Test_Liveramp;testdest");

        assertPageLoad();
        this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='email']")));
        getTextboxControl("email").enterText(getTestData().get("Email"));
        getTextboxControl("password").enterText(getTestData().get("Password"));
        getButtonControl("LOGIN").click();
        this.getAgent().getWaiter().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section.navBarContainer")));
        return new HomePage(getConfig(), getAgent(), getTestData()).createInstance();
    }
}

