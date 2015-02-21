package org.floit.waf.navigator;

import static org.junit.Assert.*;

import org.floit.waf.navigator.TestNavigator;
import org.floit.waf.navigator.TestServerApi;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;

public class TestServerApiTest {
	
	private TestNavigator nav;
	private static String androidUserAgent = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
	private static String appiumCapabilities = "{\"Capabilities\": {\"device\": \"iPhone Simulator\", \"version\": \"6.1\", \"app\": \"safari\"}}";
	private static String emulationCapabilities = "{\"Capabilities\": {\"chromeOptions\": {\"mobileEmulation\":{\"userAgent\":\"" + androidUserAgent + "\", \"deviceMetrics\":{\"width\": \"360\", \"height\": \"640\", \"pixelRatio\": \"3.0\"}}}}}";
	
	@BeforeClass
	public static void setUp() {
		System.setProperty("test.gui.browser.config", appiumCapabilities);
	}

	@Test
	public void testParseBrowserConfig() throws Exception {
		System.setProperty("test.gui.browser.config", appiumCapabilities);
		nav = new TestNavigator();
		TestServerApi server = new TestServerApi(nav);
		JSONObject obj = server.parseBrowserConfig();
		
		JSONObject obj2=(JSONObject)obj.get("Capabilities");
		String device = (String) obj2.get("device");
		
		assertEquals("iPhone Simulator", device);
		
	}	
	
	@Test
	public void testSetCapabilities() throws Exception {
		System.setProperty("test.gui.browser.config", appiumCapabilities);
		nav = new TestNavigator();
		TestServerApi server = new TestServerApi(nav);
		JSONObject obj = server.parseBrowserConfig();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		server.setCapabilities(capabilities);
	}
	
	@Test
	public void testSetCapabilitiesChromeElumator() throws Exception {
		System.setProperty("test.gui.browser.config", emulationCapabilities);
		nav = new TestNavigator();
		TestServerApi server = new TestServerApi(nav);
		JSONObject obj = server.parseBrowserConfig();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		server.setCapabilities(capabilities);
		
		String expected = "Capabilities [{chromeOptions={mobileEmulation={userAgent=Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19, deviceMetrics={height=640, width=360, pixelRatio=3.0}}}}]";
		String actual = capabilities.toString();
		assertEquals("Capabilities string do not match", expected.toString(), actual);
	}
	
	@Test
	public void testChromeElumator() throws Exception {
		System.setProperty("test.gui.browser.config", emulationCapabilities);
		System.setProperty("test.gui.browser.path", "/Users/kieran/Documents/workspace/WebDrivers/chromedriver-2.12");
		nav = new TestNavigator();
		TestServerApi server = new TestServerApi(nav);
		server.start();
		server.getBrowserDetector().getUserAgentString();
	}
	
	@Test
	@Ignore
	public void runOnIphone() throws Exception {
		System.setProperty("test.gui.browser.config", emulationCapabilities);
		System.setProperty("test.gui.server.host","qa-mac-mini");
		System.setProperty("test.gui.server.port","4723");
		System.setProperty("test.gui.configuration.mode","grid");
		System.setProperty("test.gui.browser","ipad");
		System.setProperty("test.gui.browser.window.size","");
		
		nav = new TestNavigator();
		nav.setUp();		
		TestServerApi server = nav.server;
		
		server.open("https://login.salesforce.com");
		
		assertTrue(server.isElementPresent("id=username"));
		
	}
	
	@After
	public void tearDown() {
		if(nav != null) {
			nav.tearDown();
		}
	}
}
