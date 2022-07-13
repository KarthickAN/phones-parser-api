package com.karthick.io.phones.parser.util;

import java.util.UUID;

import com.karthick.io.phones.parser.exception.InvalidInputException;

public class Utils {

	private Utils() {

	}

	public static UUID parseUUID(String taskId) throws InvalidInputException {
		try {
			return UUID.fromString(taskId);
		} catch (IllegalArgumentException ex) {
			throw new InvalidInputException(ex.getMessage());
		}

	}

}
