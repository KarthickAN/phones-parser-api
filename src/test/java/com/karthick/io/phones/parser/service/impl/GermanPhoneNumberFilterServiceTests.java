package com.karthick.io.phones.parser.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.entity.PhoneNumbers;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.german.GermanPhoneNumberFilterService;

@SpringBootTest
public class GermanPhoneNumberFilterServiceTests {

	@Autowired
	private GermanPhoneNumberFilterService service;

	@Value("classpath:phone_numbers.txt")
	private Resource phoneNumberFile;

	@Test
	public void filterNullFileTest() throws InvalidInputException {
		assertThrows(InvalidInputException.class, () -> service.filter(null),
				"Expected parseUUID() to throw, but it didn't");
	}

	@Test
	public void filterEmptyFileTest() throws InvalidInputException, IOException {
		File file = new File("numbers.txt");
		file.createNewFile();
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);

		assertThrows(InvalidInputException.class, () -> service.filter(multipartFile),
				"Expected parseUUID() to throw, but it didn't");
	}

	@Test
	public void filterFileTest() throws InvalidInputException, IOException {
		MultipartFile multipartFile = new MockMultipartFile("file", phoneNumberFile.getFilename(), "text/plain",
				phoneNumberFile.getInputStream());
		List<PhoneNumbers> numbers = service.filter(multipartFile);
		assertFalse(numbers.isEmpty());
	}
}
