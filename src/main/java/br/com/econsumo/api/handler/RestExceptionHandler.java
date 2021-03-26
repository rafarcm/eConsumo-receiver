package br.com.econsumo.api.handler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe responsável por capturar a exceção lançada pela falha na validação
 * 
 * @author Rafael Moraes
 * @since 30/05/2020
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	@SuppressWarnings("rawtypes") 
	public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        final StringBuilder msgError = new StringBuilder();
        String prefix = "";
 
        for (ConstraintViolation erro : ex.getConstraintViolations()) {
        	msgError.append(prefix);
        	prefix = ", ";
        	msgError.append(erro.getMessage());
		}
        
        return ResponseEntity.badRequest().body(msgError.toString());
	}

	
}
