package com.karthick.io.phones.parser.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "phone_numbers")
public class PhoneNumbers {

	@EmbeddedId
	private PhoneNumber phoneNumber;

	private String countryCode;

}
