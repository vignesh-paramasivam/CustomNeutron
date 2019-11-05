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
		getDropdownControl("drugtrialId_cb").enterValue(getTestData().get("studyName"));
		//TODO: Added hack to continue with the test flow; Appending [] in site search dropdown - needs further analysis
		getDropdownControl("siteIdSrch_cb").enterValue(getTestData().get("studySiteNumber") + " []");
		getButtonControl("btnSearch").click();
		assertPageLoad();
		getLinkControl("Add a new activity").click();

		String visitScheduleName = "SQV Visit - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("visitScheduleName", visitScheduleName);
		getTextboxControl("name").enterValue(visitScheduleName);
		getDropdownControl("category_cb").enterValue(getTestData().get("Category"));

		//TODO: "Save and Close" does not navigate to the visits page when performed in IE. To be fixed.
		clickSaveAndClose();

		return new SiteVisitsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SitesPageSteps verifyValuesInGrid() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();

		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Activity Name", getTestData().get("visitScheduleName"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}
}
