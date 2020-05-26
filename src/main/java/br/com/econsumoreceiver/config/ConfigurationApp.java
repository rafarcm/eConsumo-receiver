package br.com.econsumoreceiver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class ConfigurationApp {

	@Value("${mqtt.url}")
	private String mqttUrl;
	
	@Value("${mqtt.user}")
	private String mqttUser;
	
	@Value("${mqtt.password}")
	private String mqttPassword;
	
	@Value("${mqtt.topic}")
	private String mqttTopic;
	
}
