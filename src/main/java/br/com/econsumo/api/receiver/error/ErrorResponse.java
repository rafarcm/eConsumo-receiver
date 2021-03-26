package br.com.econsumo.api.receiver.error;

import java.util.List;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private String message = "Requisição possui campos inválidos";
    private int code;
    private String status;
    private List<ErrorObject> errors;
    
	public ErrorResponse(String message, int code, String status, List<ErrorObject> errors) {
		super();
		this.message = message;
		this.code = code;
		this.status = status;
		this.errors = errors;
	}
    
	public ErrorResponse(int code, String status, List<ErrorObject> errors) {
		super();
		this.code = code;
		this.status = status;
		this.errors = errors;
	}

}
