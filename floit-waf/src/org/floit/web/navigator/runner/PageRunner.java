/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.runner.PageRunner
 * $Author: garrett.muldowney $
 * $Date: 2012/03/30 15:06:30 $
 * $Revision: 1.13 $
 */
package org.floit.web.navigator.runner;

import org.apache.log4j.Logger;
import org.floit.web.config.Action;
import org.floit.web.config.Input;
import org.floit.web.config.PageElements;
import org.floit.web.config.Progress;
import org.floit.web.config.SearchTerms;
import org.floit.web.navigator.TestNavigator;

/**
 * Executes a flow as defined in the test config.
 * 
 * <p>
 * The <code>PageRunner</code> will execute a specific page from the test
 * config. A page may contain a list of {@link Input} types, {@link SearchTerms}, {@link Progress} actions and {@link PageElements}. The
 * <code>PageRunner</code> makes a copy of page from the config. There are a
 * number of methods available that allow modification of inputs and progress
 * actions within a page (see examples below). The <code>PageRunner</code> is
 * created through the {@link RunnerFactory}.
 * </p>
 * <b><u>Create a PageRunner</u></b>
 * <p>
 * <code>// Create runner to execute page index 5<br/>
 * PageRunner pageRunner = runner.createPageRunner(5); </code>
 * </p>
 * <b><u>Modifying Inputs</u></b>
 * <p>
 * Each page will have one or more defined inputs. By default page inputs that
 * are defined as <code>Required</code> (or <u>enabled</u>) will automatically
 * get executed during a flow. The <code>PageRunner</code> can override the
 * <code>Required</code> value by effectively enabling or disabling a input.
 * </p>
 * <p>
 * <b>Example 1: Enable an input by name</b>
 * <p>
 * <code>
 * pageRunner.enableInput("Child guests (hotel / f+h)");
 * </code>
 * </p>
 * <p>
 * <b>Example 2: Disable an input by index</b>
 * <p>
 * <code>
 * pageRunner.disableInput("4");
 * </code>
 * </p>
 * <p>
 * The value of inputs can also be overridden. In order for an input to executed
 * it must be defined in the page as "Required", or enabled through
 * <code>PageRunner</code> as described above.
 * </p>
 * <b>Example 3: Setting an input value</b>
 * <p>
 * pageRunner.setInputValue("Child guests (hotel / f+h)", "2");
 * </p>
 * </p> <b><u>Running a Page</u></b>
 * <p>
 * <code>// Run the current page simply call the run method <br/>
 * pageRunner.run(); </code>
 * </p>
 * <p>
 * This will ensure that all the enabled inputs are executed. If a progress
 * action is defined this will also get executed.
 * </p>
 */
public class PageRunner extends BaseRunner {

	/**
	 * Log4J logger instance for class PageRunner.
	 */
	private static final Logger CAT = Logger.getLogger(PageRunner.class);
	/**
	 * Log4J debug setting for class PageRunner.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	/**
	 * A reference to a page that will be run
	 * 
	 */
	private PageWrapper page;

	/**
	 * describes if we should enable or disable a progress action for a selected
	 * page
	 */
	boolean enableProgressAction;

	/** The name of the page */
	private String name;

	/**
	 * Constructs a new PageRunner instance. The {@link RunnerFactory}
	 * instantiates a <code>pageRunner</code> by passing an copy of a page from
	 * the config and a navigator instance. The navigator is used to execute the
	 * page.
	 * 
	 * @param page
	 *            a page to execute
	 * @param navigator
	 *            the test runtime which executes a flow
	 * @param enableProgressAction
	 *            allows us to run a page without executing the progress action
	 */
	public PageRunner(PageWrapper page, TestNavigator navigator, boolean enableProgressAction) {
		super(navigator);
		this.page = page;
		this.enableProgressAction = enableProgressAction;
		this.name = page.getName();
	}

	/**
	 * Return a reference to an input.
	 * 
	 * @param name
	 *            the name of the input
	 * @return the requested input
	 */
	public Input getInput(String name) {
		return page.getInput(name);
	}

	/**
	 * Get an inputs ID as defined in the config.
	 * 
	 * @param name
	 *            the name of the input
	 * @return the ID value
	 */
	public String getInputID(String name) {

		return page.getInput(name).getLocator();
	}

	/**
	 * Override an inputs ID before execution. This can be used if the ID is
	 * dynamically created. Note the input still needs to be enabled or its
	 * value set for the input to be used.
	 * 
	 * @param name
	 *            the name of the input from the config
	 * @param id
	 *            the new ID value
	 */
	public void setInputID(String name, String id) {
		page.getInput(name).setLocator(id);
	}

	/**
	 * Retrieves the value of a specific {@link Input}. The input is selected by
	 * specifying the input name.
	 * 
	 * @param name
	 *            the name of the input
	 * @return the value of the input
	 */
	public String getInputValue(String name) {
		return getInputValue(this.name, name);
	}

	/**
	 * Enables a specific {@link Input}. The input is selected by specifying the
	 * input name.
	 * 
	 * @param name
	 *            the name of the input
	 */
	public void enableInput(String name) throws Exception {
		super.enableInput(this.name, name);
	}

	/**
	 * Disables a specific {@link Input}. The input is selected by specifying
	 * the input name.
	 * 
	 * @param name
	 *            the name of the input
	 */
	public void disableInput(String name) {
		super.disableInput(this.name, name);
	}

	/**
	 * Gets boolean value showing whether the pre-processing of the page is
	 * enabled or not.
	 * 
	 * @return boolean value of the page pre-processing being enabled or not
	 */
	public boolean getPreProcess() {
		return super.getPreProcess(this.name);
	}

	/**
	 * Gets the name of page pre-processor class for the page.
	 * 
	 * @return the name of page pre-processor class
	 */
	public String getPreProcessor() {
		return super.getPreProcessor(this.name);
	}

	/**
	 * Gets the name of page page wait class for the page.
	 * 
	 * @return the name of page wait class
	 */
	public String getPageWaitHandler() {
		return super.getPageWaitHandler(this.name);
	}

	/**
	 * Sets the value of a specific {@link Input}. The input is selected by
	 * specifying the input name.
	 * 
	 * @param name
	 *            the name of the input
	 * @param value
	 *            the value to set
	 */
	public void setInputValue(String name, String value) throws Exception {
		setInputValue(this.name, name, value);
	}

	/**
	 * Sets the default progress action for the page. The page will contain one
	 * or {@link Action} elements defined in the page. This methods calls the
	 * {@link Progress#setActionIndex(int)} method for the page and sets the
	 * default action. The action index must represent a valid action defined
	 * for the page.
	 * 
	 * @param index
	 *            the index of the action
	 */
	public void setProgressAction(int index) {
		setProgressAction(this.name, index);
	}

	/**
	 * Sets the default progress action for the page. The page will contain one
	 * or {@link Action} elements defined in the page. This methods calls the
	 * {@link Progress#setActionIndex(int)} method for the page and sets the
	 * default action. The action name must represent a valid action defined for
	 * the page.
	 * 
	 * @param name
	 *            the name of the action
	 */
	public void setProgressAction(String name) {
		setProgressAction(this.name, name);
	}

	/**
	 * Enables/disables pre-processing for the page.
	 * 
	 * @param enable
	 *            boolean value of the page pre-processing being enabled or not
	 */
	public void setPreProcess(boolean enable) {
		setPreProcess(this.name, enable);
	}

	/**
	 * Sets the pre-processor class name for the page.
	 * 
	 * @param classReference
	 *            the name of the class to be called for page pre-processing
	 */
	public void setPreProcessor(String classReference) {
		setPreProcessor(this.name, classReference);
	}
	
	/**
	 * Sets the page wait class name for the page.
	 * 
	 * @param classReference
	 *            the name of the class to be called for page wait
	 */
	public void setPageWaitHandler(String classReference) {
		setPageWaitHandler(this.name, classReference);
	}	

	/**
	 * Enables a browser action for a page
	 * 
	 * @param action
	 *            the name of the action to be enabled
	 */
	public void enableBrowserAction(String actionName) {
		enableBrowserAction(this.name, actionName);
	}

	/**
	 * Disables all browser actions for a page
	 * 
	 * @param action
	 *            the name of the action to be enabled
	 */
	public void disableBrowserActions() {
		disableBrowserActions(this.name);
	}

	/**
	 * Runs the current <code>Page</code>.
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * <p>
	 * <code>PageRunner pageRunner = runner.createFlowRunner(6);<br/>
	 * pageRunner.run();
	 * </code>
	 * <ul>
	 * <li>
	 * Runs the <code>Page</code> with index 6, where 'runner' is an instance of
	 * {@link RunnerFactory}.</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws Exception
	 *             any exception that occurs
	 */
	public void run() throws Exception {
		runPage(getPage(this.name), enableProgressAction);
	}

	/**
	 * Returns the associated page by page name. This method provides an
	 * implementation of the getPage method defined in the {@link BaseRunner}
	 * class.
	 * 
	 * @param name
	 *            the index of the requested page
	 */
	@Override
	protected PageWrapper getPage(String name) {
		return this.page;
	}

}
