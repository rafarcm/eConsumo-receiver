package br.com.econsumoreceiver.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Document(collection = "consumo")
@Getter
@EqualsAndHashCode
@ToString
public class Consumo {
	
	@Id
	private String id;
	private BigDecimal tensao;
	private BigDecimal corrente;
	private String data;
	
	public Consumo(BigDecimal tensao, BigDecimal corrente) {
		this.tensao = tensao;
		this.corrente = corrente;
		this.data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}

}
