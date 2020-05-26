package br.com.econsumoreceiver.exception;

public class ConsumoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConsumoException(String mensagem) {
		super(mensagem);
	}
}
