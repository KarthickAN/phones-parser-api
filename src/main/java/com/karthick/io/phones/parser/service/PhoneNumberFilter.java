package com.karthick.io.phones.parser.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.entity.PhoneNumbers;
import com.karthick.io.phones.parser.exception.InvalidInputException;

public interface PhoneNumberFilter {

	List<PhoneNumbers> filter(MultipartFile file) throws InvalidInputException;
}
