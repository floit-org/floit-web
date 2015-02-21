/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.waf.config;

/**
 * Defines how the test harness should progress to the next page in
 * the sequence. The default action is the first in the sequence.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class Progress implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * References an Action from the collection of Actions.
     */
    private int _actionIndex;

    /**
     * keeps track of state for field: _actionIndex
     */
    private boolean _has_actionIndex;

    /**
     * If no Action elements are defined then the progress action
     * Locator can be defined here.
     */
    private java.lang.Object _actionLocator;

    /**
     * Each action defines a progress path from the current HTML
     * page to the next. E.g. a link or submit button. There can be
     * multiple exit points on a page and each is defined by an
     * Action
     */
    private java.util.List<org.floit.waf.config.Action> _actionList;

    /**
     * Simulates browser button functionality.
     */
    private java.util.List<org.floit.waf.config.BrowserAction> _browserActionList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Progress() {
        super();
        this._actionList = new java.util.ArrayList<org.floit.waf.config.Action>();
        this._browserActionList = new java.util.ArrayList<org.floit.waf.config.BrowserAction>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vAction
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAction(
            final org.floit.waf.config.Action vAction)
    throws java.lang.IndexOutOfBoundsException {
        this._actionList.add(vAction);
    }

    /**
     * 
     * 
     * @param index
     * @param vAction
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addAction(
            final int index,
            final org.floit.waf.config.Action vAction)
    throws java.lang.IndexOutOfBoundsException {
        this._actionList.add(index, vAction);
    }

    /**
     * 
     * 
     * @param vBrowserAction
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBrowserAction(
            final org.floit.waf.config.BrowserAction vBrowserAction)
    throws java.lang.IndexOutOfBoundsException {
        this._browserActionList.add(vBrowserAction);
    }

    /**
     * 
     * 
     * @param index
     * @param vBrowserAction
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addBrowserAction(
            final int index,
            final org.floit.waf.config.BrowserAction vBrowserAction)
    throws java.lang.IndexOutOfBoundsException {
        this._browserActionList.add(index, vBrowserAction);
    }

    /**
     */
    public void deleteActionIndex(
    ) {
        this._has_actionIndex= false;
    }

    /**
     * Method enumerateAction.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.waf.config.Action> enumerateAction(
    ) {
        return java.util.Collections.enumeration(this._actionList);
    }

    /**
     * Method enumerateBrowserAction.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.waf.config.BrowserAction> enumerateBrowserAction(
    ) {
        return java.util.Collections.enumeration(this._browserActionList);
    }

    /**
     * Method getAction.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.waf.config.Action at the
     * given index
     */
    public org.floit.waf.config.Action getAction(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._actionList.size()) {
            throw new IndexOutOfBoundsException("getAction: Index value '" + index + "' not in range [0.." + (this._actionList.size() - 1) + "]");
        }

        return (org.floit.waf.config.Action) _actionList.get(index);
    }

    /**
     * Method getAction.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.waf.config.Action[] getAction(
    ) {
        org.floit.waf.config.Action[] array = new org.floit.waf.config.Action[0];
        return (org.floit.waf.config.Action[]) this._actionList.toArray(array);
    }

    /**
     * Method getActionCount.
     * 
     * @return the size of this collection
     */
    public int getActionCount(
    ) {
        return this._actionList.size();
    }

    /**
     * Returns the value of field 'actionIndex'. The field
     * 'actionIndex' has the following description: References an
     * Action from the collection of Actions.
     * 
     * @return the value of field 'ActionIndex'.
     */
    public int getActionIndex(
    ) {
        return this._actionIndex;
    }

    /**
     * Returns the value of field 'actionLocator'. The field
     * 'actionLocator' has the following description: If no Action
     * elements are defined then the progress action Locator can be
     * defined here.
     * 
     * @return the value of field 'ActionLocator'.
     */
    public java.lang.Object getActionLocator(
    ) {
        return this._actionLocator;
    }

    /**
     * Method getBrowserAction.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.waf.config.BrowserAction
     * at the given index
     */
    public org.floit.waf.config.BrowserAction getBrowserAction(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._browserActionList.size()) {
            throw new IndexOutOfBoundsException("getBrowserAction: Index value '" + index + "' not in range [0.." + (this._browserActionList.size() - 1) + "]");
        }

        return (org.floit.waf.config.BrowserAction) _browserActionList.get(index);
    }

    /**
     * Method getBrowserAction.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.waf.config.BrowserAction[] getBrowserAction(
    ) {
        org.floit.waf.config.BrowserAction[] array = new org.floit.waf.config.BrowserAction[0];
        return (org.floit.waf.config.BrowserAction[]) this._browserActionList.toArray(array);
    }

    /**
     * Method getBrowserActionCount.
     * 
     * @return the size of this collection
     */
    public int getBrowserActionCount(
    ) {
        return this._browserActionList.size();
    }

    /**
     * Method hasActionIndex.
     * 
     * @return true if at least one ActionIndex has been added
     */
    public boolean hasActionIndex(
    ) {
        return this._has_actionIndex;
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
     * Method iterateAction.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.waf.config.Action> iterateAction(
    ) {
        return this._actionList.iterator();
    }

    /**
     * Method iterateBrowserAction.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.waf.config.BrowserAction> iterateBrowserAction(
    ) {
        return this._browserActionList.iterator();
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
     * Method removeAction.
     * 
     * @param vAction
     * @return true if the object was removed from the collection.
     */
    public boolean removeAction(
            final org.floit.waf.config.Action vAction) {
        boolean removed = _actionList.remove(vAction);
        return removed;
    }

    /**
     * Method removeActionAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.waf.config.Action removeActionAt(
            final int index) {
        java.lang.Object obj = this._actionList.remove(index);
        return (org.floit.waf.config.Action) obj;
    }

    /**
     */
    public void removeAllAction(
    ) {
        this._actionList.clear();
    }

    /**
     */
    public void removeAllBrowserAction(
    ) {
        this._browserActionList.clear();
    }

    /**
     * Method removeBrowserAction.
     * 
     * @param vBrowserAction
     * @return true if the object was removed from the collection.
     */
    public boolean removeBrowserAction(
            final org.floit.waf.config.BrowserAction vBrowserAction) {
        boolean removed = _browserActionList.remove(vBrowserAction);
        return removed;
    }

    /**
     * Method removeBrowserActionAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.waf.config.BrowserAction removeBrowserActionAt(
            final int index) {
        java.lang.Object obj = this._browserActionList.remove(index);
        return (org.floit.waf.config.BrowserAction) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vAction
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setAction(
            final int index,
            final org.floit.waf.config.Action vAction)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._actionList.size()) {
            throw new IndexOutOfBoundsException("setAction: Index value '" + index + "' not in range [0.." + (this._actionList.size() - 1) + "]");
        }

        this._actionList.set(index, vAction);
    }

    /**
     * 
     * 
     * @param vActionArray
     */
    public void setAction(
            final org.floit.waf.config.Action[] vActionArray) {
        //-- copy array
        _actionList.clear();

        for (int i = 0; i < vActionArray.length; i++) {
                this._actionList.add(vActionArray[i]);
        }
    }

    /**
     * Sets the value of field 'actionIndex'. The field
     * 'actionIndex' has the following description: References an
     * Action from the collection of Actions.
     * 
     * @param actionIndex the value of field 'actionIndex'.
     */
    public void setActionIndex(
            final int actionIndex) {
        this._actionIndex = actionIndex;
        this._has_actionIndex = true;
    }

    /**
     * Sets the value of field 'actionLocator'. The field
     * 'actionLocator' has the following description: If no Action
     * elements are defined then the progress action Locator can be
     * defined here.
     * 
     * @param actionLocator the value of field 'actionLocator'.
     */
    public void setActionLocator(
            final java.lang.Object actionLocator) {
        this._actionLocator = actionLocator;
    }

    /**
     * 
     * 
     * @param index
     * @param vBrowserAction
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setBrowserAction(
            final int index,
            final org.floit.waf.config.BrowserAction vBrowserAction)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._browserActionList.size()) {
            throw new IndexOutOfBoundsException("setBrowserAction: Index value '" + index + "' not in range [0.." + (this._browserActionList.size() - 1) + "]");
        }

        this._browserActionList.set(index, vBrowserAction);
    }

    /**
     * 
     * 
     * @param vBrowserActionArray
     */
    public void setBrowserAction(
            final org.floit.waf.config.BrowserAction[] vBrowserActionArray) {
        //-- copy array
        _browserActionList.clear();

        for (int i = 0; i < vBrowserActionArray.length; i++) {
                this._browserActionList.add(vBrowserActionArray[i]);
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
     * @return the unmarshaled org.floit.waf.config.Progress
     */
    public static org.floit.waf.config.Progress unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.waf.config.Progress) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.waf.config.Progress.class, reader);
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
