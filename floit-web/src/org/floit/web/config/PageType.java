/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config;

/**
 * Class PageType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public class PageType extends org.floit.web.config.SetType 
implements java.io.Serializable
{


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Boolean attribute to enable preprocessing of the page
     */
    private boolean _preProcess;

    /**
     * keeps track of state for field: _preProcess
     */
    private boolean _has_preProcess;

    /**
     * The plug-in which is to be used to preprocess the page
     */
    private java.lang.String _preProcessor;

    /**
     * The plug-in used handle custom page wait events such as Ajax
     * loaded pages
     */
    private java.lang.String _pageWaitHandler;

    /**
     * Field _progressActionEnabled.
     */
    private boolean _progressActionEnabled;

    /**
     * keeps track of state for field: _progressActionEnabled
     */
    private boolean _has_progressActionEnabled;

    /**
     * This page is defined in an external configuration file
     * identified by the IncludedId. The external configration file
     * must be included in the main or primary config. See
     * /TestConfig/Includes for more details.
     */
    private java.lang.String _includeId;

    /**
     * Field _isPopupWindow.
     */
    private boolean _isPopupWindow;

    /**
     * keeps track of state for field: _isPopupWindow
     */
    private boolean _has_isPopupWindow;

    /**
     * Field _popupWindowLocator.
     */
    private java.lang.String _popupWindowLocator;

    /**
     * Represents an element or entity on the web page.
     */
    private java.util.List<org.floit.web.config.Input> _inputList;

    /**
     * Defines how the test harness should progress to the next
     * page in the sequence. The default action is the first in the
     * sequence.
     */
    private org.floit.web.config.Progress _progress;

    /**
     * Collection of serach terms used to verify the content on the
     * page
     */
    private org.floit.web.config.SearchTerms _searchTerms;

    /**
     * A collection of page elements
     */
    private org.floit.web.config.PageElements _pageElements;


      //----------------/
     //- Constructors -/
    //----------------/

    public PageType() {
        super();
        this._inputList = new java.util.ArrayList<org.floit.web.config.Input>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vInput
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInput(
            final org.floit.web.config.Input vInput)
    throws java.lang.IndexOutOfBoundsException {
        this._inputList.add(vInput);
    }

    /**
     * 
     * 
     * @param index
     * @param vInput
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addInput(
            final int index,
            final org.floit.web.config.Input vInput)
    throws java.lang.IndexOutOfBoundsException {
        this._inputList.add(index, vInput);
    }

    /**
     */
    public void deleteIsPopupWindow(
    ) {
        this._has_isPopupWindow= false;
    }

    /**
     */
    public void deletePreProcess(
    ) {
        this._has_preProcess= false;
    }

    /**
     */
    public void deleteProgressActionEnabled(
    ) {
        this._has_progressActionEnabled= false;
    }

    /**
     * Method enumerateInput.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<? extends org.floit.web.config.Input> enumerateInput(
    ) {
        return java.util.Collections.enumeration(this._inputList);
    }

    /**
     * Returns the value of field 'includeId'. The field
     * 'includeId' has the following description: This page is
     * defined in an external configuration file identified by the
     * IncludedId. The external configration file must be included
     * in the main or primary config. See /TestConfig/Includes for
     * more details.
     * 
     * @return the value of field 'IncludeId'.
     */
    public java.lang.String getIncludeId(
    ) {
        return this._includeId;
    }

    /**
     * Method getInput.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the org.floit.web.config.Input at the
     * given index
     */
    public org.floit.web.config.Input getInput(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._inputList.size()) {
            throw new IndexOutOfBoundsException("getInput: Index value '" + index + "' not in range [0.." + (this._inputList.size() - 1) + "]");
        }

        return (org.floit.web.config.Input) _inputList.get(index);
    }

    /**
     * Method getInput.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public org.floit.web.config.Input[] getInput(
    ) {
        org.floit.web.config.Input[] array = new org.floit.web.config.Input[0];
        return (org.floit.web.config.Input[]) this._inputList.toArray(array);
    }

    /**
     * Method getInputCount.
     * 
     * @return the size of this collection
     */
    public int getInputCount(
    ) {
        return this._inputList.size();
    }

    /**
     * Returns the value of field 'isPopupWindow'.
     * 
     * @return the value of field 'IsPopupWindow'.
     */
    public boolean getIsPopupWindow(
    ) {
        return this._isPopupWindow;
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
     * Returns the value of field 'pageWaitHandler'. The field
     * 'pageWaitHandler' has the following description: The plug-in
     * used handle custom page wait events such as Ajax loaded
     * pages
     * 
     * @return the value of field 'PageWaitHandler'.
     */
    public java.lang.String getPageWaitHandler(
    ) {
        return this._pageWaitHandler;
    }

    /**
     * Returns the value of field 'popupWindowLocator'.
     * 
     * @return the value of field 'PopupWindowLocator'.
     */
    public java.lang.String getPopupWindowLocator(
    ) {
        return this._popupWindowLocator;
    }

    /**
     * Returns the value of field 'preProcess'. The field
     * 'preProcess' has the following description: Boolean
     * attribute to enable preprocessing of the page
     * 
     * @return the value of field 'PreProcess'.
     */
    public boolean getPreProcess(
    ) {
        return this._preProcess;
    }

    /**
     * Returns the value of field 'preProcessor'. The field
     * 'preProcessor' has the following description: The plug-in
     * which is to be used to preprocess the page
     * 
     * @return the value of field 'PreProcessor'.
     */
    public java.lang.String getPreProcessor(
    ) {
        return this._preProcessor;
    }

    /**
     * Returns the value of field 'progress'. The field 'progress'
     * has the following description: Defines how the test harness
     * should progress to the next page in the sequence. The
     * default action is the first in the sequence.
     * 
     * @return the value of field 'Progress'.
     */
    public org.floit.web.config.Progress getProgress(
    ) {
        return this._progress;
    }

    /**
     * Returns the value of field 'progressActionEnabled'.
     * 
     * @return the value of field 'ProgressActionEnabled'.
     */
    public boolean getProgressActionEnabled(
    ) {
        return this._progressActionEnabled;
    }

    /**
     * Returns the value of field 'searchTerms'. The field
     * 'searchTerms' has the following description: Collection of
     * serach terms used to verify the content on the page
     * 
     * @return the value of field 'SearchTerms'.
     */
    public org.floit.web.config.SearchTerms getSearchTerms(
    ) {
        return this._searchTerms;
    }

    /**
     * Method hasIsPopupWindow.
     * 
     * @return true if at least one IsPopupWindow has been added
     */
    public boolean hasIsPopupWindow(
    ) {
        return this._has_isPopupWindow;
    }

    /**
     * Method hasPreProcess.
     * 
     * @return true if at least one PreProcess has been added
     */
    public boolean hasPreProcess(
    ) {
        return this._has_preProcess;
    }

    /**
     * Method hasProgressActionEnabled.
     * 
     * @return true if at least one ProgressActionEnabled has been
     * added
     */
    public boolean hasProgressActionEnabled(
    ) {
        return this._has_progressActionEnabled;
    }

    /**
     * Returns the value of field 'isPopupWindow'.
     * 
     * @return the value of field 'IsPopupWindow'.
     */
    public boolean isIsPopupWindow(
    ) {
        return this._isPopupWindow;
    }

    /**
     * Returns the value of field 'preProcess'. The field
     * 'preProcess' has the following description: Boolean
     * attribute to enable preprocessing of the page
     * 
     * @return the value of field 'PreProcess'.
     */
    public boolean isPreProcess(
    ) {
        return this._preProcess;
    }

    /**
     * Returns the value of field 'progressActionEnabled'.
     * 
     * @return the value of field 'ProgressActionEnabled'.
     */
    public boolean isProgressActionEnabled(
    ) {
        return this._progressActionEnabled;
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
     * Method iterateInput.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<? extends org.floit.web.config.Input> iterateInput(
    ) {
        return this._inputList.iterator();
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
    public void removeAllInput(
    ) {
        this._inputList.clear();
    }

    /**
     * Method removeInput.
     * 
     * @param vInput
     * @return true if the object was removed from the collection.
     */
    public boolean removeInput(
            final org.floit.web.config.Input vInput) {
        boolean removed = _inputList.remove(vInput);
        return removed;
    }

    /**
     * Method removeInputAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public org.floit.web.config.Input removeInputAt(
            final int index) {
        java.lang.Object obj = this._inputList.remove(index);
        return (org.floit.web.config.Input) obj;
    }

    /**
     * Sets the value of field 'includeId'. The field 'includeId'
     * has the following description: This page is defined in an
     * external configuration file identified by the IncludedId.
     * The external configration file must be included in the main
     * or primary config. See /TestConfig/Includes for more
     * details.
     * 
     * @param includeId the value of field 'includeId'.
     */
    public void setIncludeId(
            final java.lang.String includeId) {
        this._includeId = includeId;
    }

    /**
     * 
     * 
     * @param index
     * @param vInput
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setInput(
            final int index,
            final org.floit.web.config.Input vInput)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._inputList.size()) {
            throw new IndexOutOfBoundsException("setInput: Index value '" + index + "' not in range [0.." + (this._inputList.size() - 1) + "]");
        }

        this._inputList.set(index, vInput);
    }

    /**
     * 
     * 
     * @param vInputArray
     */
    public void setInput(
            final org.floit.web.config.Input[] vInputArray) {
        //-- copy array
        _inputList.clear();

        for (int i = 0; i < vInputArray.length; i++) {
                this._inputList.add(vInputArray[i]);
        }
    }

    /**
     * Sets the value of field 'isPopupWindow'.
     * 
     * @param isPopupWindow the value of field 'isPopupWindow'.
     */
    public void setIsPopupWindow(
            final boolean isPopupWindow) {
        this._isPopupWindow = isPopupWindow;
        this._has_isPopupWindow = true;
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
     * Sets the value of field 'pageWaitHandler'. The field
     * 'pageWaitHandler' has the following description: The plug-in
     * used handle custom page wait events such as Ajax loaded
     * pages
     * 
     * @param pageWaitHandler the value of field 'pageWaitHandler'.
     */
    public void setPageWaitHandler(
            final java.lang.String pageWaitHandler) {
        this._pageWaitHandler = pageWaitHandler;
    }

    /**
     * Sets the value of field 'popupWindowLocator'.
     * 
     * @param popupWindowLocator the value of field
     * 'popupWindowLocator'.
     */
    public void setPopupWindowLocator(
            final java.lang.String popupWindowLocator) {
        this._popupWindowLocator = popupWindowLocator;
    }

    /**
     * Sets the value of field 'preProcess'. The field 'preProcess'
     * has the following description: Boolean attribute to enable
     * preprocessing of the page
     * 
     * @param preProcess the value of field 'preProcess'.
     */
    public void setPreProcess(
            final boolean preProcess) {
        this._preProcess = preProcess;
        this._has_preProcess = true;
    }

    /**
     * Sets the value of field 'preProcessor'. The field
     * 'preProcessor' has the following description: The plug-in
     * which is to be used to preprocess the page
     * 
     * @param preProcessor the value of field 'preProcessor'.
     */
    public void setPreProcessor(
            final java.lang.String preProcessor) {
        this._preProcessor = preProcessor;
    }

    /**
     * Sets the value of field 'progress'. The field 'progress' has
     * the following description: Defines how the test harness
     * should progress to the next page in the sequence. The
     * default action is the first in the sequence.
     * 
     * @param progress the value of field 'progress'.
     */
    public void setProgress(
            final org.floit.web.config.Progress progress) {
        this._progress = progress;
    }

    /**
     * Sets the value of field 'progressActionEnabled'.
     * 
     * @param progressActionEnabled the value of field
     * 'progressActionEnabled'.
     */
    public void setProgressActionEnabled(
            final boolean progressActionEnabled) {
        this._progressActionEnabled = progressActionEnabled;
        this._has_progressActionEnabled = true;
    }

    /**
     * Sets the value of field 'searchTerms'. The field
     * 'searchTerms' has the following description: Collection of
     * serach terms used to verify the content on the page
     * 
     * @param searchTerms the value of field 'searchTerms'.
     */
    public void setSearchTerms(
            final org.floit.web.config.SearchTerms searchTerms) {
        this._searchTerms = searchTerms;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled org.floit.web.config.PageType
     */
    public static org.floit.web.config.PageType unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.floit.web.config.PageType) org.exolab.castor.xml.Unmarshaller.unmarshal(org.floit.web.config.PageType.class, reader);
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
