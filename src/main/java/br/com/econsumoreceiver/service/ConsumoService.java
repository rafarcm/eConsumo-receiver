package br.com.econsumoreceiver.service;

import br.com.econsumoreceiver.exception.ConsumoException;

/**
 * Interface para definição do comportamento do Serviço de Consumo
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
public interface ConsumoService {
	
	/**
	 * Método responsável por salvar os dados de consumo na base de dados
	 * 
	 * @author Rafael Moraes
	 * @since 27/05/2020
	 * @param dadosConsumo - Deve ser enviado no seguinte padrão <b>tensão;corrente</b>
	 * <br>Os valores de tensão e corrente, devem ser decimais separados por ponto e são obrigatórios
	 * <br><b>Ex:</b> 110.5;56.2 
	 * @throws ConsumoException - Erro que são gerados caso o parâmetro não atenda o padrão pré estabelecido
	 */
	public void salvarConsumo(String dadosConsumo) throws ConsumoException;

}
