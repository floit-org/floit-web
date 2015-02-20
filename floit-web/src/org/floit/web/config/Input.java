/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * Represents an element or entity on the web page.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Input extends org.floit.web.config.SetType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The input value required by the HTML element.
     */
    private java.lang.String _value;

    /**
     * The class the input refers to if its custom type.
     */
    private java.lang.String _classRef;

    /**
     * The HMTL element identifier that associated the input element
     */
    private java.lang.String _locator;

    /**
     * The length of time in miliseconds to wait after setting this
     * input
     */
    private int _waitFor;

    /**
     * keeps track of state for field: _waitFor
     */
    private boolean _has_waitFor;

    /**
     * The type of HTML element; input, radio, checkbox, click,
     * ajaz, select, multi-select, select-link.
     */
    private org.floit.web.config.types.InputTypeType _type;

    /**
     * Indicates whether the element is mandatory or optional.
     * Mandatory elements are required to progress to the next page.
     */
    private boolean _required;

    /**
     * keeps track of state for field: _required
     */
    private boolean _has_required;

    /**
     * A page element is an entity within the web page that must
     * exist before the page is deemed loaded. Typical example is
     * an AJAX call load a search panel. Processing of the page
     * will not commence until the page element has loaded by the
     * browser. If more than one element is required please use the
     * PageElements block instead
     */
    private java.lang.String _pageElement;

    /**
     * A reference to a Java class that implements the
     * CustomWaitForInterface. This allows custom wait handling
     * functionality to be implement for an input. The PageElement
     * will be ignored if the WaitForRef attribute is used.
     */
    private java.lang.String _waitForRef;

    /**
     * A collection of page elements
     */
    private org.floit.web.config.PageElements _pageElements;


      //----------------/
     //- Constructors -/
    //----------------/

    public Input() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteRequired(
    ) {
        this._has_required= false;
    }

    /**
     */
    public void deleteWaitFor(
    ) {
        this._has_waitFor= false;
    }

    /**
     * Returns the value of field 'classRef'. The field 'classRef'
     * has the following description: The class the input refers to
     * if its custom type.
     * 
     * @return the value of field 'ClassRef'.
     */
    public java.lang.String getClassRef(
    ) {
        return this._classRef;
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
     * Returns the value of field 'pageElement'. The field
     * 'pageElement' has the following description: A page element
     * is an entity within the web page that must exist before the
     * page is deemed loaded. Typical example is an AJAX call load
     * a search panel. Processing of the page will not commence
     * until the page element has loaded by the browser. If more
     * than one element is required please use the PageElements
     * block instead
     * 
     * @return the value of field 'PageElement'.
     */
    public java.lang.String getPageElement(
    ) {
        return this._pageElement;
    }

    /**
     * Returns the value of field 'pageElements'. The field
     * 'pageElements' has the following description: A collection
     * of page elements
     * 
     * @return the value of field 'PageElements'.
     */
    public org.floit.web.config.PageElements getPageElements(
    ) {
        return this._pageElements;
    }

    /**
     * Returns the value of field 'required'. The field 'required'
     * has the following description: Indicates whether the element
     * is mandatory or optional. Mandatory elements are required to
     * progress to the next page.
     * 
     * @return the value of field 'Required'.
     */
    public boolean getRequired(
    ) {
        return this._required;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: The type of HTML element; input,
     * radio, checkbox, click, ajaz, select, multi-select,
     * select-link.
     * 
     * @return the value of field 'Type'.
     */
    public org.floit.web.config.types.InputTypeType getType(
    ) {
        return this._type;
    }

    /**
     * Returns the value of field 'value'. The field 'value' has
     * the following description: The input value required by the
     * HTML element.
     * 
     * @return the value of field 'Value'.
     */
    public java.lang.String getValue(
    ) {
        return this._value;
    }

    /**
     * Returns the value of field 'waitFor'. The field 'waitFor'
     * has the following description: The length of time in
     * miliseconds to wait after setting this input
     * 
     * @return the value of field 'WaitFor'.
     */
    public int getWaitFor(
    ) {
        return this._waitFor;
    }

    /**
     * Returns the value of field 'waitForRef'. The field
     * 'waitForRef' has the following description: A reference to a
     * Java class that implements the CustomWaitForInterface. This
     * allows custom wait handling functionality to be implement
     * for an input. The PageElement will be ignored if the
     * WaitForRef attribute is used.
     * 
     * @return the value of field 'WaitForRef'.
     */
    public java.lang.String getWaitForRef(
    ) {
        return this._waitForRef;
    }

    /**
     * Method hasRequired.
     * 
     * @return true if at least one Required has been added
     */
    public boolean hasRequired(
    ) {
        return this._has_required;
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
     * Returns the value of field 'required'. The field 'required'
     * has the following description: Indicates whether the element
     * is mandatory or optional. Mandatory elements are required to
     * progress to the next page.
     * 
     * @return the value of field 'Required'.
     */
    public boolean isRequired(
    ) {
        return this._required;
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
     * Sets the value of field 'classRef'. The field 'classRef' has
     * the following description: The class the input refers to if
     * its custom type.
     * 
     * @param classRef the value of field 'classRef'.
     */
    public void setClassRef(
            final java.lang.String classRef) {
        this._classRef = classRef;
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
     * Sets the value of field 'pageElement'. The field
     * 'pageElement' has the following description: A page element
     * is an entity within the web page that must exist before the
     * page is deemed loaded. Typical example is an AJAX call load
     * a search panel. Processing of the page will not commence
     * until the page element has loaded by the browser. If more
     * than one element is required please use the PageElements
     * block instead
     * 
     * @param pageElement the value of field 'pageElement'.
     */
    public void setPageElement(
            final java.lang.String pageElement) {
        this._pageElement = pageElement;
    }

    /**
     * Sets the value of field 'pageElements'. The field
     * 'pageElements' has the following description: A collection
     * of page elements
     * 
     * @param pageElements the value of field 'pageElements'.
     */
    public void setPageElements(
            final org.floit.web.config.PageElements pageElements) {
        this._pageElements = pageElements;
    }

    /**
     * Sets the value of field 'required'. The field 'required' has
     * the following description: Indicates whether the element is
     * mandatory or optional. Mandatory elements are required to
     * progress to the next page.
     * 
     * @param required the value of field 'required'.
     */
    public void setRequired(
            final boolean required) {
        this._required = required;
        this._has_required = true;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: The type of HTML element; input,
     * radio, checkbox, click, ajaz, select, multi-select,
     * select-link.
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final org.floit.web.config.types.InputTypeType type) {
        this._type = type;
    }

    /**
     * Sets the value of field 'value'. The field 'value' has the
     * following description: The input value required by the HTML
     * element.
     * 
     * @param value the value of field 'value'.
     */
    public void setValue(
            final java.lang.String value) {
        this._value = value;
    }

    /**
     * Sets the value of field 'waitFor'. The field 'waitFor' has
     * the following description: The length of time in miliseconds
     * to wait after setting this input
     * 
     * @param waitFor the value of field 'waitFor'.
     */
    public void setWaitFor(
            final int waitFor) {
        this._waitFor = waitFor;
        this._has_waitFor = true;
    }

    /**
     * Sets the value of field 'waitForRef'. The field 'waitForRef'
     * has the following description: A reference to a Java class
     * that implements the CustomWaitForInterface. This allows
     * custom wait handling functionality to be implement for an
     * input. The PageElement will be ignored if the WaitForRef
     * attribute is used.
     * 
     * @param waitForRef the value of field 'waitForRef'.
     */
    public void setWaitForRef(
            final java.lang.String waitForRef) {
        this._waitForRef = waitForRef;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.web.config.Input
     */
    public static org.floit.web.config.Input unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.Input) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.Input.class, reader);
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
