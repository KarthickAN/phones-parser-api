package com.karthick.io.phones.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthick.io.phones.parser.entity.PhoneNumber;
import com.karthick.io.phones.parser.entity.PhoneNumbers;

@Repository
public interface PhoneNumbersRepository extends JpaRepository<PhoneNumbers, PhoneNumber> {

}
