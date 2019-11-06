package testcore.pages.Templates.Steps.Questionnaire;

import agent.IAgent;
import central.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testcore.controls.common.GridControl;
import testcore.pages.Templates.Questionnaire.AssignedQuesMasterTmplPage;

import java.util.HashMap;
import java.util.Map;

public class AssignedQuesMasterTmplPageSteps extends AssignedQuesMasterTmplPage {


	public AssignedQuesMasterTmplPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return AssignedQuesMasterTmplPageSteps.class.getSimpleName();
	}



	public AssignedQuesMasterTmplPageSteps addAssignedQuesMasterTmplForSite() throws Exception {
		getDropdownControl("assignedObj_cb").enterValue(getTestData().get("QuestionnaireAssignedTo"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		getDropdownControl("assignedObjId_cb").enterValue(getTestData().get("studyName"));
		getButtonControl("btnSearch").click();
		assertPageLoad();
		getLinkControl("Assign Master Questionnaires From Base Tmpl").click();
		assertPageLoad();

		HashMap<String, String> uniqueValuesToIdentifyRow = new HashMap<>();
		uniqueValuesToIdentifyRow.put("Template Name", getTestData().get("Template Name"));

		//TODO: We have three tables in this page with same identifiers so we identified it static way - need to discuss
		WebElement gridElement = this.getAgent().getWebDriver().findElements(By.cssSelector("table.Gentable")).get(1);

		GridControl grid = new GridControl("myGrid", this, gridElement);
		WebElement row = grid.getRow_BasedOnUniqueColumnValues(uniqueValuesToIdentifyRow);
		grid.getColumn(row, "Select").findElement(By.id("questGrpTmplId")).click();
		assertPageLoad();

		String actualMessage = getNotificationControl("").getValue();
		String expectedMessage = getTestData().get("SuccessMsgMasterTmplCreation");
		Assert.assertTrue(actualMessage.contains(expectedMessage));

		return new AssignedQuesMasterTmplPageSteps(getConfig(), getAgent(), getTestData());
	}
}
