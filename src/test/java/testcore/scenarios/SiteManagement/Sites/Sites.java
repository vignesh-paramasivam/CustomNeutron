package testcore.scenarios.SiteManagement.Sites;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Sites extends SupportTest {

    @Test(enabled = true, description = "Verify adding a new study successfully")
    public void AddNewSite() throws Exception {
        ctms.createInstance()
                .login()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .addNewStudy()
                .navigateTo("Site Management;Sites")
                .onSitesPage()
                .addNewSiteAndSave()
                .navigateTo("Site Management;Sites")
                .onSitesPage()
                .searchNewlyAddedSite()
                .verifyValuesInGrid();
    }


    @Test(enabled = true, description = "Verify adding a new study successfully")
    public void AddVisitScheduleForSite() throws Exception {
        ctms.createInstance()
                .login()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .addNewStudy()
                .navigateTo("Site Management;Sites")
                .onSitesPage()
                .addNewSite()
                .navigateTo("Site Management;Site Visits")
                .onSiteVisitsPage()
                .addVisitScheduleForSite()
                .verifyValuesInGrid();

    }
}
