package br.com.econsumoreceiver.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Classe responsável por criar um erro padrão para o Response
 * 
 * @author Rafael Moraes
 * @since 30/05/2020
 *
 */
public class CreateErrorResponse {

	/**
	 * Cria um ResponseEntity de erro
	 * 
	 * @param ex - Exception
	 * @param classe - Classe DTO com os parâmetros informados
	 * @param status - HttpStatus
	 * @return ResponseEntity
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResponseEntity createErrorResponseEntity(Exception ex, Class classe, HttpStatus status) {
		List<ErrorObject> errors = new ArrayList<>();
		errors.add(new ErrorObject(ex.getMessage(), "", null));
		ErrorResponse errorResponse = 
			new ErrorResponse(status.value(), status.getReasonPhrase(), classe.getName(), errors);
		return new ResponseEntity(errorResponse, status);
	}
}
