/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * Details of an included test configuration. This test config will
 * be accessible to the test framework and can be used in the same
 * way as the parent test config. NOTE: Includes within included
 * files will be ignored to prevent circular references
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Include implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The location of the test config on the file system. This can
     * be a relative or absolute path
     */
    private java.lang.String _path;

    /**
     * The ID is used to reference the imported test config within
     * the test framework. Only upper and lower case chars are
     * permitted.
     */
    private java.lang.String _ID;


      //----------------/
     //- Constructors -/
    //----------------/

    public Include() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'ID'. The field 'ID' has the
     * following description: The ID is used to reference the
     * imported test config within the test framework. Only upper
     * and lower case chars are permitted.
     * 
     * @return the value of field 'ID'.
     */
    public java.lang.String getID(
    ) {
        return this._ID;
    }

    /**
     * Returns the value of field 'path'. The field 'path' has the
     * following description: The location of the test config on
     * the file system. This can be a relative or absolute path
     * 
     * @return the value of field 'Path'.
     */
    public java.lang.String getPath(
    ) {
        return this._path;
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
     * Sets the value of field 'ID'. The field 'ID' has the
     * following description: The ID is used to reference the
     * imported test config within the test framework. Only upper
     * and lower case chars are permitted.
     * 
     * @param ID the value of field 'ID'.
     */
    public void setID(
            final java.lang.String ID) {
        this._ID = ID;
    }

    /**
     * Sets the value of field 'path'. The field 'path' has the
     * following description: The location of the test config on
     * the file system. This can be a relative or absolute path
     * 
     * @param path the value of field 'path'.
     */
    public void setPath(
            final java.lang.String path) {
        this._path = path;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.web.config.Include
     */
    public static org.floit.web.config.Include unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.Include) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.Include.class, reader);
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
