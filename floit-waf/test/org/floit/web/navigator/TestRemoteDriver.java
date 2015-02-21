package org.floit.web.navigator;

import static org.junit.Assert.*;

import org.floit.web.navigator.TestNavigator;
import org.junit.Test;

public class TestRemoteDriver {

	@Test
	public void test_RemoteIEDriver() throws Exception {
		
		TestNavigator nav = new TestNavigator();
		nav.setUp();
		
		nav.tearDown();
		
		fail("Not yet implemented");
	}

}
