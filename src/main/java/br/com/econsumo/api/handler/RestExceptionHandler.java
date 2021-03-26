package br.com.econsumo.api.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.econsumo.api.error.ErrorObject;
import br.com.econsumo.api.error.ErrorResponse;

/**
 * Classe responsável por capturar a exceção lançada pela falha na validação
 * 
 * @author Rafael Moraes
 * @since 30/05/2020
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        final List<ErrorObject> errors = getErrors(ex);
        final ErrorResponse errorResponse = getErrorResponse(ex, HttpStatus.BAD_REQUEST, errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

    private ErrorResponse getErrorResponse(ConstraintViolationException ex, HttpStatus status, List<ErrorObject> errors) {
        return new ErrorResponse(status.value(), status.getReasonPhrase(), errors);
    }

    private List<ErrorObject> getErrors(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(error -> new ErrorObject(error.getMessage(), error.getInvalidValue()))
                .collect(Collectors.toList());
    }
	
}
