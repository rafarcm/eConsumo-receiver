package br.com.econsumoreceiver.api.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConsumoDTO {

	@NotBlank(message = "{equipamento.not.empty}")
	private String equipamento;
	
	@NotNull(message = "{dataInicial.not.null}")
	@Past(message = "{dataInicial.past}")
	private LocalDate dataInicial; 
	
	@NotNull(message = "{dataFinal.not.null}")
	@Past(message = "{dataFinal.past}")
	private LocalDate dataFinal;
	
}
