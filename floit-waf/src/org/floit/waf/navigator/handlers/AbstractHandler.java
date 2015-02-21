/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.AbstractHandler
 * Author: $Author: kieran $
 * Created: $Date: 2010/06/29 11:14:15 $
 * Revision: $Revision: 1.4 $
 */
package org.floit.waf.navigator.handlers;

import org.apache.log4j.Logger;
import org.floit.waf.config.PageElement;
import org.floit.waf.config.PageElements;
import org.floit.waf.navigator.TestNavigator;

/**
 * Base handler class for from which all handlers are derived.
 *
 * <p>
 * The base handler from which all handlers are derived. It provides common wait
 * functionality to allow the framework to wait on elements to load before
 * proceeding
 * </p>
 */
public abstract class AbstractHandler {
    /**
     * Log4J logger instance for class AbstractHandler.
     */
    private static final Logger CAT = Logger.getLogger(AbstractHandler.class);
    /**
     * Log4J debug setting for class {AbstractHandler.
     */
    private static final boolean DEBUG = CAT.isDebugEnabled();

    /** the time to wait. */
    protected static final int pauseTime = TestNavigator.getPauseTime();

    /** The spinner url. */
    protected static final String spinnerURL = TestNavigator
            .getSpinner();

    /** the enter key. */
    protected static final String ENTER_KEY = "\\13";

    protected TestNavigator navigator = null;

    /**
     * Constructs a new Handler
     *
     * @param navigator
     *            the base navigator instance
     */
    public AbstractHandler(TestNavigator navigator) {
        this.navigator = navigator;
    }

    /**
     * Waits for elements on a page to load. If the elements exist then the
     * method will return. Timeout will result in a SeleniumTimeout exception
     * being thrown to the framework. <br/>
     * The timeout is the default pause time. The <code>WaitAfter</code> time in
     * the configuration can be used to override this timeout if required.
     *
     * @param elements
     *            a collection of PageElements to check
     * @throws Exception 
     */
    protected void waitForPageElements(PageElements elements) throws Exception {

        for (int i = 0; i < elements.getPageElementCount(); i++) {

            PageElement element = elements.getPageElement(i);
            int waitTime = pauseTime;
            if (element.hasWaitFor()) {
                waitTime = element.getWaitFor();
            }
			CAT.debug("Waiting for Page Element [" + element.getLocator() + "]");
            try {
				navigator.getTestServer().waitForPageElement(element.getLocator(),
				        waitTime);
			} catch (Exception e) {
				CAT.error("Page Element [" + element.getLocator() + "] not found");
				throw e;
			}
        }
    }
}
