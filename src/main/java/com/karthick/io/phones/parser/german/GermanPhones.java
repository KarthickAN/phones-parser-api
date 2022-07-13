package com.karthick.io.phones.parser.german;

import com.karthick.io.phones.parser.entity.PhoneNumber;
import com.karthick.io.phones.parser.entity.PhoneNumbers;

public class GermanPhones {

	private GermanPhones() {

	}

	public static PhoneNumbers phoneNumber(String phone) {
		String countryCode = phone.startsWith("0049") ? "0049" : "+49";
		String number = phone.startsWith("0049") ? phone.replace("0049", "") : phone.replace("+49", "");
		PhoneNumber phoneNumber = new PhoneNumber("GERMANY", number);
		return PhoneNumbers.builder().countryCode(countryCode).phoneNumber(phoneNumber).build();
	}
}
