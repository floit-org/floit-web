/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.ConfigHandler
 * $Author: michael.loughran $
 * $Date: 2011/06/24 08:40:39 $
 * $Revision: 1.4 $
 */
package org.floit.waf.navigator.handlers;

import java.io.File;
import java.io.FileReader;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.Unmarshaller;
import org.floit.waf.config.Flow;
import org.floit.waf.config.Include;
import org.floit.waf.config.Page;
import org.floit.waf.config.TestConfig;
import org.floit.waf.navigator.TestNavigator;


/**
 * Handles sets of <code>Flow</code> and <code>Page</code> objects.
 * <p>
 * This class will run a particular <code>Flow</code> or <code>Page</code> in
 * different ways. Calling <code>navigator</code> from a class which extends
 * <code>BaseGUITestNavigator</code>, will enable you to use the runner methods
 * found in this class.
 * </p>
 */

public class ConfigHandler {

    /** Hashtable of page configs */
    private Hashtable<String, Hashtable<String, Page>> pageConfigs = new Hashtable<String, Hashtable<String, Page>>();

    /** Hashtable of flow configs */
    private Hashtable<String, Hashtable<String, Flow>> flowConfigs = new Hashtable<String, Hashtable<String, Flow>>();

    /** Handles page interaction with the framework */
    private PageHandler ph;

    /** The logger uses log4j to configure and control the logging output. */
    private static final Logger CAT = Logger.getLogger(ConfigHandler.class);

    /** Determines if the log4j debug mode is on or off. */
    private static final boolean DEBUG = CAT.isDebugEnabled();

    private static final String MAIN_CONF = "_main";

    /** The parent directory of the config file */
    private String parentPath;

    /**
     * Parses the <code>Page</code> and <code>Flow</code> objects from the
     * configuration. They are then cached to be used by the framework and user
     * tests.
     *
     * @param filename
     *            The test configuration file parse.
     * @param navigator
     *            The Selenium instance is obtained from the
     *            {@link TestNavigator} and is used to perform actions on
     *            the <code>Page</code>.
     */
    public ConfigHandler(String filename, TestNavigator navigator) {
        File file = new File(filename);
        parentPath = file.getParent();

        // Parse the config file
        TestConfig testConfig = parseConfig(file);

        // process the main config objects
        processConfig(testConfig, true, null);

    }

    /**
     * Loads the <code>Page</code> and <code>Flow</code> objects from the
     * primary test config. If the primary config includes alternative configs
     * then they will also be processed.<br/>
     * If the included config files also include
     *
     * @param testConfig
     *            an object representing a test configuration file.
     * @param main
     *            <code>true</code> if this is the primary test config. The
     *            configId should be <code>null</code> if this flag is true.
     * @param configId
     *            a configuration file identifier, <code>null</code> if the
     *            primary config is used.
     */
    private void processConfig(TestConfig testConfig, boolean main,
            String configId) {

        if (DEBUG) {
            if (main)
                CAT.debug("Loading config: _main");
            else
                CAT.debug("Loading config: " + configId);
        }
        // Get a copy of pages and flows from the config
        Page[] pageArray = testConfig.getPageSet().getPage();
        Flow[] flowArray = testConfig.getFlowSet().getFlow();

        // List<Page> page = new ArrayList<Page>(pageArray.length);
        Hashtable<String, Page> page = new Hashtable<String, Page>();
        // List<Flow> flow = new ArrayList<Flow>(flowArray.length);
        Hashtable<String, Flow> flow = new Hashtable<String, Flow>();

        // Add pages to list
        for (int i = 0; i < pageArray.length; i++) {
            // Add page by name
            if (pageArray[i].getName() != null) {
                page.put(pageArray[i].getName(), pageArray[i]);
            }
        }
        // Add flows to list
        for (int i = 0; i < flowArray.length; i++) {
            // Add flow by name
            if (flowArray[i].getName() != null) {
                flow.put(flowArray[i].getName(), flowArray[i]);
            }
        }

        // Determine the type of config. Only one main should exist
        if (main) {
            // Store the page and flow configs
            pageConfigs.put(MAIN_CONF, page);
            flowConfigs.put(MAIN_CONF, flow);
        } else {
            pageConfigs.put(configId, page);
            flowConfigs.put(configId, flow);
        }

        // Handle includes if they exit
        if (testConfig.getIncludes() != null
                && testConfig.getIncludes().getIncludeCount() > 0 && main) {

            // Process each include entry
            for (int i = 0; i < testConfig.getIncludes().getIncludeCount(); i++) {
                Include c = testConfig.getIncludes().getInclude(i);
                String path = c.getPath();
                String id = c.getID();

                // Read included config and parse it
                File file = new File(path);
                String absolutePath = path;
                if (!file.isAbsolute()) {
                    // Must assume it's relative to main parent config file
                    absolutePath = parentPath + File.separator + path;
                }

                // Read and parse the included config
                File newFile = new File(absolutePath);
                TestConfig config = parseConfig(newFile);
                processConfig(config, false, id);
            }
        }
    }

    /**
     * Reads a test configuration file and unmarshall it into TestConfig object.
     *
     * @param filename
     *            the path of the config file
     * @return a TestConfig object representing the test configuration file
     */
    private TestConfig parseConfig(File filename) {

        TestConfig testConfig = null;

        try {
            // Initialise the configuration settings and the test navigator.
            FileReader fileReader = new FileReader(filename);

            // Create a new Unmarshaller
            Unmarshaller unmarshaller = new Unmarshaller(TestConfig.class);

            // Unmarshal the person object
            testConfig = (TestConfig) unmarshaller.unmarshal(fileReader);

        } catch (NoClassDefFoundError e) {
            CAT.error("ConfigHandler: Unable to create unmarshaller. "
                    + "Check the correct .jar files are included."
                    + e.getMessage(), e);
        } catch (Exception e) {
            if (DEBUG) {
                CAT.error("ConfigHandler: Unable to unmarshall Test Config; "
                        + e.getMessage(), e);
            } else {
                CAT.error("ConfigHandler: Unable to unmarshall Test Config; "
                        + e.getMessage());
            }
        }

        return testConfig;
    }

    /**
     * Checks to see if the test failed due to a Selenium Exception.
     *
     * @return testFailed boolean value which indicates a selenium error.
     */
    protected boolean throwSeleniumException() {
        return ph.testFailed;
    }

    /**
     * Returns a {@link Page} with index <code>pageIndex</code> from the
     * configuration identified by <code>configId</code>.
     *
     * @param pageIndex
     *            the index of the requested page
     * @param configId
     *            the id of config that contains the requested page
     * @return the requested page (or null)
     */
    public Page getPage(int pageIndex, String configId) {
        if (configId == null) {
            configId = MAIN_CONF;
        }
        if (pageIndex < 1) {
            return null;
        }
        return pageConfigs.get(configId).get(pageIndex + "");
    }

    /**
     * Returns a {@link Flow} with index <code>flowIndex</code> from the
     * configuration identified by <code>configId</code>.
     *
     * @param flowIndex
     *            the index of the requested flow
     * @return the requested flow (or null)
     */
    public Flow getFlow(int flowIndex) {
        if (flowIndex < 1) {
            return null;
        }
        return flowConfigs.get(MAIN_CONF).get(flowIndex + "");
    }

    /**
     * Returns a {@link Page} with index <code>pageIndex</code> from the main
     * config.
     *
     * @param pageIndex
     *            the index of the requested page
     * @return the requested page (or null)
     */
    public Page getPage(int pageIndex) {
        if (pageIndex < 1) {
            return null;
        }
        return pageConfigs.get(MAIN_CONF).get(pageIndex + "");
    }

    /**
     * Returns a {@link Page} with the name <code>pageName</code> from the
     * configuration identified by <code>configId</code>.
     *
     * @param pageName
     *            the name of the requested page
     * @param configId
     *            the id of config that contains the requested page
     * @return the requested page (or null)
     */
    public Page getPage(String pageName, String configId) {
        if (pageName == null || configId == null) {
            return null;
        }
        return pageConfigs.get(configId).get(pageName);
    }

    /**
     * Returns a {@link Page} with name <code>pageName</code> from the main
     * config.
     *
     * @param pageName
     *            the name of the requested page
     * @return the requested page (or null)
     */
    public Page getPage(String pageName) {
        if (pageName == null) {
            return null;
        }
        return pageConfigs.get(MAIN_CONF).get(pageName);
    }

    /**
     * Returns a {@link Flow} with the name <code>flowName</code> from the main
     * config.
     *
     * @param flowName
     *            the index of the requested flow
     * @return the requested page (or null)
     */
    public Flow getFlow(String flowName) {
        if (flowName == null) {
            return null;
        }
        return flowConfigs.get(MAIN_CONF).get(flowName + "");
    }
}
