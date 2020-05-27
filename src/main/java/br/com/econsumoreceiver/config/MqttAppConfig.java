package br.com.econsumoreceiver.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import br.com.econsumoreceiver.service.ConsumoService;

/**
 * Classe responsável pela configuração do Broker Mqtt que será ouvido
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
@Configuration
public class MqttAppConfig {
	
	private static final Logger LOGGER = LogManager.getLogger(MqttAppConfig.class);
	
	private static final String SEPARADOR_CONSUMOS = "%";
	
	@Autowired
	private ConsumoService consumoService;
	
	@Autowired
	private ConfigurationApp config;
	
	@Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
	
	@Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { config.getMqttUrl() });
        options.setUserName(config.getMqttUser());
        options.setPassword(config.getMqttPassword().toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }
	
	@Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(config.getMqttUrl(), mqttClientFactory(), config.getMqttTopic());
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
	
	@Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
            	try {
            		final String[] consumos = message.getPayload().toString().split(SEPARADOR_CONSUMOS);
            		
            		for (int i = 0; i < consumos.length; i++) {
            			consumoService.salvarConsumo(consumos[i]);
					}
            	} catch (Exception e) {
            		LOGGER.error(e);
				}
            }
        };
    }
}
