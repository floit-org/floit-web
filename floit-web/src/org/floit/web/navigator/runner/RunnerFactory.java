/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.runner.RunnerFactory
 * $Author: eoin.costelloe $
 * $Date: 2010/09/17 07:51:56 $
 * $Revision: 1.5 $
 */
package org.floit.web.navigator.runner;

import org.apache.log4j.Logger;
import org.floit.web.config.Flow;
import org.floit.web.config.Page;
import org.floit.web.config.Pages;
import org.floit.web.navigator.TestNavigator;
import org.floit.web.navigator.handlers.ConfigHandler;

/**
 * This is a factory class which produces page and flow runners.
 * 
 * <p>
 * The function of this factory class is to produce {@link PageRunner} and
 * {@link FlowRunner} objects. These runner objects are then used within a test
 * to execute a single page or a flow. The runner factory is instantiated within
 * the {@link TestNavigator} class and is referenced via the instance variable
 * <code>runner</code>.
 * <p>
 * <b>NOTE:</b> In the examples below the <code>runner</code> is an instance of
 * the RunnerFactory which is provide by the {@link TestNavigator} class.
 * </p>
 * </p> <b>Example 1: Create a flow runner using the flow index</b>
 * <p>
 * <code>
 * // Create runner to execute flow index 1</br>
 * FlowRunner flowRunner = runner.createFlowRunner(1);
 * </code>
 * </p>
 * <b>Example 2: Create a flow runner using the name</b>
 * <p>
 * <code>
 * // Create runner to execute flow "Search Flight/Hotel"</br>
 * FlowRunner flowRunner = runner.createFlowRunner("Search Flight/Hotel");
 * </code>
 * </p>
 * <b>Example 3: Create a page runner using the page index</b>
 * <p>
 * <code>
 * // Create runner to execute flow index 2</br>
 * FlowRunner flowRunner = runner.createPageRunner(2);
 * </code>
 * </p>
 * <b>Example 4: Create a page runner using the page name</b>
 * <p>
 * <code>
 * // Create runner to execute flow "Package Search Results"</br>
 * FlowRunner flowRunner = runner.createFlowRunner("Package Search Results");
 * </code>
 * </p>
 */
public class RunnerFactory {
	/**
	 * Log4J logger instance for class FlowRunnerFactory.
	 */
	private static final Logger CAT = Logger.getLogger(RunnerFactory.class);
	/**
	 * Log4J debug setting for class {FlowRunnerFactory.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	/** Provides access to the test config */
	private ConfigHandler configHandler = null;

	/**
	 * Instance of the core of the test framework. Required to interact with GUI
	 * Test API.
	 */
	private TestNavigator navigator;

	/**
	 * Construct a instance of the RunnerFactory.
	 * 
	 * @param navigator
	 *            reference to core test framework runtime
	 */
	public RunnerFactory(TestNavigator navigator) {
		this.configHandler = navigator.getConfigHandler();
		this.navigator = navigator;
	}

	/**
	 * Creates a new {@link FlowRunner} for a page specified by page
	 * <code>name</code>.
	 * 
	 * @param name
	 *            the name of the flow to run
	 * @return a {@link FlowRunner} object
	 */
	public FlowRunner createFlowRunner(String name) {
		Flow flow = getFlowFromConfig(-1, name);

		// Clone the flow from the config.
		if (flow != null) {
			return new FlowRunner(addPagesToFlow(flow), navigator);
		}

		if (DEBUG) {
			CAT.error("Flow not found for name " + name);
		}
		return null;
	}

	/**
	 * Creates a new {@link PageRunner} for a page specified by page
	 * <code>name</code>.
	 * 
	 * @param name
	 *            the name of the page to run
	 * @param enableProgressAction
	 *            allows us to run a page without executing the progress action
	 * @return a {@link PageRunner} object
	 */
	public PageRunner createPageRunner(String name, boolean enableProgressAction) {
		PageWrapper page = copyPage(-1, name, null);
		return new PageRunner(page, navigator, enableProgressAction);
	}

	/**
	 * Creates a new {@link PageRunner} for a page specified by page
	 * <code>name</code>.
	 * 
	 * @param name
	 *            the name of the page to run
	 * @return a {@link PageRunner} object
	 */
	public PageRunner createPageRunner(String name) {
		Page page = getPageFromConfig(name, null);
		if (page == null) {
			CAT.error("Page name [" + name + "] not found");
			return null;
		}
		return createPageRunner(name, page.getProgressActionEnabled());
	}

	/**
	 * Creates a new {@link PageRunner} for a page specified by page
	 * <code>name</code> and <code>configId</code>. The configId is used to
	 * identfy pages that are imported from another config. If the page is
	 * located in the main config then use
	 * {@link RunnerFactory#createPageRunner(String)}.
	 * 
	 * @param name
	 *            the page name
	 * @param configId
	 *            the id of an imported config
	 * @param enableProgressAction
	 *            allows us to run a page without executing the progress action
	 * @return a {@link PageRunner} object
	 */
	public PageRunner createPageRunner(String name, String configId, boolean enableProgressAction) {
		PageWrapper page = copyPage(-1, name, configId);
		return new PageRunner(page, navigator, enableProgressAction);
	}

	/**
	 * Creates a new {@link PageRunner} for a page specified by page
	 * <code>name</code> and <code>configId</code>. The configId is used to
	 * identfy pages that are imported from another config. If the page is
	 * located in the main config then use
	 * {@link RunnerFactory#createPageRunner(String)}.
	 * 
	 * @param name
	 *            the page name
	 * @param configId
	 *            the id of an imported config
	 * @return a {@link PageRunner} object
	 */
	public PageRunner createPageRunner(String name, String configId) {
		Page page = getPageFromConfig(name, configId);
		if (page == null) {
			CAT.error("Page name [" + name + "] from config [" + configId + "] not found");
			return null;
		}
		return createPageRunner(name, configId, page.getProgressActionEnabled());
	}

	/**
	 * Retrieves a page from the config.
	 * 
	 * @param pageName
	 *            the target page name
	 * @param configId
	 *            the config where the page is defined
	 * 
	 * @return the request page from the config
	 */
	private Page getPageFromConfig(String pageName, String configId) {
		Page page = null;

		if (pageName != null) {
			if (configId != null) {
				page = configHandler.getPage(pageName, configId);
			} else {
				page = configHandler.getPage(pageName);
			}
		} else {
			CAT.error("Cannot locate page name [" + pageName +"]");
		}

		// Return page from config
		return page;
	}

	/**
	 * Makes a copy of an existing page in the config. Either the index or name
	 * should be used to identify a page.
	 * 
	 * @param pageIndex
	 *            the target page index
	 * @param pageName
	 *            the target page name
	 * @param configId
	 *            the config where the page is defined
	 * @return a copy of the requested page
	 */
	private PageWrapper copyPage(int pageIndex, String pageName, String configId) {

		Page configPage = getPageFromConfig(pageName, configId);
		return PageFactory.createPage(configPage);
	}

	/**
	 * Retrieves a flow from the {@link ConfigHandler}. The index or name can be
	 * used. If the flow index > 0 then the index is use. If the flow name is
	 * not null and index <= 0, then the name is used to retrieve the flow.
	 * 
	 * @param flowIndex
	 *            the index of the flow
	 * @param flowName
	 *            the name of the flow
	 * @return
	 */
	private Flow getFlowFromConfig(int flowIndex, String flowName) {

		Flow flow = null;

		if (flowIndex > 0) {
			flow = configHandler.getFlow(flowIndex);
		} else if (flowName != null) {
			flow = configHandler.getFlow(flowName);
		}

		return flow;
	}

	/**
	 * Makes a copy of a flow contained within the config.
	 * 
	 * @param configFlow
	 *            the flow to copy
	 * @return a copy of a flow from the config
	 */
	private Flow addPagesToFlow(Flow configFlow) {
		Flow flow = new Flow();

		if (configFlow != null) {
			// Collection of pages
			Pages pages = new Pages();
			// Add each page in to flow config to a new flow
			for (int i = 0; i < configFlow.getPages().getPageCount(); i++) {
				Page flowPage = configFlow.getPages().getPage(i);

				String name = flowPage.getName();
				String configId = flowPage.getIncludeId();
				// Retrieve the current page from config
				Page configPage = getPageFromConfig(name, configId);
				// Create a copy of the flow page
				PageWrapper newPage = PageFactory.createPage(configPage, flowPage);
				// Add to pages collection
				pages.addPage(newPage);
			}

			// Add pages collection to flow
			flow.setPages(pages);
			// Copy flow attributes from config
			if (configFlow.getDescription() != null)
				flow.setDescription(configFlow.getDescription());
			if (configFlow.getName() != null)
				flow.setName(configFlow.getName());
		}

		return flow;
	}

}
