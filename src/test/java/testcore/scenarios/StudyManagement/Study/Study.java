package testcore.scenarios.StudyManagement.Study;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Study extends SupportTest {


    @Test(enabled = true, description = "Verify adding a new study successfully")
    public void AddNewStudy() throws Exception {
        ctms.createInstance()
                .login()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .addNewStudyDetailsAndSave()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .searchNewlyAddedStudy()
                .verifyValuesInGrid();
    }


    @Test(enabled = true, description = "Verify adding a new study and fail it")
    public void AddNewStudyAndFailIt() throws Exception {
        ctms.createInstance()
                .login()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .addNewStudyDetailsAndSave()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .searchNewlyAddedStudy()
                .verifyValuesInGrid();
    }

}
