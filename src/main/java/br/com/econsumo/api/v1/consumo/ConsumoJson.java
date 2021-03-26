package br.com.econsumo.api.v1.consumo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.econsumo.api.common.json.EConsumoApiJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "Consumo")
public class ConsumoJson implements EConsumoApiJson {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("equipamento")
	@NotEmpty(message = "{equipamento.not.empty}")
	private String equipamento;
	
	@JsonProperty("tensao")
	@NotNull(message = "{tensao.not.null}")
	private String tensao;
	
	@JsonProperty("corrente")
	@NotEmpty(message = "{corrente.not.null}")
	private String corrente;
	
	@JsonProperty("data")
	@NotNull(message = "{data.not.null}")
	private String data;
	
}
