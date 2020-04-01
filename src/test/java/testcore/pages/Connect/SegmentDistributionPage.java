package testcore.pages.Connect;

import agent.IAgent;
import central.Configuration;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import testcore.pages.BasePage;
import testcore.pages.Connect.Steps.SegmentDistributionPageSteps;
import testcore.pages.desktop.Connect.DesktopSegmentDistributionPage;
import testcore.pages.desktop.Connect.DesktopSegmentsPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegmentDistributionPage extends BasePage {


	public SegmentDistributionPage(Configuration conf, IAgent agent, Map<String, String> testData) throws Exception {
		super(conf, agent, testData);
		assertPageLoad();
	}

	public SegmentDistributionPage createInstance() throws Exception{
		logger.info(String.format("started with the create instance"));
		logger.info(getPlatform().toString());
		SegmentDistributionPage derivedSegmentsPage;
		switch(getPlatform()){
		case DESKTOP_WEB:
			derivedSegmentsPage = new DesktopSegmentDistributionPage(getConfig(),getAgent(),getTestData());
			break;
		default:
			throw new NotImplementedException("Invalid platform - please check the platform argument: " + getPlatform().toString()) ;
		}
		return derivedSegmentsPage;
	}

	@Override
	public String pageName() {
		return SegmentDistributionPage.class.getSimpleName();
	}


	public SegmentDistributionPage selectDestination(String name) throws Exception {
		WebElement card = getCardFromAvailableDestinations(name);
		if (!card.getAttribute("class").contains("active")) {
			card.click();
			assertPageLoad();
		}

		String cardClasses = card.getAttribute("class");
		Assert.assertTrue(cardClasses.contains("active"), name + " not selected. Runtime value:: " + cardClasses);
		clearDestinationInSearch();
		return this;
	}

	public SegmentDistributionPage unSelectDestination(String name) throws Exception {
		WebElement card = getCardFromSelectedDestinations(name);
		if (card.getAttribute("class").contains("active")) {
			card.click();
			assertPageLoad();
		}

		String cardClasses = card.getAttribute("class");
		Assert.assertFalse(cardClasses.contains("active"), "Unable to unselect " + name + "Runtime value:: " + cardClasses);
		clearDestinationInSearch();
		return this;
	}

	public List<String> getAllSelectedDestinationsTitles() throws Exception {
		String path = "//div[contains(@class, 'selectedDestinationCardsContainer')]//section[contains(@class, 'destinationCard')]//span[contains(@class, 'destinationName')]";
		List<WebElement> cards = driver().findElements(By.xpath(path));

		List<String> allSectionTitles = new ArrayList<>();
		for (WebElement card: cards) {
			allSectionTitles.add(card.getAttribute("title"));
		}

		return allSectionTitles;
	}


	public SegmentDistributionPage enterDestinationInSearch(String name) throws Exception {
		getTextboxControl("Search Destinations").enterValue(name);
		waitToLoadDestinations();
		return this;
	}

	public SegmentDistributionPage clearDestinationInSearch() throws Exception {
		getTextboxControl("Search Destinations").clear();
		waitToLoadDestinations();
		return this;
	}


	private WebElement getCardFromSelectedDestinations(String cardTitle) throws Exception {
		enterDestinationInSearch(cardTitle);
		String path = "//div[contains(@class, 'selectedDestinationCardsContainer')]//section[contains(@class, 'destinationCard')]//span[@title='" + cardTitle + "']/ancestor::section[1]";
		return driver().findElement(By.xpath(path));
	}

	private WebElement getCardFromAvailableDestinations(String cardTitle) throws Exception {
		enterDestinationInSearch(cardTitle);
		String path = "//div[contains(@class, 'availableDestinationCardsContainer')]//section[contains(@class, 'destinationCard')]//span[@title='" + cardTitle + "']/ancestor::section[1]";
		return driver().findElement(By.xpath(path));
	}

	private void waitToLoadDestinations() throws Exception {
		//If this wait continuously failing, validate the xpath - and check if it is always present on the screen.
		String path = "//*[contains(text(), 'Loading Destinations')]";
		waiter().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(path)));
		assertPageLoad();
	}

}
