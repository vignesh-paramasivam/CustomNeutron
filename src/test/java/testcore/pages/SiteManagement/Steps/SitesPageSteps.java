package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import control.IControl;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testcore.controls.common.GridControl;
import testcore.pages.BasePage;
import testcore.pages.HomePage;
import testcore.pages.SiteManagement.SitesPage;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.StudyManagement.StudyPage;
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
		String studyName = getTestData().get("StudyName");
		getDropdownControl("drugtrialIdSrch_cb").enterValue(studyName);
		getButtonControl("btnSelect").click();
		assertPageLoad();

		getLinkControl("Add a new site").click();
		assertPageLoad();

		String studySiteNumber = "TestSite - " + RandomData.alpha_numeric_string(3);

		getTestData().put("studySiteNumber", studySiteNumber);

		getTextboxControl("Study Site Number").enterValue(studySiteNumber);
		getLinkControl("Pick Address").click();

		assertPageLoad();

		onOrgAddressPick();

		assertPageLoad();

		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

	public StudyPageSteps onOrgAddressPick() throws Exception {
		switchToNewWindow();
		getTextboxControl("companyNameSrch").enterValue("*");
		getButtonControl("btnSearch").click();

		assertPageLoad();
		String orgName = getTestData().get("StudyName");
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Organization Name", "Test25 - Org1");

		GridControl grid = new GridControl("myGrid", this, getGridControl("Gentable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		List<WebElement> expectedColumn = grid.columns(row);
		expectedColumn.get(expectedColumn.size() - 1).findElement(By.cssSelector("input")).click();

		assertPageLoad();

		switchToMainWindow();

		getButtonControl("save2").click();

		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps searchNewlyAddedSite() throws Exception {
		String studyName = getTestData().get("StudyName");
		getDropdownControl("drugtrialId_cb").enterValue(studyName);
		getButtonControl("btnSearch").click();
		assertPageLoad();
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps verifyValuesInGrid() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}
}
