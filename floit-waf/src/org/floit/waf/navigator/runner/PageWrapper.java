/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.runner.PageWrapper
 * $Author: kieran $
 * $Date: 2010/07/06 09:46:34 $
 * $Revision: 1.5 $
 */
package org.floit.waf.navigator.runner;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.floit.waf.config.Action;
import org.floit.waf.config.Input;
import org.floit.waf.config.Page;

/**
 * Wraps a {@link Page} object while providing additional functionality.
 * 
 * <p>
 * The PageWrapper class extends {@link Page} therefore providing the same
 * functionality as {@link Page}. It supports addition features such as lookup
 * and retrieval of {@link Input} objects by name from an input cache. The
 * {@link Page} class only supports retrieval of {@link Input} objects by index.
 * </P>
 * <P>
 * Similarly the PageWrapper allows the progress action to be set by name rather
 * than relaying on the using the action index.
 * </P>
 */
public class PageWrapper extends Page {

	/**
     *
     */
	private static final long serialVersionUID = 1L;
	/**
	 * Log4J logger instance for class PageWrapper.
	 */
	private static final Logger CAT = Logger.getLogger(PageWrapper.class);
	/**
	 * Log4J debug setting for class {PageWrapper.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	/** Cache of the pages inputs */
	private Hashtable<String, Input> inputs = new Hashtable<String, Input>();
	private Hashtable<String, Action> actions = new Hashtable<String, Action>();

	/** A reference to the log directory */
	private String logDir = null;

	/**
	 * Constructs a new PageWrapper
	 */
	public PageWrapper() {
		super();
	}

	/**
	 * Populates the data from the Page object to PageWrapper caches to
	 * facilitate name lookups.
	 */
	public void processPage() {
		extractInputs();
		extractActions();
	}

	/**
	 * Extract the inputs from the Page and add to the inputs cache.
	 */
	private void extractInputs() {

		if (super.getInput().length > 0) {
			int size = super.getInput().length;
			Input[] inputArray = super.getInput();
			for (int i = 0; i < size; i++) {
				Input input = inputArray[i];
				// Save the input by index
				inputs.put(input.getName(), input);
			}
		}
	}

	/**
	 * Extract the actions from the Page and add to the actions cache.
	 */
	private void extractActions() {
		
		if (getProgress()!= null && getProgress().getActionCount() > 0) {
			for (int i = 0; i < getProgress().getActionCount(); i++) {
				Action action = getProgress().getAction()[i];
				// Save action by index
				actions.put(action.getIndex() + "", action);
				// Save action by name
				if (action.getName() != null) {
					actions.put(action.getName(), action);
				}
			}
		}
	}

	/**
	 * Return the Input by specifying the input name.
	 * 
	 * @param name
	 *            the name of the Input requested
	 * @return the requested input
	 */
	public Input getInput(String name) {
		return (Input) inputs.get(name);
	}

	/**
	 * Return the Input by specifying the input index. This overrides the
	 * {@link Page#getInput(int)} to provide a convenient method of retrieving
	 * the Input. The {@link Page#getInput(int)} method relies on the index
	 * number matching the sequence in an array of inputs. While this method
	 * stores the index values in a Hashtable for easy retrieval.
	 * 
	 * @param index
	 *            the index of the Input requested
	 * @return the requested input
	 */
	public Input getInput(int index) {
		return (Input) inputs.get(index + "");
	}

	/**
	 * Set the default action by name
	 * 
	 * @param name
	 */
	public void setProgressAction(String name) {
		if (name != null) {
			Action action = (Action) actions.get(name);
			if (action != null) {
				int index = action.getIndex();
				getProgress().setActionIndex(index);
			}
		}
	}

	/**
	 * Get the relative path to the log directory if set.
	 * 
	 * @return the log directory or null
	 */
	public String getLogDir() {
		return logDir;
	}

	/**
	 * Set the log directory
	 * 
	 * @param logDir
	 *            relative path to the log directory
	 */
	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

}
