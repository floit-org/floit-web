package org.floit.waf.navigator.handlers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.floit.waf.config.Input;
import org.floit.waf.config.types.InputTypeType;
import org.floit.waf.exceptions.InputHandlerException;
import org.floit.waf.navigator.TestNavigator;
import org.floit.waf.navigator.handlers.InputHandler;

public class InputHandlerTest {
	
	String inputName = "My Test Input";
	Input input = new Input();
	TestNavigator nav = new TestNavigator();

	@Test
	public void testHandleInputNoTypeAttribute() {
		input.setName(inputName);
		InputHandler handler = new InputHandler(nav);
		String actual = "";
		String attribute = "Type";
		String expected = "The " + attribute + " attribute was not set for Input [" + inputName + "]";	
		
		try {
			handler.handleInput(input);
		} catch (InputHandlerException e) {
			actual = e.getError();
		} catch (Exception e) {
			fail("General Exception should not be thrown");
		}
		
		assertEquals("Error string does not match", expected, actual);
	}
	
	@Test
	public void testHandleInputNoValueAttribute() {
		input.setName(inputName);
		input.setType(InputTypeType.INPUT);
		
		InputHandler handler = new InputHandler(nav);
		String actual = "";
		String attribute = "Value";
		String expected = "The " + attribute + " attribute was not set for Input [" + inputName + "]";	
		
		try {
			handler.handleInput(input);
		} catch (InputHandlerException e) {
			actual = e.getError();
		} catch (Exception e) {
			fail("General Exception should not be thrown");
		}
		
		assertEquals("Error string does not match", expected, actual);
	}
	
	@Test
	public void testHandleInputNull() {
		
		InputHandler handler = new InputHandler(nav);
		String actual = "";
		String expected = null;	
		
		try {
			handler.handleInput(null);
		} catch (Exception e) {
			actual = e.getMessage();
		}
		
		assertEquals("Error string does not match", expected, actual);
	}	
}
