package br.com.econsumoreceiver.error;

import java.util.List;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private String message = "Requisição possui campos inválidos";
    private int code;
    private String status;
    private String objectName;
    private List<ErrorObject> errors;
    
	public ErrorResponse(String message, int code, String status, String objectName, List<ErrorObject> errors) {
		super();
		this.message = message;
		this.code = code;
		this.status = status;
		this.objectName = objectName;
		this.errors = errors;
	}
    
	public ErrorResponse(int code, String status, String objectName, List<ErrorObject> errors) {
		super();
		this.code = code;
		this.status = status;
		this.objectName = objectName;
		this.errors = errors;
	}

}
