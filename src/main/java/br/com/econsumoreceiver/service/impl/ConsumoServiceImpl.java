package br.com.econsumoreceiver.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.econsumoreceiver.enums.ConsumoPayloadEnum;
import br.com.econsumoreceiver.model.entity.Consumo;
import br.com.econsumoreceiver.model.repository.ConsumoRepository;
import br.com.econsumoreceiver.service.ConsumoService;

/**
 * Classe de implementação da Interface ConsumoService
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
@Service
public class ConsumoServiceImpl implements ConsumoService {

	@Autowired
	private ConsumoRepository consumoRepository;

	private static final String FORMATO_DATA = "dd-MM-yyyy HH:mm:ss"; 
	
	@Override
	public void salvarConsumo(String dadosConsumo) {
		final String[] dadosConsumoArray = dadosConsumo.split(";");
		final String equipamento = dadosConsumoArray[ConsumoPayloadEnum.EQUIPAMENTO.getPosicao()].trim();
		final Double tensao = Double.valueOf(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim());
		final Double corrente = Double.valueOf(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim());
		final LocalDateTime data = LocalDateTime.parse(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim(), 
						DateTimeFormatter.ofPattern(FORMATO_DATA));
		
		consumoRepository.save(new Consumo(equipamento, tensao, corrente, data));
	}

}
