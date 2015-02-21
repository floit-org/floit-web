package org.floit.web.plugin;

import org.floit.web.config.Input;
import org.floit.web.navigator.TestNavigator;
import org.floit.web.navigator.TestServerApi;

/**
 * Interface which enables the use of special Wait handlers / plug-ins.
 * <p>
 * A WaitFor handler will block the framework until a particular event has
 * occurred. A typical implementation to to wait for the DOM to change as a
 * result of an Ajax call. These handlers are used in place of the
 * {@link org.floit.web.config.PageElement} when a PageElement is not provide
 * sufficient wait functionality. One limitation of PageElements is that they
 * only support situations when an element is added to the DOM as the result of
 * an Ajax call. They do not support wait on elements to be removed from the
 * DOM.
 * </p>
 * <p>
 * A example of configuring a {@link CustomWaitForInterface} handler is as
 * follows:<br/>
 * <br/>
 * <code>
 * &lt;Input Locator=".." Name=".." WaitForRef="com.mydomain.CustomWaitHandler" WaitFor="30"/>
 * </code>
 * </p>
 * <p>
 * The above config example illustrates how to configure a custom wait handler.
 * The WaitFor attribute is optional and allows for finer grain control over the
 * wait time. This wait time is the maximum amount of time before a timeout
 * exception occurs. The wait time can also be deriver programmatically from the
 * framework config through {@link TestNavigator#getPauseTime()}.
 * </p>
 * <p>
 * The WaitForRef and PageElement attributes are mutually exclusive. If a custom
 * wait handler is used then the PageElement is ignored. Therefore it is not
 * valid to have a PageElement when using the WaitForRef attribute.
 * </p>
 */
public interface CustomWaitForInterface {

	/**
	 * This is the method which will take the Input and perform the necessary
	 * wait action.
	 * 
	 * @param input
	 *            The Input instance to be used.
	 * @throws Exception
	 */
	void waitFor(Input input) throws Exception;

	/**
	 * This method provides access to the test server runtime so it can be used
	 * with the Input.
	 * 
	 * @param server
	 *            the GUI test server instance.
	 */
	void setServer(TestServerApi server);
}
