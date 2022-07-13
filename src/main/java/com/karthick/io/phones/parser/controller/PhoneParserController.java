package com.karthick.io.phones.parser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.dto.TaskDto;
import com.karthick.io.phones.parser.dto.TaskResultsDto;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.exception.NoContentException;
import com.karthick.io.phones.parser.exception.TaskNotFoundException;
import com.karthick.io.phones.parser.service.PhoneParser;

@RestController
@RequestMapping(value = "phones/v1")
public class PhoneParserController {

	@Autowired
	private PhoneParser parser;

	@GetMapping(value = "/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TaskResultsDto getTask(@PathVariable String taskId) throws TaskNotFoundException, InvalidInputException {
		return parser.getTask(taskId);
	}

	@GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskDto> getTasks() throws NoContentException {
		List<TaskDto> tasks = parser.getAllTasks();
		if (tasks.isEmpty())
			throw new NoContentException("No tasks found!");
		return parser.getAllTasks();
	}

	@PostMapping(value = "/tasks", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public TaskDto submitTask(@RequestParam("file") @RequestPart(required = true) MultipartFile file)
			throws InvalidInputException {
		return parser.submitTask(file, "DEFAULT_USER");
	}

	@DeleteMapping(value = "/tasks/{taskId}")
	public void deleteTask(@PathVariable String taskId) throws InvalidInputException, TaskNotFoundException {
		parser.deleteTask(taskId);
	}

}
