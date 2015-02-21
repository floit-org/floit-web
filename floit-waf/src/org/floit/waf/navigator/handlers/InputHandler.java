/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.InputHandler
 * $Author: kieran $
 * $Date: 2010/10/12 07:43:29 $
 * $Revision: 1.6 $
 */
package org.floit.waf.navigator.handlers;

import org.apache.log4j.Logger;

import com.thoughtworks.selenium.Wait;

import org.floit.waf.config.Input;
import org.floit.waf.exceptions.InputHandlerException;
import org.floit.waf.navigator.TestNavigator;
import org.floit.waf.navigator.TestServerApi;
import org.floit.waf.plugin.CustomHandlerInterface;

/**
 * Handles standard <code>Input</code> HTML elements.
 * <p>
 * This class performs standard interaction between the test framework and HTML
 * input fields. The predefined <code>Input</code> types are as follows:
 * <ul>
 * <li><b>input:</b> Handles a standard input box (hidden and visible).</li>
 * <li><b>radio:</b> Handles radio buttons.</li>
 * <li><b>checkbox:</b> Handles check boxes.</li>
 * <li><b>click:</b> Handles click functionality, e.g. click a link</li>
 * <li><b>ajax:</b> Handles auto type input boxes.</li>
 * <li><b>select:</b> Handles select boxes.</li>
 * <li><b>multi-select:</b> Handles select boxes with multi-select options.</li>
 * <li><b>select-link:</b> Handles select boxes whose elements are also links.</li>
 * </ul>
 * </li> </ul>
 * <p>
 */

public class InputHandler extends AbstractHandler implements
        CustomHandlerInterface {

    /**
     * Log4J logger instance for class AbstractHandler.
     */
    private static final Logger CAT = Logger.getLogger(AbstractHandler.class);
    /**
     * Log4J debug setting for class {AbstractHandler.
     */
    private static final boolean DEBUG = CAT.isDebugEnabled();

    /**
     * Constructs a new InputHandler.
     */
    public InputHandler(TestNavigator navigator) {
        super(navigator);
    }

    /**
     * <p>
     * Checks the type of the <code>Input</code>, and handles it appropriately.
     * </p>
     *
     * <p>
     * <b>Example:</b>
     * </p>
     * <code>
     * <p><i>if</i> input.getType() = "click"</p><p>
     * <i>then</i> handleClick(input)</p></code>
     *
     * @param input
     * @throws Exception 
     */
    public void handleInput(Input input) throws Exception {

		try {
			if (input.getType() == null) {
				throw new InputHandlerException(input, InputHandlerException.ERR_TYPE);
			}

			if (input.getType().toString().equals("input")) {
				if (input.getValue() == null) {
					throw new InputHandlerException(input, InputHandlerException.ERR_VALUE);
				}
				/** Basic input */
				handleTextInput(input);

			} else if (input.getType().toString().equals("radio") || input.getType().toString().equals("checkbox")) {
				/** Radio buttons */
				handleRadioCheckbox(input);

			} else if (input.getType().toString().equals("click")) {
				/** Click */
				handleClick(input);

			} else if (input.getType().toString().equals("select")) {
				/** Selects */
				handleSelect(input);

			} else if (input.getType().toString().equals("multi-select")) {
				/** Multi-select */
				handleMultiSelect(input);

			} else if (input.getType().toString().equals("select-link")) {
				/** A select which is also a link */
				handleSelectLink(input);
			} else if (input.getType().toString().equals("file")) {
				/** File upload dialog */
				handlefile(input);
			}

		} catch (Exception e) {
			if(e instanceof InputHandlerException) {
				throw e;
			} else {
				CAT.error("Error handling input - " + printInput(input));
				throw e;
			}
		}
    	
        // Check for dependent page elements
        if (input.getPageElement() != null) {
            // Check for a single element Id
            int waitTime = pauseTime;
            if (input.hasWaitFor()) {
                waitTime = input.getWaitFor();
            }       	
    		navigator.getTestServer()
                .waitForPageElement(input.getPageElement(), waitTime);

        } else if (input.getPageElements() != null
                && input.getPageElements().getPageElementCount() > 0) {
            // Check for a collection of elements
            waitForPageElements(input.getPageElements());
        }
    }

    /**
     * Handles basic text input.
     *
     * @param input
     *            the Input instance
     */
    protected void handleTextInput(Input input) {

        if (!input.getValue().equals("")) {
            // If the action has a value then type it
            navigator.getTestServer().type(input.getLocator(), input.getValue());
        }
    }

    /**
     * Handles radio buttons or check boxes.
     *
     * @param input
     *            the Input instance
     */
    protected void handleRadioCheckbox(Input input) {
    	
        navigator.getTestServer().click(input.getLocator());
        // Pause for one second to allow changes
        // take effect.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a standard clickable object.
     *
     * @param input
     *            the Input instance
     */
    protected void handleClick(Input input) {
        navigator.getTestServer().click(input.getLocator());
    }

	/**
	 * Handles select menus.
	 * 
	 * @param input
	 *            the Input instance
	 */
	protected void handleSelect(Input input) {
		if (!input.getValue().equals("")) {
			// If the action has a value then type it
			navigator.getTestServer().select(input.getLocator(), input.getValue());
		}
	}

	/**
	 * Handles multi-select menus.
	 * 
	 * @param input
	 *            the Input instance
	 */
	protected void handleMultiSelect(Input input) {
		if (!input.getValue().equals("")) {
			//TODO: Implement multi-select
			//navigator.getTestServer().removeAllSelections(input.getLocator());
			//navigator.getTestServer().addSelection(input.getLocator(), input.getValue());
		}
	}

    /**
     * Handles select menus whose options are also links.
     *
     * @param input
     *            the Input instance
     */
    protected void handleSelectLink(Input input) {
        if (!input.getValue().equals("")) {

            navigator.getTestServer().select("" + input.getLocator(),
                    "label=" + input.getValue());
            //navigator.getTestServer().tryWait();

            if (navigator.getTestServer().getLocation().contains(spinnerURL)) {
                final String currSpinner = spinnerURL;
                //TODO use Webdriver wait rather than Selenium wait
                new Wait() {
                    public boolean until() {
                        return !navigator.getTestServer().getLocation()
                                .contains(currSpinner);
                    }
                }.wait("Spinner has progressed");
                // Wait for new page to load
                //navigator.getTestServer().tryWait();
            }
        }
    }
    
	/**
	 * Handles file select dialog.
	 * 
	 * @param input
	 *            the Input instance
	 */
	protected void handlefile(Input input) {
		if (!input.getValue().equals("")) {
			navigator.getTestServer().uploadFile(input.getLocator(), input.getValue());
		} else {
			CAT.error("File path is not provided in Input value, [" + input.getName() + "]");
		}
	}  

    /**
     * Sets the <code>Selenium</code> object to interact with the
     * <code>Input</code> on the page.
     *
     * @param server
     *            The navigator.getApiWrapper() object.
     */
    public void setServer(TestServerApi server) {
        // this.navigator.getApiWrapper() = server;
    }
    
    /**
     * Create a string representation of a Page Input
     * @param input the input to print
     * @return string version of the input
     */
    private String printInput(Input input) {
    	if(input==null) {
    		return "The input is NULL.";
    	}
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append("Name [");
    	sb.append(input.getName());
    	sb.append("] Locator [");   	
    	sb.append(input.getLocator());
    	sb.append("] Type [");  
    	sb.append(input.getType());
    	sb.append("]");
    	if (input.getValue() != null && input.getValue().length() > 0) {
    		sb.append(" Value [");   
        	sb.append(input.getValue());
        	sb.append("]");   		
    	}
    	
    	return sb.toString();
    }
}
