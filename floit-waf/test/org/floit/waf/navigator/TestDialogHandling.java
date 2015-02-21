package org.floit.waf.navigator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.floit.waf.navigator.TestNavigator;
import org.floit.waf.navigator.TestServerApi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDialogHandling {
	
	protected static TestNavigator nav = new TestNavigator();
	TestServerApi server = nav.getTestServer();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		nav.setUp();
		
		URL url = (new File("./test/data/testDialog.html")).toURI().toURL();
		// Go to URL
		nav.server.open(url.toString());
	}
	
	@Test
	public void test_IsAlertDialogPresent() {
		
		assertTrue("Alert dialog not present",server.isAlertPresent());
	}
	
	@Test
	public void test_handleAlertDialog() {
		String expected = "Is it OK";
		String actual = "";
		
		if(server.isAlertPresent()) {
			actual = server.getAlert();
		}
		
		assertEquals("Alert text does not matched expected",expected, actual);
	}
	
	@Test
	public void test_AlertDialogClosed() {
		
		assertTrue("Alert text does not matched expected",!server.isAlertPresent());
	}
	
	@AfterClass
	public static void shutdown() {
		nav.tearDown();
	}
}
