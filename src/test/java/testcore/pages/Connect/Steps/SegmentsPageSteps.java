package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import org.testng.Assert;
import testcore.pages.Connect.SegmentsPage;
import utils.DBUtils;

import java.sql.ResultSet;
import java.util.Map;

public class SegmentsPageSteps extends SegmentsPage {


	public SegmentsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}


	public SegmentDetailsPageSteps gotoCreateSegmentPage() throws Exception {
		getButtonControl("CREATE SEGMENT").click();
		assertPageLoad();
		return new SegmentDetailsPageSteps(this.getConfig(), this.getAgent(), this.getTestData());
	}

	public SegmentsPageSteps selectOrgAndCountry() throws Exception {
		assertPageLoad();
		getDropdownControl("Select Organization").enterValue(getTestData().get("Organization"));
		assertPageLoad();
		getDropdownControl("Select Country").enterValue(getTestData().get("Country"));
		assertPageLoad();
		return this;
	}

	public SegmentsPageSteps verifyNewSegmentIsDisplayed() throws Exception {
		String createdSegmentName = getTestData().get("SegmentName");
		getTextboxControl("Search Segments").enterValue(createdSegmentName);
		Thread.sleep(1000); // Static wait of 1s is added to get the loading spinner to display
		assertPageLoad();

		//Adding the segment status to testdata, it can be accessed as getTestData().get("SegmentStatus");
		getTestData().put("SegmentStatus", getSegmentStatus(createdSegmentName));
		getTestData().put("SegmentID", getSegmentID(createdSegmentName));

		Assert.assertTrue(getAllSegmentPostersNames().contains(createdSegmentName), createdSegmentName + " is not available. Available posters:: " + getAllSegmentPostersNames());
		return this;
	}

	public SegmentsPageSteps verifyNewSegmentInDatabase() throws Exception {
		//todo: THIS METHOD IS WIP

		String username = "zatuser";
		String password = "dNyEjFxtl88iaeuG";
		String dbUrl = "jdbc:postgresql://localhost:5431/prod_zat_rds";
		String query = "SELECT * FROM public.segment_details where segment_id_zat=" + getTestData().get("SegmentID") + ";";

		String segmentIdFromDB = "";
		String dbQueryAttributes = "";

		DBUtils dbConnection = new DBUtils(username, password, dbUrl);
		ResultSet queryResult = dbConnection.select(query);
		while (queryResult.next()){
			segmentIdFromDB = queryResult.getString("segment_id_zat");
			dbQueryAttributes = queryResult.getString("dg_query_attributes");

			/*System.out.println("id: " + queryResult.getString("id"));
			System.out.println("segment_id_zat: " + queryResult.getString("segment_id_zat"));
			System.out.println("data_groups: " + queryResult.getString("data_groups"));
			System.out.println("segment_suppression_attributes: " + queryResult.getString("segment_suppression_attributes"));
			System.out.println("targeting_segment_details: " + queryResult.getString("targeting_segment_details"));
			System.out.println("dg_ui_query: " + queryResult.getString("dg_ui_query"));
			System.out.println("dg_transformed_query: " + queryResult.getString("dg_transformed_query"));
			System.out.println("dg_query_attributes: " + queryResult.getString("dg_query_attributes"));*/
		}

		//Verifies the ID is available in the database
		Assert.assertEquals(segmentIdFromDB, getTestData().get("SegmentID"), "Segment ID from DB is not matching with ID in UI");
		String partialStringToVerify = getTestData().get("SegmentID") + "as segmentid"; // e.g. 50414 as segmentid
		Assert.assertTrue(dbQueryAttributes.contains(partialStringToVerify), partialStringToVerify + " not added to the query");

		return this;
	}

}
