package agent.internal;

import enums.ConfigType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import central.Configuration;
import enums.Platform;

import java.util.concurrent.TimeUnit;

public class DesktopWebAgent extends WebAgent {

	public DesktopWebAgent(Configuration config, WebDriver driver) throws Exception {
		super(config, driver);
	}
	
	@Override
	public void assertPageLoad() throws Exception {
		// LEPANTO RELATED CHANGE: Page load parameters to be identified and defined for lepanto, until that we will not use this wait.
		if(true) { return;	}
		if (Platform.isWebPlatform(this.getPlatform()) || isWebView()) {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

					boolean documentReady = ((String) ((JavascriptExecutor) driver).executeScript("return document.readyState"))
							.equals("complete");

					/*pace-done is applied to body once the page load is complete
					 * we can extend this method to add more assertions like this for page load
					 *
					 * Also, if you find once of the locators are added permanently available to the
					 * page(ideally shouldn't) please remove that assertion from here and add appropriate
					 * wait locators.
					 *
					 * e.g. incase loadingBtn is added as in ID to one of the elements in a page,
					 * then this assertion method will wait until it disappears(it will not)
					 * and fail stating the page is not loaded.
					 */

					boolean pageLoaded;

					if (driver.findElements(By.cssSelector("body#runtime")).size() > 0) {
						// Admin page doesn't have pace-done class, so taking this approach.
						pageLoaded = driver.findElements(By.cssSelector("body#runtime.pc")).size() > 0;
					}
					else {
						pageLoaded = driver.findElements(By.cssSelector("body.pace-done")).size() > 0;
					}
					boolean loadingSpinnerDisplayed = driver.findElements(By.cssSelector("div.ant-spin-spinning")).size() == 0;
					boolean gridSpinnerDisplayed = driver.findElements(By.cssSelector(".ant-spin-dot")).size() == 0;
					boolean pageBlur = driver.findElements(By.cssSelector(".ant-spin-blur")).size() == 0;
					boolean loadingBtn = driver.findElements(By.cssSelector("button#loadingBtn")).size() == 0;


					int timeOut = System.getProperty("implicit_wait") == null ? Integer.parseInt(getConfig().getValue(ConfigType.IMPLICIT_WAIT)) : Integer.parseInt(System.getProperty("implicit_wait"));
					driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);

					return (pageLoaded && loadingSpinnerDisplayed && documentReady && gridSpinnerDisplayed && pageBlur && loadingBtn);
				}
			};
			this.getWaiter().until(pageLoadCondition);
		}
	}

}
