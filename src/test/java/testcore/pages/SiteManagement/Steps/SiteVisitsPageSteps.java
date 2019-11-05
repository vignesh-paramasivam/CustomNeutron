package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testcore.controls.common.GridControl;
import testcore.pages.SiteManagement.SiteVisitsPage;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.StudyManagement.StudyPage;
import utils.RandomData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiteVisitsPageSteps extends SiteVisitsPage {


	public SiteVisitsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SiteVisitsPage.class.getSimpleName();
	}


	public SiteVisitsPageSteps addVisitScheduleForSite() throws Exception {
		Thread.sleep(3000);

		getDropdownControl("drugtrialId_cb").enterValue(getTestData().get("studyName"));
		//getDropdownControl("drugtrialId_cb").enterValue("Test - 20191105 191724");//getTestData().get("studyName")

		//TODO: Added hack to continue with the test flow; Appending [] in site search dropdown - needs further analysis
		getDropdownControl("siteIdSrch_cb").enterValue(getTestData().get("studySiteNumber") + " []");
		//getDropdownControl("siteIdSrch_cb").enterValue("TestSite - 20191105 191800 []");

		getButtonControl("btnSearch").click();
		assertPageLoad();

		getLinkControl("Add a new activity").click();
		assertPageLoad();

		Thread.sleep(2000);

		String visitScheduleName = "SQV Visit - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("visitScheduleName", visitScheduleName);
		getTextboxControl("name").enterValue(visitScheduleName);
		getDropdownControl("category_cb").enterValue(getTestData().get("Category"));

		clickSaveAndClose();
		//TODO: "Save and Close" does not navigate to the visits page when performed in IE. Added hack - To be fixed.
		if(getButtonControl("save1").isVisible()){
			getLinkControl("Back to previous view").click();
			assertPageLoad();
		}

		return new SiteVisitsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SitesPageSteps verifyValuesInGrid() throws Exception {
		Thread.sleep(2000);
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();

		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Activity Name", getTestData().get("visitScheduleName"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}
}
