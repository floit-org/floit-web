/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.PageFactory
 * $Author: garrett.muldowney $
 * $Date: 2012/03/30 15:07:16 $
 * $Revision: 1.7 $
 */
package org.floit.web.navigator.runner;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.floit.web.config.Action;
import org.floit.web.config.BrowserAction;
import org.floit.web.config.Flow;
import org.floit.web.config.Input;
import org.floit.web.config.Page;
import org.floit.web.config.PageElement;
import org.floit.web.config.PageElements;
import org.floit.web.config.Progress;

/**
 * Factory that generates a deep copy of page object from the config.
 *
 * <p>
 * The page factory class will create a copy of {@link PageFactory} from the
 * config. A {@link PageFactory} object must be provided as parameter when
 * creating a copy.<br/>
 * There are two modes:
 * </p>
 * <b><u>Copy Page</u></b>
 * <p>
 * In this mode a single page from the config is copied exactly as defined in
 * the config.
 * </p>
 * </p> <b><u>Copy-Modify Page</u></b>
 * <p>
 * In this mode a single page from the {@link Flow} is copied exactly as
 * defined. If there are any inputs or actions defined in the flow for that
 * page, then they override the values configured for that page.
 * </p>
 *
 */
public class PageFactory {
    /**
     * Log4J logger instance for class PageFactory.
     */
    private static final Logger CAT = Logger.getLogger(PageFactory.class);
    /**
     * Log4J debug setting for class {PageFactory.
     */
    private static final boolean DEBUG = CAT.isDebugEnabled();

    /**
     * Factory method which creates a copy of Page to be used by the Flow. The
     * created Page contains only those Inputs specified in the Flow and the
     * Inputs in from the Page template that are identified as mandatory e.g
     * <code>Required=true</code>.
     *
     * @param page
     *            the page to copy
     * @param flowPage
     *            the page as defined in the flow
     * @return a copy of the page as a {@link PageWrapper}
     */
    public static PageWrapper createPage(Page page, Page flowPage) {
        PageWrapper newPage = new PageWrapper();
        clonePage(page, newPage, flowPage);
        newPage.processPage();
        return newPage;
    }

    /**
     * Factory method which creates a copy of Page from the config.
     *
     * @param page
     *            the page to copy
     * @return a copy of the page as a {@link PageWrapper}
     */
    public static PageWrapper createPage(Page page) {
        PageWrapper newPage = new PageWrapper();
        clonePage(page, newPage, null);
        newPage.processPage();
        return newPage;
    }

    /**
     * Copies one page element to another. Only the data in FROM will be copied
     * to the TO if the data exists. The flowPage is taken from the Flow config
     * and will be replaced with pageTo. The object 'pageTo' will be a
     * combination of Inputs from flowPage and the mandatory Inputs found in the
     * pageFrom object.
     *
     * @param pageFrom
     *            the source input element. This page is configured under the
     *            Pages element in the config file.
     * @param pageTo
     *            the page element to copy to. This is new page that will be
     *            added to the current flow.
     * @param flowPage
     *            the page from the flow config. This page will be replaced with
     *            pageTo once the merge is complete.
     */
    private static void clonePage(Page pageFrom, PageWrapper pageTo,
            Page flowPage) {

        Input masterInputs[] = pageFrom.getInput();

        // Set the page include id
        if (pageFrom.getIncludeId() != null)
            pageFrom.setIncludeId(pageFrom.getIncludeId());
        // Set the page name
        if (pageFrom.getName() != null)
            pageTo.setName(pageFrom.getName());
        // Set PreProcess attribute
        if (pageFrom.hasPreProcess())
            pageTo.setPreProcess(pageFrom.getPreProcess());
        // Set PreProcessor attribute
        if (pageFrom.getPreProcessor() != null)
            pageTo.setPreProcessor(pageFrom.getPreProcessor());
        // Set the progress action enabled status
        if(pageFrom.hasProgressActionEnabled()) {
        	pageTo.setProgressActionEnabled(pageFrom.getProgressActionEnabled());
        }
        // Set PageWaitHandler attribute
        if (pageFrom.getPageWaitHandler() != null) {
            pageTo.setPageWaitHandler(pageFrom.getPageWaitHandler());     
        }
        // Set the is pop-up window flag
        if(pageFrom.hasIsPopupWindow()) {
        	pageTo.setIsPopupWindow(pageFrom.getIsPopupWindow());
        	
            if(pageFrom.getIsPopupWindow()) {
            	pageTo.setPopupWindowLocator(pageFrom.getPopupWindowLocator());
            }       	
        }
        
        for (int i = 0; i < masterInputs.length; i++) {

            // Get the input that will be copied
            Input currInput = masterInputs[i];

            // Copy input from page template to new page
            Input newInput = new Input();
            cloneInput(currInput, newInput, false);

            // Add the input to the Page
            pageTo.addInput(newInput);
        }

        // Clone actions
        Progress newProgress = new Progress();
        if(pageFrom.getProgress() != null) {
            Action actions[] = pageFrom.getProgress().getAction();
            Action newActions[] = new Action[actions.length];
            for (int i = 0; i < actions.length; i++) {
                Action newAction = new Action();
                cloneProgressAction(actions[i], newAction);
                newActions[i] = newAction;
            }

            // Add actions for new page
            newProgress.setAction(newActions);
            if (newActions.length > 0) {
                newProgress.setActionIndex(newActions[0].getIndex());
            }      	
        
	        //clone BrowserActions
	        BrowserAction browserActions[] = pageFrom.getProgress().getBrowserAction();
	        BrowserAction newBrowserActions[] = new BrowserAction[browserActions.length];
	        for (int i = 0; i < browserActions.length; i++) {
	            BrowserAction newAction = new BrowserAction();
	            cloneBrowserAction(browserActions[i], newAction);
	            newBrowserActions[i] = newAction;
	        }       
	        newProgress.setBrowserAction(newBrowserActions);
	        pageTo.setProgress(newProgress);
        }


        // Clone the page elements if they exist
        if (pageFrom.getPageElements() != null
                && pageFrom.getPageElements().getPageElementCount() > 0) {

            // The new page elements
            PageElements newPageElements = new PageElements();

            // Copy the page elements
            clonePageElements(pageFrom.getPageElements(), newPageElements);

            // Add the array of page elements to the current input
            pageTo.setPageElements(newPageElements);
        }

        if (flowPage != null) {
            // Override the PreProcess attribute with flow page PreProcess value if this exists
            if(flowPage.hasPreProcess())
            	pageTo.setPreProcess(flowPage.getPreProcess());

            // Override the PreProcessor attribute with flow page PreProcessor value if this exists
            if(flowPage.getPreProcessor() != null)
            	pageTo.setPreProcessor(flowPage.getPreProcessor());

            // Set the progress action enabled status
            if(flowPage.hasProgressActionEnabled()) {
            	pageTo.setProgressActionEnabled(flowPage.getProgressActionEnabled());
            }
            
            // Set PageWaitHandler attribute
            if (flowPage.getPageWaitHandler() != null) {
                pageTo.setPageWaitHandler(flowPage.getPageWaitHandler());  
            }            

            // Override the master inputs with flow input values
            Input flowInputs[] = flowPage.getInput();
            // Create lookup table of the pages current inputs
            Hashtable<String, Input> mapInputs = mapInputs(pageTo.getInput());

            for (int i = 0; i < flowInputs.length; i++) {

                Input flowInput = flowInputs[i];
                String name = flowInput.getName();

                Input input = (Input) mapInputs.get(name);

                // Override the existing input attributes with the flow
                cloneInput(flowInput, input, true);

            }

            // Set the default progress action
            if (flowPage.getProgress() != null) {
                (pageTo.getProgress()).setActionIndex(flowPage.getProgress()
                        .getActionIndex());
            }
        }
    }

    /**
     * Copies one input element to another. Only the data in FROM will be copied
     * to the TO if the data exists.
     *
     * @param inputFrom
     *            the source input element
     * @param inputTo
     *            the input element to copy to
     * @param override
     *            if true override the inputTo with the inputFrom value
     */
    private static void cloneInput(Input inputFrom, Input inputTo,
            boolean override) {

        // Set the ID if exists
        if (inputFrom.getLocator() != null) {
            inputTo.setLocator(inputFrom.getLocator());
        }

        // Set the Name if exists
        if (inputFrom.getName() != null) {
            inputTo.setName(inputFrom.getName());
        }

        // Set the PageElement value
        if (inputFrom.getPageElement() != null) {
            inputTo.setPageElement(inputFrom.getPageElement());
        }

        // Set the required flag
        if (override && !inputFrom.hasRequired()) {
            inputTo.setRequired(true);
        } else {
            inputTo.setRequired(inputFrom.getRequired());
        }

        // Set the input type
        if (inputFrom.getType() != null) {
            inputTo.setType(inputFrom.getType());
        }

        // Set the input value
        if (inputFrom.getValue() != null) {
            inputTo.setValue(inputFrom.getValue());
        }

        // Set the wait after value
        if (inputFrom.getWaitFor() > 0) {
            inputTo.setWaitFor(inputFrom.getWaitFor());
        }

        // Set the Class Ref
        if (inputFrom.getClassRef() != null) {
            inputTo.setClassRef(inputFrom.getClassRef());
        }

        // Handled nested PageElements
        if (inputFrom.getPageElements() != null
                && inputFrom.getPageElements().getPageElementCount() > 0) {

            // The new page elements
            PageElements newPageElements = new PageElements();

            // Copy the page elements
            clonePageElements(inputFrom.getPageElements(), newPageElements);

            // Add the array of page elements to the current input
            inputTo.setPageElements(newPageElements);
        }
    }

    /**
     * Copy collection of page elements to a new collection of page elements
     *
     * @param fromPageElements
     *            the collection to copy from
     * @param toPageElements
     *            the collection to copy to
     */
    private static void clonePageElements(PageElements fromPageElements,
            PageElements toPageElements) {

        // Loop through the array of page elements copying to the new list
        for (int j = 0; j < fromPageElements.getPageElementCount(); j++) {
            PageElement exsiting = fromPageElements.getPageElement(j);
            PageElement newPageElement = new PageElement();

            // Copy the contents of the existing page element to the new one
            clonePageElement(exsiting, newPageElement);
            toPageElements.addPageElement(newPageElement);
        }
    }

    /**
     * Copies one page element to another. Only the data in FROM will be copied
     * to the TO if the data exists.
     *
     * @param pageElementFrom
     *            the source page element
     * @param pageElementTo
     *            the page element to copy to
     */
    private static void clonePageElement(PageElement pageElementFrom,
            PageElement pageElementTo) {

        // Set the page element Id
        if (pageElementFrom.getLocator() != null) {
            pageElementTo.setLocator(pageElementFrom.getLocator());
        }
        // Set the page element wait time
        if (pageElementFrom.getWaitFor() > 0) {
            pageElementTo.setWaitFor(pageElementFrom.getWaitFor());
        }
    }

    /**
     * Copies one Action element to another. Only the data in FROM will be
     * copied to the TO if the data exists.
     *
     * @param actionFrom
     *            the source action element
     * @param actionTo
     *            the action element to copy to
     */
    private static void cloneProgressAction(Action actionFrom, Action actionTo) {

        actionTo.setIndex(actionFrom.getIndex());
        if (actionFrom.getName() != null) {
            actionTo.setName(actionFrom.getName());
        }
        if (actionFrom.getLocator() != null) {
            actionTo.setLocator(actionFrom.getLocator());
        }
        if (actionFrom.getSpinnerURL() != null) {
            actionTo.setSpinnerURL(actionFrom.getSpinnerURL());
        }
    }

    /**
     * Copies one BrowserAction element to another. Only the data in FROM will be
     * copied to the TO if the data exists.
     *
     * @param actionFrom
     *            the source action element
     * @param actionTo
     *            the action element to copy to
     */
    private static void cloneBrowserAction(BrowserAction actionFrom, BrowserAction actionTo) {

        actionTo.setIndex(actionFrom.getIndex());
        if (actionFrom.getName() != null) {
            actionTo.setName(actionFrom.getName());
        }

        actionTo.setEnabled(actionFrom.getEnabled());

        if (actionFrom.getType() != null) {
            actionTo.setType(actionFrom.getType());
        }
    }

    /**
     * Adds inputs to Hashtable for easy retrieval.
     *
     * @param from
     *            array of inputs
     * @return Hashtable of inputs
     */
    private static Hashtable<String, Input> mapInputs(Input[] from) {
        Hashtable<String, Input> data = new Hashtable<String, Input>();
        for (int i = 0; i < from.length; i++) {
            String name = from[i].getName();
            // Get the input that will be copied
            data.put(name, from[i]);
        }
        return data;
    }
}
