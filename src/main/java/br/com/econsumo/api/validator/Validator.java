package br.com.econsumo.api.validator;

/**
 * Interface para validação do payload
 * 
 * @author Rafael Moraes
 * @since 29/05/2020
 */
public interface Validator {
	
	/**
	 * Método responsável por realizar a validação do Payload
	 * 
	 * @param payload - Payload
	 * @author Rafael Moraes
	 * @since 29/05/2020
	 */
	public void validar(String payload);

}
