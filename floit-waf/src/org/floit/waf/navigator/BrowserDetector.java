/**
 *
 *
 * Project: GUI Test Framework
 * Class: org.floit.test.gui.navigator.BrowserDetector
 * Author: $Author: garrett.muldowney $
 * Created: $Date: 2011/10/24 09:36:59 $
 * Revision: $Revision: 1.4 $
 */
package org.floit.waf.navigator;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses the user agent string in an attempt to determine the
 * browser and version.
 * <p>
 * The function of the class is to identify the HTTP user agent (browser or
 * robot) by examining the User-Agent string in the HTTP header of the request
 * to the WEB server. The User-Agent string format is described by <a
 * href="http://www.ietf.org/rfc/rfc1945.txt">RFC 1945</a> and <a
 * href="http://www.ietf.org/rfc/rfc2068.txt">RFC 2068</a>. Unfortunately the
 * standard is not fully adhered too making it difficult to correctly identify
 * browser user agents. Moreover many browsers identify themselves as another
 * browser, for compatibility reasons, and this makes it harder for a piece of
 * software the recognition of the actual name and version of the agent.
 * </p>
 * <p>
 * The User-Agent string consists of one or more products (with optional comment
 * field). Each product entry in the User-Agent string should contain a product
 * name and version number.
 * </p>
 * <p>
 * The following browsers types are supported:
 * </p>
 * <ol>
 * <li>Microsoft Internet Explorer</li>
 * <li>Firefox</li>
 * <li>Google Chrome</li>
 * <li>Safari</li>
 * <li>Opera</li>
 * </ol>
 * <p>
 * If a browser cannot be matched to this list of supported browser then its
 * type is considered "unknown". There is one exception, if a browser is not
 * identifiable but does contain Mozilla in the User-Agent string then its type
 * be set to "Mozilla".
 * </p>
 *
 */
public class BrowserDetector {
    public static final String MSIE = "MSIE";
    public static final String OPERA = "OPERA";
    public static final String MOZILLA = "MOZILLA";
    public static final String FIREFOX = "FIREFOX";
    public static final String GOOGLE = "CHROME";
    public static final String SAFARI = "SAFARI";

    private static int counter = 1;

    public static final int BROWSER_MOZILLA = counter++;
    public static final int BROWSER_IE = counter++;
    public static final int BROWSER_OPERA = counter++;
    public static final int BROWSER_FIREFOX = counter++;
    public static final int BROWSER_SAFARI = counter++;
    public static final int BROWSER_GOOGLE = counter++;

    public static final String WINDOWS = "Windows";
    public static final String UNIX = "Unix";
    public static final String MACINTOSH = "Macintosh";

    private Hashtable<String, Product> products = new Hashtable<String, Product>();

    /** The user agent string. */
    private String userAgentString = "";

    /** The browser name specified in the user agent string. */
    private String browserName = "unknown";

    /** The browser type code, 0 indicates unknown */
    private int browserType = 0;

    /**
     * The browser version specified in the user agent string. If we can't parse
     * the version just assume an old browser.
     */
    private String browserVersion = "unknown";

    /**
     * The browser platform specified in the user agent string.
     */
    private String browserPlatform = "unknown";

    private final String PATTERN = "^([^/\\s]*)(/([\\S]*))?([\\s]*\\[[a-zA-Z][a-zA-Z]\\])?[\\s]*(\\((([^()]|(\\([^()]*\\)))*)\\))?[\\s]*";

    /**
     * Constructor used to initialize this class.
     *
     * @param userAgentString
     *            A String with the user agent field.
     */
    public BrowserDetector(String userAgentString) {
        this.userAgentString = userAgentString;
        // parse();
        parseProducts();
        setBrowserType();
    }

    /**
     * The user agent string is comprised of products. Each product contains one
     * or more fields defined by RFC 1945 and RFC 2068.
     */
    private void parseProducts() {

        boolean process = true;
        Pattern p = Pattern.compile(PATTERN);
        String input = userAgentString;

        while (process) {
            process = false;
            Matcher match = p.matcher(input);
            if (match.find()) {
                if (match.groupCount() > 0) {
                    // Get the first match
                    final String m = match.group(0);
                    if (m != null && m.length() > 0) {

                        Product product = new Product(m);
                        if (!"unknown".equals(product.getName())) {
                            products.put(product.getName().toUpperCase(),
                                    product);
                        }

                        if (input.startsWith(m)) {
                            input = input.substring(m.length());
                            process = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Set the browser type if available.
     */
    private void setBrowserType() {

        String browser = null;
        for (int i = 1; i < counter; i++) {

            // Check all known browser types
            if (i == BROWSER_IE) {
                browser = MSIE;
            } else if (i == BROWSER_FIREFOX) {
                browser = FIREFOX;
            } else if (i == BROWSER_GOOGLE) {
                browser = GOOGLE;
            } else if (i == BROWSER_OPERA) {
                browser = OPERA;
            } else if (i == BROWSER_SAFARI) {
            	//The google chrome user agent contains safari so this checks if it is
            	//the chrome user agent string instead
            	if(userAgentString.contains("Chrome")){
            		browser = GOOGLE;
            	}
            	else{
            		browser= SAFARI;
            	}
            }

            if (browser != null && products.containsKey(browser)) {
                setProwserType(browser, i);
                setPlatform();
                break;
            }
        }

        // If browser is unknown they the Mozilla type
        if (browserName == "unknown" && products.containsKey(MOZILLA)) {
            setProwserType(MOZILLA, BROWSER_MOZILLA);
            setPlatform();
        }
    }

    private void setProwserType(String browser, int code) {
        Product product = (Product) products.get(browser);
        browserName = product.getName();
        browserVersion = product.getVersion();
        browserType = code;
    }

    /**
     * Set the platform if available.
     */
    private void setPlatform() {
        // Iterate over all product entries and find the first comment as it
        // should contain the platform
        Enumeration<String> e = products.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            Product product = (Product) products.get(key);
            if (product.getComment() != null
                    && product.getComment().length() > 0) {
                parsePlatform(product.getComment());
            }

        }
    }

    /**
     * Determines the operating system platform.
     *
     * @param comment
     *            string to search for platform detail
     */
    private void parsePlatform(String comment) {

        // Try to figure out what platform.
        if ((comment.indexOf("Windows") != -1)
                || (comment.indexOf("WinNT") != -1)
                || (comment.indexOf("Win98") != -1)
                || (comment.indexOf("Win95") != -1)) {
            browserPlatform = WINDOWS;
        }

        if (comment.indexOf("Mac") != -1) {
            browserPlatform = MACINTOSH;
        }

        if (comment.indexOf("X11") != -1) {
            browserPlatform = UNIX;
        }
    }

    /**
     * The browser name specified in the user agent string.
     *
     * @return A String with the browser name.
     */
    public String getBrowserName() {
        return browserName;
    }

    /**
     * The browser platform specified in the user agent string.
     *
     * @return A String with the browser platform.
     */
    public String getBrowserPlatform() {
        return browserPlatform;
    }

    /**
     * The browser version specified in the user agent string.
     *
     * @return a String with the browser version.
     */
    public String getBrowserVersion() {
        return browserVersion;
    }

    /**
     * The browser type as a number value.
     *
     * @return the browser type code.
     */
    public int getBrowserType() {
        return browserType;
    }

    /**
     * The user agent string for this class.
     *
     * @return A String with the user agent.
     */
    public String getUserAgentString() {
        return userAgentString;
    }

    /**
     * Outputs the browser details.
     *
     * @return format string containing the browser details
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("Browser Name: ");
        sb.append(getBrowserName());
        sb.append(", Browser Version: ");
        sb.append(getBrowserVersion());
        sb.append(", Browser Platform: ");
        sb.append(getBrowserPlatform());

        return sb.toString();
    }

    class Product {

        private String name = "unknown";
        private String version = "0.0";
        private String comment = "";
        private String productString = null;

        public Product(String product) {
            this.productString = product;
            parse();
        }

        private void parse() {
            int versionStartIndex = productString.indexOf("/");
            int versionEndIndex = productString.indexOf(" ");

            // Get the browser name and version.
            if (versionStartIndex > 0) {
                name = productString.substring(0, versionStartIndex);
            }

            try {
                // Not all user agents will have a space in the reported
                // string.
                String agentSubstring = null;
                if (versionEndIndex < 0) {
                    agentSubstring = productString
                            .substring(versionStartIndex + 1);
                } else {
                    agentSubstring = productString.substring(
                            versionStartIndex + 1, versionEndIndex);
                }
                version = agentSubstring;
            } catch (Exception e) {
                // Just use the default value.
            }

            // Parse optional commnet
            if (versionEndIndex >= 0
                    && versionEndIndex < productString.length()) {
                String commentString = productString.substring(versionEndIndex,
                        productString.length());
                comment = commentString;
            }

            // MSIE lies about its name. Of course...
            if (productString.indexOf(MSIE) != -1) {
                // Ex: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)
                versionStartIndex = (productString.indexOf(MSIE)
                        + MSIE.length() + 1);
                versionEndIndex = productString.indexOf(";", versionStartIndex);

                name = MSIE;
                try {
                    version = userAgentString.substring(versionStartIndex,
                            versionEndIndex);
                } catch (Exception e) {
                    // Just use the default value.
                }
            }
        }

        public String getName() {
            return name;
        }

        public String getComment() {
            return comment;
        }

        public String getVersion() {
            return "" + version;
        }

        public String getProductString() {
            return productString;
        }

        public String toString() {

            StringBuffer sb = new StringBuffer();
            sb.append("\nPRODUCT STRING ");
            sb.append(getProductString());
            sb.append("\nPRODUCT NAME ");
            sb.append(getName());
            sb.append("\nPRODUCT VERSION ");
            sb.append(getVersion());
            sb.append("\nPRODUCT COMMENT ");
            sb.append(getComment());

            return sb.toString();

        }
    }
}
