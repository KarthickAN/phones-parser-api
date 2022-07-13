package com.karthick.io.phones.parser.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResultsDto {

	private UUID taskId;

	private long startTime;

	private long endTime;

	private String status;

	private long totalEntries;

	private String fileName;

	private String startedBy;

	private List<PhoneNumberDto> phoneNumbers;
}
