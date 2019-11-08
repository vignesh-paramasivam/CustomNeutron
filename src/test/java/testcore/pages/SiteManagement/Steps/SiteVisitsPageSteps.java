package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
		/*getDropdownControl("drugtrialId_cb").enterValue(getTestData().get("studyName"));
		//TODO: Added hack to continue with the test flow; Appending [] in site search dropdown - needs further analysis
		  getDropdownControl("siteIdSrch_cb").enterValue(getTestData().get("studySiteNumber") + " []");
		  getButtonControl("btnSearch").click(); */

		assertPageLoad();

		waiter().until(ExpectedConditions.visibilityOfElementLocated(By.id("toolBarTable")));
		getLinkControl("Add a new activity").click();
		assertPageLoad();

		String visitScheduleName = "SQV Visit - " + RandomData.dateTime_yyyyMMddHHmmss();
		this.getTestData().put("visitScheduleName", visitScheduleName);

		//TODO: IE SPECIFIC - SendKeys is inconsistent in entering value for IE browser. Modified with JS script as workaround until rca/ fix
		//getTextboxControl("name").enterValue(visitScheduleName);
		((JavascriptExecutor) driver()).executeScript(String.format("document.getElementById('name').value='" + visitScheduleName + "';"));

		getDropdownControl("category_cb").enterValue(getTestData().get("Category"));

		clickSaveAndClose();
		//TODO: IE SPECIFIC - "Save and Close" does not navigate to the visits page when performed in IE. Added hack - To be fixed.
		if(System.getProperty("browser").equalsIgnoreCase("ie")){
			getLinkControl("Back to previous view").click();
			assertPageLoad();
		}

		return new SiteVisitsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SitesPageSteps verifyValuesInGrid() throws Exception {
		waitForVisibilityById("summaryTable");
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Study Site Number", getTestData().get("studySiteNumber"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Activity Name", getTestData().get("visitScheduleName"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

}
