/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * Defines the test config for Flowit's GUI test harness
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class TestConfig implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * A collection of included test configuration files
     */
    private org.floit.web.config.Includes _includes;

    /**
     * Collection of pages flow definitions. Each flow defines a
     * set of web pages. The sequence of pages must be a logical
     * path through a web application
     */
    private org.floit.web.config.FlowSet _flowSet;

    /**
     * Collection of page definitions. Each of the page are grouped
     * together to define a page flow.
     */
    private org.floit.web.config.PageSet _pageSet;


      //----------------/
     //- Constructors -/
    //----------------/

    public TestConfig() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'flowSet'. The field 'flowSet'
     * has the following description: Collection of pages flow
     * definitions. Each flow defines a set of web pages. The
     * sequence of pages must be a logical path through a web
     * application
     * 
     * @return the value of field 'FlowSet'.
     */
    public org.floit.web.config.FlowSet getFlowSet(
    ) {
        return this._flowSet;
    }

    /**
     * Returns the value of field 'includes'. The field 'includes'
     * has the following description: A collection of included test
     * configuration files
     * 
     * @return the value of field 'Includes'.
     */
    public org.floit.web.config.Includes getIncludes(
    ) {
        return this._includes;
    }

    /**
     * Returns the value of field 'pageSet'. The field 'pageSet'
     * has the following description: Collection of page
     * definitions. Each of the page are grouped together to define
     * a page flow.
     * 
     * @return the value of field 'PageSet'.
     */
    public org.floit.web.config.PageSet getPageSet(
    ) {
        return this._pageSet;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'flowSet'. The field 'flowSet' has
     * the following description: Collection of pages flow
     * definitions. Each flow defines a set of web pages. The
     * sequence of pages must be a logical path through a web
     * application
     * 
     * @param flowSet the value of field 'flowSet'.
     */
    public void setFlowSet(
            final org.floit.web.config.FlowSet flowSet) {
        this._flowSet = flowSet;
    }

    /**
     * Sets the value of field 'includes'. The field 'includes' has
     * the following description: A collection of included test
     * configuration files
     * 
     * @param includes the value of field 'includes'.
     */
    public void setIncludes(
            final org.floit.web.config.Includes includes) {
        this._includes = includes;
    }

    /**
     * Sets the value of field 'pageSet'. The field 'pageSet' has
     * the following description: Collection of page definitions.
     * Each of the page are grouped together to define a page flow.
     * 
     * @param pageSet the value of field 'pageSet'.
     */
    public void setPageSet(
            final org.floit.web.config.PageSet pageSet) {
        this._pageSet = pageSet;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.web.config.TestConfig
     */
    public static org.floit.web.config.TestConfig unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.TestConfig) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.TestConfig.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
