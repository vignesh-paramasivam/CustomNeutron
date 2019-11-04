package testcore.pages.desktop.Templates.Questionnaire;

import agent.IAgent;
import central.Configuration;
import testcore.pages.Templates.Questionnaire.AssignedQuesMasterTmplPage;

import java.util.Map;

public class DesktopAssignedQuesMasterTmpl extends AssignedQuesMasterTmplPage {


	public DesktopAssignedQuesMasterTmpl(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

}
