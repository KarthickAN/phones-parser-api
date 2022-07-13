package com.karthick.io.phones.parser.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.karthick.io.phones.parser.converter.ModelConverter;
import com.karthick.io.phones.parser.dto.TaskDto;
import com.karthick.io.phones.parser.dto.TaskResultsDto;
import com.karthick.io.phones.parser.entity.PhoneNumber;
import com.karthick.io.phones.parser.entity.PhoneNumbers;
import com.karthick.io.phones.parser.entity.Task;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.exception.TaskNotFoundException;
import com.karthick.io.phones.parser.repository.PhoneNumbersRepository;
import com.karthick.io.phones.parser.repository.TaskRepository;
import com.karthick.io.phones.parser.service.PhoneNumberFilter;

@SpringBootTest
public class PhoneParserServiceTests {

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private PhoneNumbersRepository phoneNumbersRepository;

	@Mock
	private ModelConverter converter;

	@Mock
	private PhoneNumberFilter germanPhoneNumberFilter;

	@InjectMocks
	private PhoneParserService service;

	@Test
	public void submitTaskTest() throws IOException, InvalidInputException {
		PhoneNumber num = new PhoneNumber("GERMANY", "12345678901");
		PhoneNumbers number1 = new PhoneNumbers(num, "0049");
		List<PhoneNumbers> phones = new ArrayList<>();
		phones.add(number1);
		File file = new File("numbers.txt");
		file.createNewFile();
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);
		when(germanPhoneNumberFilter.filter(multipartFile)).thenReturn(phones);
		when(phoneNumbersRepository.existsById(num)).thenReturn(false);
		when(taskRepository.save(any(Task.class))).thenReturn(new Task());
		when(converter.taskEntityToDto(any(Task.class))).thenReturn(new TaskDto());
		assertNotNull(service.submitTask(multipartFile, "DEFAULT_USER"));
	}

	@Test
	public void submitTaskNoNewPhoneNumberTest() throws IOException, InvalidInputException {
		PhoneNumber num = new PhoneNumber("GERMANY", "12345678901");
		PhoneNumbers number1 = new PhoneNumbers(num, "0049");
		List<PhoneNumbers> phones = new ArrayList<>();
		phones.add(number1);
		File file = new File("numbers.txt");
		file.createNewFile();
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);
		when(germanPhoneNumberFilter.filter(multipartFile)).thenReturn(phones);
		when(phoneNumbersRepository.existsById(num)).thenReturn(true);
		assertThrows(InvalidInputException.class, () -> service.submitTask(multipartFile, "DEFAULT_USER"));
	}

	@Test
	public void getAllTasksEmptyTest() {
		assertTrue(service.getAllTasks().isEmpty());
	}

	@Test
	public void getAllTasksTest() {
		List<TaskDto> result = new ArrayList<>();
		result.add(new TaskDto());
		when(converter.taskEntityToDto(Collections.emptyList())).thenReturn(result);
		assertFalse(service.getAllTasks().isEmpty());
	}

	@Test
	public void getTaskTest() throws TaskNotFoundException, InvalidInputException {
		String taskId = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
		UUID id = UUID.fromString(taskId);
		Task t = new Task();
		t.setTaskId(id);
		Optional<Task> task = Optional.of(t);
		TaskResultsDto tDto = new TaskResultsDto();
		tDto.setTaskId(id);
		when(taskRepository.findById(id)).thenReturn(task);
		when(converter.taskEntityToResultsDto(t)).thenReturn(tDto);
		assertEquals(tDto.getTaskId(), service.getTask(taskId).getTaskId());
	}

	@Test
	public void getTaskEmptyTest() throws TaskNotFoundException, InvalidInputException {
		String taskId = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
		UUID id = UUID.fromString(taskId);
		when(taskRepository.findById(id)).thenReturn(Optional.empty());
		assertThrows(TaskNotFoundException.class, () -> service.getTask(taskId), "Expected to throw but it didn't");
	}

	@Test
	public void deleteTaskTest() {
		String taskId = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
		UUID id = UUID.fromString(taskId);
		doNothing().when(taskRepository).deleteById(id);
		assertDoesNotThrow(() -> service.deleteTask(taskId));
	}

	@Test
	public void deleteTaskExceptionTest() {
		String taskId = "38400000-8cf0-11bd-b23e-10b96e4ef00d";
		UUID id = UUID.fromString(taskId);
		doThrow(EmptyResultDataAccessException.class).when(taskRepository).deleteById(id);
		assertThrows(TaskNotFoundException.class, () -> service.deleteTask(taskId), "Expected to throw but it didn't");
	}

}
