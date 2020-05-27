package br.com.econsumoreceiver.model.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Entidade de Consumo. Contém os dados de consumo da residência
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
@Document(collection = "consumo")
@Getter
@EqualsAndHashCode
@ToString
public class Consumo {
	
	private static final String FORMATO_DATA = "dd/MM/yyyy HH:mm:ss"; 
	
	@Id
	private String id;
	private Double tensao;
	private Double corrente;
	private String data;
	
	/**
	 * Construtor da Classe Consumo
	 * 
	 * @param tensao - Tensão de leitura
	 * @param corrente - Corrente de leitura
	 * @param data - Data da leitura
	 * @author Rafael Moraes
	 * @since 27/05/2020
	 */
	public Consumo(Double tensao, Double corrente, LocalDateTime data) {
		this.tensao = tensao;
		this.corrente = corrente;
		this.data = data.format(DateTimeFormatter.ofPattern(FORMATO_DATA));
	}

}
