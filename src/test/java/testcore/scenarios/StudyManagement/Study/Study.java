package testcore.scenarios.StudyManagement.Study;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Study extends SupportTest {


    @Test(enabled = true, description = "Verify adding a new study successfully")
    public void AddNewStudy() throws Exception {
        ctms.createInstance()
                .login()
                .navigateTo("Study Management;Study")
                .studyPage()
                .addNewStudyDetailsAndSave()
                .navigateTo("Study Management;Study")
                .studyPage()
                .searchNewlyAddedStudy()
                .verifyValuesInGrid();
    }
}
