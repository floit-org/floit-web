/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * A page element is an entity within the web page that must exist
 * before the page is deemed loaded. Typical example is an AJAX
 * call load a search panel. Processing of the page will not
 * commence until all page elements have loaded by the browser.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PageElement implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The HMTL element identifier that associated the input element
     */
    private java.lang.String _locator;

    /**
     * The length of time in miliseconds to wait for an element to
     * load. If no wait is defined then the default pause time will
     * be used.
     */
    private int _waitFor;

    /**
     * keeps track of state for field: _waitFor
     */
    private boolean _has_waitFor;


      //----------------/
     //- Constructors -/
    //----------------/

    public PageElement() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteWaitFor(
    ) {
        this._has_waitFor= false;
    }

    /**
     * Returns the value of field 'locator'. The field 'locator'
     * has the following description: The HMTL element identifier
     * that associated the input element
     * 
     * @return the value of field 'Locator'.
     */
    public java.lang.String getLocator(
    ) {
        return this._locator;
    }

    /**
     * Returns the value of field 'waitFor'. The field 'waitFor'
     * has the following description: The length of time in
     * miliseconds to wait for an element to load. If no wait is
     * defined then the default pause time will be used.
     * 
     * @return the value of field 'WaitFor'.
     */
    public int getWaitFor(
    ) {
        return this._waitFor;
    }

    /**
     * Method hasWaitFor.
     * 
     * @return true if at least one WaitFor has been added
     */
    public boolean hasWaitFor(
    ) {
        return this._has_waitFor;
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
     * Sets the value of field 'locator'. The field 'locator' has
     * the following description: The HMTL element identifier that
     * associated the input element
     * 
     * @param locator the value of field 'locator'.
     */
    public void setLocator(
            final java.lang.String locator) {
        this._locator = locator;
    }

    /**
     * Sets the value of field 'waitFor'. The field 'waitFor' has
     * the following description: The length of time in miliseconds
     * to wait for an element to load. If no wait is defined then
     * the default pause time will be used.
     * 
     * @param waitFor the value of field 'waitFor'.
     */
    public void setWaitFor(
            final int waitFor) {
        this._waitFor = waitFor;
        this._has_waitFor = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.web.config.PageElement
     */
    public static org.floit.web.config.PageElement unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.PageElement) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.PageElement.class, reader);
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
