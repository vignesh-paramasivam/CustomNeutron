package testcore.pages.Templates.Questionnaire;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import testcore.pages.BasePage;
import testcore.pages.desktop.Templates.Questionnaire.DesktopAssignedQuesMasterTmpl;

import java.util.Map;

public class AssignedQuesMasterTmplPage extends BasePage {


	public AssignedQuesMasterTmplPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public AssignedQuesMasterTmplPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		AssignedQuesMasterTmplPage derivedAssignedQuesMasterTmpl;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedAssignedQuesMasterTmpl = new DesktopAssignedQuesMasterTmpl(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedAssignedQuesMasterTmpl;
	}

	@Override
	public String pageName() {
		return AssignedQuesMasterTmplPage.class.getSimpleName();
	}

}
