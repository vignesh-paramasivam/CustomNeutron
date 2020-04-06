package testcore.pages.Connect.Steps;

import agent.IAgent;
import central.Configuration;
import enums.ConfigType;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
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
		String username = getConfig().getValue(ConfigType.DB_USER);
		String password = getConfig().getValue(ConfigType.DB_PASSWORD);
		String dbUrl = getConfig().getValue(ConfigType.DB_URL);
		String query = "SELECT segments.id AS segmentid,segments.segment_name,organization.name AS Organization,country.name AS Country,segment_status.status,segment_details.dg_query_attributes\n" +
				"FROM segments \n" +
				"JOIN organization ON segments.organization_id = organization.org_guid \n" +
				"JOIN country ON segments.segment_country_id=country.country_id\n" +
				"JOIN segment_status ON segments.segment_status_id=segment_status.status_id\n" +
				"JOIN segment_details ON segments.id=segment_details.segment_id_zat\n" +
				"where (segments.id="+ getTestData().get("SegmentID")+")";

		String segmentIdFromDB = "";
		String SegmentNameFromDb = "";
		String organizationNameFromDb = "";
		String CountryFromDb = "";
		String SegmentStatusFromDb="";
		String dbQueryAttributes = "";
		DBUtils dbConnection = new DBUtils(username, password, dbUrl);
		ResultSet queryResult = dbConnection.select(query);

		while (queryResult.next()){
			segmentIdFromDB = queryResult.getString("segmentid");
			SegmentNameFromDb= queryResult.getString("segment_name");
			organizationNameFromDb= queryResult.getString("organization");
			CountryFromDb= queryResult.getString("country");
			SegmentStatusFromDb = queryResult.getString("status");
			dbQueryAttributes = queryResult.getString("dg_query_attributes");
		}

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(segmentIdFromDB, getTestData().get("SegmentID"), "Segment ID from DB is not matching with ID in UI");
		softAssert.assertEquals(SegmentNameFromDb, getTestData().get("SegmentName"),"Segment name not matching with name in UI");
		softAssert.assertEquals(organizationNameFromDb, getTestData().get("Organization"),"Organization is not matching with org in the UI");
		softAssert.assertEquals(CountryFromDb, getTestData().get("Country"),"Country not matching with Country shown in the UI");
		softAssert.assertEquals(SegmentStatusFromDb, getTestData().get("SegmentStatus"),"Segment status not matching status shown in UI");
		String partialStringToVerify = getTestData().get("SegmentID") + " as segmentid"; // e.g. "50414 as segmentid"
		softAssert.assertTrue(dbQueryAttributes.contains(partialStringToVerify), partialStringToVerify + " not added to the query");

		softAssert.assertAll();
		return this;
	}


	public SegmentsPageSteps verifyDataLayerStatus() throws Exception {
		//TODO: This method is WIP
		String username = getConfig().getValue(ConfigType.DB_USER);
		String password = getConfig().getValue(ConfigType.DB_PASSWORD);
		String dbUrl = getConfig().getValue(ConfigType.DB_URL);
		String query = "SELECT segment_id,type,job_guid FROM public.segment_running_status where segment_id="+ getTestData().get("SegmentID");
		DBUtils dbConnection = new DBUtils(username, password, dbUrl);
		ResultSet queryResult = dbConnection.select(query);

		while (queryResult.next()){
			String type = queryResult.getString("type");
			String jobId = queryResult.getString("job_guid");
			logger.info("type: " + type);
			logger.info("jobId: " + jobId);

			//Add the JobID for respective type into the generic data
			getTestData().put("JobId_" + type, jobId);
		}
		return this;
	}
}
