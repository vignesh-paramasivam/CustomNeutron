package testcore.scenarios.Login;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Login extends SupportTest {


    @Test(enabled = true)
    public void Login_ValidateLoginAndLogout() throws Exception {
        login.login();
    }


}
