package com.karthick.io.phones.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDto {

	private String countryCode;

	private String phoneNumber;

	private String country;
}
