package br.com.alura.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.forum.model.ExceptionResponse;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ExceptionResponse handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "EMAIL JÁ CADASTRADO");
	}
	
	
}