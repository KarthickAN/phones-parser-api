package com.karthick.io.phones.parser.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.karthick.io.phones.parser.dto.ErrorDto;
import com.karthick.io.phones.parser.exception.InvalidInputException;
import com.karthick.io.phones.parser.exception.NoContentException;
import com.karthick.io.phones.parser.exception.TaskNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class PhoneParserControllerAdvices {

	@ExceptionHandler(NoContentException.class)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void handleNoTasksException(NoContentException ex, HttpServletRequest req) {
		log.error(ex.getMessage());
	}

	@ExceptionHandler(TaskNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorDto handleTaskNotFoundException(TaskNotFoundException ex, HttpServletRequest req) {
		log.error(ex.getMessage());
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.NOT_FOUND.value()).error(ex.getMessage())
				.path(req.getRequestURI()).build();
	}

	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleInvalidInputException(InvalidInputException ex, HttpServletRequest req) {
		log.error(ex.getMessage());
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value()).error(ex.getMessage())
				.path(req.getRequestURI()).build();
	}

	@ExceptionHandler(MissingServletRequestPartException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorDto handleMissingServletRequestPartException(MissingServletRequestPartException ex,
			HttpServletRequest req) {
		log.error(ex.getMessage());
		return ErrorDto.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value()).error(ex.getMessage())
				.path(req.getRequestURI()).build();
	}
}
