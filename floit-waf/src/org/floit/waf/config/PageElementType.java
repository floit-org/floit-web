/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.waf.config;

/**
 * Class PageElementType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PageElementType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * A page element is an entity within the web page that must
     * exist before the page is deemed loaded. Typical example is
     * an AJAX call load a search panel. Processing of the page
     * will not commence until all page elements have loaded by the
     * browser.
     */
    private java.util.List<org.floit.waf.config.PageElement> _pageElementList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PageElementType() {
        super();
        this._pageElementList = new java.util.ArrayList<org.floit.waf.config.PageElement>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPageElement
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPageElement(
            final org.floit.waf.config.PageElement vPageElement)
    throws java.lang.IndexOutOfBoundsException {
        this._pageElementList.add(vPageElement);
    }

    /**
     * 
     * 
     * @param index
     * @param vPageElement
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPageElement(
            final int index,
            final org.floit.waf.config.PageElement vPageElement)
    throws java.lang.IndexOutOfBoundsException {
        this._pageElementList.add(index, vPageElement);
    }

    /**
     * Method enumeratePageElement.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.waf.config.PageElement> enumeratePageElement(
    ) {
        return java.util.Collections.enumeration(this._pageElementList);
    }

    /**
     * Method getPageElement.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.waf.config.PageElement at
     * the given index
     */
    public org.floit.waf.config.PageElement getPageElement(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pageElementList.size()) {
            throw new IndexOutOfBoundsException("getPageElement: Index value '" + index + "' not in range [0.." + (this._pageElementList.size() - 1) + "]");
        }

        return (org.floit.waf.config.PageElement) _pageElementList.get(index);
    }

    /**
     * Method getPageElement.Returns the contents of the collection
     * in an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.waf.config.PageElement[] getPageElement(
    ) {
        org.floit.waf.config.PageElement[] array = new org.floit.waf.config.PageElement[0];
        return (org.floit.waf.config.PageElement[]) this._pageElementList.toArray(array);
    }

    /**
     * Method getPageElementCount.
     * 
     * @return the size of this collection
     */
    public int getPageElementCount(
    ) {
        return this._pageElementList.size();
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
     * Method iteratePageElement.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.waf.config.PageElement> iteratePageElement(
    ) {
        return this._pageElementList.iterator();
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
     */
    public void removeAllPageElement(
    ) {
        this._pageElementList.clear();
    }

    /**
     * Method removePageElement.
     * 
     * @param vPageElement
     * @return true if the object was removed from the collection.
     */
    public boolean removePageElement(
            final org.floit.waf.config.PageElement vPageElement) {
        boolean removed = _pageElementList.remove(vPageElement);
        return removed;
    }

    /**
     * Method removePageElementAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.waf.config.PageElement removePageElementAt(
            final int index) {
        java.lang.Object obj = this._pageElementList.remove(index);
        return (org.floit.waf.config.PageElement) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPageElement
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPageElement(
            final int index,
            final org.floit.waf.config.PageElement vPageElement)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pageElementList.size()) {
            throw new IndexOutOfBoundsException("setPageElement: Index value '" + index + "' not in range [0.." + (this._pageElementList.size() - 1) + "]");
        }

        this._pageElementList.set(index, vPageElement);
    }

    /**
     * 
     * 
     * @param vPageElementArray
     */
    public void setPageElement(
            final org.floit.waf.config.PageElement[] vPageElementArray) {
        //-- copy array
        _pageElementList.clear();

        for (int i = 0; i < vPageElementArray.length; i++) {
                this._pageElementList.add(vPageElementArray[i]);
        }
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.waf.config.PageElementType
     */
    public static org.floit.waf.config.PageElementType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.waf.config.PageElementType) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.waf.config.PageElementType.class, reader);
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
