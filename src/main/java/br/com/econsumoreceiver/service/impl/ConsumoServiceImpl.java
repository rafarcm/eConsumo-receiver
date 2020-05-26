package br.com.econsumoreceiver.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.econsumoreceiver.exception.ConsumoException;
import br.com.econsumoreceiver.model.entity.Consumo;
import br.com.econsumoreceiver.model.repository.ConsumoRepository;
import br.com.econsumoreceiver.service.ConsumoService;

@Service
public class ConsumoServiceImpl implements ConsumoService {

	@Autowired
	private ConsumoRepository consumoRepository;

	private final int POSICAO_TENSAO = 0;
	private final int POSICAO_CORRENTE = 1;

	@Override
	public void salvarConsumo(String dadosConsumo) {

		if (StringUtils.isEmpty(dadosConsumo)) {
			throw new ConsumoException("Dados de consumo não informado");
		}
		final String[] dadosConsumoArray = dadosConsumo.split(";");
		
		if (dadosConsumoArray == null || dadosConsumoArray.length != 2) {
			throw new ConsumoException("Dados de consumo inválidos");
		}
		final BigDecimal tensao = StringUtils.isEmpty(dadosConsumoArray[POSICAO_TENSAO].trim()) ? 
				BigDecimal.ZERO : new BigDecimal(dadosConsumoArray[POSICAO_TENSAO].trim());
		final BigDecimal corrente = StringUtils.isEmpty(dadosConsumoArray[POSICAO_CORRENTE].trim()) ? 
				BigDecimal.ZERO : new BigDecimal(dadosConsumoArray[POSICAO_CORRENTE].trim());
		consumoRepository.save(new Consumo(tensao, corrente));
	}

}
