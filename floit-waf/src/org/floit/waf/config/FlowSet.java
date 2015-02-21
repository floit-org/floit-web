/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.waf.config;

/**
 * Collection of pages flow definitions. Each flow defines a set of
 * web pages. The sequence of pages must be a logical path through
 * a web application
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class FlowSet implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Defines a valid web flow, which is a logical sequence of web
     * pages through a web application.
     */
    private java.util.List<org.floit.waf.config.Flow> _flowList;


      //----------------/
     //- Constructors -/
    //----------------/

    public FlowSet() {
        super();
        this._flowList = new java.util.ArrayList<org.floit.waf.config.Flow>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vFlow
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addFlow(
            final org.floit.waf.config.Flow vFlow)
    throws java.lang.IndexOutOfBoundsException {
        this._flowList.add(vFlow);
    }

    /**
     * 
     * 
     * @param index
     * @param vFlow
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addFlow(
            final int index,
            final org.floit.waf.config.Flow vFlow)
    throws java.lang.IndexOutOfBoundsException {
        this._flowList.add(index, vFlow);
    }

    /**
     * Method enumerateFlow.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.waf.config.Flow> enumerateFlow(
    ) {
        return java.util.Collections.enumeration(this._flowList);
    }

    /**
     * Method getFlow.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.waf.config.Flow at the
     * given index
     */
    public org.floit.waf.config.Flow getFlow(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._flowList.size()) {
            throw new IndexOutOfBoundsException("getFlow: Index value '" + index + "' not in range [0.." + (this._flowList.size() - 1) + "]");
        }

        return (org.floit.waf.config.Flow) _flowList.get(index);
    }

    /**
     * Method getFlow.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.waf.config.Flow[] getFlow(
    ) {
        org.floit.waf.config.Flow[] array = new org.floit.waf.config.Flow[0];
        return (org.floit.waf.config.Flow[]) this._flowList.toArray(array);
    }

    /**
     * Method getFlowCount.
     * 
     * @return the size of this collection
     */
    public int getFlowCount(
    ) {
        return this._flowList.size();
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
     * Method iterateFlow.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.waf.config.Flow> iterateFlow(
    ) {
        return this._flowList.iterator();
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
    public void removeAllFlow(
    ) {
        this._flowList.clear();
    }

    /**
     * Method removeFlow.
     * 
     * @param vFlow
     * @return true if the object was removed from the collection.
     */
    public boolean removeFlow(
            final org.floit.waf.config.Flow vFlow) {
        boolean removed = _flowList.remove(vFlow);
        return removed;
    }

    /**
     * Method removeFlowAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.waf.config.Flow removeFlowAt(
            final int index) {
        java.lang.Object obj = this._flowList.remove(index);
        return (org.floit.waf.config.Flow) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vFlow
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setFlow(
            final int index,
            final org.floit.waf.config.Flow vFlow)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._flowList.size()) {
            throw new IndexOutOfBoundsException("setFlow: Index value '" + index + "' not in range [0.." + (this._flowList.size() - 1) + "]");
        }

        this._flowList.set(index, vFlow);
    }

    /**
     * 
     * 
     * @param vFlowArray
     */
    public void setFlow(
            final org.floit.waf.config.Flow[] vFlowArray) {
        //-- copy array
        _flowList.clear();

        for (int i = 0; i < vFlowArray.length; i++) {
                this._flowList.add(vFlowArray[i]);
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
     * @return the unmarshaled org.floit.waf.config.FlowSet
     */
    public static org.floit.waf.config.FlowSet unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.waf.config.FlowSet) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.waf.config.FlowSet.class, reader);
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
