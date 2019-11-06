package testcore.pages.SiteManagement.Steps;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testcore.controls.common.GridControl;
import testcore.pages.SiteManagement.SiteVisitsPage;
import utils.RandomData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitReportPageSteps extends SiteVisitsPage {


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
		return new VisitReportPageSteps(getConfig(), getAgent(), getTestData());
	}


	public SitesPageSteps verifyValuesInGrid() throws Exception {
		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();

		uniqueValuesToIdentifyRow.put("Linked Visit", getTestData().get("visitScheduleName"));

		HashMap<String, String> allValuesToIdentifyRow = new HashMap<>();
		allValuesToIdentifyRow.put("Status", getTestData().get("ReportStatus"));

		getGridControl("summaryTable").verifyValues(uniqueValuesToIdentifyRow, allValuesToIdentifyRow);
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}


	/*
	 *PRIVATE METHODS TO BE ADDED BELOW: USED AS SUB-STEP UNDER STEP
	 * Add methods as private only when it is used as a sub-step. Else, make it public to use..
	 * ..it under test cases
	 */

	private void selectActivity() throws Exception {
		getLinkControl("Pick Site Visit Activity").click();
		waiter().until(ExpectedConditions.numberOfWindowsToBe(2));
		switchToNewWindow();
		assertPageLoad();

		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Activity Name", getTestData().get("visitScheduleName"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("summaryTable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		List<WebElement> expectedColumn = grid.columns(row);
		//Clicking on the pick button
		expectedColumn.get(expectedColumn.size() - 1).findElement(By.cssSelector("input")).click();
		switchToMainWindow();
		assertPageLoad();
	}

	private void selectReportName() throws Exception {
		switchToMainWindow();
		getLinkControl("pickAndCommitQuestionnaire").click();
		waiter().until(ExpectedConditions.numberOfWindowsToBe(2));
		switchToNewWindow();

		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Title", getTestData().get("Template Name"));

		GridControl grid = new GridControl("myGrid", this, getGridControl("Gentable").thisControlElement());
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		List<WebElement> expectedColumn = grid.columns(row);
		//Clicking on the pick button
		expectedColumn.get(expectedColumn.size() - 1).findElement(By.cssSelector("input")).click();
		switchToMainWindow();
		assertPageLoad();
	}

}
