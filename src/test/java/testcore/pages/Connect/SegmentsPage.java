package testcore.pages.Connect;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import testcore.pages.BasePage;
import testcore.pages.desktop.Connect.DesktopSegmentsPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegmentsPage extends BasePage {


	public SegmentsPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public SegmentsPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		SegmentsPage derivedSegmentsPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedSegmentsPage = new DesktopSegmentsPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedSegmentsPage;
	}

	@Override
	public String pageName() {
		return SegmentsPage.class.getSimpleName();
	}

	//Poster methods

	private WebElement getPoster(String segmentName) throws Exception {
		String path = "//div[contains(@class, 'poster')]//zui-poster-header//div[contains(@class, 'segment-name')][text()='" + segmentName + "']";
		return driver().findElement(By.xpath(path + "/ancestor::zui-poster[1]"));
	}

	public String getSegmentStatus(String segmentName) throws Exception {
		return getPoster(segmentName).findElement(By.xpath(".//div[contains(@class, 'status')]")).getAttribute("ng-reflect-ng-class").trim();
	}

	public String getSegmentID(String segmentName) throws Exception {
		return getPoster(segmentName).findElement(By.xpath(".//div[contains(@class, 'segment-id')]")).getText().trim();
	}

	public List<String> getAllSegmentPostersNames() throws Exception {
		String path = "//div[contains(@class, 'poster')]//zui-poster-header//div[contains(@class, 'segment-name')]";
		List<WebElement> allPosters = driver().findElements(By.xpath(path));

		List<String> allPostersNames = new ArrayList<>();
		for (WebElement poster: allPosters) {
			allPostersNames.add(poster.getText().trim());
		}
		return allPostersNames;
	}


}
