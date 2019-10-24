package testcore.pages.StudyManagement;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.StudyManagement.DesktopStudyPage;
import testcore.pages.desktop.StudyManagement.DesktopStudyVendorsPage;

import java.util.Map;

public class StudyVendorsPage extends BasePage {


	public StudyVendorsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public StudyVendorsPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		StudyVendorsPage derivedStudyVendorsPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedStudyVendorsPage = new DesktopStudyVendorsPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedStudyVendorsPage;
	}

	@Override
	public String pageName() {
		return StudyVendorsPage.class.getSimpleName();
	}
	
}
