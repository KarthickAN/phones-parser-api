package com.karthick.io.phones.parser.converter;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthick.io.phones.parser.dto.TaskDto;
import com.karthick.io.phones.parser.dto.TaskResultsDto;
import com.karthick.io.phones.parser.entity.Task;

@Service
public class ModelConverter {

	@Autowired
	private ModelMapper mapper;

	public TaskDto taskEntityToDto(Task task) {
		return mapper.map(task, TaskDto.class);
	}

	public TaskResultsDto taskEntityToResultsDto(Task task) {
		return mapper.map(task, TaskResultsDto.class);
	}

	public List<TaskDto> taskEntityToDto(List<Task> tasks) {
		return tasks.stream().map(this::taskEntityToDto).toList();
	}
}
