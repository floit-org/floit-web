package org.floit.waf.exceptions;

import org.floit.waf.config.Input;

/**
 * Handle validation errors found in the Input element.
 * @author Kieran
 *
 */
public class InputHandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT = "One or more mandatory Input attributes not set in config.";
	public static final int ERR_TYPE = 1;
	public static final int ERR_PAGE_ELEMENT = 2;
	public static final int ERR_VALUE = 3;
	public static final int ERR_LOCATOR = 4;
	
	private static String errorTypeStr = "The Type";
	public static final String errorPageElementStr= "The PageElement";
	public static final String errorValueStr = "The Value";
	public static final String errorLocatorStr = "The Locator";	
	
	private String msgPart = "attribute was not set for Input [";
	private String name = "";
	private String message;

	public InputHandlerException(Input input, int type) {
		super();
		this.name = input.getName();
		this.message = setMessage(type);
	}

	public InputHandlerException() {
		super(DEFAULT);
		this.message = setMessage(0);
	}

	public String getError() {
		return this.message;
	}
	
	public String getMessage() {
		return this.message;
	}	
	
	private String setMessage(int type) {
		
		StringBuffer sb = new StringBuffer();
		
		switch (type) {
		case ERR_TYPE:
			sb.append(errorTypeStr);
			break;
		case ERR_PAGE_ELEMENT:
			sb.append(errorPageElementStr);			
			break;
		case ERR_VALUE:
			sb.append(errorValueStr);				
			break;	
		case ERR_LOCATOR:
			sb.append(errorLocatorStr);				
			break;				
		default:
			sb.append("An");
			break;
		}
		
		sb.append(" ");
		sb.append(msgPart);
		sb.append(this.name);
		sb.append("]");
		
		return sb.toString();
	}

}
