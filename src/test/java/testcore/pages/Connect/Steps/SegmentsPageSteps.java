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
import java.sql.SQLException;
import java.util.ArrayList;
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


	@Step("Go to create segment page")
	public SegmentDetailsPageSteps gotoCreateSegmentPage() throws Exception {
		getButtonControl("CREATE SEGMENT").click();
		assertPageLoad();
		return new SegmentDetailsPageSteps(this.getConfig(), this.getAgent(), this.getTestData());
	}

	@Step("Select organization and country")
	public SegmentsPageSteps selectOrgAndCountry() throws Exception {
		assertPageLoad();
		getDropdownControl("Select Organization").enterValue(getTestData().get("Organization"));
		assertPageLoad();
		getDropdownControl("Select Country").enterValue(getTestData().get("Country"));
		assertPageLoad();
		return this;
	}

	@Step("Verify new segment is displayed in segments page")
	public SegmentsPageSteps verifyNewSegmentIsDisplayed() throws Exception {
		String createdSegmentName = getTestData().get("SegmentName");
		getTextboxControl("Search Segments").enterValue(createdSegmentName);
		Thread.sleep(1000); // Static wait of 1s is added to get the loading spinner to display
		assertPageLoad();

		//Adding the segment status to testdata, it can be accessed as getTestData().get("SegmentStatus");
		getTestData().put("SegmentStatus", getSegmentStatus(createdSegmentName));

		String segmentId = getSegmentID(createdSegmentName);
		getTestData().put("SegmentID", segmentId);
		logger.info(segmentId);

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


	public SegmentsPageSteps fetchJobIIDWithType() throws Exception {
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

	@Step("Verify workflow status changed to success by waiting for {waitTimeInMin} ")
	public SegmentsPageSteps verifyWorkflowAndJobStatus(int waitTimeInMin) throws SQLException, ClassNotFoundException, InterruptedException {
		String query = "SELECT workflow_status FROM workflow_jobs WHERE workflow_id in('"+getTestData().get("JobId_matchtest")+"','"+getTestData().get("JobId_export")+"') " +
				"AND update_ts IN (SELECT max(update_ts) FROM workflow_jobs WHERE workflow_id in ('"+getTestData().get("JobId_matchtest")+"','"+getTestData().get("JobId_export")+"'))";
		String username = "connect-user";
		String password = "MzHsjB7u2KL4B8dA";
		String dbUrl = "jdbc:postgresql://localhost:5443/segments";

		DBUtils dbConnection = new DBUtils(username, password, dbUrl);

		int count = 0;
		int maxWaitTime = waitTimeInMin * 2; //To have polling interval as 30s
		int pollingWaitTimeInMilliSec = 30000; //waitTimeInSec * pollingWaitTimeInMilliSec = Total wait time; 10 * 2 * 30000 = 10Mins;

		while (true) {
			ResultSet queryResult = dbConnection.select(query);
			boolean isWorkflowSuccess = true;
			String workflow_status = "";

			int rsCount = 0;

			while (queryResult.next()) {
				rsCount++;

				workflow_status = queryResult.getString("workflow_status");
				if (!workflow_status.equalsIgnoreCase("success")) {
					isWorkflowSuccess = false;
				}
			}

			if(rsCount == 0) {
				Assert.fail("There are no rows from the output result");
			}

			//If all job status are success - break the loop
			if (isWorkflowSuccess) {
				SoftAssert sf = verifyJobStatus();
				sf.assertAll();
				break;
			} else if(workflow_status.equalsIgnoreCase("failed")){
				SoftAssert sf = verifyJobStatus();
				sf.fail("Workflow failed");
				sf.assertAll();
			} else if(count == maxWaitTime) {
				SoftAssert sf = verifyJobStatus();
				sf.fail("Failing the test due to: Workflow is still running after " + maxWaitTime + "Mins");
				sf.assertAll();
			}

			// Some of the job status are not success - wait for 30s and continue checking again
			Thread.sleep(pollingWaitTimeInMilliSec);
			count++;
		}
		return this;
	}
}
