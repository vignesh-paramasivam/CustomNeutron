package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

		String studySiteNumber = "TestSite - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("studySiteNumber", studySiteNumber);

		getTextboxControl("Study Site Number").enterValue(studySiteNumber);
		getLinkControl("Pick Address").click();
		assertPageLoad();

		onOrgAddressPick();
		clickSave();
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps searchNewlyAddedSite() throws Exception {
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

		GridControl grid = new GridControl("myGrid", this, getGridControl("Gentable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		grid.getColumn(row, "Study Site Number");
		return new SiteVisitsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public VisitReportPageSteps openVisitReportForSite() throws Exception {
		Thread.sleep(3000); //TODO: Looks like page is getting refreshed in splits. Static wait will be removed after fix
		getDropdownControl("drugtrialIdSrch_cb").enterValue(getTestData().get("studyName"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("summaryTable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		grid.getColumn(row, "Visit Reports").findElement(By.cssSelector("a img")).click();
		assertPageLoad();
		return new VisitReportPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps addNewSite() throws Exception {
		addNewSiteAndSave();
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

	/*
	*PRIVATE METHODS TO BE ADDED BELOW: USED AS SUB-STEP UNDER STEP
	* Add methods as private only when it is used as a sub-step. Else, make it public to use..
	* ..it under test cases
	*/

	private void onOrgAddressPick() throws Exception {
		switchToNewWindow();
		getTextboxControl("companyNameSrch").enterValue("*");
		getButtonControl("btnSearch").click();
		assertPageLoad();

		//Using the existing Org name for test purpose
		String orgName = "Test25 - Org1"; // getTestData().get("studyName");
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Organization Name", orgName);

		GridControl grid = new GridControl("myGrid", this, getGridControl("Gentable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		List<WebElement> expectedColumn = grid.columns(row);
		//Clicking on the pick button
		expectedColumn.get(expectedColumn.size() - 1).findElement(By.cssSelector("input")).click();
		switchToMainWindow();
		assertPageLoad();

	}
}
