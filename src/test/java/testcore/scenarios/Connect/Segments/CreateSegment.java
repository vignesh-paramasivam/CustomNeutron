package testcore.scenarios.Connect.Segments;

import org.testng.annotations.Test;
import testcore.scenarios.SupportTest;

public class CreateSegment extends SupportTest {


    @Test(enabled = true, description = "Create new segment")
    public void CreateNewSegment() throws Exception {
        unity.createInstance()
                .login()
                .navigateTo("CONNECT")
                .onSegmentsPage()
                .selectOrgAndCountry()
                .gotoCreateSegmentPage()
                .enterBasicDetails()
                .chooseDataCollections()
                .chooseInputIdentifiers()
                .addFiltersToDataCollections()
                .extendFirstPartyAudience()
                .verifyOutputIdentifiersOptions()
                .chooseOutputIdentifiers()
                .proceed()
                .chooseDestinations()
                .createAndDistribute()
                .verifyNewSegmentIsDisplayed()
                .verifyNewSegmentInDatabase()
                .fetchJobIIDWithType()
                .verifyWorkflowAndJobStatus(10);
    }

    //We are deliberately making this test to fail by passing invalid expected values
    @Test(enabled = true, description = "Validate the options of input identifiers")
    public void ValidateInputIdentifier() throws Exception {
        unity.createInstance()
                .login()
                .navigateTo("CONNECT")
                .onSegmentsPage()
                .selectOrgAndCountry()
                .gotoCreateSegmentPage()
                .enterBasicDetails()
                .chooseDataCollections()
                .verifyInputIdentifiersOptions();
    }

}
