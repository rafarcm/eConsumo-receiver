package br.com.econsumo.api.receiver.service;

import java.time.LocalDate;
import java.util.List;

import br.com.econsumo.api.receiver.model.entity.Consumo;

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
	 * @param payload - Deve ser enviado no seguinte padrão <b>tensão;corrente</b>
	 * <br>Os valores de tensão e corrente, devem ser decimais separados por ponto e são obrigatórios
	 * <br><b>Ex:</b> 110.5;56.2 
	 * @return Objeto Consumo populado
	 */
	public Consumo salvarConsumo(String payload);
	
	/**
	 * Método responsável por salvar os dados de consumo na base de dados
	 * 
	 * @author Rafael Moraes
	 * @since 27/05/2020
	 * @param consumo - Objeto Consumo a ser salvo na base
	 */
	public Consumo salvarConsumo(Consumo consumo);
	
	/**
	 * Método responsável por buscar a lista de consumos de um equipamento em um data
	 * 
	 * @author Rafael Moraes
	 * @since 27/05/2020
	 * @param equipamento - Id do Equipamento
	 * @param data - Data com os dados de leitura
	 * @return Lista de Consumos do equipamento na data informada
	 */
	public List<Consumo> buscar(String equipamento, LocalDate data);

}
