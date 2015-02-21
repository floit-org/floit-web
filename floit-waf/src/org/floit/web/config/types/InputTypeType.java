/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.3</a>, using an XML
 * Schema.
 * $Id$
 */

package org.floit.web.config.types;

/**
 * Enumeration InputTypeType.
 * 
 * @version $Revision$ $Date$
 */
@SuppressWarnings("serial")
public enum InputTypeType implements java.io.Serializable {


      //------------------/
     //- Enum Constants -/
    //------------------/

    /**
     * Constant RADIO
     */
    RADIO("radio"),
    /**
     * Constant CLICK
     */
    CLICK("click"),
    /**
     * Constant CHECKBOX
     */
    CHECKBOX("checkbox"),
    /**
     * Constant INPUT
     */
    INPUT("input"),
    /**
     * Constant SELECT
     */
    SELECT("select"),
    /**
     * Constant SELECT_LINK
     */
    SELECT_LINK("select-link"),
    /**
     * Constant MULTI_SELECT
     */
    MULTI_SELECT("multi-select"),
    /**
     * Constant FILE
     */
    FILE("file");

      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field value.
     */
    private final java.lang.String value;


      //----------------/
     //- Constructors -/
    //----------------/

    private InputTypeType(final java.lang.String value) {
        this.value = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method fromValue.
     * 
     * @param value
     * @return the constant for this value
     */
    public static org.floit.web.config.types.InputTypeType fromValue(
            final java.lang.String value) {
        for (InputTypeType c: InputTypeType.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException(value);
    }

    /**
     * 
     * 
     * @param value
     */
    public void setValue(
            final java.lang.String value) {
    }

    /**
     * Method toString.
     * 
     * @return the value of this constant
     */
    public java.lang.String toString(
    ) {
        return this.value;
    }

    /**
     * Method value.
     * 
     * @return the value of this constant
     */
    public java.lang.String value(
    ) {
        return this.value;
    }

}
