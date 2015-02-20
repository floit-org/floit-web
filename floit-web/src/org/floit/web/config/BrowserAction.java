/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * Simulates browser button functionality.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class BrowserAction extends org.floit.web.config.SetType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The type of browser action to take. Currently Back and
     * Refresh are supported
     */
    private org.floit.web.config.types.BrowserActionTypeType _type;

    /**
     * Sets whether the browser action is to be performed or not
     */
    private boolean _enabled;

    /**
     * keeps track of state for field: _enabled
     */
    private boolean _has_enabled;

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

    public BrowserAction() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteEnabled(
    ) {
        this._has_enabled= false;
    }

    /**
     */
    public void deleteIndex(
    ) {
        this._has_index= false;
    }

    /**
     * Returns the value of field 'enabled'. The field 'enabled'
     * has the following description: Sets whether the browser
     * action is to be performed or not
     * 
     * @return the value of field 'Enabled'.
     */
    public boolean getEnabled(
    ) {
        return this._enabled;
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
     * Returns the value of field 'type'. The field 'type' has the
     * following description: The type of browser action to take.
     * Currently Back and Refresh are supported
     * 
     * @return the value of field 'Type'.
     */
    public org.floit.web.config.types.BrowserActionTypeType getType(
    ) {
        return this._type;
    }

    /**
     * Method hasEnabled.
     * 
     * @return true if at least one Enabled has been added
     */
    public boolean hasEnabled(
    ) {
        return this._has_enabled;
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
     * Returns the value of field 'enabled'. The field 'enabled'
     * has the following description: Sets whether the browser
     * action is to be performed or not
     * 
     * @return the value of field 'Enabled'.
     */
    public boolean isEnabled(
    ) {
        return this._enabled;
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
     * Sets the value of field 'enabled'. The field 'enabled' has
     * the following description: Sets whether the browser action
     * is to be performed or not
     * 
     * @param enabled the value of field 'enabled'.
     */
    public void setEnabled(
            final boolean enabled) {
        this._enabled = enabled;
        this._has_enabled = true;
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
     * Sets the value of field 'type'. The field 'type' has the
     * following description: The type of browser action to take.
     * Currently Back and Refresh are supported
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final org.floit.web.config.types.BrowserActionTypeType type) {
        this._type = type;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.web.config.BrowserAction
     */
    public static org.floit.web.config.BrowserAction unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.BrowserAction) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.BrowserAction.class, reader);
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
