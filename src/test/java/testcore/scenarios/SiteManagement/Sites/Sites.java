package testcore.scenarios.SiteManagement.Sites;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class Sites extends SupportTest {

    @Test(enabled = true, description = "Adding a visit report & submit it for CRA submission")
    public void AddVisitReportAndScheduleForSite() throws Exception {
        ctms.createInstance()
                .login()
                .navigateTo("Study Management;Study")
                .onStudyPage()
                .addNewStudy()
                .navigateTo("Site Management;Sites")
                .onSitesPage()
                .addNewSite()
                .openVisitScheduleForSite()
                .addVisitScheduleForSite()
                .verifyValuesInGrid()
                .navigateTo("Templates;Questionnaire Tmpls;Assigned Questionnaires Master Group")
                .onAssignedQuesMasterTmplPageSteps()
                .addAssignedQuesMasterTmplForSite()
                .navigateTo("Site Management;Sites")
                .onSitesPage()
                .openVisitReportForSite()
                .selectVisitActivityAndReportName()
                .verifyValuesInGrid()
                .openReportTrackingForSite()
                .updateReportStatusUnderReportTracking();
    }


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

}
