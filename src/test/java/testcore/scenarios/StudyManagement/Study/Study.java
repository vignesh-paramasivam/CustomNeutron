package testcore.scenarios.StudyManagement.Study;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Study extends SupportTest {


    @Test(enabled = true)
    public void AddStudy() throws Exception {
        ctms.createInstance().login()
        .navigateTo("Study Management;Study")

        //Fix this
        .addStudy();
    }
}
