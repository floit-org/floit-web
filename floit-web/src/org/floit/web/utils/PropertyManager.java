/**
 * 
 */
package org.floit.web.utils;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ##### ADD SUMMARY COMMENT HERE #####.
 * <p>
 * (C)opyright Flowit.org 2008
 * </p>
 * <ul>
 * <li><b>Project:</b> </li>
 * <li><b>Class:</b> org.floit.test.common.PropertyManager</li>
 * <li><b>Author:</b> kieran.cummins@floit.org</li>s
 * <li><b>Created:</b> 27 May 2010 08:54:16</li>
 * <li><b>Description:</b></li>
 * <li><b>Functional spec. ref.:</b></li>
 * <li><b>Design spec.ref.:</b></li>
 * </ul>
 */
public class PropertyManager {
	/**
	 * Log4J logger instance for class PropertyManager.
	 */
	private static final Logger CAT = Logger.getLogger(PropertyManager.class);
	/**
	 * Log4J debug setting for class {PropertyManager.
	 */
	private static final boolean DEBUG = CAT.isDebugEnabled();

	/**
	 * Provides support for Ant style properties. Allows properties defined in a
	 * property file to be reused with in the property file. This method
	 * replaces the property token with the actual property value.
	 * 
	 * @param props
	 *            the set of properties to normalise
	 * @return a normalised set of properties
	 */
	public static Properties normalise(Properties props) {
		Properties newProps = new Properties();

		Iterator<?> it = props.entrySet().iterator();
		while (it.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			int len = value.length() - 1;

			int idx1 = 0;
			while ((idx1 = value.indexOf("${")) != -1) {
				int idx2 = value.indexOf('}');
				if (idx2 != -1) {
					String subAttr = value.substring(idx1 + 2, idx2);
					String subAttrVal = props.getProperty(subAttr);

					if (subAttrVal == null) {
						throw new RuntimeException("Cannot normalize '" + key
								+ "' property. Non existing sub-attribute found: " + subAttr);
					}

					value = value.substring(0, idx1) + subAttrVal + (idx2 != len ? value.substring(idx2 + 1) : "");
				} else {
					throw new RuntimeException("Cannot normalize '" + key + "' property");
				}
			}

			//Ignore this as values can exist with these chars
			if (value.indexOf('{') != -1 || value.indexOf('}') != -1 || value.indexOf('$') != -1) {
				//throw new RuntimeException("Cannot normalize '" + key + "' property");
			}

			newProps.put(key, value);
		}

		return newProps;
	}

	/**
	 * Overrides a property with a system property if the names match.
	 * @param prop list of properties to override
	 */
	public static void overrideWithSystemProperties(Properties prop) {
		CAT.info("Copying system properties to Test properties");
		for (Enumeration<?> e = System.getProperties().propertyNames(); e.hasMoreElements();) {
			String propName = (String) e.nextElement();
			if(propName.startsWith("test.")) {
				// This is a test property override
				String propValue = System.getProperty(propName);
				if(DEBUG)
					CAT.debug("Overridding property [" + propName + "] with system property value [" + propValue + "]");
				prop.setProperty(propName, propValue);
			}
		}
	}

}
