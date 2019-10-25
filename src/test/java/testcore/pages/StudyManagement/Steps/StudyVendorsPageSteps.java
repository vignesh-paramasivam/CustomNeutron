package testcore.pages.StudyManagement.Steps;

import agent.IAgent;
import central.Configuration;
import io.qameta.allure.Step;
import testcore.pages.BasePage;
import testcore.pages.StudyManagement.StudyPage;
import utils.RandomData;

import java.util.Map;

public class StudyVendorsPageSteps extends BasePage {


	public StudyVendorsPageSteps(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}

}
