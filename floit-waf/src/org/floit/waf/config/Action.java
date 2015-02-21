/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.waf.config;

/**
 * Each action defines a progress path from the current HTML page
 * to the next. E.g. a link or submit button. There can be multiple
 * exit points on a page and each is defined by an Action
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Action extends org.floit.waf.config.SetType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Defines the Locator of the action element. Typically it will
     * be link of submit button etc.
     */
    private java.lang.String _locator;

    /**
     * Defines the spinner URL to wait on for this action
     */
    private java.lang.String _spinnerURL;

    /**
     * Field _index.
     */
    private int _index;

    /**
     * keeps track of state for field: _index
     */
    private boolean _has_index;


      //----------------/
     //- Constructors -/
    //----------------/

    public Action() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteIndex(
    ) {
        this._has_index= false;
    }

    /**
     * Returns the value of field 'index'.
     * 
     * @return the value of field 'Index'.
     */
    public int getIndex(
    ) {
        return this._index;
    }

    /**
     * Returns the value of field 'locator'. The field 'locator'
     * has the following description: Defines the Locator of the
     * action element. Typically it will be link of submit button
     * etc.
     * 
     * @return the value of field 'Locator'.
     */
    public java.lang.String getLocator(
    ) {
        return this._locator;
    }

    /**
     * Returns the value of field 'spinnerURL'. The field
     * 'spinnerURL' has the following description: Defines the
     * spinner URL to wait on for this action
     * 
     * @return the value of field 'SpinnerURL'.
     */
    public java.lang.String getSpinnerURL(
    ) {
        return this._spinnerURL;
    }

    /**
     * Method hasIndex.
     * 
     * @return true if at least one Index has been added
     */
    public boolean hasIndex(
    ) {
        return this._has_index;
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
     * Sets the value of field 'index'.
     * 
     * @param index the value of field 'index'.
     */
    public void setIndex(
            final int index) {
        this._index = index;
        this._has_index = true;
    }

    /**
     * Sets the value of field 'locator'. The field 'locator' has
     * the following description: Defines the Locator of the action
     * element. Typically it will be link of submit button etc.
     * 
     * @param locator the value of field 'locator'.
     */
    public void setLocator(
            final java.lang.String locator) {
        this._locator = locator;
    }

    /**
     * Sets the value of field 'spinnerURL'. The field 'spinnerURL'
     * has the following description: Defines the spinner URL to
     * wait on for this action
     * 
     * @param spinnerURL the value of field 'spinnerURL'.
     */
    public void setSpinnerURL(
            final java.lang.String spinnerURL) {
        this._spinnerURL = spinnerURL;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.waf.config.Action
     */
    public static org.floit.waf.config.Action unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.waf.config.Action) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.waf.config.Action.class, reader);
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
