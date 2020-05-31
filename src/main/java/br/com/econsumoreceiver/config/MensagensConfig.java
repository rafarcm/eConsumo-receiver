package br.com.econsumoreceiver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@PropertySource("classpath:ValidationMessages.properties")
@Component
@Getter
public class MensagensConfig {
	
	@Value("${periodo.invalido}")
	private String periodoInvalido;
	
	@Value("${data.inicial.final.invalida}")
	private String dataInicialFinalInvalida;
	
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
	
	@Value("${data.leitura.null}")
	private String dataLeituraNull;
	
	@Value("${tensao.invalida}")
	private String tensaoInvalida;
	
	@Value("${corrente.invalida}")
	private String correnteInvalida;
	
	@Value("${data.leitura.invalida}")
	private String dataLeituraInvalida;

}
