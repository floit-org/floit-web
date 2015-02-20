/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * A collection of included test configuration files
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Includes implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Details of an included test configuration. This test config
     * will be accessible to the test framework and can be used in
     * the same way as the parent test config. NOTE: Includes
     * within included files will be ignored to prevent circular
     * references
     */
    private java.util.List<org.floit.web.config.Include> _includeList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Includes() {
        super();
        this._includeList = new java.util.ArrayList<org.floit.web.config.Include>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vInclude
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInclude(
            final org.floit.web.config.Include vInclude)
    throws java.lang.IndexOutOfBoundsException {
        this._includeList.add(vInclude);
    }

    /**
     * 
     * 
     * @param index
     * @param vInclude
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInclude(
            final int index,
            final org.floit.web.config.Include vInclude)
    throws java.lang.IndexOutOfBoundsException {
        this._includeList.add(index, vInclude);
    }

    /**
     * Method enumerateInclude.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.web.config.Include> enumerateInclude(
    ) {
        return java.util.Collections.enumeration(this._includeList);
    }

    /**
     * Method getInclude.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.web.config.Include at the
     * given index
     */
    public org.floit.web.config.Include getInclude(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._includeList.size()) {
            throw new IndexOutOfBoundsException("getInclude: Index value '" + index + "' not in range [0.." + (this._includeList.size() - 1) + "]");
        }

        return (org.floit.web.config.Include) _includeList.get(index);
    }

    /**
     * Method getInclude.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.web.config.Include[] getInclude(
    ) {
        org.floit.web.config.Include[] array = new org.floit.web.config.Include[0];
        return (org.floit.web.config.Include[]) this._includeList.toArray(array);
    }

    /**
     * Method getIncludeCount.
     * 
     * @return the size of this collection
     */
    public int getIncludeCount(
    ) {
        return this._includeList.size();
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
     * Method iterateInclude.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.web.config.Include> iterateInclude(
    ) {
        return this._includeList.iterator();
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
    public void removeAllInclude(
    ) {
        this._includeList.clear();
    }

    /**
     * Method removeInclude.
     * 
     * @param vInclude
     * @return true if the object was removed from the collection.
     */
    public boolean removeInclude(
            final org.floit.web.config.Include vInclude) {
        boolean removed = _includeList.remove(vInclude);
        return removed;
    }

    /**
     * Method removeIncludeAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.web.config.Include removeIncludeAt(
            final int index) {
        java.lang.Object obj = this._includeList.remove(index);
        return (org.floit.web.config.Include) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vInclude
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setInclude(
            final int index,
            final org.floit.web.config.Include vInclude)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._includeList.size()) {
            throw new IndexOutOfBoundsException("setInclude: Index value '" + index + "' not in range [0.." + (this._includeList.size() - 1) + "]");
        }

        this._includeList.set(index, vInclude);
    }

    /**
     * 
     * 
     * @param vIncludeArray
     */
    public void setInclude(
            final org.floit.web.config.Include[] vIncludeArray) {
        //-- copy array
        _includeList.clear();

        for (int i = 0; i < vIncludeArray.length; i++) {
                this._includeList.add(vIncludeArray[i]);
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
     * @return the unmarshaled org.floit.web.config.Includes
     */
    public static org.floit.web.config.Includes unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.Includes) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.Includes.class, reader);
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
