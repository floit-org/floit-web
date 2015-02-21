package org.floit.demo;

import java.io.File;

import org.floit.web.navigator.TestNavigator;

public class TestGUIFramework {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestNavigator navigator = new  TestNavigator();
		try {
			
			String testClass = (new TestGUIFramework()).getClass().getName();
			String testName = "main";
	        String path = testClass.replace('.', File.separatorChar);
	        String logDir = path + File.separator + testName;
	        
	        System.out.println("Log Dir is "+logDir);

	        /*
			// Startup
			navigator.setUp();
			
			Calendar c = Calendar.getInstance();
			// Create a date in mm/dd/yyyy format
			Date now = new Date();
			c.setTime(now);
			c.add(Calendar.MONTH, 3);
			
			long lDateTime = now.getTime();
			String oppName = "Auto Test Opp "+lDateTime;
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String createDate = sdf.format(c.getTime());
			
	        FlowRunner flowRunner = navigator.runner.createFlowRunner("Create new Opportunity");
	        flowRunner.setInputValue(4, "Opportunity Name", oppName);
	        flowRunner.setInputValue(4, "Close Date", createDate);
	        flowRunner.run();
	        
	        //PageRunner pageRunner = navigator.runner.createPageRunner("DM Assessment", false);
	        //pageRunner.run();
			
			// Shutdown
			navigator.tearDown();
			
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			navigator.tearDown();
		}
	}

}
