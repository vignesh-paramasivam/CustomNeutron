package testcore.pages;

import agent.IAgent;
import central.Configuration;
import testcore.pages.SiteManagement.Steps.SiteVisitsPageSteps;
import testcore.pages.SiteManagement.Steps.SitesPageSteps;
import testcore.pages.StudyManagement.Steps.StudyPageSteps;
import testcore.pages.Templates.Steps.Questionnaire.AssignedQuesMasterTmplPageSteps;

import java.util.Map;

public class AllPages extends BasePage {


	public AllPages(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return AllPages.class.getSimpleName();
	}


	public StudyPageSteps onStudyPage() throws Exception {
		return new StudyPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SitesPageSteps onSitesPage() throws Exception {
		return new SitesPageSteps(getConfig(), getAgent(), getTestData());
	}

	public SiteVisitsPageSteps onSiteVisitsPage() throws Exception {
		return new SiteVisitsPageSteps(getConfig(), getAgent(), getTestData());
	}

	public AssignedQuesMasterTmplPageSteps onAssignedQuesMasterTmplPageSteps() throws Exception {
		return new AssignedQuesMasterTmplPageSteps(getConfig(), getAgent(), getTestData());
	}

}
