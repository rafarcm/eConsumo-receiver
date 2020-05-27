package br.com.econsumoreceiver.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.econsumoreceiver.exception.ConsumoException;
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

	private static final int POSICAO_TENSAO = 0;
	private static final int POSICAO_CORRENTE = 1;
	private static final int POSICAO_DATA = 2;
	private static final String FORMATO_DATA = "dd-MM-yyyy HH:mm:ss"; 
	
	@Override
	public void salvarConsumo(String dadosConsumo) {

		if (StringUtils.isEmpty(dadosConsumo)) {
			throw new ConsumoException("Dados de consumo não informado");
		}
		final String[] dadosConsumoArray = dadosConsumo.split(";");
		
		if (dadosConsumoArray == null || dadosConsumoArray.length != 3) {
			throw new ConsumoException("Dados de consumo inválidos");
		}
		
		final Double tensao = StringUtils.isEmpty(dadosConsumoArray[POSICAO_TENSAO].trim()) ? 
				0.0 : Double.valueOf(dadosConsumoArray[POSICAO_TENSAO].trim());
		final Double corrente = StringUtils.isEmpty(dadosConsumoArray[POSICAO_CORRENTE].trim()) ? 
				0.0 : Double.valueOf(dadosConsumoArray[POSICAO_CORRENTE].trim());
		final LocalDateTime data = StringUtils.isEmpty(dadosConsumoArray[POSICAO_DATA].trim()) ? 
				LocalDateTime.now() : 
				LocalDateTime.parse(dadosConsumoArray[POSICAO_DATA].trim(), 
						DateTimeFormatter.ofPattern(FORMATO_DATA));
		
		consumoRepository.save(new Consumo(tensao, corrente, data));
	}

}
