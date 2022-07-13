package com.karthick.io.phones.parser.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.dto.TaskDto;
import com.karthick.io.phones.parser.dto.TaskResultsDto;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.exception.TaskNotFoundException;

public interface PhoneParser {

	TaskResultsDto getTask(String taskId) throws InvalidInputException, TaskNotFoundException;

	TaskDto submitTask(MultipartFile file, String userName) throws InvalidInputException;

	List<TaskDto> getAllTasks();

	void deleteTask(String taskId) throws InvalidInputException, TaskNotFoundException;
}
