package br.com.econsumoreceiver.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.econsumoreceiver.model.entity.Consumo;

public interface ConsumoRepository extends MongoRepository<Consumo, String>{
}
