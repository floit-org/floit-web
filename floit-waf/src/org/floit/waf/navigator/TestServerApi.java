/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.TestServerApi
 * Author: $Author: garrett.muldowney $
 * Created: $Date: 2012/03/30 15:07:41 $
 * Revision: $Revision: 1.26 $
 */
package org.floit.waf.navigator;

//import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import sun.misc.BASE64Decoder;

import com.google.common.base.Function;
import com.opera.core.systems.OperaDriver;

/**
 * Test server wrapper.
 * 
 * This class provides an interface to GUI test runtime. It encapsulates the
 * test environment and provides methods to access the functions of that
 * environment.
 */
public class TestServerApi {
	/**
	 * Log4J logger instance for class TestServerApi.
	 */
	private static final Logger CAT = Logger.getLogger(TestServerApi.class);
	/**
	 * Log4J debug setting for class {TestServerApi.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	private String serverHost = null;
	private int serverPort = 0;
	private WebDriver driver;
	private String firefoxProfileLoc;
	private boolean useFFProfile;
	private TestNavigator navigator = null;
	private int pauseTime;
	private int slowModeTime = 500;
	private boolean inSlowMode = false;

	/** Detects the browser type from the user agent string */
	BrowserDetector browser = null;

	/** Configured browser type taken from the the test properties */
	private String browserType;
	
	private JSONObject browserConfig = null;

	/** The test drive mode, local, remote or grid */
	private String configMode;
	private String browserVersion;
	private WebDriver popup;
	private WebDriver frame;
	private String mainWindowHandle;

	private Set<Class<? extends Throwable>> waitExceptions = new HashSet<Class<? extends Throwable>>();

	/**
	 * Constructs a instance of test server.
	 * 
	 * @param navigator
	 *            an instance of the base navigator class
	 */
	public TestServerApi(TestNavigator navigator) {
		this.navigator = navigator;
		this.serverHost = navigator.getServerHost();
		this.serverPort = navigator.getServerPort();
		navigator.getBrowserPath();
		this.browserType = navigator.getBrowser();
		this.browserVersion = navigator.getBrowserVersion();
		this.configMode = navigator.getConfigMode();
		this.useFFProfile = navigator.getUseFirefoxProfile();
		this.firefoxProfileLoc = navigator.getFirefoxProfileLocation();
		navigator.getBaseURL();
		this.inSlowMode = navigator.getSlowModeEnabled();
		this.slowModeTime = navigator.getSlowModeTime();

		this.pauseTime = TestNavigator.getPauseTime();
		
		if(navigator.getBrowserConfig() != null) {
			this.browserConfig = parseBrowserConfig();
		}

		// Add set of wait exceptions that should be ignored during wait
		// functionality
		waitExceptions.add(org.openqa.selenium.WebDriverException.class);
		waitExceptions.add(org.openqa.selenium.NotFoundException.class);
	}

	/**
	 * Set the framework to run in remote driver mode.
	 */
	private void setupRemoteMode(boolean isGrid) {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if ("firefox".equals(browserType)) {
			CAT.info("Using a remote Firefox browser");
			capabilities = DesiredCapabilities.firefox();
			// Override as appropriate 
			setCapabilities(capabilities);
		} else if ("googlechrome".equals(browserType) || "chrome".equals(browserType)) {
			CAT.info("Using a remote Google Chrome browser");
			capabilities = DesiredCapabilities.chrome();
			// Override as appropriate 
			setCapabilities(capabilities);
		} else if ("opera".equals(browserType)) {
			CAT.info("Using a remote Opera browser");
			capabilities = DesiredCapabilities.opera();
			// Override as appropriate 
			setCapabilities(capabilities);
		} else if ("iexplorer".equals(browserType)) {
			if (!isGrid) {
				CAT.error("Using a remote mode is not supported for Internet Explorer browser. The Grid proxy is required");
				// TODO: Throw unsupported feature exception
			} else {
				CAT.info("Using a remote Internet Explorer browser with Grid");
				capabilities = DesiredCapabilities.internetExplorer();
				// Override as appropriate 
				setCapabilities(capabilities);
			}
		} else if ("safari".equals(browserType)) {
			CAT.info("Using a remote Safari browser");
			capabilities = DesiredCapabilities.safari();
			setCapabilities(capabilities);
		} else if ("ipad".equals(browserType)) {
			CAT.info("Using a remote iPad browser");
			setCapabilities(capabilities);
		} else if ("iphone".equals(browserType)) {
			CAT.info("Using a remote iPad browser");
			setCapabilities(capabilities);
		} else if ("android".equals(browserType)) {
			CAT.info("Using a remote iPad browser");
			setCapabilities(capabilities);
		} else {
			CAT.error("Invalid browser type supplier [" + browserType + "]");
		}

		if (browserVersion != null) {
			capabilities.setVersion(browserVersion);
		}
		capabilities.setJavascriptEnabled(true);
		String file = "";
		if (isGrid) {
			file = "/wd/hub";
		}

		try {
			
			URL remoteUrl = new URL("http://"+ serverHost + ":" + serverPort + file);
			//URL remoteUrl = new URL("http", serverHost, serverPort, file);

			if (!"android".equals(browserType) && !"ipad".equals(browserType)
					&& !"iphone".equals(browserType)) {
				driver = new RemoteWebDriver(remoteUrl, capabilities);
			} else {
				driver = new TouchWebDriver(remoteUrl, capabilities);
			}
		} catch (MalformedURLException e) {
			CAT.error("Unable to create URL to remote driver host using [http, "
					+ serverHost + ", " + serverPort + ",]");
		}
	}

	/**
	 * Set the framework to run in local driver mode.
	 */
	private void setupLocalMode() {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		if ("firefox".equals(browserType)) {
			CAT.info("Using Firefox browser");
			if (useFFProfile) {
				CAT.info("Using Firefox profile: " + firefoxProfileLoc);
				File profileDir = new File(firefoxProfileLoc);
				FirefoxProfile profile = new FirefoxProfile(profileDir);
				driver = new FirefoxDriver(profile);
			} else {
				driver = new FirefoxDriver();
			}
		} else if ("googlechrome".equals(browserType) || "chrome".equals(browserType)) {
			CAT.info("Using Google Chrome browser");
			System.setProperty("webdriver.chrome.driver", navigator.getBrowserPath());
			setCapabilities(capabilities);
			driver = new ChromeDriver(capabilities);
		} else if ("opera".equals(browserType)) {
			CAT.info("Using Opera browser");
			driver = new OperaDriver();
		} else if ("iexplorer".equals(browserType)) {
			CAT.info("Using Internet Explorer browser");
			driver = new InternetExplorerDriver();
		}
	}

	/**
	 * Starts an instance of the test runtime. Normally this will be called as
	 * part of test setup. Note that the test server must be running. This will
	 * launch the browser and execute the tests.
	 */
	public void start() {

		if ("local".equals(this.configMode)) {
			setupLocalMode();
		} else if ("remote".equals(this.configMode)) {
			setupRemoteMode(false);
		} else if ("grid".equals(this.configMode)) {
			setupRemoteMode(true);
		}

		// Get window handle
		if (driver != null) {
			mainWindowHandle = driver.getWindowHandle();
			CAT.info("Main window title [" + getTitle() + "] has handle [" + mainWindowHandle + "]");
		}

		// Set the window dimensions
		if(!"android".equals(browserType) && !"ipad".equals(browserType) && !"iphone".equals(browserType)) {
			if (!"opera".equals(browserType)) {
				if (navigator.getMaximiseBrowser()) {
					driver.manage().window().maximize();
				} else if (navigator.getWindowSize() != null){
					Dimension size = new Dimension(navigator.getWindowSize().width, navigator.getWindowSize().height);
					driver.manage().window().setSize(size);
				}
			}
		}
	}
	
	/**
	 * Parse the capabilities in JSON into a DesiredCapabilities object
	 * @param capabilities an empty DesiredCapabilities object
	 */
	protected void setCapabilities(DesiredCapabilities capabilities) {
		
		if(browserConfig != null) {
			JSONObject cap = (JSONObject) browserConfig.get("Capabilities");
		    Iterator<?> it = cap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<String, Object> pairs = (Map.Entry<String, Object>)it.next();
		        if("chromeOptions".equals(pairs.getKey())) {
					ChromeOptions options = new ChromeOptions();
					
					if(pairs.getValue() instanceof JSONArray) {
						JSONArray optionsArray = (JSONArray) pairs.getValue();					
						
						for (int i = 0; i < optionsArray.size(); i++) {
							String option = (String) optionsArray.get(i);
							options.addArguments(option);
							capabilities.setCapability(
									ChromeOptions.CAPABILITY, options);
							CAT.info("Adding ChromeOptions [" + option + "]");
						}
					}
					
					if(pairs.getValue() instanceof JSONObject) {
						JSONObject optionsObj = (JSONObject) pairs.getValue();
						if(optionsObj.containsKey("mobileEmulation")) {
							//parse mobileEmulation
							parseMobileEmulation((JSONObject) optionsObj.get("mobileEmulation"), capabilities);
						}
						
					}
					
		        } else {
			        capabilities.setCapability(pairs.getKey(), (String) pairs.getValue());
			        CAT.info("Adding Browser Capability ["+ pairs +"]");
		        }
		        it.remove(); 
		    }			
		}
	}
	
	protected void parseMobileEmulation(JSONObject obj, DesiredCapabilities capabilities) {
		CAT.info("Parse Mobile Emulation");
		Map<String, Object> mobileEmulation = new HashMap<String, Object>();
		
		if(obj.containsKey("userAgent")) {
			mobileEmulation.put("userAgent", obj.get("userAgent").toString());
		}
		
		if(obj.containsKey("deviceName")) {
			mobileEmulation.put("deviceName", obj.get("deviceName").toString());			
		}
		
		if(obj.containsKey("deviceMetrics")) {
			Map<String, String> deviceMetrics = new HashMap<String, String>();
			JSONObject device = (JSONObject) obj.get("deviceMetrics");
			
			if(device.containsKey("width")) {
				deviceMetrics.put("width", device.get("width").toString());
			}
			
			if(device.containsKey("height")) {
				deviceMetrics.put("height", device.get("height").toString());
			}
			
			if(device.containsKey("pixelRatio")) {
				deviceMetrics.put("pixelRatio", device.get("pixelRatio").toString());
			}
			
			mobileEmulation.put("deviceMetrics", deviceMetrics);
		}
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("mobileEmulation", mobileEmulation);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		CAT.info("Mobile Emulation Options [" + options + "]");		
	}

	/**
	 * Stops an instance of the test runtime. Normally will be called during
	 * test tear-down.
	 */
	public void stop() {
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * Specifies the amount of time that test runtime will wait for actions to
	 * complete. This is not the server timeout. The default is 30 seconds.
	 * 
	 * @param timeout
	 *            a timeout in milliseconds, after which an action will return
	 *            an error.
	 */
	public void setTimeout(String timeout) {
		// selenium.setTimeout(timeout);
	}

	/**
	 * Opens an URL in the test frame. This accepts both relative and absolute
	 * URLs. The "open" command waits for the page to load before proceeding.
	 * 
	 * @param url
	 *            the URL to open; may be relative or absolute
	 */
	public void open(String url) {
		getDriver().get(url);
	}

	/**
	 * Returns the current web driver. Maybe the main window driver or a pop-up
	 * window driver.
	 * 
	 * @return the current web driver
	 */
	public WebDriver getDriver() {
		if (popup != null)
			return popup;
		else
			return driver;
	}

	/**
	 * Sets the driver to be a pop-up window identified by a locator
	 * 
	 * @param popup
	 *            the locator to identify the pop-up window
	 */
	public String setPopUpWindowDriver(String locator) {
		return getPopUpWindow(locator).getWindowHandle();
	}

	/**
	 * Always call to restate the main window driver
	 */
	public void switchToMainWindow() {
		driver.switchTo().window(mainWindowHandle);

		CAT.info("Switching to main window title [" + getTitle() + "] with handle [" + driver.getWindowHandle() + "]");
	}

	/**
	 * Close all open pop-up windows
	 */
	public void closePopUpWindow() {
		if (popup != null) {
			popup.close();
		}
	}

	/**
	 * Checks if a window is visible
	 * 
	 * @return true if the window is visible
	 */
	public boolean isWindowVisible(String windowHandle) {
		// Use the main window driver for this and not getDriver(). The
		// getDriver() returns the current window driver but is this was a popup
		// it may no longer be visible.
		Iterator<String> windowIterator = driver.getWindowHandles().iterator();
		while (windowIterator.hasNext()) {
			String currentHandle = windowIterator.next();
			if (currentHandle.contains(windowHandle)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets a reference to pop-up window
	 * 
	 * @param locator
	 *            identifies the window
	 * @return reference to a pop-up window
	 */
	public WebDriver getPopUpWindow(String locator) {
		String currentWindowHandle = driver.getWindowHandle();
		CAT.info("Main window title [" + getTitle() + "] has handle [" + currentWindowHandle + "]");
		
		Iterator<String> windowIterator = driver.getWindowHandles().iterator();
		while (windowIterator.hasNext()) {
			String windowHandle = windowIterator.next();
			if(currentWindowHandle.equals(windowHandle)) {
				CAT.debug("Try another window");
				continue;
			}
			
			popup = driver.switchTo().window(windowHandle);

			// Wait for pop-up windows's page to load
			(new WebDriverWait(popup, this.pauseTime)).until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {					
					return d.findElement(By.tagName("body"));
				}
			});    	

			// Get pop-up window body tag
			WebElement parent = popup.findElement(By.tagName("body"));
			// Make sure we have the right window
			if (isElementPresent(parent, getLocatorType(locator))) {
				
				try {
					CAT.info("Switching to window title [" + popup.getTitle() + "] with handle [" + popup.getWindowHandle()
							+ "]");
				} catch (Exception e) {
					CAT.error("Unable output window title or window handle id", e);
				}
				
				break;
			}
		}

		return popup;
	}

	/**
	 * Waits for a new page to load. Simply waits for the presence of a body
	 * tag.
	 * 
	 * @param time
	 *            a timeout in milliseconds, after which this command will
	 *            return with an error
	 */
	private void waitForPageToLoad(int time) {
		// selenium.waitForPageToLoad(time);
		// This is a stupid check, needs some more
		if (DEBUG)
			CAT.debug("Waiting for page to load...");
		(new WebDriverWait(getDriver(), time)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
		if (DEBUG)
			CAT.debug("Page has loaded successfully");
	}

	/**
	 * Gets the result of evaluating the specified JavaScript snippet. The
	 * snippet may have multiple lines, but only the result of the last line
	 * will be returned.
	 * 
	 * @param script
	 *            the JavaScript snippet to run
	 * @return the results of evaluating the snippet
	 */
	public Object getEval(String script) {
		return ((JavascriptExecutor) getDriver()).executeScript(script);
	}

	/**
	 * Verifies that the specified element is somewhere on the page.
	 * 
	 * @param locator
	 *            an element locator
	 * @return true if the element is present, false otherwise
	 */
	public boolean isElementPresent(String locator) {
		// This is a real hack for now
		By by = getLocatorType(locator);
		return isValidLocator(by, false);
	}

	/**
	 * Helper method to verify the absence of an element
	 * 
	 * @param locator
	 *            an element locator
	 * @return true if the element is not on the page, false otherwise
	 */
	public boolean verifyElementNotPresent(String locator) {
		By by = getLocatorType(locator);
		try {
			WebElement el = findElementBy(by);
			if (el != null) {
				return false;
			}
		} catch (NoSuchElementException e) {
			return true;
		}
		return false;
	}

	/**
	 * Verifies that the specified element is somewhere on the page
	 * 
	 * @param by
	 *            element identifier
	 * @return true if the element is present, false otherwise
	 */
	public boolean isElementPresent(By by) {
		return isValidLocator(by, false);
	}

	/**
	 * Verifies that the specified element is relative to a parent element.
	 * 
	 * @param parent
	 *            the parent element
	 * @param by
	 *            relative element identifier
	 * @return true if the element is present, false otherwise
	 */
	public boolean isElementPresent(WebElement parent, By by) {
		return isValidLocator(parent, by);
	}

	/**
	 * Returns the correctly locator type based on the locator string.
	 * 
	 * @param locator
	 *            the locator string
	 * @return a formated locator object
	 */
	public By getLocatorType(String locator) {
		By validLocator = null;

		if (locator.startsWith("//")) {
			validLocator = By.xpath(locator);
		} else if (locator.startsWith("link=")) {
			String link = locator.substring("link=".length(), locator.length());
			validLocator = By.linkText(link);
		} else if (locator.startsWith("document.")) {
			// DOM locator not supported
			CAT.error("DOM locator not supported, e.g. document.xxxx. Null will be retruned");
			return null;
		} else if (locator.startsWith("name=")) {
			String name = locator.substring("name=".length(), locator.length());
			validLocator = By.name(name);
		} else if (locator.startsWith("id=")) {
			String id = locator.substring("id=".length(), locator.length());
			validLocator = By.id(id);
		} else if (locator.startsWith("css=")) {
			String css = locator.substring("css=".length(), locator.length());
			validLocator = By.cssSelector(css);
		} else if (locator.startsWith("tag=")) {
			String tag = locator.substring("tag=".length(), locator.length());
			validLocator = By.cssSelector(tag);
		} else {
			validLocator = new ByIdOrName(locator);
		}

		if (DEBUG)
			CAT.debug("Element: " + locator + " is located " + validLocator);

		return validLocator;
	}

	/**
	 * Check if the location returns a valid element.
	 * 
	 * @param locator
	 *            identifies a DOM element
	 * @return true if the element is found
	 */
	private boolean isValidLocator(By locator, boolean silent) {

		boolean present = false;
		try {
			findElementBy(locator);
			present = true;
		} catch (NoSuchElementException e) {
			if (!silent)
				CAT.error("Element [" + locator.toString() + "] is not present");
		} catch (WebDriverException wde) {
			if (browser.getBrowserType() == BrowserDetector.BROWSER_IE) {
				// This is expected when an element cannot be found
				if (!silent)
					CAT.error("Element [" + locator.toString() + "] is not present on " + browser);
			} else {
				throw wde;
			}
		}
		
		return present;
	}

	/**
	 * Check if the location returns a valid element relative to a parent.
	 * 
	 * @param parent
	 *            the parent element
	 * @param locator
	 *            identifies a DOM element
	 * @return true if the element is found
	 */
	private boolean isValidLocator(WebElement parent, By locator) {

		boolean present = false;
		try {
			findElementBy(parent, locator);
			present = true;
		} catch (NoSuchElementException e) {
			CAT.error("Element [" + locator.toString() + "] is not present");
		}
		return present;
	}

	/**
	 * Wrapper method for findElelement
	 * 
	 * @param locator
	 *            a By locator object
	 * @return the web element
	 */
	public WebElement findElementBy(By locator) {

		// Fix a bug in FF14
		if (inSlowMode) {
			try {
				Thread.sleep(slowModeTime);
			} catch (InterruptedException e) {
			}
		}

		try {
			return getDriver().findElement(locator);
		} catch (Exception e) {

			if (e instanceof NoSuchElementException) {
				String error = "No such element found [" + locator + "]";
				CAT.error(error);
				throw new NoSuchElementException(error, e);
			} else {
				String error = "Unknown error locating element [" + locator + "]";
				CAT.error(error);
				throw new WebDriverException(error, e.getCause());
			}
		}
	}

	/**
	 * Wrapper method for findElelement
	 * 
	 * @param locator
	 *            a locator string
	 * @return the web element
	 */
	public WebElement findElementBy(String locator) {

		return findElementBy(getLocatorType(locator));
	}

	/**
	 * Wrapper method for findElelement relative to a parent.
	 * 
	 * @param parent
	 *            the parent element
	 * @param locator
	 *            a By locator object
	 * @return the web element
	 */
	public WebElement findElementBy(WebElement parent, By locator) {

		// Fix a bug in FF14
		if (inSlowMode) {
			try {
				Thread.sleep(slowModeTime);
			} catch (InterruptedException e) {
			}
		}

		return parent.findElement(locator);
	}

	/**
	 * Returns the entire HTML source between the opening and closing "html"
	 * tags.
	 * 
	 * @return the entire HTML source
	 */
	public String getHtmlSource() {

		try {
			return getDriver().getPageSource();
		} catch (org.openqa.selenium.NoSuchWindowException e) {
			CAT.error("Unable to get page source. The browser window may have been closed.");
			return "<html><body><span>" + e.getMessage() + "</span></body></html>";
		}
	}

	/**
	 * Captures a PNG screenshot to the specified file.
	 * 
	 * @param filename
	 *            the absolute path to the file to be written
	 */
	public void captureScreenshot(String filename) {
		try {

			// Bug: https://thetasgroup.onjira.com/browse/AGTF-2
			// If Fireforx wait for body tag
			if (browser.getBrowserType() == BrowserDetector.BROWSER_FIREFOX) {
				waitForPageToLoad(pauseTime);
			}

			File screenshot = null;
			// TODO: Check if there is a better solution
			if (!"local".equals(this.configMode) && getDriver() instanceof RemoteWebDriver) {
				WebDriver augmentedDriver = new Augmenter().augment(getDriver());
				screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			} else {
				screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			}
			FileUtils.copyFile(screenshot, new File(filename));
		} catch (Exception e) {
			// Remote screen shot exception
			if (e.getCause() instanceof ScreenshotException) {
				String base64Str = ((ScreenshotException) e.getCause()).getBase64EncodedScreenshot();
				saveSnapshotImage(filename, base64Str);
			} else if (e.getCause() instanceof NoSuchWindowException) {
				CAT.error("Unable to save screenshot. The browser window may have been closed.");
			} else {
				CAT.error("Unable to save screenshot", e);
			}
		}
	}

	/**
	 * Save a snapshot. Requires an image as a base 64 encoded string and a file
	 * name.
	 * 
	 * @param filename
	 *            the absolute path to the file
	 * @param base64Str
	 *            image as a base64 string
	 */
	public void saveSnapshotImage(String filename, String base64Str) {
		try {			
			// Converting a Base64 String into Image byte array
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByteArray = decoder.decodeBuffer(base64Str);
             
            // Write a image byte array into file system
            FileOutputStream imageOutFile = new FileOutputStream(filename);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
			
		} catch (Exception e) {
			CAT.error("Unable to save remote base64 encoded image", e);
		}
	}

	/**
	 * Captures a PNG screenshot to the specified file. This will only capture
	 * the viewable are of the screen.
	 * 
	 * @param filename
	 *            the absolute path to the file to be written
	 */
	public void captureEntirePageScreenshot(String filename) {
		captureScreenshot(filename);
	}

	/**
	 * Gets the absolute URL of the current page. The method returns an empty
	 * string of the URL cannot be determined.
	 * 
	 * @return the absolute URL of the current page or an empty String.
	 */
	public String getLocation() {
		String location = "";
		
		try {
			location = getDriver().getCurrentUrl();
		} catch (Exception e) {
			CAT.error("Unable to determine page URL, see stacktrace for more info", e);
		}

		return location;
	}

	/**
	 * Select an option from a drop-down using the option text.
	 * 
	 * @param selectLocator
	 *            an element locator identifying a drop-down menu
	 * @param optionLocator
	 *            an option text
	 */
	public void select(String selectLocator, String optionLocator) {
		By locatorType = getLocatorType(selectLocator);
		Select select = new Select(findElementBy(locatorType));
		select.selectByVisibleText(optionLocator);

		if (DEBUG)
			CAT.debug("Select element[" + locatorType + "] with select text [" + optionLocator + "]");
	}

	/**
	 * Handles loading of a spinner page. The method check if the specified
	 * spinner is the current page. Once the spinner has been confirmed wait for
	 * notification that the spinner has progressed to the target page. The wait
	 * period is specified by the the timeout argument passed to the method. If
	 * a timeout occurs a timeout exception is thrown by the underline
	 * architecture which causes the current test to report an error.
	 * 
	 * @param spinner
	 *            the name of the spinner page e.g. spinner.do
	 * @param timeout
	 *            the timeout value in milliseconds
	 */
	void handleSpinner(String spinner, int timeout) {

		// TODO: Fix spinner code
		/*
		 * KC removed 20/07/12
		 * 
		 * if (spinner != null && getLocation().contains(spinner)) {
		 * CAT.debug("Currently in spinner page " + spinner); final String
		 * currSpinner = spinner; new Wait() { public boolean until() { return
		 * !getLocation().contains(currSpinner); }
		 * }.wait("Spinner wait timeout, waited for " + timeout, timeout);
		 * 
		 * // Wait for new page to load. This should wait for the target page //
		 * to complete loading before proceeding. //
		 * server.waitForPageToLoad(timeout + "");
		 * 
		 * CAT.debug("Spinner page has progressed to " + getLocation()); //
		 * Fallback to ensure that a page has loaded new Wait() { public boolean
		 * until() { return isElementPresent("document.body"); }
		 * }.wait("Page wait timeout, waited for " + timeout, timeout); }
		 */
	}

	/**
	 * Performs the navigate function by clicking on a link that loads a new
	 * page. The method also handles an intermediate spinner page. A default
	 * spinner is assumed, if no spinner is required then use
	 * {@link #navigateNoSpinner(String)}
	 * 
	 * @param link
	 *            the link to be clicked.
	 */
	public void navigate(String link) {
		navigate(link, TestNavigator.getSpinner(), pauseTime, pauseTime);
	}

	/**
	 * Performs the navigate function by clicking on a link that loads a new
	 * page. The method also handles an intermediate spinner page. The spinner
	 * can be a single page or a pipe delimited list of spinner pages.</br> The
	 * following example illustrates how to define single and multiple spinner
	 * pages:<br/>
	 * <br/>
	 * <p>
	 * <code>&nbsp;&nbsp;&nbsp;&nbsp;spinner = "/spinner.do"<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;or <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;spinner = "/spinner.do|/myspinner.do|/anotherspinner.do"</code>
	 * </p>
	 * 
	 * @param link
	 *            the link to be clicked.
	 * @param spinner
	 *            the spinner page to be used. The default spinner will override
	 *            the default spinner.
	 */
	public void navigate(String link, String spinner) {
		navigate(link, spinner, pauseTime, pauseTime);
	}

	/**
	 * Performs the navigate function by clicking on a link that loads a new
	 * page. The method also handles an intermediate spinner page. The default
	 * page load time is overridden by the waitTimeout value.
	 * 
	 * @param link
	 *            the link to be clicked.
	 * @param waitTimeout
	 *            the time to wait for page to load. Overrides the default pause
	 *            time defined by test property <code>test.gui.pause.time</code>
	 */
	public void navigate(String link, int waitTimeout) {
		navigate(link, TestNavigator.getSpinner(), pauseTime, waitTimeout);
	}

	/**
	 * Performs the navigate function by clicking on a link that loads a new
	 * page. The method also handles an intermediate spinner page. The default
	 * spinner page and wait time are overridden by the spinner and
	 * <code>spinnerTimeout</code> values respectively. The spinner can be a
	 * single page or a pipe delimited list of spinner pages.<br/>
	 * <br/>
	 * The following example illustrates how to define single and multiple
	 * spinner pages:<br/>
	 * <p>
	 * <code>&nbsp;&nbsp;&nbsp;&nbsp;spinner = "/spinner.do"<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;or <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;spinner = "/spinner.do|/myspinner.do|/anotherspinner.do"</code>
	 * </p>
	 * 
	 * @param link
	 *            the link to be clicked.
	 * @param spinner
	 *            the spinner page to be used. The default spinner will override
	 *            the default spinner.
	 * @param spinnerTimeout
	 *            the wait time in milliseconds for the spinner to load.
	 *            Overrides the default spinner timeout value.
	 */
	public void navigate(String link, String spinner, int spinnerTimeout) {
		navigate(link, spinner, spinnerTimeout, pauseTime);
	}

	/**
	 * Performs the navigate function <b>without</b> spinner support. Clicks on
	 * a link that loads a new page without an intermediate spinner. The default
	 * timeout will be used. To override the default timeout then see
	 * {@link #navigateNoSpinner(String, int)}
	 * 
	 * @param link
	 *            the link to be clicked.
	 */
	public void navigateNoSpinner(String link) {

		navigateNoSpinner(link, pauseTime);
	}

	/**
	 * Performs the navigate function <b>without</b> spinner support. Clicks on
	 * a link that loads a new page without an intermediate spinner.
	 * 
	 * @param link
	 *            the link to be clicked.
	 * @param waitTimeout
	 *            the time to wait for page to load. Overrides the default pause
	 *            time defined by test property <code>test.gui.pause.time</code>
	 */
	public void navigateNoSpinner(String link, int waitTimeout) {
		// No spinner timeout is required as spinner=null
		navigate(link, null, waitTimeout, waitTimeout);
	}

	/**
	 * Performs the navigate function by clicking on a link that loads a new
	 * page. The method also handles an intermediate spinner page if specified.
	 * If there is no spinner page then simply supply <code>null</code>. The
	 * spinner can be a single page or a pipe delimited list of spinner pages.<br/>
	 * <br/>
	 * The following example illustrates how to define single and multiple
	 * spinner pages:<br/>
	 * <p>
	 * <code>&nbsp;&nbsp;&nbsp;&nbsp;spinner = "/spinner.do"<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;or <br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;spinner = "/spinner.do|/myspinner.do|/anotherspinner.do"</code>
	 * </p>
	 * 
	 * 
	 * @param link
	 *            the link to be clicked.
	 * @param spinner
	 *            the spinner to wait on or null if no spinner.
	 * @param spinnerTimeout
	 *            the wait time in milliseconds for the spinner to load.
	 *            Overrides the default spinner timeout value.
	 * @param waitTimeout
	 *            the amount of time in milliseconds to wait for a page to load.
	 *            Overrides the default pause time defined by test property
	 *            <code>test.gui.pause.time</code>
	 */
	public void navigate(String link, String spinner, int spinnerTimeout, int waitTimeout) {

		CAT.debug("Navigating to link " + link + " with spinner " + spinner + " From " + getLocation());

		// Click a link and wait for page to load
		click(link);
		// waitForPageToLoad(waitTimeout);

		if (spinner != null) {
			if (spinner.indexOf('|') > -1) {
				// Contains a list of spinners
				if (spinner.indexOf('|') == 0) {
					spinner = spinner.substring(1);
				}
				if (spinner.lastIndexOf('|') == spinner.length()) {
					spinner = spinner.substring(0, spinner.length() - 1);
				}
			}

			// Parse the pipe delimited list of spinners
			// The pipe is reserved in regExp so escape it
			String spinners[] = spinner.split("\\|");
			if (spinners.length > 0) {
				for (int i = 0; i < spinners.length; i++) {
					handleSpinner(spinners[i], spinnerTimeout);
				}
			} else {
				// Handle spinner
				handleSpinner(spinner, spinnerTimeout);
			}
		}
		CAT.debug("New location " + getLocation());
	}

	/**
	 * Sets the value of an input field, as though you typed it in.
	 * 
	 * @param locator
	 *            an element locator
	 * @param value
	 *            the value to type
	 */
	public void type(String locator, String value) {

		By by = getLocatorType(locator);
		WebElement element = findElementBy(by);
		type(element, value);
	}

	/**
	 * Sets the value of an input field, as though you typed it in.
	 * 
	 * @param locator
	 *            an element locator
	 * @param value
	 *            the value to type
	 */
	public void type(WebElement element, String value) {
		element.click();
		element.clear();
		element.sendKeys(value);
	}

	/**
	 * Switches focus to a specific iframe.
	 * 
	 * @param locator
	 *            identifies the iframe
	 */
	public void switchToFrame(String locator) {
		By by = getLocatorType(locator);

		if (isValidLocator(by, false)) {
			WebElement frame = findElementBy(by);
			// getDriver().switchTo().frame(frame);
			// waitForPageElement(by);
			String name = frame.getAttribute("name");
			String id = frame.getAttribute("id");
			if (name != null && name.length() > 0) {
				// Wait for package installation details page to load
				(new WebDriverWait(getDriver(), pauseTime)).until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(name));
			} else if (id != null && id.length() > 0) {
				// Wait for package installation details page to load
				(new WebDriverWait(getDriver(), pauseTime)).until(ExpectedConditions
						.frameToBeAvailableAndSwitchToIt(id));
			} else {
				// Throw error here
				CAT.error("Frame could but be found using " + by);
			}
		}
	}

	/**
	 * Return a list of web elements by tag name
	 * 
	 * @param tag
	 *            identify the elements to return
	 * @return a list of web elements
	 */
	public List<WebElement> getElementsByTag(String tag) {

		List<WebElement> elements = getDriver().findElements(By.tagName(tag));
		return elements;
	}

	/**
	 * Clicks on a link, button, checkbox or radio button.
	 * 
	 * @param locator
	 *            an element locator
	 */
	public void click(String locator) {
		By by = getLocatorType(locator);
		click(by);
	}

	/**
	 * Clicks on a link, button, checkbox or radio button.
	 * 
	 * @param locator
	 *            an element locator
	 */
	public void click(By by) {
		WebElement element = findElementBy(by);
		element.click();
	}

	/**
	 * Gets the text of an element. This works for any element that contains
	 * text.
	 * 
	 * @param locator
	 *            an element locator
	 * @return the text of the element
	 */
	public String getText(String locator) {
		By by = getLocatorType(locator);
		return findElementBy(by).getText();
	}

	/**
	 * Waits for a page element to be present on the page before proceeding. If
	 * the element is not found then a timeout occurs.
	 * 
	 * @param locator
	 *            an element locator
	 * 
	 * @param waitTime
	 *            the timeout in seconds
	 */
	public void waitForPageElement(String locator, int waitTime) throws Exception {

		// final String id = locator;
		By by = getLocatorType(locator);
		waitForPageElement(by, waitTime);
	}

	/**
	 * Waits for a page element to be present on the page before proceeding. If
	 * the element is not found then a timeout occurs.
	 * 
	 * @param by
	 *            element locator
	 * 
	 * @param waitTime
	 *            the timeout in seconds
	 * @throws Exception
	 */
	public void waitForPageElement(final By by, int waitTime) throws Exception {

		if (DEBUG)
			CAT.debug("Waiting for element [" + by + "]");

		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(waitTime, TimeUnit.SECONDS)
					.pollingEvery(500, TimeUnit.MILLISECONDS).ignoreAll(waitExceptions);

			wait.until(new Function<WebDriver, Boolean>() {
				public Boolean apply(WebDriver driver) {
					if (isValidLocator(by, true)) {
						CAT.debug("The following element has loaded [" + by + "]");
						return true;
					} else {
						return false;
					}
				}
			});
		} catch (Exception e) {
			CAT.error("Element not found [" + by + "]", e);
			throw e;
		}
	}

	/**
	 * Waits for a page element to be present on the page before proceeding. If
	 * the element is not found then a timeout occurs. The default pauseTime is
	 * set as the timeout interval see {@link TestNavigator#getPauseTime()}.
	 * 
	 * @param elementId
	 *            the page element to check
	 * @throws Exception
	 */
	public void waitForPageElement(String elementId) throws Exception {
		waitForPageElement(elementId, this.pauseTime);
	}

	/**
	 * Waits for a page element to be present on the page before proceeding. If
	 * the element is not found then a timeout occurs. The default pauseTime is
	 * set as the timeout interval see {@link TestNavigator#getPauseTime()}.
	 * 
	 * @param elementId
	 *            the page element to check
	 * @throws Exception
	 */
	public void waitForPageElement(By by) throws Exception {
		waitForPageElement(by, this.pauseTime);
	}

	/**
	 * Wait for a element to be deleted. The wait time is the default pause
	 * time.
	 * 
	 * @param element
	 *            the element that is being deleted
	 */
	public void waitForPageElementDelete(WebElement element) {
		waitForPageElementDelete(element, this.pauseTime);
	}

	/**
	 * Wait for a element to be deleted.
	 * 
	 * @param element
	 *            the element that is being deleted
	 * @param time
	 *            the wait duration in seconds
	 */
	public void waitForPageElementDelete(WebElement element, int time) {
		(new WebDriverWait(getDriver(), time)).until(ExpectedConditions.stalenessOf(element));
	}

	/**
	 * Wait for element to be invisible or deleted from the page.
	 * 
	 * @param locator
	 *            element locator
	 */
	public void waitForInvisibilityOfPageElement(By locator) {
		(new WebDriverWait(getDriver(), this.pauseTime))
				.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * Wait for element to be invisible or deleted from the page.
	 * 
	 * @param locator
	 *            element locator
	 */
	public void waitForInvisibilityOfPageElement(String locator) {
		(new WebDriverWait(getDriver(), this.pauseTime)).until(ExpectedConditions
				.invisibilityOfElementLocated(getLocatorType(locator)));
	}

	/**
	 * Wait for element to be invisible or deleted from the page.
	 * 
	 * @param locator
	 *            element locator
	 * @param time
	 *            wait time in seconds
	 */
	public void waitForInvisibilityOfPageElement(By locator, int time) {
		(new WebDriverWait(getDriver(), time)).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * Wait for element to be invisible or deleted from the page.
	 * 
	 * @param locator
	 *            element locator
	 * @param time
	 *            wait time in seconds
	 */
	public void waitForInvisibilityOfPageElement(String locator, int time) {
		(new WebDriverWait(getDriver(), time)).until(ExpectedConditions
				.invisibilityOfElementLocated(getLocatorType(locator)));
	}
	
	/**
	 * Wait for until an element is visible and enabled such that you can click
	 * it.
	 * 
	 * @param locator
	 *            element locator
	 */
	public void waitForElementToBeClickable(String locator) {
		waitForElementToBeClickable(locator, this.pauseTime);
	}	
	
	/**
	 * Wait for until an element is visible and enabled such that you can click
	 * it.
	 * 
	 * @param locator
	 *            element locator
	 * @param time
	 *            wait time in seconds
	 */
	public void waitForElementToBeClickable(String locator, int time) {
		(new WebDriverWait(getDriver(), time)).until(ExpectedConditions
				.elementToBeClickable(getLocatorType(locator)));
	}

	/**
	 * Drag a source element and drop it on a target element.
	 * 
	 * @param source
	 *            the locator of the source element
	 * @param target
	 *            the locator of the target element
	 */
	public void dragAndDrop(String source, String target) {
		By src = getLocatorType(source);
		By tar = getLocatorType(target);

		dragAndDrop(src, tar);
	}

	/**
	 * Drag a source element and drop it on a target element.
	 * 
	 * @param source
	 *            the locator of the source element
	 * @param target
	 *            the locator of the target element
	 */
	public void dragAndDrop(By source, By target) {
		WebElement srcElement = findElementBy(source);
		WebElement tarElement = findElementBy(target);

		dragAndDrop(srcElement, tarElement);
	}

	/**
	 * Drag a source element and drop it on a target element.
	 * 
	 * @param source
	 *            the locator of the source element
	 * @param target
	 *            the locator of the target element
	 */
	public void dragAndDrop(WebElement source, WebElement target) {

		(new Actions(getDriver())).dragAndDrop(source, target).perform();
	}

	/**
	 * Set the path on the file upload input.
	 * 
	 * @param locator
	 *            the file input locator
	 * @param path
	 *            the path to the file
	 */
	public void uploadFile(String locator, String path) {
		By by = getLocatorType(locator);
		if (isElementPresent(by)) {
			WebElement element = getDriver().findElement(by);
			element.sendKeys(path);
		} else {
			CAT.error("File input not found, unable to upload file");
		}
	}

	/**
	 * Returns the browser type. Provides access to browser name which is
	 * derived from the UserAgent value returned by the browser.
	 * 
	 * @return a the browser type code
	 */
	public int getBrowserType() {
		if (browser != null)
			return browser.getBrowserType();

		CAT.error("Browser type not set");
		return -1;

	}

	/**
	 * Returns the browser name. Provides access to browser name which is
	 * derived from the UserAgent value returned by the browser.
	 * 
	 * @return the browser name
	 */
	public String getBrowserName() {
		if (browser != null)
			return browser.getBrowserName();

		CAT.error("Browser name not set");
		return null;

	}

	/**
	 * Returns the browser version.
	 * 
	 * @return the browser version
	 */
	public String getBrowserVersion() {
		if (browser != null)
			return browser.getBrowserVersion();

		CAT.error("Browser version not set");
		return null;
	}

	/**
	 * Returns the browser detector which wraps the current browser UserAgent
	 * string.
	 * 
	 * @return
	 */
	public BrowserDetector getBrowserDetector() {
		return browser;
	}

	/**
	 * Sets the browser type from the USER-AGENT string.
	 * 
	 * @param agentString
	 *            describes the browser type
	 */
	public void setBrowserType(String agentString) {

		browser = new BrowserDetector(agentString);
		// Output browser details
		if (DEBUG) {
			CAT.debug(browser);
		}

	}

	/**
	 * Returns true if text is present
	 * 
	 * @param value
	 *            the text to search
	 * @return true if text is present
	 */
	public boolean isTextPresent(String value) {
		WebElement body = getDriver().findElement(By.tagName("body"));
		return body.getText().contains(value);

	}
	
	/**
	 * Set focus on a target element.
	 * 
	 * @param element
	 *            the element to focus on
	 */
	public void setFocus(WebElement element) {

		if (browser.getBrowserType() == BrowserDetector.BROWSER_FIREFOX
				|| browser.getBrowserType() == BrowserDetector.BROWSER_IE) {
			element.sendKeys("");
		} else {
			new Actions(getDriver()).moveToElement(element).perform();
		}
	}
	
	/**
	 * Tests if an alert box is displayed.
	 * 
	 * @return true if an alert is displayed
	 */
	public boolean isAlertPresent() {
		return isAlertPresent(false);
	} // isAlertPresent()	
	
	/**
	 * Tests if an alert box is displayed.
	 * 
	 * @param accept
	 *            true if the alert should be accepted
	 * @return true if an alert is displayed
	 */
	public boolean isAlertPresent(boolean accept) {
		try {
			Alert alert = getDriver().switchTo().alert();
			if(accept)
				alert.accept();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	} // isAlertPresent()
	
	/**
	 * Gets the text of the alert message and it clicks the OK button to make it
	 * go away,
	 * 
	 * @return the text of the alert message. 
	 */
    public String getAlert()
    { 
        Alert alert = getDriver().switchTo().alert(); 
        String str = alert.getText(); 

        alert.accept(); 
        return str; 
    }   // getAlert()
    
	/**
	 * Returns the title of the current page. If the tile cannot be determined
	 * then null is returned.
	 * 
	 * @return the page title or null.
	 */
	public String getTitle() {

		try {
			return getDriver().getTitle();
		} catch (Exception e) {
			CAT.error("Unable obtain the page title", e);
			return null;
		}
	}	
	
	/**
	 * Parse the browser config JSON string into a JSONObject
	 * 
	 * @return a JSONObject representing the browser config
	 */
	public JSONObject parseBrowserConfig() {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		String browserConfig = navigator.getBrowserConfig();
		if(browserConfig != null) {
			try {
				obj = (JSONObject) parser.parse(browserConfig);
			} catch (ParseException e) {
				
			}
		}
		
		return obj;
	}

	/**
	 * A web driver instance that supports a touch interface. Will be used
	 * specifically to drive mobile devices.
	 * 
	 * @author kieran
	 * 
	 */
	class TouchWebDriver extends RemoteWebDriver implements HasTouchScreen {

		RemoteTouchScreen touch = null;

		public TouchWebDriver(URL remoteUrl, Capabilities desiredCapabilities) {

			super(remoteUrl, desiredCapabilities);
			touch = new RemoteTouchScreen(getExecuteMethod());
		}

		public TouchScreen getTouch() {
			return touch;
		}
	}	
}
