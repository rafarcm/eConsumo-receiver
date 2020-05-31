package br.com.econsumoreceiver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AppConfig {

	@Value("${mqtt.url}")
	private String mqttUrl;
	
	@Value("${mqtt.user}")
	private String mqttUser;
	
	@Value("${mqtt.password}")
	private String mqttPassword;
	
	@Value("${mqtt.topic}")
	private String mqttTopic;
	
	@Value("${formato.data}")
	private String formatoData;
	
	@Value("${separador.payload}")
	private String separadorPayload;
	
	@Value("${separador.dados.payload}")
	private String separadorDadosPayload;
	
}
