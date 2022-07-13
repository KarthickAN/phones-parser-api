package com.karthick.io.phones.parser.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PhoneNumber implements Serializable {

	private static final long serialVersionUID = -5584503932224250098L;

	private String country;

	private String number;
}
