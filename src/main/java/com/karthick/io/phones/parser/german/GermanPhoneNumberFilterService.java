package com.karthick.io.phones.parser.german;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.entity.PhoneNumbers;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.service.PhoneNumberFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GermanPhoneNumberFilterService implements PhoneNumberFilter {

	@Override
	public List<PhoneNumbers> filter(MultipartFile file) throws InvalidInputException {
		if (file == null || file.isEmpty())
			throw new InvalidInputException("File is empty or null");
		List<PhoneNumbers> filtered = Collections.emptyList();
		try {
			InputStream inputStream = file.getInputStream();
			filtered = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
					.map(line -> line.replace(" ", "")).filter(line -> Pattern.matches("^(\\+49|0049)\\d{11}", line))
					.map(GermanPhones::phoneNumber).distinct().toList();
		} catch (IOException e) {
			log.error("Exception while parsing the file {}", e);
			throw new InvalidInputException("Error while parsing the file " + file.getOriginalFilename());
		}

		return filtered;
	}

}
