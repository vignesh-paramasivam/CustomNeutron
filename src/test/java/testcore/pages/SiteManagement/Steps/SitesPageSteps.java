package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testcore.controls.common.GridControl;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.StudyManagement.StudyPage;
import testcore.pages.Templates.Steps.Questionnaire.AssignedQuesMasterTmplPageSteps;
import utils.RandomData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitesPageSteps extends SitesPage {


	public SitesPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}

	public SitesPageSteps addNewSiteAndSave() throws Exception {
		String studyName = getTestData().get("studyName");
		getDropdownControl("drugtrialIdSrch_cb").enterValue(studyName);
		getButtonControl("btnSelect").click();
		assertPageLoad();

		getLinkControl("Add a new site").click();
		assertPageLoad();

		String studySiteNumber = "AA_TestSite - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("studySiteNumber", studySiteNumber);

		getTextboxControl("Study Site Number").enterValue(studySiteNumber);
		getLinkControl("Pick Address").click();
		assertPageLoad();

		onOrgAddressPick();
		clickSaveAndClose();

		//TODO: IE SPECIFIC - "Save and Close" does not navigate to the visits page when performed in IE. Added hack - To be fixed.
		if(System.getProperty("browser").equalsIgnoreCase("ie")) {
			getLinkControl("Back to previous view").click();
			assertPageLoad();
		}

		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps searchNewlyAddedSite() throws Exception {
		waitForVisibilityById("drugtrialIdSrch_cb");
		String studyName = getTestData().get("studyName");
		getDropdownControl("drugtrialIdSrch_cb").enterValue(studyName);
		getButtonControl("btnSearch").click();
		assertPageLoad();
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps verifyValuesInGrid() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();

		System.out.println(getTestData().get("studySiteNumber"));
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SiteVisitsPageSteps openVisitScheduleForSite() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("summaryTable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		grid.getColumn(row, "Visit Schedule").findElement(By.cssSelector("a img")).click();;
		return new SiteVisitsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public VisitReportPageSteps openVisitReportForSite() throws Exception {
		waitForVisibilityById("summaryTable");
		getDropdownControl("drugtrialIdSrch_cb").enterValue(getTestData().get("studyName"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("summaryTable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		WebElement columnIcon = grid.getColumn(row, "Visit Reports").findElement(By.cssSelector("a img"));
		waitUntilElementVisible(columnIcon);
		columnIcon.click();
		assertPageLoad();
		return new VisitReportPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps addNewSite() throws Exception {
		addNewSiteAndSave();
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

	public VisitReportPageSteps openReportTrackingForSite() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("summaryTable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		grid.getColumn(row, "Report Tracking").findElement(By.cssSelector("a img")).click();
		assertPageLoad();
		return new VisitReportPageSteps(getConfig(), getAgent(), getTestData());
	}

}
