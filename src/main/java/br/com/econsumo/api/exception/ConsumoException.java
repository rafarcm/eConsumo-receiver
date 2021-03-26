package br.com.econsumo.api.exception;

/**
 * Exception de negócio para o serviço de Consumo
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
public class ConsumoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConsumoException(String mensagem) {
		super(mensagem);
	}
}
