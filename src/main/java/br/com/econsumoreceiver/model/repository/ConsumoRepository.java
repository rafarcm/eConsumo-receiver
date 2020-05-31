package br.com.econsumoreceiver.model.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.econsumoreceiver.model.entity.Consumo;

/**
 * Interface responsável por realizar as operações na base de dados de Consumo
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
public interface ConsumoRepository extends MongoRepository<Consumo, String>{

	public List<Consumo> findByEquipamentoAndDataBetween(String equipamento, LocalDate dataInicial, LocalDate dataFinal);
	
}
