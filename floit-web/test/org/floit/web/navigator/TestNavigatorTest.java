package org.floit.web.navigator;

import static org.junit.Assert.*;

import java.awt.Toolkit;

import org.openqa.selenium.Dimension;
import org.junit.Test;
import org.floit.web.navigator.TestNavigator;
import org.floit.web.navigator.runner.FlowRunner;

public class TestNavigatorTest {

	@Test
	public void testSetCustomProperty() throws Exception {

		String expected = "some test value";
		TestNavigator nav = new TestNavigator();
		nav.setUp();

		nav.setCustomProperty("test.temp.test", expected);

		String actual = nav.getCustomProperty("test.temp.test");

		nav.tearDown();

		assertEquals(expected, actual);
	}

	@Test
	public void testSetSystemProperty() throws Exception {

		String expected = "some test value";
		String key = "test.build.dir";
		System.setProperty(key, expected);

		TestNavigator nav = new TestNavigator();
		nav.setUp();
		String actual = nav.getTestProperty(key);
		nav.tearDown();

		assertEquals(expected, actual);

	}

	@Test
	public void testSetWindowDimensions1280x768() throws Exception {

		String expected = "1280,768";
		String key = "test.gui.browser.window.size";
		System.setProperty(key, expected);
		System.setProperty("test.gui.browser.maximise", "" + false);

		TestNavigator nav = new TestNavigator();
		nav.setUp();
		Dimension size = nav.getTestServer().getDriver().manage().window().getSize();
		int x = size.getWidth();
		int y = size.getHeight();

		String actual = x + "," + y;
		assertEquals(expected, actual);

		nav.tearDown();
	}

	@Test
	public void testSetWindowDimensions800x600() throws Exception {

		String expected = "800,600";
		String key = "test.gui.browser.window.size";
		System.setProperty(key, expected);
		System.setProperty("test.gui.browser.maximise", "" + false);

		TestNavigator nav = new TestNavigator();
		nav.setUp();
		Dimension size = nav.getTestServer().getDriver().manage().window().getSize();
		int x = size.getWidth();
		int y = size.getHeight();

		String actual = x + "," + y;
		assertEquals(expected, actual);

		nav.tearDown();
	}

	@Test
	public void testSetWindowDimensionsMaximise() throws Exception {

		System.setProperty("test.gui.browser.maximise", "" + true);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		java.awt.Dimension dim = toolkit.getScreenSize();
		String expected = dim.width + "," + dim.height;

		TestNavigator nav = new TestNavigator();
		nav.setUp();
		Dimension size = nav.getTestServer().getDriver().manage().window().getSize();
		int x = size.getWidth();
		int y = size.getHeight();

		String actual = x + "," + y;
		
		nav.tearDown();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testRunFlow() throws Exception {
		
		TestNavigator nav = new TestNavigator();
		nav.setUp();
		
		FlowRunner runner = nav.runner.createFlowRunner("Search for Opportunity");
		runner.run();
		
		nav.tearDown();		
		
		assertTrue(true);
	}
}
