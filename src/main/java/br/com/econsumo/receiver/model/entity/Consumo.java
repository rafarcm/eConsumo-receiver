package br.com.econsumo.receiver.model.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade de Consumo. Contém os dados de consumo da residência
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
@Document(collection = "consumo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consumo {
	
	@Id
	private String id;
	
	@NotEmpty(message = "{equipamento.not.empty}")
	private String equipamento;
	
	@NotNull(message = "{tensao.not.null}")
	private Double tensao;
	
	@NotEmpty(message = "{corrente.not.null}")
	private Double corrente;
	
	@NotNull(message = "{data.not.null}")
	private LocalDateTime data;

}
