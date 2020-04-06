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
                .enterBuildSegmentDetails()
                .addFiltersToDataCollections()
                .extendFirstPartyAudience()
                .verifyOutputIdentifiersOptions()
                .chooseOutputIdentifiers()
                .proceed()
                .chooseDestinations()
                .createAndDistribute()
                .verifyNewSegmentIsDisplayed()
                .verifyNewSegmentInDatabase()
                .verifyDataLayerStatus();
    }

}
