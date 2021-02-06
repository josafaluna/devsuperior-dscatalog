package br.com.jluna.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.jluna.dscatalog.services.exceptions.DatabaseException;
import br.com.jluna.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError erro = new StandardError();
		erro.setTimestamp(Instant.now());
		erro.setStatus(status.value());
		erro.setError("Resource Not Found");
		erro.setMessage("Categoria não encontrada");
		erro.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(erro);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError erro = new StandardError();
		erro.setTimestamp(Instant.now());
		erro.setStatus(status.value());
		erro.setError("Database Exception");
		erro.setMessage("Integrity Violation");
		erro.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(erro);
	}

}
