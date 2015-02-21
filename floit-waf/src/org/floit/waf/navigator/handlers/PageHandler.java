/**
 * (C)opyright Flowit.org
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.runner.PageHandler
 * $Author: garrett.muldowney $
 * $Date: 2012/03/30 15:05:16 $
 * $Revision: 1.8 $
 */
package org.floit.waf.navigator.handlers;

import static org.junit.Assert.assertTrue;

import java.nio.ShortBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.thoughtworks.selenium.SeleniumException;

import org.floit.waf.config.Action;
import org.floit.waf.config.BrowserAction;
import org.floit.waf.config.Input;
import org.floit.waf.config.Page;
import org.floit.waf.config.SearchTerms;
import org.floit.waf.navigator.TestNavigator;
import org.floit.waf.plugin.CustomHandlerInterface;
import org.floit.waf.plugin.CustomWaitForPageInterface;

/**
 * Handles <code>Page</code> objects.
 *
 * This class will run a <code>Page</code>, check the <code>SearchTerms</code>,
 * sending any required <code>Input</code> to the appropriate handler, and
 * navigate away from the <code>Page</code> using the specified
 * <code>Progress Action</code>.
 */
public class PageHandler extends AbstractHandler {

    /** A <code>Page</code> object. */
    private Page page;

    /** List of all possible <code>Input</code>s. */
    private List<Input> allOptions = new LinkedList<Input>();

    /** The logger uses log4j to configure and control the logging output. */
    private static final Logger CAT = Logger.getLogger(PageHandler.class);

    /** Determines if the log4j debug mode is on or off. */
    private static final boolean DEBUG = CAT.isDebugEnabled();

    /** Set to true when the test fails due to Selenium exceptions. */
    protected boolean testFailed = false;

    /** Set to true when there is a browser action to be performed so
     *  that multiple progress actions are not performed */
    protected boolean browserActionEnabled = false;
    
    /** Current pop-up window handle */
	private String windowHandle = null;

    /**
     * Creates the <code>Page</code> object, and adds the <code>Input</code>
     * array it contains.
     *
     * @param page
     *            The <code>Page</code> instance you want to create.
     * @param navigator
     *            The Selenium instance is obtained from the
     *            BaseGUITestNavigator instance and used to perform actions on
     *            the <code>Page</code>.
     */
    public PageHandler(Page page, TestNavigator navigator)
            throws Exception {
        super(navigator);
        initilise(page);
    }

    /**
     * Creates the <code>Page</code> object, and adds the <code>Input</code>
     * array it contains.
     *
     * @param page
     *            The <code>Page</code> instance you want to create.
     * @param selenium
     *            The Selenium instance to be used to perform actions on the
     *            <code>Page</code>.
     */
    private void initilise(Page page) throws Exception {
        this.page = page;
        // this.selenium = selenium;

        // Set the inputs
        Input[] inputArray = page.getInput();

        for (int i = 0; i < inputArray.length; i++) {
            // Add the input to all inputs
            allOptions.add(inputArray[i]);
        }
    }

    /**
     * Clears all of the Inputs from the <code>Page</code> object.
     */
    public void clearInputs() {
        allOptions.clear();
    }

    /**
     * This method will navigate the given <code>Page</code>. Each required
     * <code>Input</code> will be processed and executed.
     *
     * @param actionIndex
     *            The <code>Progress Action</code> to take to leave the current
     *            <code>Page</code>. Use -1 to disable the progress action.
     */
    public void go(int actionIndex) throws Exception {
    	
    	// Add page token
    	UUID uid = java.util.UUID.randomUUID();
    	StringBuffer pageToken = new StringBuffer();
    	pageToken.append("PageLoadMonitor-");
    	pageToken.append(uid);
    	boolean pagetokenAdded = false;
    	boolean isTranisation = false;
    	
        // Handle the custom page wait
        if(page.getPageWaitHandler() != null && page.getPageWaitHandler().length() > 0) {
        	/* Register a new page wait handler */
        	Class<?> PageWaitHandler = Class.forName(page.getPageWaitHandler());
        	CustomWaitForPageInterface pageWait =
        		(CustomWaitForPageInterface) PageWaitHandler.newInstance();

            // Pass a reference to the test server and execute the page handler
        	pageWait.setServer(navigator.getTestServer());
        	pageWait.handlePage(page);
        }   	
    	
    	// Wait for document model to be ready
    	try {
			String readScript = "return document.readyState;";
			String readyState = ((String)navigator.getTestServer().getEval(readScript)).toString();
			CAT.info("Ready State of current page " + readyState);
		} catch (Exception e) {
			CAT.error("Error trying to obtain the current pages ready state");
		}
    	   	
    	// Check if this page is a pop-up window
    	boolean isPopupWindow = page.isIsPopupWindow();
    	if(isPopupWindow) {
    		windowHandle = navigator.getTestServer().setPopUpWindowDriver(page.getPopupWindowLocator());
    	}
        
		try {
			String script = "document.getElementsByTagName('body')[0].appendChild(document.createElement('div')).setAttribute('id', '"
					+ pageToken.toString() + "');";
			navigator.getTestServer().getEval(script);
			pagetokenAdded = true;
		} catch (Exception e) {
			CAT.error("Failed to add page token " + pageToken.toString(), e);
		}        
    	
        // Check that all page elements have loaded
        if (page.getPageElements() != null
                && page.getPageElements().getPageElementCount() > 0) {
            waitForPageElements(page.getPageElements());
        }
        
        // Snapshot the current page after loading
        savePage();

        // Check we're on the right page with the search terms
        try {
            CAT.info("Checking SearchTerm text in page.");
            checkText(page.getSearchTerms());
        } catch (SeleniumException e) {
            throwSeleniumException(e);
        } catch (NullPointerException nullex) {
            // There are no SearchTerms defined in the config.
            CAT.info("No SearchTerms to check.");
        }

        //Perform any enabled browser actions first, since they make
        //any inputs useless
        browserActionEnabled = false;
    	//check if there is a browser action to be performed
        if(page.getProgress()!= null && page.getProgress().getBrowserActionCount() > 0){
	    	
			// TODO: Add logging for browser actions. Need to save a snapshot
			// for each page transition for due forward/back transitions
        	
        	for(int i=0; i< page.getProgress().getBrowserActionCount(); i++){
	    		BrowserAction bAction = page.getProgress().getBrowserAction()[i];
	    		if(bAction.getEnabled() == true){
	    			// KC To many save page requests, remove this one as page is saved on load
	    			// savePage();
	    			browserActionEnabled = true;
	    			//disable the browser action so it doesn't loop on the page
	    			page.getProgress().getBrowserAction()[i].setEnabled(false);
	    			if(bAction.getType().toString() == "Back"){
	    				//TODO: implement browser Back
	    				//navigator.getTestServer().goBack();
	    			}
	    			if(bAction.getType().toString() == "Refresh"){
	    				//TODO: implement browser Refresh
	    				//navigator.getTestServer().refresh();
	    			}
	    			//navigator.getTestServer().waitForPageToLoad(navigator.getPauseTime());
	    			break;
	    		}
	    	}
    	}
        
        //if there are no browser actions then proceed with the page execution
        if(browserActionEnabled == false){
	        // PRE-process the page if the PreProcess attribute is present and set to TRUE
	        if(page.hasPreProcess() && page.getPreProcess()) {
	            // check if the page preprocessor class is provided
	        	if(page.getPreProcessor() != null) {
		            String preProcessors[] = page.getPreProcessor().split(" ");
		            //perform each of the pre-processes of the page
		            for(int i=0; i < preProcessors.length; i++){
		        		try {
				        	/* Register a new page preprocessor */
			            	Class<?> PagePreProcessor = Class.forName(preProcessors[i]);
				        	PageHandlerPreProcessorInterface preProcessor =
				        		(PageHandlerPreProcessorInterface) PagePreProcessor.newInstance();

			                // Pass a reference to the test server and execute the PREPROCESSING
				        	preProcessor.setServer(navigator.getTestServer());
				        	preProcessor.preProcessPage(page);

				        	CAT.info("PREPROCESSING of page index '" + page.getName() + "' was successful.");

			            } catch(Exception e) {
			            	// catalogue the error with its stack trace
			            	CAT.error(
			            			"There was a problem with the specified page preprocessor class: '" +
			            			preProcessors[i] + "' for following '" + page.getName() + "'", e);
			            }
		            }
			    }
			}

	        // If the input has a classref, then use that, otherwise use
	        // the default input handler.
	        CustomHandlerInterface in;

	        // Iterate through the enabled inputs and pass them to their handler
	        for (Iterator<Input> iter = allOptions.iterator(); iter.hasNext();) {
	            Input tmp = (Input) iter.next();

	            // If the input is enabled
	            if (tmp.getRequired()) {
	                if (DEBUG) {
	                    CAT.debug("Running input #" + tmp.getName() + ".");
	                }

	                // Get the class reference
	                if (tmp.getClassRef() != null) {
	                    if (DEBUG) {
	                        CAT.debug("Creating custom class: " + tmp.getClassRef()
	                                + ".");
	                    }

	                    /* Register a new Custom Handler */
	                    Class<?> CustomHandler = Class.forName(tmp.getClassRef());
	                    in = (CustomHandlerInterface) CustomHandler.newInstance();

	                } else {
	                    /* Register a new InputHandler */
	                    in = new InputHandler(navigator);
	                }

	                // Pass a reference to the test server and run the delegator
	                in.setServer(navigator.getTestServer());

	                try {
	                    in.handleInput(tmp);
	                    if(pagetokenAdded) {
							// Check if the page token is present
							if (!navigator.getTestServer().isElementPresent("id=" + pageToken.toString())) {
								CAT.debug("Page looks to have changed unable to locate " + pageToken.toString());
								isTranisation = true;
							}
							// TODO Handle custom input wait handler if one exists
	                    }
	                } catch (Exception e) {
	                    throwSeleniumException(e);
	                }
	            }
	        }

	        //if the progress action of the page has been enabled
	        if(actionIndex != -1)
	        {
		        // Get the actions available
		        Action[] actionsAvail = page.getProgress().getAction();
		        Action currAction = actionsAvail[actionIndex];
		        
		        // Save the page before submitting the request so that the input values
		        // get recorded.
		        // KC: To many save page requests, on save on page load only
		        //savePage();	        

		        if (DEBUG) {
		            CAT.debug("Using progress action - Name [" + currAction.getName() + "] Index [" + currAction.getIndex() + "]");
		        }

		        try {
		            // Override property defined spinner with per action one if defined
		            String spinner = currAction.getSpinnerURL();
		            if (spinner != null && !spinner.equals("")) {
		                navigator.getTestServer().navigate(currAction.getLocator(),
		                        spinner);
		            } else {
		                navigator.getTestServer().navigate(currAction.getLocator());
		            }
		        } catch (Exception e) {
		            throwSeleniumException(e);
		        }
				// TODO: This should not really be done here. What should happen
				// is that a page runner cannot execute a page action. This can
				// only happen through a flow runner as the target page should
				// be included in the flow. The target page handler should then
				// be responsible for taking a snapshot of the loaded page.
		        
		        // Save new page after transition
		        
		        // KC: To many save page requests, on save on page load only
		        //savePage();	
	        }
	        else
	        {
				// TODO: This assumes that the page has loaded and that all lazy
				// loaded elements have been complete. This should be handled by
				// an input wait handler. Otherwise the page shapshot will be
				// taken while the page is still loading.
	        	if(isTranisation) {
		        	//save the page after the inputs forced a page transition
			        // KC: To many save page requests, on save on page load only
			        //savePage();	
	        	}
	        }
	        
	    	if(isPopupWindow) {
		        // Reset to main window if pop-up was selected
		        navigator.getTestServer().switchToMainWindow();	 
	    	}
        }
    }

    /**
     * Checks the text is present in the <code>Page</code>.
     *
     * @param searchTerms
     *            The text to be checked.
     */
    protected void checkText(SearchTerms searchTerms) {

        // Get the search terms
        String[] termsArray = searchTerms.getItem();

        for (int i = 0; i < termsArray.length; i++) {
            String errorString = "Page #" + page.getName()
                    + "; Text not found: " + termsArray[i];
            if (navigator.getTestServer().isTextPresent(termsArray[i])) {
                assertTrue(errorString, navigator.getTestServer()
                        .isTextPresent(termsArray[i]));
            } else {

                CAT.error(errorString);
                this.testFailed = true;
                assertTrue(errorString, false);
            }
        }
    }

    
	// TODO: Get rid of this method and create a PageHandlerException class to
	// cater for PageHandler exceptions.
	/**
	 * Sets testFailed true to indicate a selenium exception has occurred, and
	 * fails the test.
	 * 
	 * @param e
	 *            The Selenium Exception which has occurred.
	 */
    protected void throwSeleniumException(Exception e) throws Exception {
        this.testFailed = true;
        // Reset to main window if pop-up was selected
        navigator.getTestServer().switchToMainWindow();

        // Save the current page if enabled
        savePage();

        CAT.error("Page Name [" + page.getName() + "]; Selenium Exception: " + e);
        
        throw e;
    }

    /**
     * Saves the current page if the feature is enabled.
     */
    private void savePage() {
    	// Check the current window is displayed
    	if(windowHandle!=null) {
    		if(!navigator.getTestServer().isWindowVisible(windowHandle)) {
    			CAT.info("Cannot save page as the window has been closed");
    			return;
    		}
    	}
    	
        if (navigator.getSaveEveryPage()) {
            navigator.savePage(page);
        }
    }
}
