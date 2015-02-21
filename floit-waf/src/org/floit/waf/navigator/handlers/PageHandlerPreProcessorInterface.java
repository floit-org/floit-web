/**
 * (C)opyright Flowit.org 2011
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.plugin.navigator.handlers.PageHandlerPreProcessorInterface
 * Author: $Author: kieran $
 * Created: $Date: 2011/11/10 10:31:26 $
 */
package org.floit.waf.navigator.handlers;

import org.floit.waf.config.Page;
import org.floit.waf.navigator.TestServerApi;

/**
 * Interface which enables the use of special Page handlers preprocessor plug-ins.
 * <p>
 *
 * If there are special requirements needed for the page before its input elements
 * are processed by the page handler these requirements can be handled by a special
 * plug-in which must implement this interface.
 * </p>
 */
public interface PageHandlerPreProcessorInterface {

	/**
	 * This method will take a page and execute page handler preprocessor plug-in
     *
     * <p>
     * <b>Example:</b>
     * </p>
     * <p>
     * preProcessPage (Page page) { populateFields(select, 1); }
     * </p>
     *
     * @param page
     *            The Page instance to pre-process.
	 */
	void preProcessPage(Page page);

    /**
     * This method provides access to the test server runtime so it can be used
     * with the Page.
     *
     * @param server
     *            the GUI test server instance.
     */
    void setServer(TestServerApi server);
}
