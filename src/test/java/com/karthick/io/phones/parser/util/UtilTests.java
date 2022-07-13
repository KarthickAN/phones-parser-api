package com.karthick.io.phones.parser.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.karthick.io.phones.parser.exception.InvalidInputException;

class UtilTests {

	@Test
	void validUUID() throws InvalidInputException {
		UUID expected = UUID.randomUUID();
		UUID actual = Utils.parseUUID(expected.toString());
		assertEquals(expected, actual);
	}

	@Test
	void invalidUUID() throws InvalidInputException {
		assertThrows(InvalidInputException.class, () -> Utils.parseUUID("invalid"),
				"Expected parseUUID() to throw, but it didn't");
	}

}
