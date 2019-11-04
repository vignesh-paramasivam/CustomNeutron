package testcore.scenarios.SiteManagement.Sites;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Sites extends SupportTest {

    @Test(enabled = true, description = "Verify adding a new site successfully")
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


    @Test(enabled = true, description = "Verify adding a visit schedule successfully")
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
                .verifyValuesInGrid()
                .navigateTo("Templates;Questionnaire Tmpls;Assigned Questionnaires Master Group")
                .onAssignedQuesMasterTmplPageSteps()
                .addAssignedQuesMasterTmplForSite();
    }
}
