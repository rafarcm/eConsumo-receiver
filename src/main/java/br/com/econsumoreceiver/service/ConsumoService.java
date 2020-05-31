package br.com.econsumoreceiver.service;

import java.time.LocalDate;
import java.util.List;

import br.com.econsumoreceiver.exception.ConsumoException;
import br.com.econsumoreceiver.model.entity.Consumo;

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
	
	/**
	 * Método responsável por buscar a lista de consumos de um equipamento em um dado período
	 * <br>O período <b>não</b> pode ser superior a um mês
	 * 
	 * @param equipamento - Id do Equipamento
	 * @param dataInicial - Data Inicial, inclusive, do período de busca
	 * @param dataFinal - Data final, inclusive, do período de busca
	 * @return Lista de Consumos do equipamento informado no período desejado
	 */
	public List<Consumo> buscar(String equipamento, LocalDate dataInicial, LocalDate dataFinal);

}
