package br.com.econsumo.api.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.config.MqttAppConfig;
import br.com.econsumo.api.service.ConsumoService;

public class ConsumoMessageHandler implements MessageHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(MqttAppConfig.class);
	
	@Autowired
	private ConsumoService consumoService;
	
	@Autowired
	private AppConfig appConfig;

	@Override
    public void handleMessage(Message<?> message) throws MessagingException {
    	try {
    		final String[] consumos = message.getPayload().toString().split(appConfig.getSeparadorDadosPayload());
    		for (int i = 0; i < consumos.length; i++) {
    			consumoService.salvarConsumo(consumos[i]);
			}
    	} catch (Exception e) {
    		LOGGER.error(e);
		}
    }
	
}
