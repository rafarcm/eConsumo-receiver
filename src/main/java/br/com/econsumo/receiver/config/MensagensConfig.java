package br.com.econsumo.receiver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@PropertySource("classpath:ValidationMessages.properties")
@Component
@Getter
public class MensagensConfig {
	
	@Value("${dados.consumo.null}")
	private String dadosConsumoNull;
	
	@Value("${dados.consumo.invalido}")
	private String dadosConsumoInvalida;
	
	@Value("${equipamento.null}")
	private String equipamentoNull;
	
	@Value("${tensao.null}")
	private String tensaoNull;
	
	@Value("${corrente.null}")
	private String correnteNull;
	
	@Value("${data.not.null}")
	private String dataNull;
	
	@Value("${tensao.invalida}")
	private String tensaoInvalida;
	
	@Value("${corrente.invalida}")
	private String correnteInvalida;
	
	@Value("${data.past}")
	private String dataInvalida;

}
