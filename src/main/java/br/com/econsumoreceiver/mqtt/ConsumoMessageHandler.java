package br.com.econsumoreceiver.mqtt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import br.com.econsumoreceiver.config.MqttAppConfig;
import br.com.econsumoreceiver.service.ConsumoService;
import br.com.econsumoreceiver.validator.ConsumoPayloadValidator;
import br.com.econsumoreceiver.validator.PayloadValidator;

public class ConsumoMessageHandler implements MessageHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(MqttAppConfig.class);
	
	private static final String SEPARADOR_CONSUMOS = "%";
	
	@Autowired
	private ConsumoService consumoService;

	@Override
    public void handleMessage(Message<?> message) throws MessagingException {
    	try {
    		final String[] consumos = message.getPayload().toString().split(SEPARADOR_CONSUMOS);
    		for (int i = 0; i < consumos.length; i++) {
    			final PayloadValidator validator = new ConsumoPayloadValidator();
    			validator.validar(consumos[i]);
    			consumoService.salvarConsumo(consumos[i]);
			}
    	} catch (Exception e) {
    		LOGGER.error(e);
		}
    }
	
}
