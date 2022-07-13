package com.karthick.io.phones.parser.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.converter.ModelConverter;
import com.karthick.io.phones.parser.dto.TaskDto;
import com.karthick.io.phones.parser.dto.TaskResultsDto;
import com.karthick.io.phones.parser.entity.PhoneNumbers;
import com.karthick.io.phones.parser.entity.Status;
import com.karthick.io.phones.parser.entity.Task;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.exception.TaskNotFoundException;
import com.karthick.io.phones.parser.repository.PhoneNumbersRepository;
import com.karthick.io.phones.parser.repository.TaskRepository;
import com.karthick.io.phones.parser.service.PhoneNumberFilter;
import com.karthick.io.phones.parser.service.PhoneParser;
import com.karthick.io.phones.parser.util.Utils;

@Service
public class PhoneParserService implements PhoneParser {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private PhoneNumbersRepository phoneNumbersRepository;

	@Autowired
	private ModelConverter converter;

	@Autowired
	private PhoneNumberFilter germanPhoneNumberFilter;

	@Override
	public TaskDto submitTask(MultipartFile file, String userName) throws InvalidInputException {
		Instant start = Instant.now();
		List<PhoneNumbers> filtered = germanPhoneNumberFilter.filter(file);
		List<PhoneNumbers> filteredDuplicates = filtered.stream()
				.filter(num -> !phoneNumbersRepository.existsById(num.getPhoneNumber())).toList();
		if (filteredDuplicates.isEmpty())
			throw new InvalidInputException("No new phone numbers found in the file!");
		Task t = Task.builder().fileName(file.getOriginalFilename()).startedBy(userName)
				.startTime(start.getEpochSecond()).status(Status.COMPLETED).endTime(Instant.now().getEpochSecond())
				.phoneNumbers(filteredDuplicates).totalEntries(filteredDuplicates.size()).build();
		return converter.taskEntityToDto(taskRepository.save(t));
	}

	@Override
	public List<TaskDto> getAllTasks() {
		return converter.taskEntityToDto(taskRepository.findAll());
	}

	@Override
	public TaskResultsDto getTask(String taskId) throws TaskNotFoundException, InvalidInputException {
		UUID taskUUID = Utils.parseUUID(taskId);
		Optional<Task> task = taskRepository.findById(taskUUID);
		if (task.isEmpty())
			throw new TaskNotFoundException("Task with id " + taskId + " not found");
		return converter.taskEntityToResultsDto(task.get());
	}

	@Override
	public void deleteTask(String taskId) throws TaskNotFoundException, InvalidInputException {
		try {
			UUID taskUUID = Utils.parseUUID(taskId);
			taskRepository.deleteById(taskUUID);
		} catch (EmptyResultDataAccessException e) {
			throw new TaskNotFoundException("Task with id " + taskId + " not found");
		}
	}

}
