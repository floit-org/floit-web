/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.waf.config;

/**
 * Collection of page definitions. Each of the page are grouped
 * together to define a page flow.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PageSet implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Describes the elements on a web page from a test
     * perspective. The elements are expressed as a collection of
     * Inputs.
     */
    private java.util.List<org.floit.waf.config.Page> _pageList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PageSet() {
        super();
        this._pageList = new java.util.ArrayList<org.floit.waf.config.Page>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPage
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPage(
            final org.floit.waf.config.Page vPage)
    throws java.lang.IndexOutOfBoundsException {
        this._pageList.add(vPage);
    }

    /**
     * 
     * 
     * @param index
     * @param vPage
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPage(
            final int index,
            final org.floit.waf.config.Page vPage)
    throws java.lang.IndexOutOfBoundsException {
        this._pageList.add(index, vPage);
    }

    /**
     * Method enumeratePage.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.waf.config.Page> enumeratePage(
    ) {
        return java.util.Collections.enumeration(this._pageList);
    }

    /**
     * Method getPage.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.waf.config.Page at the
     * given index
     */
    public org.floit.waf.config.Page getPage(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pageList.size()) {
            throw new IndexOutOfBoundsException("getPage: Index value '" + index + "' not in range [0.." + (this._pageList.size() - 1) + "]");
        }

        return (org.floit.waf.config.Page) _pageList.get(index);
    }

    /**
     * Method getPage.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.waf.config.Page[] getPage(
    ) {
        org.floit.waf.config.Page[] array = new org.floit.waf.config.Page[0];
        return (org.floit.waf.config.Page[]) this._pageList.toArray(array);
    }

    /**
     * Method getPageCount.
     * 
     * @return the size of this collection
     */
    public int getPageCount(
    ) {
        return this._pageList.size();
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
     * Method iteratePage.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.waf.config.Page> iteratePage(
    ) {
        return this._pageList.iterator();
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
    public void removeAllPage(
    ) {
        this._pageList.clear();
    }

    /**
     * Method removePage.
     * 
     * @param vPage
     * @return true if the object was removed from the collection.
     */
    public boolean removePage(
            final org.floit.waf.config.Page vPage) {
        boolean removed = _pageList.remove(vPage);
        return removed;
    }

    /**
     * Method removePageAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.waf.config.Page removePageAt(
            final int index) {
        java.lang.Object obj = this._pageList.remove(index);
        return (org.floit.waf.config.Page) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPage
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPage(
            final int index,
            final org.floit.waf.config.Page vPage)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._pageList.size()) {
            throw new IndexOutOfBoundsException("setPage: Index value '" + index + "' not in range [0.." + (this._pageList.size() - 1) + "]");
        }

        this._pageList.set(index, vPage);
    }

    /**
     * 
     * 
     * @param vPageArray
     */
    public void setPage(
            final org.floit.waf.config.Page[] vPageArray) {
        //-- copy array
        _pageList.clear();

        for (int i = 0; i < vPageArray.length; i++) {
                this._pageList.add(vPageArray[i]);
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
     * @return the unmarshaled org.floit.waf.config.PageSet
     */
    public static org.floit.waf.config.PageSet unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.waf.config.PageSet) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.waf.config.PageSet.class, reader);
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
