package org.floit.waf.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;
import org.floit.waf.config.Input;
import org.floit.waf.exceptions.InputHandlerException;

public class InputHandlerExceptionTest {

	String inputName = "My Test Input";
	Input input = new Input();

	@Test
	public void testHasNoTypeError() {

		String attribute = "Type";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_TYPE);
		assertEquals("Error string does not match", ihe.getError(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}
	
	@Test
	public void testHasNoLocatorError() {

		String attribute = "Locator";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_LOCATOR);
		assertEquals("Error string does not match", ihe.getError(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}	
	
	@Test
	public void testHasNoPageElementError() {

		String attribute = "PageElement";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_PAGE_ELEMENT);
		assertEquals("Error string does not match", ihe.getError(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}	
	
	@Test
	public void testHasNoValueError() {

		String attribute = "Value";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_VALUE);
		assertEquals("Error string does not match", ihe.getError(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}
	
	@Test
	public void testHasNoTypeMessage() {

		String attribute = "Type";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_TYPE);
		assertEquals("Message string does not match", ihe.getMessage(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}
	
	@Test
	public void testHasNoLocatorMessage() {

		String attribute = "Locator";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_LOCATOR);
		assertEquals("Message string does not match", ihe.getMessage(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}	
	
	@Test
	public void testHasNoPageElementMessage() {

		String attribute = "PageElement";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_PAGE_ELEMENT);
		assertEquals("Message string does not match", ihe.getMessage(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}	
	
	@Test
	public void testHasNoValueMessage() {

		String attribute = "Value";
		input.setName(inputName);

		InputHandlerException ihe = new InputHandlerException(input, InputHandlerException.ERR_VALUE);
		assertEquals("Message string does not match", ihe.getMessage(), "The " + attribute + " attribute was not set for Input [" + inputName + "]");
	}	

}
