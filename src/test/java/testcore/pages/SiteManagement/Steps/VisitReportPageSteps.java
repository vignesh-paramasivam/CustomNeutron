package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import testcore.controls.common.GridControl;
import testcore.pages.SiteManagement.SiteVisitsPage;
import testcore.pages.SiteManagement.VisitReportPage;
import utils.RandomData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitReportPageSteps extends VisitReportPage {


	public VisitReportPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SiteVisitsPage.class.getSimpleName();
	}


	public VisitReportPageSteps selectVisitActivityAndReportName() throws Exception {
		getLinkControl("Add record(s)").click();
		assertPageLoad();
		selectActivity();
		selectReportName();

		//TODO: IE SPECIFIC - After selecting report, page does not navigate back when performed in IE. Added hack - To be fixed.
		if(System.getProperty("browser").equalsIgnoreCase("ie")) {
			getLinkControl("Back to previous view").click();
			assertPageLoad();
		}

		return new VisitReportPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps verifyValuesInGrid() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();

		uniqueValuesToIdentifyRow.put("Linked Visit", getTestData().get("visitScheduleName"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Status", getTestData().get("ReportStatusBeforeUpdate"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}


	public VisitReportPageSteps updateReportStatusUnderReportTracking() throws Exception {
		waitForVisibilityByClass("Gentable");
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Report Name", getTestData().get("Template Name"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("Gentable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		//Click on Edit under "Visit Report Tracking - View"
		grid.getColumn(row, "Edit").findElement(By.cssSelector("a img")).click();
		assertPageLoad();

		getDropdownControl("status_cb").enterValue(getTestData().get("ReportStatusAfterUpdate"));
		clickSaveAndClose();
		getButtonControl("btnSign", eSignPopup()).click();

		performESign();

		switchToMainWindow();

		//Verify Report status updated in the table
		waitForVisibilityByClass("Gentable");
		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Status", getTestData().get("ReportStatusAfterUpdate"));
		getGridControl("Gentable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);

		return new VisitReportPageSteps(getConfig(), getAgent(), getTestData());
	}

}
