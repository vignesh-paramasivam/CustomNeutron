package testcore.scenarios.SiteManagement.Sites;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Sites extends SupportTest {

    @Test(enabled = true, description = "Verify adding a new study successfully")
    public void AddNewSite() throws Exception {
        ctms.createInstance()
                .login()
                /*.navigateTo("Study Management;Study")
                .on().studyPage().createInstance()
                .addNewStudy()*/
                .navigateTo("Site Management;Sites")
                .on().sitesPage()
                .addNewSiteForStudy();
    }
}
