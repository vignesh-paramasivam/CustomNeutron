package testcore.pages.SiteManagement;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testcore.controls.common.GridControl;
import testcore.pages.BasePage;
import testcore.pages.desktop.SiteManagement.DesktopSiteVisitsPage;
import testcore.pages.desktop.SiteManagement.DesktopVisitReportPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitReportPage extends BasePage {


	public VisitReportPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public VisitReportPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		VisitReportPage derivedVisitReportPage;
		switch(getPlatform()){
			case DESKTOP_WEB:
				derivedVisitReportPage = new DesktopVisitReportPage(getConfig(),getAgent(),getTestData());
				break;
			default:
				throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedVisitReportPage;
	}

	@Override
	public String pageName() {
		return VisitReportPage.class.getSimpleName();
	}


	protected SitesPage clickSave() throws Exception {
		// The ID of save button is different from other pages
		getButtonControl("save3").click();
		assertPageLoad();
		return new SitesPage(getConfig(), getAgent(), getTestData());
	}

	protected SitesPage clickSaveAndClose() throws Exception {
		// The ID of save button is different from other pages
		getButtonControl("save2").click();
		assertPageLoad();
		return new SitesPage(getConfig(), getAgent(), getTestData());
	}

	protected WebElement eSignPopup() throws Exception {
		return driver().findElement(By.id("passwordDiv"));
	}


	protected void selectActivity() throws Exception {
		getLinkControl("Pick Site Visit Activity").click();
		waiter().until(ExpectedConditions.numberOfWindowsToBe(2));
		switchToNewWindow();
		assertPageLoad();

		waitForVisibilityById("summaryTable");
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

	protected void selectReportName() throws Exception {
		switchToMainWindow();
		getLinkControl("pickAndCommitQuestionnaire").click();
		waiter().until(ExpectedConditions.numberOfWindowsToBe(2));
		switchToNewWindow();
		waiter().until(ExpectedConditions.visibilityOfElementLocated(By.className("Gentable")));

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

	protected void performESign() throws Exception {
		switchToNewWindow();
		waitForVisibilityById("username");
		getTextboxControl("username").enterValue(getTestData().get("User"));
		getTextboxControl("password").enterValue(getTestData().get("Password"));
		getButtonControl("create_session_link").click();
		waiter().until(ExpectedConditions.numberOfWindowsToBe(1));
	}

}
