/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.BaseGUITestNavigator
 * Author: $Author: garrett.muldowney $
 * Created: $Date: 2011/11/02 11:08:31 $
 * Revision: $Revision: 1.54 $
 */
package org.floit.waf.navigator;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.floit.waf.config.Page;
import org.floit.waf.navigator.handlers.ConfigHandler;
import org.floit.waf.navigator.runner.RunnerFactory;
import org.floit.waf.utils.PropertyManager;

/**
 * Base GUI test case which extends <code>SeleneseTestCase</code>.
 * <p>
 * This is the base GUI test class which reads in the properties file,
 * starts/stops the <code>Selenium</code> server and unmarshalls the XML
 * configuration file.
 * </p>
 */

public class TestNavigator {

	/** The logger uses log4j to configure and control the logging output. */
	static final Logger CAT = Logger.getLogger(TestNavigator.class);

	/** Determines if the log4j debug mode is on or off. */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	/** The name of test harness configuration properties file. */
	private static final String TEST_PROP_FILE_NAME = System.getProperty("test.prop.file", "ttg_test.properties");

	private static final String SAVE_ON_EXIT = "EXIT";
	private static final String SAVE_ALL_PAGES = "ALL";

	/**
	 * The server object. Must be protected for inheriting test cases to use.
	 */
	public TestServerApi server;

	/** The test navigator which handles the config file. */
	protected static ConfigHandler configHandler;

	/** The properties file to be set/used. */
	private Properties properties;

	/** Custom Properties Object */
	private Properties customProperties;

	/* Variables set in the properties file. */

	/** If the test server should be started and stopped after each test. */
	// private boolean startServer;

	/** The configuration file to be used to test. */
	private String configFile;

	/** The base url. */
	private String url;

	/** The string to be added to the base url to test. */
	private String startURL;

	/** The default timeout. */
	private String defaultTimeout = "100000";

	/** The path to the browser to be used by Selenium. */
	private String browserPath;

	/** The spinner URL of the application. */
	private static String spinnerURL;

	/**
	 * The amount of time the to wait for element or page to load. (seconds)
	 */
	private static int pauseTime;

	private String saveByBrowser;

	/** Boolean to determine whether a FF profile will be used */
	private boolean useFFProfile;

	/** The location of the firefox profile to use if specified */
	private String firefoxProfileLoc;

	/** If selenium should save the page on error, default is false. */
	private boolean savePage = false;

	/** The location to save the page on error. */
	private String saveLocation;

	/** The format to save the page in, eg html, png. */
	private String saveFormat;

	/** The save mode to use: onexit or allpages */
	private String saveMode = SAVE_ON_EXIT;

	/** The server host. */
	private String serverHost;

	/** The server port to start the Selenium server on. */
	private int serverPort;

	/** The xdist tester url. */
	private String testerURL;

	/** Index use to track saved pages */
	private int savePageIndex = 1;

	/** Tracks all the executed inputs for each page in a test */
	private Document executedInputs = null;

	/** The name of the current test class */
	// private String testClass = null;

	/** The name of the current test */
	// private String testName = null;

	/** Custom Properties File Name */
	private String customPropName = null;

	/** Factory instance to create Page and Flow runners */
	public RunnerFactory runner = null;

	/** Run in slow mode */
	private boolean isSlowMode = false;

	/** Slow mode wait time, default 500 milliseconds */
	private int slowModeTime = 500;

	private String logDir = null;

	/** Configuration mode property */
	private String configMode = "local";

	/** Configuration mode property */
	private String browser = null;

	private String browserVersion = null;

	private int logCount = 0;

	private boolean maximiseBrowser = false;

	private Dimension windowSize = null;
	
	private String browserOverride = null;

	/**
	 * Instantiate the test navigator and load the property.
	 * 
	 * @throws Exception
	 */
	public TestNavigator() {

		try {
			if (DEBUG) {
				CAT.debug("Loading property file.");
			}
			// Get the properties file
			properties = getPropertiesFromClasspath(TEST_PROP_FILE_NAME);

			// Add any test properties set as System properties at runtime
			PropertyManager.overrideWithSystemProperties(properties);			
			
			// Normalise properties
			properties = PropertyManager.normalise(properties);

			// Set the properties to the ones contained in the file
			setTestProperties(properties);
			checkScreenGrabDirectory();

			if (customPropName != null && customPropName.length() > 0) {
				customProperties = getPropertiesFromClasspath(customPropName);
				// Normalise properties
				// TODO: Port the property manager
				customProperties = PropertyManager.normalise(customProperties);
			}

		} catch (IOException e) {
			CAT.error("Failed to load property file", e);
		}
	}
	
	/**
	 * The setup methoid unmarshalls the config file an starts the web browser.
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception {

		// Create an instance ofthe ConfigHandler, this provides the framework
		// access to the config. The config should be loaded for every test so
		// that each test has its own copy of the config
		configHandler = new ConfigHandler(configFile, this);
		runner = new RunnerFactory(this);
		
		server = new TestServerApi(this);
		server.start();

		CAT.info("Opening url; " + url + "/" + startURL);
		// Open the base url and append the start URL onto the end of this
		server.open(url + "/" + startURL);

		// Determine browser type from the User-Agent string
		String agentString = (String)server.getEval("return navigator.userAgent;");
		CAT.info("UserAgent [" + agentString + "]");
		server.setBrowserType(agentString);
	}	

	public String getConfigMode() {
		return configMode;
	}

	public String getServerHost() {
		return serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getBrowser() {
		return browser;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public String getBrowserPath() {
		return browserPath;
	}

	public boolean getUseFirefoxProfile() {
		return useFFProfile;
	}

	public String getFirefoxProfileLocation() {
		return firefoxProfileLoc;
	}

	/**
	 * Checks that the location to which screenGrabs will be saved exists and
	 * creates it if necessary
	 */
	private void checkScreenGrabDirectory() {
		if (savePage) {
			try {
				File dataDir = new File(saveLocation);
				// Check the error directory exists
				if (!dataDir.exists()) {
					dataDir.mkdirs();
				}
			} catch (Exception e) {
				if (DEBUG) {
					CAT.debug("Couldn't create screengrab directory " + saveLocation + ": " + e.getMessage(), e);

				} else {
					CAT.info("Couldn't create screengrab directory " + saveLocation);
				}
			}
		}
	}

	/**
	 * Shutsdown the test server occurred.
	 */
	public void tearDown() {
		if (server != null) {
			server.stop();
		} else {
			CAT.error("The server is null, looks like it was never initialised");
		}
	}

	/**
	 * The method sets the mandatory test configuration properties. <br/>
	 * The following mandatory properties should be configured:
	 * <ul>
	 * <li><b>test.gui.config.file</b> - The XML configuration file.</li>
	 * <li><b>test.gui.start.apiWrapper.server</b> - Whether the Selenium server
	 * should be started and ended before/after each test.</li>
	 * <li><b>test.gui.base.url</b> - The base URL for testing, eg;
	 * 'http://localhost'.</li>
	 * <li><b>test.gui.start.url</b> - The start URL to add to the base for
	 * testing, eg; 'home.do'.</li>
	 * <li><b>test.default.timeout</b> - The default <code>Selenium</code>
	 * timeout. (milliseconds)</li>
	 * <li><b>test.gui.browser.path</b> - The path to the browser you want to
	 * test with.</li>
	 * <li><b>test.gui.spinner.url</b> - The wait/spinner page of the
	 * application.</li>
	 * <li><b>test.gui.server.host</b> - The test server host name.</li>
	 * <li><b>test.gui.save.location</b> - Where to save the page when an error
	 * occurs.</li>
	 * <li><b>test.gui.save.format</b> - The format to save the page in when an
	 * error occurs.</li>
	 * <li><b>test.gui.profile.location</b> - The location of the Firefox
	 * profile to use.</li>
	 * <li><b>test.gui.use.profile</b> - Set this to true if you want to use a
	 * Firefox profile, and false if not.</li>
	 * <li><b>test.gui.server.port</b> - The port that the test server listens
	 * on.</li>
	 * <li><b>test.gui.pause.time</b> - The time to wait during navigating for
	 * the web pages to load.</li>
	 * <li><b>test.gui.save.page</b> - if the HTML page should be saved.</li>
	 * </ul>
	 * 
	 * @param prop
	 *            list of name value pairs in a Properties object
	 */
	private void setTestProperties(Properties prop) {

		// Set all of the properties
		configFile = tryGetProperty(prop, "test.gui.config.file");
		url = tryGetProperty(prop, "test.gui.base.url");
		startURL = tryGetProperty(prop, "test.gui.start.url");
		// KC 20/07/12 defaultTimeout = tryGetProperty(prop,
		// "test.default.timeout");
		browserPath = tryGetProperty(prop, "test.gui.browser.path");
		spinnerURL = tryGetProperty(prop, "test.gui.spinner.url");
		serverHost = tryGetProperty(prop, "test.gui.server.host");
		saveLocation = tryGetProperty(prop, "test.gui.save.location");
		saveFormat = tryGetProperty(prop, "test.gui.save.format");
		saveMode = tryGetProperty(prop, "test.gui.save.mode");
		saveByBrowser = tryGetProperty(prop, "test.gui.save.by.browser", "false");
		firefoxProfileLoc = tryGetProperty(prop, "test.gui.profile.location");
		testerURL = tryGetProperty(prop, "test.gui.tester.url");
		customPropName = tryGetProperty(prop, "test.gui.custom.prop.file");
		configMode = tryGetProperty(prop, "test.gui.configuration.mode");
		String browserStrings[] = tryGetProperty(prop, "test.gui.browser").split("\\|");
		browser = browserStrings[0];
		if (browserStrings.length == 2)
			browserVersion = browserStrings[1];
		
		browserOverride = tryGetProperty(prop, "test.gui.browser.config");

		String useFFProfileTemp = tryGetProperty(prop, "test.gui.use.profile");
		String serverPortTemp = tryGetProperty(prop, "test.gui.server.port");
		String pauseTimeTemp = tryGetProperty(prop, "test.gui.pause.time");
		String savePageTemp = tryGetProperty(prop, "test.gui.save.page");
		String isSlowModeTemp = tryGetProperty(prop, "test.gui.slow.mode", "" + isSlowMode);
		String slowModeTimeTemp = tryGetProperty(prop, "test.gui.slow.mode.time", "" + slowModeTime);
		String maximiseBrowserTemp = tryGetProperty(prop, "test.gui.browser.maximise", "" + maximiseBrowser);
		String windowSizeTemp = tryGetProperty(prop, "test.gui.browser.window.size");

		// Parse Strings to ints/booleans so they can be used where needed
		useFFProfile = Boolean.parseBoolean(useFFProfileTemp);
		serverPort = Integer.parseInt(serverPortTemp);
		pauseTime = Integer.parseInt(pauseTimeTemp);
		savePage = Boolean.parseBoolean(savePageTemp);
		isSlowMode = Boolean.parseBoolean(isSlowModeTemp);
		slowModeTime = Integer.parseInt(slowModeTimeTemp);
		maximiseBrowser = Boolean.parseBoolean(maximiseBrowserTemp);

		try {
			if (windowSizeTemp.length() > 0 && windowSizeTemp.contains(",")) {
				try {
					String[] dim = windowSizeTemp.split(",");
					int x = Integer.parseInt(dim[0]);
					int y = Integer.parseInt(dim[1]);
					windowSize = new Dimension(x, y);
				} catch (Exception e) {
					CAT.error("Unable to parse window size, using defaults: " + windowSize.toString(), e);
				}
			}
		} catch (Exception e) {
			CAT.error("Unable to parse window size, using defaults: " + windowSize.toString(), e);
		}

		if (DEBUG) {
			StringBuffer sb = new StringBuffer();
			sb.append("The following properties have been configured :\n");
			sb.append(prop.toString());
			CAT.debug(sb.toString());
		}
	}

	/**
	 * The method gets the custom test properties.
	 * 
	 * @param custom_property
	 *            is the value that will be retrieved from the
	 *            custom_prop.properties file.
	 * @return cust_val cust_val is the value retrieved from the custom
	 *         properties file.
	 */
	public String getCustomProperty(String custom_property) {
		return tryGetProperty(customProperties, custom_property);
	}

	/**
	 * The method gets the custom test properties, reloading the test properties
	 * file first.
	 * 
	 * @param custom_property
	 *            is the value that will be retrieved from the
	 *            custom_prop.properties file.
	 * @param reload
	 *            true to reload the properties file
	 * @return cust_val cust_val is the value retrieved from the custom
	 *         properties file.
	 */
	public String getCustomProperty(String custom_property, boolean reload) {
		if (reload) {
			if (customPropName != null && customPropName.length() > 0) {
				try {
					customProperties = getPropertiesFromClasspath(customPropName);
					customProperties = PropertyManager.normalise(customProperties);
				} catch (IOException e) {
					CAT.error("Failed to reload custom properties", e);
				}
			}
		}
		return getCustomProperty(custom_property);
	}

	/**
	 * Get a test property.
	 * 
	 * @param key
	 *            the property name
	 * @return the property value
	 */
	public String getTestProperty(String key) {
		return tryGetProperty(properties, key);
	}

	/**
	 * Sets a custom property. Will override a property if it exists.
	 * 
	 * @param name
	 *            the name of the property
	 */
	public void setCustomProperty(String name, String value) {
		customProperties.setProperty(name, value);

		URL url = this.getClass().getClassLoader().getResource(customPropName);
		try {
			File file = new File(url.toURI());
			if (file.isFile()) {
				FileWriter fstream = new FileWriter(file);
				customProperties.store(fstream, "Updating custom properties");
			} else {
				CAT.error("Unable to write to custom property file [" + url.toURI() + "]");
			}
		} catch (Exception e) {
			CAT.error("Unable to write to custom property file", e);
		}
	}

	/**
	 * Loads the test properties from the classpath.
	 * 
	 * @param propFileName
	 *            The name of the property file.
	 * @return Property An object containing the test properties.
	 * @throws IOException
	 *             Unable to read property file
	 */
	private Properties getPropertiesFromClasspath(String propFileName) throws IOException {

		// loading properties from the classpath
		Properties props = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}

		props.load(inputStream);

		return props;
	}

	/**
	 * Save the contents of the current Page.
	 * 
	 * @param page
	 *            the page to be saved.
	 */
	public void savePage(Page page) {
		String pageName = page.getName();
		StringBuffer sb = new StringBuffer();
		sb.append("page");
		sb.append(savePageIndex);
		sb.append("_");
		sb.append(pageName);
		String filename = sb.toString();
		savePage(filename);
		// Increment the counter
		savePageIndex++;
	}

	/**
	 * Saves the HTML source when a test fails. The HTML source will be captured
	 * in debug mode to allow developers to have full information of the error
	 * 
	 * @throws IOException
	 *             Unable to create the file.
	 */
	public void savePage(String fileName) {

		if (!savePage) {
			CAT.info("Save page is disabled");
			return;
		}
		
		String baseFileName = getSavePath(fileName);
		saveHtml(baseFileName);
		saveSnapshot(baseFileName);
	}
	
	/**
	 * Save the HTML page source to the log directory
	 * 
	 * @param baseFileName
	 *            the absolute file path without extension
	 */
	public void saveHtml(String baseFileName) {
		String filename = baseFileName + ".html";
		try {

			if (saveFormat.equalsIgnoreCase("html") || saveFormat.equalsIgnoreCase("both")
					|| saveFormat.equalsIgnoreCase("all")) {
				String htmlSource = server.getHtmlSource();
				FileWriter htmlFileWriter = new FileWriter(filename, false);
				htmlFileWriter.write(htmlSource);
				htmlFileWriter.close();
			}

		} catch (Exception e) {
			CAT.error("Couldn't create file " + filename + ": " + e.getMessage(), e);
		}
	}
	
	/**
	 * Save a snapshot of the page to the log directory
	 * 
	 * @param baseFileName
	 *            the absolute file path without extension
	 */	
	public void saveSnapshot(String baseFileName) {
		String filename = baseFileName + ".png";
		
		try {
			// Only Firefox supports capture page. All other browsers must do a
			// standard screen grab.
			if (saveFormat.equalsIgnoreCase("png") || saveFormat.equalsIgnoreCase("both")
					|| saveFormat.equalsIgnoreCase("all")) {
				if (server.getBrowserType() != BrowserDetector.BROWSER_FIREFOX) {
					server.captureScreenshot(filename);
				} else {
					server.captureEntirePageScreenshot(filename);
				}
			}

		} catch (Exception e) {
			CAT.error("Couldn't create file " + filename + ": " + e.getMessage(), e);	
		}
	}
	
	/**
	 * Create the absolute file path with out file extension.
	 * @param filename the name of the file
	 * @return the file extension
	 */ 
	public String getSavePath(String filename) {
		filename = this.logCount++ + "-" + filename;

		String outputDirectoryPath = "";
		String baseFileName = "";
		try {
			if ("true".equalsIgnoreCase(saveByBrowser)) {
				outputDirectoryPath = saveLocation + File.separator + server.getBrowserName() + File.separator + logDir;
				outputDirectoryPath = createOuputDirectoryPath(outputDirectoryPath);

				baseFileName = outputDirectoryPath + File.separator + filename;
			} else {
				outputDirectoryPath = saveLocation + File.separator + logDir;
				outputDirectoryPath = createOuputDirectoryPath(outputDirectoryPath);

				baseFileName = outputDirectoryPath + File.separator + filename;
			}		
		} catch (Exception e) {
			CAT.error("Couldn't create file " + outputDirectoryPath + ": " + e.getMessage(), e);			
		}
		
		return baseFileName;
	}

	/**
	 * Creates the path to output directory. The output directory is where
	 * screen captured images are saved.
	 * 
	 * @param outputDirectoryPath
	 *            the path of directory to be created
	 * @return the outputDirectoryPath passed to the method
	 * @throws Exception
	 *             any error that occurs when trying to create the directory
	 */
	private String createOuputDirectoryPath(String outputDirectoryPath) throws Exception {

		File outputDir = new File(outputDirectoryPath);
		outputDirectoryPath = outputDir.getAbsolutePath();

		if (!outputDir.exists()) {

			if (outputDir.mkdirs()) {
				CAT.debug("Creating save directory " + outputDirectoryPath);
			} else {
				CAT.debug("Unable to creating save directory " + outputDirectoryPath);
			}
		}

		return outputDirectoryPath;
	}

	/**
	 * Creates a log directory based on package and test name
	 * 
	 * @return the log directory path
	 */
	/**
	 * KC removed 02/08/12 private String createLogDirectory() {
	 * 
	 * String path = testClass.replace('.', File.separatorChar); return path +
	 * File.separator + testName; }
	 */

	/**
	 * Output the page inputs used on each page during the test.
	 */
	private void logPageInputs() {
		String outputDirectoryPath = saveLocation + File.separator + logDir;

		try {
			outputDirectoryPath = createOuputDirectoryPath(outputDirectoryPath);
		} catch (Exception e) {
			CAT.error("Couldn't create output directory " + outputDirectoryPath + ": " + e.getMessage(), e);
		}

		try {

			File file = new File(outputDirectoryPath + File.separator + "PageInputs.xml");

			if (file.createNewFile()) {
				FileOutputStream fos = new FileOutputStream(file);

				// Serialise DOM
				// TODO: Replace with another serialiser
				// DOMUtilities.serialise(executedInputs, fos);
			} else {
				CAT.error("Couldn't create PageInputs.xml in " + outputDirectoryPath);
			}

		} catch (Exception e) {
			CAT.error("Couldn't create PageInputs.xml in " + outputDirectoryPath, e);
		}
	}

	/**
	 * Tries to read the property from the file, and if the property isn't set,
	 * it logs it.
	 * 
	 * @param prop
	 *            An instance of Properties containing the test properties.
	 * @param propName
	 *            The name of the property to get.
	 * @return The property value.
	 */
	private String tryGetProperty(Properties prop, String propName) {
		return tryGetProperty(prop, propName, null);
	}

	/**
	 * Tries to read the property from the file, and if the property isn't set,
	 * it logs it.
	 * 
	 * @param prop
	 *            An instance of Properties containing the test properties.
	 * @param propName
	 *            The name of the property to get.
	 * @param defaultValue
	 *            a default value if the property is not found.
	 * @return The property value.
	 */
	private String tryGetProperty(Properties prop, String propName, String defaultValue) {

		String propValue;

		if (prop.getProperty(propName) == null) {
			propValue = defaultValue;

			if (DEBUG) {
				CAT.info("Property value for '" + propName + "' not set.");
			}
		} else {
			propValue = prop.getProperty(propName);
		}
		return propValue;
	}

	/**
	 * Returns the pause time as defined in the properties file for config
	 * classes to use.
	 * 
	 * @return pauseTime The time to pause.
	 */
	public static int getPauseTime() {
		return pauseTime;
	}

	/**
	 * Gets the spinner URL.
	 * 
	 * @return spinnerURL The spinner url as specified in the properties file.
	 */
	public static String getSpinner() {
		return spinnerURL;
	}

	/**
	 * Gets the Base URL.
	 * 
	 * @return url The base url as specified in the properties file.
	 */
	public String getBaseURL() {
		return url;
	}

	/**
	 * Gets the Tester URL.
	 * 
	 * @return url The tester url as specified in the properties file.
	 */
	public String getTesterURL() {
		return testerURL;
	}

	/**
	 * Determines if save every page is enabled
	 * 
	 * @return true if all pages should be saved
	 */
	public boolean getSaveEveryPage() {
		boolean value = false;
		if (savePage) {
			if (saveMode.equalsIgnoreCase(SAVE_ALL_PAGES))
				value = true;
		}
		return value;
	}

	/**
	 * Returns a reference to DOM that is used to track input data per page.
	 * 
	 * @return a document to track page input data
	 */
	public Document getInputTracker() {
		return executedInputs;
	}

	/**
	 * Return reference to config handler instance.
	 * 
	 * @return refernce to config handler.
	 */
	public ConfigHandler getConfigHandler() {
		return configHandler;
	}

	/**
	 * Returns a reference to the {@link TestServerApi} instance. This provides
	 * access to the test runtime.
	 * 
	 * @return the test server object.
	 */
	public TestServerApi getTestServer() {
		return server;
	}

	/**
	 * Determines if slow mode is enabled. Slow mode will make the framework
	 * slow down during execution. Handy for debugging.
	 * 
	 * @return true is enabled otherwise false
	 */
	public boolean getSlowModeEnabled() {
		return isSlowMode;
	}

	/**
	 * Get the slow mode time in milliseconds
	 * 
	 * @return the slow mode time in milliseconds
	 */
	public int getSlowModeTime() {
		return slowModeTime;
	}

	/**
	 * Return the value of maximise browser property.
	 * 
	 * @return value of maximise browsers property
	 */
	public boolean getMaximiseBrowser() {
		return maximiseBrowser;
	}

	/**
	 * Return the window dimension property as a Dimension object.
	 * 
	 * @return the size of the window
	 */
	public Dimension getWindowSize() {
		return windowSize;
	}

	/**
	 * Sets the relative log directory path. It also resets the log counter. The
	 * log count value is prefixed to file name so the the log order is known.
	 * 
	 * @param logDir
	 *            the log directory path
	 */
	public void setLogDir(String logDir) {
		this.logDir = logDir;
		// Reset log counter
		this.logCount = 0;
	}
	
	/**
	 * Returns the name of the absolute path of the save directory.
	 * 
	 * @return the path to the save directory
	 */
	public String getSaveDir() {
		return saveLocation;
	}
	
	/**
	 * A JSON string describing the browser configuration.
	 * 
	 * @return browser configuration as a JSON string
	 */
	public String getBrowserConfig() {
		return browserOverride;
	}	

}
