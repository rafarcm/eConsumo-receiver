package br.com.econsumoreceiver.model.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

/**
 * Entidade de Consumo. Contém os dados de consumo da residência
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
@Document(collection = "consumo")
@Builder
@Data
public class Consumo {
	
	@Id
	private String id;
	
	@NotEmpty(message = "{equipamento.not.empty}")
	private String equipamento;
	
	@NotNull(message = "{tensao.not.empty}")
	private Double tensao;
	
	@NotEmpty(message = "{corrente.not.empty}")
	private Double corrente;
	
	@NotNull(message = "{data.not.null}")
	@Past(message = "{data.past}")
	private LocalDateTime data;

}
