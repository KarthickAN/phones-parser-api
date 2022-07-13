package com.karthick.io.phones.parser.converter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.karthick.io.phones.parser.dto.TaskDto;
import com.karthick.io.phones.parser.dto.TaskResultsDto;
import com.karthick.io.phones.parser.entity.Task;

@SpringBootTest
public class ModelConverterTests {

	@Autowired
	private ModelConverter converter;

	@Test
	public void taskEntityToDtoTest() {
		assertTrue(converter.taskEntityToDto(new Task()) instanceof TaskDto);
	}

	@Test
	public void taskEntityToResultsDtoTest() {
		assertTrue(converter.taskEntityToResultsDto(new Task()) instanceof TaskResultsDto);
	}

	@Test
	public void taskEntityToDtoListTest() {
		List<Task> tasks = new ArrayList<>();
		tasks.add(new Task());
		converter.taskEntityToDto(tasks).stream().forEach(t -> assertTrue(t instanceof TaskDto));
	}
}
