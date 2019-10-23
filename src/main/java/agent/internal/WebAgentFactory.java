package agent.internal;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.Alert;

import agent.IAgent;
import central.AutomationCentral;
import central.Configuration;
import enums.ConfigType;
import enums.DesktopBrowser;
import enums.Platform;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebAgentFactory {
	static WebDriver driver = null;

	public static IAgent createAgent(Configuration config) throws Exception {
		Platform platform = config.getPlatform();
		DesktopBrowser browser = DesktopBrowser.valueOf(getProperty("browser", config).toUpperCase());
		switch (platform) {
		case DESKTOP_WEB:
			String GoOnToTheWebpageLocator = "overridelink";
			initDriver(config, browser);
			Dimension resolution = new Dimension(Integer.parseInt(System.getProperty("browser_resolution_width")),Integer.parseInt(System.getProperty("browser_resolution_height")));
			driver.manage().window().setSize(resolution);
			driver.get(getProperty("app_browser_url", config));
			switch(browser){
			case IE :
				break;

			case EDGE:
				driver.findElement(By.id("moreInformationDropdownLink")).click();
				driver.findElement(By.id(GoOnToTheWebpageLocator)).click();
				AutomationCentral.getLogger().info("Certificate handled successfully");
				break;
			}
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			break;
		default:
			throw new Exception("Invalid platform, Supported platform is desktop");
		}

		return new DesktopWebAgent(config, driver);
	}

	@SuppressWarnings("deprecation")
	private static WebDriver initDriver(Configuration config, DesktopBrowser browser) throws Exception {
		DesiredCapabilities caps = null;

		String grid_url = System.getProperty("grid_url");
		if(System.getProperty("grid_url") != null) {
			caps = getCapability(browser);
			driver = new RemoteWebDriver(new URL(grid_url), caps);
			driver.manage().deleteAllCookies();
			return driver;
		}

		switch (browser) {
		case CHROME:
			caps = getCapability(browser);
			driver = new ChromeDriver(caps);
			break;
		case EDGE:
			caps = getCapability(browser);
			driver = new EdgeDriver(caps);
			break;
		case FIREFOX:
			caps = getCapability(browser);
			driver = new FirefoxDriver(caps);
			break;
		case IE:
			caps = getCapability(browser);
			driver = new InternetExplorerDriver(caps);
			break;
		case SAFARI:
			caps = DesiredCapabilities.safari();
			caps.setVersion(ConfigType.PLATFORM_VER.toString());
			driver = new SafariDriver(caps);
			break;
		default:
			break;
		}
		return driver;
	}

	private static void setPropertyByOS(DesktopBrowser browser) throws Exception {
		if(System.getProperty("grid_url") != null) {
			return;
		}
		//String driverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/desktop/";
		switch (getOS()) {
		case "OS_WINDOWS":
			switch (browser) {
			case CHROME:
				System.setProperty("webdriver.chrome.bin", System.getProperty("chrome_bin_path"));
				System.setProperty("webdriver.chrome.driver", System.getProperty("chrome_driver_path"));
				break;
			case FIREFOX:
				System.setProperty("webdriver.firefox.bin", System.getProperty("firefox_bin_path"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("firefox_driver_path"));
				break;
			case IE:
				System.setProperty("webdriver.ie.bin", System.getProperty("ie_bin_path"));
				System.setProperty("webdriver.ie.driver", System.getProperty("ie_driver_path"));
				break;
			case EDGE:
				System.setProperty("webdriver.edge.driver", System.getProperty("edge_driver_path"));
				break;
			default:
				break;
			}
			break;
		case "OS_MAC":
			switch (browser) {
			case CHROME:
				System.setProperty("webdriver.chrome.bin", System.getProperty("chrome_bin_path"));
				System.setProperty("webdriver.chrome.driver", System.getProperty("chrome_driver_path"));
				//				System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
				break;
			case FIREFOX:
				System.setProperty("webdriver.firefox.bin", System.getProperty("firefox_bin_path"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("firefox_driver_path"));
				break;
			default:
				break;
			}
			break;
		case "OS_LINUX":
			switch (browser) {
			case CHROME:
				System.setProperty("webdriver.chrome.bin", System.getProperty("chrome_bin_path"));
				System.setProperty("webdriver.chrome.driver", System.getProperty("chrome_driver_path"));
				break;
			case FIREFOX:
				System.setProperty("webdriver.firefox.bin", System.getProperty("firefox_bin_path"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("firefox_driver_path"));
				break;
			default:
				break;
			}
			break;
		default:
			throw new Exception("Unknown OS, Please check the Operating System Paramter");
		}
	}

	public static String getOS() {
		String osType = null;
		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();
		if (osNameMatch.contains("linux")) {
			osType = "OS_LINUX";
		} else if (osNameMatch.contains("windows")) {
			osType = "OS_WINDOWS";
		} else if (osNameMatch.contains("solaris") || osNameMatch.contains("sunos")) {
			osType = "OS_SOLARIS";
		} else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
			osType = "OS_MAC";
		} else {
			osType = "Unknown";
		}
		return osType;
	}

	public static String getProperty(String arg, Configuration config) {
		String ret_val="";

		if ( arg.equalsIgnoreCase("browser") ) {
			if ( System.getProperty("browser") != null )
				ret_val=System.getProperty("browser");
			else
				ret_val=config.getValue(ConfigType.BROWSER);
		}

		if ( arg.equalsIgnoreCase("app_browser_url") ) {
			if ( System.getProperty("app_browser_url") != null )
				ret_val=System.getProperty("app_browser_url");
			else
				ret_val=config.getValue(ConfigType.APP_URL);
		}
		return ret_val;
	}

	private static DesiredCapabilities getCapability(DesktopBrowser browser) throws Exception {
		DesiredCapabilities caps = null;
		String downloadFilepath = (System.getProperty("user.dir") + "\\download");

		if(getOS().equalsIgnoreCase("OS_LINUX")) {
			downloadFilepath = downloadFilepath.replace("\\", "/");
		}

		switch (browser) {
		case CHROME:
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("download.default_directory", downloadFilepath);
			chromePrefs.put("download.prompt_for_download", false);
			chromePrefs.put("download.directory_upgrade", true);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-infobars");
			if (System.getProperty("chrome_bin_path") != null) {
				options.setBinary(System.getProperty("chrome_bin_path"));
			}
			caps = DesiredCapabilities.chrome();
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			caps.setVersion("ANY");
			caps.setBrowserName("chrome");
			caps.setCapability("acceptSslCerts", true);
			caps.setCapability("acceptInsecureCerts", true);
			caps.setCapability("ignore-certificate-errors", true);
			setPropertyByOS(browser);
			break;
		case EDGE:
			caps = DesiredCapabilities.edge();
			setPropertyByOS(browser);
			break;
		case FIREFOX:
			/* For details on the firefox options see
			https://stackoverflow.com/questions/36309314/set-firefox-profile-to-download-files-automatically-using-selenium-and-java/36309735
			* */
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
			profile.setPreference("browser.download.dir", downloadFilepath);
			profile.setPreference("browser.download.useDownloadDir", true);
			profile.setPreference("security.insecure_field_warning.contextual.enabled", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);

			caps = DesiredCapabilities.firefox();
			caps.setVersion("ANY");
			caps.setBrowserName("firefox");
			caps.setCapability("marionette", true);
			caps.setCapability(FirefoxDriver.PROFILE, profile);
			setPropertyByOS(browser);
			break;
		case IE:
			caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("nativeEvents", false);
			caps.setCapability("unexpectedAlertBehaviour", "accept");
			caps.setCapability("ignoreProtectedModeSettings", true);
			caps.setCapability("disable-popup-blocking", true);
			caps.setCapability("enablePersistentHover", true);
			caps.setCapability("ignoreZoomLevel", true);
			caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			/*SSL Certs are not getting ignored or Accepted for the application
			 * TODO: Need to find a way to bypass it*/
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			setPropertyByOS(browser);
			break;
		case SAFARI:
			caps = DesiredCapabilities.safari();
			caps.setVersion(ConfigType.PLATFORM_VER.toString());
			break;
		default:
			break;
		}
		return caps;
	}

	private static void checkAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
