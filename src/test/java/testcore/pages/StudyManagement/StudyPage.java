package testcore.pages.StudyManagement;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.StudyManagement.DesktopStudyPage;

import java.util.Map;

public class StudyPage extends BasePage {


	public StudyPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public StudyPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		StudyPage derivedStudyPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedStudyPage = new DesktopStudyPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedStudyPage;
	}

	@Override
	public String pageName() {
		return StudyPage.class.getSimpleName();
	}

}
