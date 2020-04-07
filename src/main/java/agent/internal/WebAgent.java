package agent.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import agent.IAgent;
import central.Configuration;
import central.AutomationCentral;
import enums.ConfigType;
import enums.Direction;
import enums.Platform;
import utils.JavaUtils;
import utils.RandomData;

public abstract class WebAgent implements IAgent {
	protected static Logger logger = AutomationCentral.getLogger();
	private Configuration config = null;
	private WebDriver driver;
	private WebDriverWait wait;
	private String screenShotsDir = null;
	private String screenDateFormat = null;
	private boolean snapshotsEnabled = false;
	private boolean alwaysTakeSnapshot = false;
	private int scrollPixelCount;
	

	public WebAgent(Configuration config, WebDriver driver) throws Exception {
		this.config = config;
		this.driver = driver;
		this.scrollPixelCount = Integer.parseInt(this.getConfig().getValue(ConfigType.SCROLL_PIXEL_COUNT));
		this.screenShotsDir = AutomationCentral.INSTANCE.getScreenShotsDir();
		this.screenDateFormat = AutomationCentral.INSTANCE.getScreenShotTimeStampFormat();
		configureImplicitWait();
		createWaiter();
		String screenFlag = config.getValue(ConfigType.SCREENSHOT_ONLY_ON_ERROR);
		if (screenFlag.toUpperCase().equals("FALSE")) {
			alwaysTakeSnapshot = true;
		}
		snapshotsEnabled = Boolean.parseBoolean(config.getValue(ConfigType.SCREENSHOTS_ON).toLowerCase());
	}

	protected void configureImplicitWait() {
		if (System.getProperty("implicit_wait") != null) {
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("implicit_wait")),
					TimeUnit.SECONDS);
		} else {
			driver.manage().timeouts().implicitlyWait(Integer.valueOf(config.getValue(ConfigType.IMPLICIT_WAIT)),
					TimeUnit.SECONDS);

			System.setProperty("implicit_wait", config.getValue(ConfigType.IMPLICIT_WAIT));
		}
	}

	protected void createWaiter() {
		if (System.getProperty("explicit_wait") != null) {
			wait = new WebDriverWait(driver, Integer.parseInt(System.getProperty("explicit_wait")));
		} else {
			wait = new WebDriverWait(driver, Integer.valueOf(config.getValue(ConfigType.EXPLICIT_WAIT)));
		}
	}

	protected Platform getPlatform() {
		return this.config.getPlatform();
	}

	protected Configuration getConfig() {
		return this.config;
	}

	protected String getScreenShotsDir() {
		return this.screenShotsDir;
	}

	protected boolean shouldAlwaysTakeSnapShot() {
		return this.alwaysTakeSnapshot;
	}

	public WebDriver getWebDriver() throws Exception {
		return driver;
	}

	public WebDriverWait getWaiter() {
		return wait;
	}

	protected void throwAgentException(String msg) throws Exception {
		logger.error(msg);
		throw new Exception(String.format("(%s) %s", this.getPlatform(), msg));
	}

	protected void throwAgentException(Exception e, String additionalMessage) throws Exception {
		throwAgentException(String.format("%s : %s ", additionalMessage, e.getMessage()));
	}

	protected void throwActionException(String action, Exception e) throws Exception {
		throwAgentException(e, String.format("Issue in %s action", action));
	}

	protected void throwActionException(Exception e) throws Exception {
		throwActionException(JavaUtils.getCaller(), e);
	}

	protected void throwUnsupportedActionException() throws Exception {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[1];
		throw new Exception(String.format("%s action is not supported for %s platform",
				stackTraceElement.getMethodName(), this.getPlatform()));
	}

	public void takeConditionalSnapShot() throws Exception {
		if (this.snapshotsEnabled && shouldAlwaysTakeSnapShot()) {
			takeSnapShot();
		}
	}

	@Override
	public File takeSnapShot() throws Exception {
		if (!this.snapshotsEnabled)
			return null;
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(
					this.screenShotsDir + File.separator + JavaUtils.datetime(screenDateFormat) + ".png");
			FileUtils.copyFile(SrcFile, DestFile);
			return DestFile;
		} catch (Exception e) {
			logger.error("Issue with takeSnapShot : " + e.getMessage());
		}
		throw new Exception("Failed capturing screenshot");
	}
	
	@Override
	public File takeSnapShot(String testname) throws Exception {
		if (!this.snapshotsEnabled)
			return null;
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(
					this.screenShotsDir + File.separator + System.getProperty("browser") + RandomData.dateTime_yyyyMMddHHmmss() + "_" + testname + ".png");
			FileUtils.copyFile(SrcFile, DestFile);
			return DestFile;
		} catch (Exception e) {
			logger.error("Issue with takeSnapShot : " + e.getMessage());
		}
		throw new Exception("Failed capturing screenshot");
	}

	@Override
	public void goTo(String url) throws Exception {
		try {
			logger.debug(String.format("Navigating to %s", url));
			driver.get(url);
			this.takeConditionalSnapShot();
		} catch (Exception e) {
			throwAgentException(String.format("Issue while Navigating to %s : %s", url, e.getMessage()));
		}
	}

	@Override
	public void goBack() throws Exception {
		try {
			logger.debug("Navigate one step back in browser history");
			driver.navigate().back();
			this.takeConditionalSnapShot();
		} catch (Exception e) {
			this.throwActionException(e);
		}
	}
	
	@Override
	public void switchToNewWindow() throws Exception {
		try {
			logger.debug("Switching to new Window");
			Set<String> s = driver.getWindowHandles();
			Iterator<String> itr = s.iterator();
			itr.next();
			String w2 = (String) itr.next();
			driver.switchTo().window(w2);
			this.takeConditionalSnapShot();
		} catch (Exception e) {
			this.throwActionException(e);
		}
	}

	@Override
	public void switchToMainWindow() throws Exception {
		try {
			logger.debug("Switching to Default  Window");
			List<String> windows = new ArrayList<String>(driver.getWindowHandles());
			for (int i = 1; i < windows.size(); i++) {
				driver.switchTo().window(windows.get(i));
				driver.close();
			}
			driver.switchTo().window(windows.get(0));
			this.takeConditionalSnapShot();
		} catch (Exception e) {
			this.throwActionException(e);
		}
	}

	@Override
	public void acceptAlert() throws Exception {
		try {
			logger.debug("Accepting the alert ");
			Alert alert = driver.switchTo().alert();
			alert.accept();
			this.takeConditionalSnapShot();
		} catch (Exception e) {
			this.throwActionException(e);
		}
	}

	private void scroll(Direction direction, int count) throws Exception {
		try {
			logger.debug(String.format("Trying to Scroll %s for %d times", direction, count));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			int spc = 0;
			if (direction == Direction.DOWN) {
				spc = scrollPixelCount;
			} else if (direction == Direction.UP) {
				spc = -scrollPixelCount;
			} else {
				throw new Exception("Scroll left/right is not yet supported.");
			}
			for (int i = 0; i < count; i++) {
				jse.executeScript(String.format("window.scrollBy(0, %d)", spc), "");
			}
			this.takeConditionalSnapShot();
		} catch (Exception e) {
			throwActionException(String.format("scroll%s", direction), e);
		}
	}

	@Override
	public void scrollDown() throws Exception {
		scroll(Direction.DOWN, 1);
	}

	@Override
	public void scrollDown(int count) throws Exception {
		scroll(Direction.DOWN, count);
	}

	@Override
	public void scrollUp() throws Exception {
		scroll(Direction.UP, 1);
	}

	@Override
	public void scrollUp(int count) throws Exception {
		scroll(Direction.UP, count);
	}

	protected boolean isWebView() {
		// Irrelevant for web agent. Should be overriden by MobileAgent, so that
		// assertPageLoad
		// logic can be in one place for all agents.
		return true;
	}

	@Override
	public void assertPageLoad() throws Exception {
		throw new Exception("Implemented under DesktopWebAgent");
	}

	public void quit() {
		this.driver.quit();
	}

	@Override
	public void swipeUp() throws Exception {
		throwUnsupportedActionException();
	}

	@Override
	public void swipeUp(int count) throws Exception {
		throwUnsupportedActionException();
	}

	@Override
	public void swipeDown() throws Exception {
		throwUnsupportedActionException();
	}

	@Override
	public void swipeDown(int count) throws Exception {
		throwUnsupportedActionException();
	}

	public void switchToNativeView() throws Exception {
		throwUnsupportedActionException();
	}

	public void switchToWebView() throws Exception {
		throwUnsupportedActionException();
	}
	
	@Override
	public void swipeDownTillElement(String elementName) throws Exception {
		throwUnsupportedActionException();
	}
	
	@Override
	public void scrollIntoView(WebElement element) throws Exception {
    	((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView(true);", element);
    	Thread.sleep(500);
	}
}
