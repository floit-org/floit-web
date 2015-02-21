/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.runner.BaseRunner
 * $Author: garrett.muldowney $
 * $Date: 2012/03/30 15:05:48 $
 * $Revision: 1.10 $
 */
package org.floit.waf.navigator.runner;

import org.apache.log4j.Logger;
import org.floit.waf.config.Action;
import org.floit.waf.config.Flow;
import org.floit.waf.config.Input;
import org.floit.waf.config.Page;
import org.floit.waf.config.PageElement;
import org.floit.waf.navigator.TestNavigator;
import org.floit.waf.navigator.handlers.PageHandler;

/**
 * Base test config runner.
 * 
 * <p>
 * This a base class that provides the core functions for test config runners. A
 * test config consist of 2 main components i.e Pages and Flows. A flow is a
 * collection of pages that when executed follows a logical sequence through a
 * web application. This class provides the basic functions necessary to execute
 * a page and control its inputs and progress actions.
 * </p>
 * <p>
 * Classes that extend this base class provide the necessary specifics to
 * execute/run a {@link Page} or {@link Flow} as required.
 * </p>
 */
public abstract class BaseRunner {
	/**
	 * Log4J logger instance for class BaseRunner.
	 */
	private static final Logger CAT = Logger.getLogger(BaseRunner.class);
	/**
	 * Log4J debug setting for class {BaseRunner.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	protected TestNavigator navigator;

	public BaseRunner(TestNavigator navigator) {
		this.navigator = navigator;
	}

	/**
	 * Provides access to a given page identified by Page name.
	 * 
	 * @param name
	 *            the name of the page as defined in the config. Can also be a
	 *            page sequence number isf used in a flow.
	 * @return the requested page
	 */
	protected abstract PageWrapper getPage(String name);

	/**
	 * Executes a page or flow. The implementing class will provide the
	 * necessary runner functionality.
	 * 
	 * @throws Exception
	 *             any exception that occurs
	 */
	abstract void run() throws Exception;

	/**
	 * Sets the input value for a specific page.
	 * 
	 * An input's value can be overridden for a specific page. Once the page has
	 * been identified, which is the responsibility of extending class, the
	 * {@link Input}, identified by name, can have its value set. <br/>
	 * The input is automatically enabled once the
	 * {@link #setInputValue(int, String, String)} is called.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param input
	 *            the name of the {@link Input}
	 * @return the input value
	 */
	protected void setInputValue(String page, String input, String value) throws Exception {
		if (value != null && input != null) {
			getPage(page).getInput(input).setValue(value);
			enableInput(page, input);
		} else
			CAT.error("Unable to set input value " + value);
	}

	/**
	 * Gets the input value for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param input
	 *            the name of the {@link Input}
	 * @return the input value
	 */
	protected String getInputValue(String page, String input) {
		if (input == null) {
			CAT.error("Unable to get value for input " + input);
			return null;
		}

		return getPage(page).getInput(input).getValue();
	}

	/**
	 * Gets boolean value showing whether the pre-processing of the page is
	 * enabled or not.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @return boolean value of the page pre-processing being enabled or not
	 */
	protected boolean getPreProcess(String page) {
		return getPage(page).getPreProcess();
	}

	/**
	 * Gets the name of page pre-processor class for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @return the name of page pre-processor class
	 */
	protected String getPreProcessor(String page) {
		return getPage(page).getPreProcessor();
	}
	
	/**
	 * Gets the name of page wait handler class for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @return the name of page handler class
	 */
	protected String getPageWaitHandler(String page) {
		return getPage(page).getPageWaitHandler();
	}

	/**
	 * Enables an input on a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param input
	 *            the name of the {@link Input}
	 */
	protected void enableInput(String page, String name) throws Exception {
		try {
			if (name != null)
				getPage(page).getInput(name).setRequired(true);
			else
				CAT.error("Unable to enable input " + name);
		} catch (Exception e) {
			CAT.error("Error setting Input [" + name + "] on page sequence number [" + page + "]");
			throw e;
		}
	}

	/**
	 * Disables an input on a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param input
	 *            the name of the {@link Input}
	 */
	protected void disableInput(String page, String name) {
		if (name != null)
			getPage(page).getInput(name).setRequired(false);
		else
			CAT.error("Unable to disable input " + name);
	}

	/**
	 * Sets the progress action for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param action
	 *            the index of the {@link Action}
	 */
	protected void setProgressAction(String page, int action) {
		getPage(page).getProgress().setActionIndex(action);
	}

	/**
	 * Sets the progress action for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param action
	 *            the name of the {@link Action}
	 */
	protected void setProgressAction(String page, String action) {
		getPage(page).setProgressAction(action);
	}

	/**
	 * Enables/disables pre-processing for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param enable
	 *            boolean value of the page pre-processing being enabled or not
	 */
	protected void setPreProcess(String page, boolean enable) {
		getPage(page).setPreProcess(enable);
	}

	/**
	 * Sets the pre-processor class name for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param classReference
	 *            the name of the class to be called for page pre-processing
	 */
	protected void setPreProcessor(String page, String classReference) {
		getPage(page).setPreProcessor(classReference);
	}
	
	/**
	 * Sets the wait for page class name for a specific page.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param classReference
	 *            the name of the class to be called to handle page waits
	 */
	protected void setPageWaitHandler(String page, String classReference) {
		getPage(page).setPageWaitHandler(classReference);
	}	

	/**
	 * Sets the enabled value of a specified Browser Action to true.
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 * @param actionName
	 *            the name of the browser action to be enabled
	 */
	protected void enableBrowserAction(String page, String actionName) {
		for (int i = 0; i < getPage(page).getProgress().getBrowserActionCount(); i++) {
			if (getPage(page).getProgress().getBrowserAction()[i].getName().compareTo(actionName) == 0) {
				getPage(page).getProgress().getBrowserAction()[i].setEnabled(true);
			}
		}
	}

	/**
	 * Sets the Enabled values of all browser actions on a page to false
	 * 
	 * @param page
	 *            a reference to a page. This can be the page index or page
	 *            sequence number within flow, depending on the implementation.
	 */
	protected void disableBrowserActions(String page) {
		for (int i = 0; i < getPage(page).getProgress().getBrowserActionCount(); i++) {
			getPage(page).getProgress().getBrowserAction()[i].setEnabled(false);
		}
	}

	/**
	 * Runs a specific page allowing a progress action.
	 * 
	 * @throws Exception
	 *             throws any error that occurs
	 */
	protected final void runPage(PageWrapper page, boolean enableProgressAction) throws Exception {
		PageHandler pageHandler = new PageHandler(page, navigator);

		// -1 is a disabled progress action
		int newActionIndex = -1;

		// if we enabled a progress action
		if (enableProgressAction) {
			// The action index to use should be set on Progress element
			newActionIndex = page.getProgress().getActionIndex();

			// normalise the action index so its zero based
			newActionIndex--;
		}

		// Run the page
		pageHandler.go(newActionIndex);
	}

	/**
	 * Runs a specific page.
	 * 
	 * @throws Exception
	 *             throws any error that occurs
	 */
	protected final void runPage(PageWrapper page) throws Exception {

		boolean enableProgressAction = true;
		if (page.hasProgressActionEnabled())
			enableProgressAction = page.getProgressActionEnabled();

		runPage(page, enableProgressAction);
	}

	/**
	 * Prints out the details of a page.
	 * 
	 * @param page
	 *            the source page
	 * @param seqNum
	 *            the sequence within a flow
	 * @return the page details as a string
	 */
	public String printFlowPage(PageWrapper page, int seqNum) {
		StringBuffer sb = new StringBuffer();
		if (seqNum == 0) {
			sb.append("\nPage");
		} else {
			sb.append("\nPage Sequence Num");
			sb.append(seqNum);
		}
		sb.append(" - Name [");
		sb.append(page.getName());
		sb.append("]\nInputs:\n");
		if (page.getInput().length > 0) {
			for (int i = 0; i < page.getInput().length; i++) {
				Input input = page.getInput()[i];
				sb.append("\tInput Sequence Num[");
				sb.append(i + 1);
				sb.append("] - Name [");
				sb.append(input.getName());
				sb.append("] - Value [");
				sb.append(input.getValue());
				sb.append("] - Enabled [");
				sb.append(input.getRequired());
				sb.append("]\n");
			}
		}
		sb.append("Search Terms:\n");
		if (page.getSearchTerms() != null && page.getSearchTerms().getItemCount() > 0) {
			for (int i = 0; i < page.getSearchTerms().getItemCount(); i++) {
				String term = page.getSearchTerms().getItem(i);
				sb.append("\tTerm Seguence Num[");
				sb.append(i + 1);
				sb.append("] - Value [");
				sb.append(term);
				sb.append("]\n");
			}
		}

		sb.append("Page Elements:\n");
		if (page.getPageElements() != null && page.getPageElements().getPageElementCount() > 0) {
			for (int i = 0; i < page.getPageElements().getPageElementCount(); i++) {
				PageElement element = page.getPageElements().getPageElement(i);
				sb.append("\tElement Seguence Num[");
				sb.append(i + 1);
				sb.append("] - ID [");
				sb.append(element.getLocator());
				sb.append("] - WaitFor [");
				sb.append(element.getWaitFor());
				sb.append("]\n");
			}
		}

		sb.append("Progress Actions:\n");
		if (page.getProgress() != null && page.getProgress().getActionCount() > 0) {
			for (int i = 0; i < page.getProgress().getActionCount(); i++) {
				Action action = page.getProgress().getAction(i);
				sb.append("\tAction Seguence Num[");
				sb.append(i + 1);
				sb.append("] - Index [");
				sb.append(action.getIndex());
				sb.append("] - Name [");
				sb.append(action.getName());
				sb.append("] - XPath [");
				sb.append(action.getLocator());
				sb.append("]\n");
			}
		}

		return sb.toString();
	}

}
