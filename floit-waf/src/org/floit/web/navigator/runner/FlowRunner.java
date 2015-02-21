/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.runner.FlowRunner
 * $Author: garrett.muldowney $
 * $Date: 2012/03/30 15:06:12 $
 * $Revision: 1.9 $
 */
package org.floit.web.navigator.runner;

import org.apache.log4j.Logger;
import org.floit.web.config.Flow;
import org.floit.web.config.Input;
import org.floit.web.config.PageElements;
import org.floit.web.config.Progress;
import org.floit.web.config.SearchTerms;
import org.floit.web.navigator.TestNavigator;

/**
 * Executes a flow as defined in the test config.
 * 
 * <p>
 * Each flow in the config contains at least one configured page. The
 * <code>FlowRunner</code> will execute each page in the flow. Each page may
 * contain a list of {@link Input} types, {@link SearchTerms}, {@link Progress}
 * actions and {@link PageElements}. The <code>FlowRunner</code> makes a copy of
 * flow from the config. There are a number of methods available that allow
 * modification of inputs and progress actions (see examples below). The
 * <code>FlowRunner</code> is created through the {@link RunnerFactory}.
 * </p>
 * <b><u>Create a FlowRunner</u></b>
 * <p>
 * <code>// Create runner to execute flow index 1<br/>
 * FlowRunner flowRunner = runner.createFlowRunner(1); </code>
 * </p>
 * <b><u>Modifying Inputs</u></b>
 * <p>
 * Each page will have one or more defined inputs. The flow defines which of the
 * pages inputs to execute. The flow normally contains a subset of the available
 * inputs defined for a page. By default page inputs that are defined as
 * <code>Required</code> (or <u>enabled</u>) will automatically get executed
 * during a flow. The <code>FlowRunner</code> can override the
 * <code>Required</code> value by effectively enabling or disabling a input.
 * </p>
 * <p>
 * <b>Example 1: Enable an input by name</b><br/>
 * Enables input with name "Child guests (hotel / f+h)" on the 3rd page in the
 * flow.
 * <p>
 * <code>
 * flowRunner.enableInput(3, "Child guests (hotel / f+h)");
 * </code>
 * </p>
 * <p>
 * <b>Example 2: Disable an input by index</b><br/>
 * Disables input with index 1 on the 3rd page in the flow.
 * <p>
 * <code>
 * flowRunner.disableInput(3, 1);
 * </code>
 * </p>
 * <p>
 * The value of inputs can also be overridden. In order for an input to executed
 * it must be either defined in the page as "Required", set in the flow, or
 * enabled through <code>FlowRunner</code> as described above.
 * </p>
 * <b>Example 3: Setting an input value by input name</b><br/>
 * Sets the input with name "Child guests (hotel / f+h)" on the 2nd page in the
 * flow to a value of 4.
 * <p>
 * flowRunner.setInputValue(2, "Child guests (hotel / f+h)", "4");
 * </p>
 * <b>Example 4: Setting an input value by input index</b><br/>
 * Sets the input with index 3 on the 1st page in the flow to a value of 2.
 * <p>
 * flowRunner.setInputValue(1, 3, "2");
 * </p>
 * <b><u>Set Progress Action</u></b>
 * <p>
 * The default progress action on any page in a flow can be changed. The default
 * progress action is determined from the config when the flow is created. The
 * pages default progress action can be overridden in the flow config as well.
 * Before the flow is run the progress action for a page can be change through
 * code as follows:
 * </p>
 * <b>Example 1: Set Progress Action by Index</b><br/>
 * Sets the progress action on the 3rd page within the flow to an action with
 * index 2. Note the action must exists in the pages config.<br/>
 * <br/>
 * <code>
 * flowRunner.setProgressAction(3,2)
 * </code>
 * <p>
 * See the Javadoc associated with each method for more details.
 * </p>
 */
public class FlowRunner extends BaseRunner {

	/**
	 * Log4J logger instance for class FlowRunner.
	 */
	private static final Logger CAT = Logger.getLogger(FlowRunner.class);
	/**
	 * Log4J debug setting for class {FlowRunner.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	/** The flow to execute */
	private Flow flow = null;

	/** Constant representing the first page number */
	private static final int START_PAGE = 1;

	/**
	 * Constructs a new FlowRunner instance. The {@link RunnerFactory}
	 * instantiates a <code>FlowRunner</code> by passing an copy of a flow from
	 * the config and a navigator instance. The navigator is used to execute
	 * each page in the flow.
	 * 
	 * @param flow
	 *            the flow to execute
	 * @param navigator
	 *            the test runtime which executes a flow
	 */
	public FlowRunner(Flow flow, TestNavigator navigator) {
		super(navigator);
		this.flow = flow;
	}

	/**
	 * Runs the current <code>Flow</code> from start to finish. Each
	 * <code>Page</code> is executed in <i><b>sequence</b></i> as defined in the
	 * flow set. The key here is the word <i>sequence</i>, the page index does
	 * <u>NOT</u> determine the order in which a flow is executed. It is merely
	 * an identifier for a page within a flow.
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * <p>
	 * <code>FlowRunner flowRunner = runner.createFlowRunner(1);<br/>
	 * flowRunner.run();
	 * </code>
	 * <ul>
	 * <li>
	 * Runs the <code>Flow</code> with index 1, where 'runner' is an instance of
	 * {@link RunnerFactory}.</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws Exception
	 *             any exception that occurs
	 */
	public void run() throws Exception {
		int startPage = START_PAGE;
		int endPage = flow.getPages().getPageCount();
		runFlowSequence(startPage, endPage);

	}

	/**
	 * Runs the current <code>Flow</code> between two specific pages. Each
	 * <code>Page</code> is executed in <i><b>sequence</b></i>, between
	 * <code>startPage</code> and <code>endPage</code> as defined in the flow
	 * set. The key here is the word <i>sequence</i>, the page index does
	 * <u>NOT</u> determine the order in which a flow is executed. It is merely
	 * an identifier for a within a flow.
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * <p>
	 * <code>FlowRunner flowRunner = runner.createFlowRunner(4);<br/>
	 * flowRunner.runFlow(2,5);
	 * </code>
	 * <ul>
	 * <li>
	 * Runs the <code>Flow</code> with index 4, where 'runner' is an instance of
	 * {@link RunnerFactory}.</li>
	 * <li>Execution of the flow will start at page 2 and end at page 5.</li>
	 * <li>The default progress action will be fired for each page in the flow
	 * unless its overridden in the <code>FlowRunner</code> before executing the
	 * running the flow.</li>
	 * <li>The page numbers are 1 based e.g. <code>> 0</code></li>
	 * <li>If <code>startPage=x</code> and <code>endPage=x</code> then page
	 * <code>x</code> in the flow is executed</li>
	 * </ul>
	 * </p>
	 * 
	 * @param startPage
	 *            the sequence number of the start page. The
	 *            <code>startPage</code> > 0.
	 * @param endPage
	 *            the sequence number of the end page. The <code>endPage</code>
	 *            >= <code>startPage</code>.
	 * @throws Exception
	 *             any exception that occurs
	 */
	public void runFlow(int startPage, int endPage) throws Exception {
		runFlowSequence(startPage, endPage);
	}

	/**
	 * Runs the current <code>Flow</code> between two specific pages. Each
	 * <code>Page</code> is executed in <i><b>sequence</b></i>, between
	 * <code>startPage</code> and <code>endPage</code> as defined in the flow
	 * set. The key here is the word <i>sequence</i>, the page index does
	 * <u>NOT</u> determine the order in which a flow is executed. It is merely
	 * an identifier for a page within a flow.
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * <p>
	 * <code>FlowRunner flowRunner = runner.createFlowRunner(4);<br/>
	 * flowRunner.runFlowFrom(2);
	 * </code>
	 * <ul>
	 * <li>
	 * Runs the <code>Flow</code> with index 4, where 'runner' is an instance of
	 * {@link RunnerFactory}.</li>
	 * <li>Execution of the flow will start at page 2 and stop at the end of the
	 * flow.</li>
	 * <li>The default progress action will be fired for each page in the flow
	 * unless its overridden in the <code>FlowRunner</code> before executing the
	 * running the flow.</li>
	 * <li>The page numbers are 1 based e.g. <code>> 0</code></li>
	 * <li>If <code>startPage=x</code> where page <code>x</code> is the last
	 * page defined in the flow, then only one page is executed</li>
	 * </ul>
	 * </p>
	 * 
	 * @param startPage
	 *            the sequence number of the start page. The
	 *            <code>startPage</code> > 0.
	 * @throws Exception
	 *             any exception that occurs
	 */
	public void runFlowFrom(int startPage) throws Exception {
		int endPage = flow.getPages().getPageCount();
		runFlowSequence(startPage, endPage);
	}

	/**
	 * Runs the current <code>Flow</code> between two specific pages. Each
	 * <code>Page</code> is executed in <i><b>sequence</b></i>, between
	 * <code>startPage</code> and <code>endPage</code> as defined in the flow
	 * set. The key here is the word <i>sequence</i>, the page index does
	 * <u>NOT</u> determine the order in which a flow is executed. It is merely
	 * an identifier for a page within a flow.
	 * <p>
	 * <b>Example:</b>
	 * </p>
	 * <p>
	 * <code>FlowRunner flowRunner = runner.createFlowRunner(4);<br/>
	 * flowRunner.runFlowTo(6);
	 * </code>
	 * <ul>
	 * <li>
	 * Runs the <code>Flow</code> with index 4, where 'runner' is an instance of
	 * {@link RunnerFactory}.</li>
	 * <li>Execution of the flow will start at the first page and stop at page 6
	 * of the flow.</li>
	 * <li>The default progress action will be fired for each page in the flow
	 * unless its overridden in the <code>FlowRunner</code> before executing the
	 * running the flow.</li>
	 * <li>The page numbers are 1 based e.g. <code>> 0</code></li>
	 * <li>If <code>endPage=x</code> where page <code>x</code> is the last page
	 * in the flow, then only one page is executed</li>
	 * </ul>
	 * </p>
	 * 
	 * @param endPage
	 *            the sequence number of the end page. The <code>endPage</code>
	 *            > 0 and <= the number of pages in the flow.
	 * @throws Exception
	 *             any exception that occurs
	 */
	public void runFlowTo(int endPage) throws Exception {
		int startPage = START_PAGE;
		runFlowSequence(startPage, endPage);
	}

	/**
	 * Runs a flow sequence given a start and end page.
	 * 
	 * @param startPage
	 *            the start of the sequence
	 * @param endPage
	 *            the end of the sequence
	 * @throws Exception
	 *             any exception that occurs
	 */
	private void runFlowSequence(int startPage, int endPage) throws Exception {

		if (startPage > 0 && endPage >= startPage) {
			// Normalise the start and end pages so that they are zero based.
			startPage = startPage - 1;
			endPage = endPage - 1;
		} else {
			throw new Exception("Invalid startPage or endpage specified");
		}

		for (int i = startPage; i <= endPage; i++) {
			PageWrapper current = (PageWrapper) flow.getPages().getPage(i);
			if (DEBUG)
				CAT.debug(printFlowPage(current, i + 1));
			try {
				runPage(current);
			} catch (Exception e) {
				StringBuffer sb = new StringBuffer();
				sb.append("Unable to run page sequence number ");
				sb.append(i + 1);
				sb.append(" from flow ");
				sb.append(flow.getName());
				sb.append(" [");
				sb.append(flow.getDescription());
				sb.append("]");
				CAT.error(sb.toString(), e);
				throw e;
			}
		}
	}

	/**
	 * Helper which returns the sequence number of the last page in the flow.
	 * 
	 * @return the sequence number of the last page in the flow
	 */
	public int getLastPage() {
		return flow.getPages().getPageCount();
	}

	/**
	 * Returns the value of the selected input on the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param inputName
	 *            the name of the input required
	 * @return the value of the input
	 */
	public String getInputValue(int pageSeq, String inputName) {
		return super.getInputValue(pageSeq + "", inputName);
	}

	/**
	 * Enables the selected input on the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param inputName
	 *            the name of the input required
	 */
	public void enableInput(int pageSeq, String inputName) throws Exception {
		super.enableInput(pageSeq + "", inputName);
	}

	/**
	 * Disables the selected input on the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param inputName
	 *            the name of the input required
	 */
	public void disableInput(int pageSeq, String inputName) {
		super.disableInput(pageSeq + "", inputName);
	}

	/**
	 * Gets boolean value showing whether the pre-processing of the selected
	 * page is enabled or not.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @return boolean value of the page pre-processing being enabled or not
	 */
	public boolean getPreProcess(int pageSeq) {
		return super.getPreProcess(pageSeq + "");
	}

	/**
	 * Gets the name of page pre-processor class for the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @return the name of page pre-processor class
	 */
	public String getPreProcessor(int pageSeq) {
		return super.getPreProcessor(pageSeq + "");
	}
	
	/**
	 * Gets the name of page pre-processor class for the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @return the name of page pre-processor class
	 */
	public String getPageWaitHandler(int pageSeq) {
		return super.getPageWaitHandler(pageSeq + "");
	}

	/**
	 * Sets an input's value on the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param inputName
	 *            the name of the input within the page
	 * @param value
	 *            the value to set
	 */
	public void setInputValue(int pageSeq, String inputName, String value) throws Exception {
		super.setInputValue(pageSeq + "", inputName, value);
	}

	/**
	 * Sets the progress action on the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param actionName
	 *            the name of the progress action to use
	 */
	public void setProgressAction(int pageSeq, String actionName) {
		super.setProgressAction(pageSeq + "", actionName);
	}

	/**
	 * Enables/disables pre-processing for the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param enable
	 *            boolean value of the page pre-processing being enabled or not
	 */
	public void setPreProcess(int pageSeq, boolean enable) {
		super.setPreProcess(pageSeq + "", enable);
	}

	/**
	 * Sets the pre-processor class name for the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param classReference
	 *            the name of the class to be called for page pre-processing
	 */
	public void setPreProcessor(int pageSeq, String classReference) {
		super.setPreProcessor(pageSeq + "", classReference);
	}
	
	/**
	 * Sets the wait for page class name for the selected page.
	 * 
	 * @param pageSeq
	 *            the sequence number of the page in the flow
	 * @param classReference
	 *            the name of the class to be called to handle page waits
	 */
	public void setPageWaitHandler(int pageSeq, String classReference) {
		super.setPageWaitHandler(pageSeq + "", classReference);
	}	

	/**
	 * Returns a specific page in the flow. This method provides an
	 * implementation of the getPage method defined in the {@link BaseRunner}
	 * class. The getPage method behaves differently when deal with flows. The
	 * page identifier i.e. <code>pageSeq</code> is the position of the
	 * requested page within a <code>Flow</code>.</br>The pageSeq or page number
	 * is 1 based and can be determined from the <code>Flow</code>
	 * configuration.
	 * 
	 * @param pageSeq
	 *            the position of the requested page within the flow
	 */
	public PageWrapper getPage(int pageSeq) {
		// Retrieve page from flow normalising the page sequence number to 1
		// based
		return (PageWrapper) flow.getPages().getPage(--pageSeq);
	}

	/**
	 * {@inheritDoc} </br> Note this method has no function when dealing with
	 * flows. The same page may be included in a flow more than once and in this
	 * situation the page name is not a unique identifier.
	 */
	protected PageWrapper getPage(String page) {
		// Assume its an page sequence number
		int pageSeq = Integer.parseInt(page);
		return getPage(pageSeq);
	}

	/**
	 * Enables a browser action for a page.
	 * 
	 * @param action
	 *            the name of the action to be enabled
	 */
	public void enableBrowserAction(int pageNum, String action) {
		super.enableBrowserAction(pageNum + "", action);
	}
}
