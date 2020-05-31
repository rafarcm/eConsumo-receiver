package br.com.econsumoreceiver.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.econsumoreceiver.config.AppConfig;
import br.com.econsumoreceiver.enums.ConsumoPayloadEnum;
import br.com.econsumoreceiver.model.entity.Consumo;
import br.com.econsumoreceiver.model.repository.ConsumoRepository;
import br.com.econsumoreceiver.service.ConsumoService;
import br.com.econsumoreceiver.validator.Validator;

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
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private AppConfig appConfig;

	@Override
	public Consumo salvarConsumo(String payload) {
		return salvarConsumo(parsePayloadToConsumo(payload));
	}

	@Override
	@Transactional
	public Consumo salvarConsumo(Consumo consumo) {
		return consumoRepository.save(consumo);
	}

	@Override
	public List<Consumo> buscar(String equipamento, LocalDate data) {
		return consumoRepository.findByEquipamentoAndDataBetween(equipamento, data.atStartOfDay(), data.atTime(23, 59, 59, 9999));
	}
	
	private Consumo parsePayloadToConsumo(String payload) {
		validator.validar(payload);
		final String[] dadosConsumo = payload.split(appConfig.getSeparadorPayload());
		final String equipamento = dadosConsumo[ConsumoPayloadEnum.EQUIPAMENTO.getPosicao()].trim();
		final Double tensao = Double.valueOf(dadosConsumo[ConsumoPayloadEnum.TENSAO.getPosicao()].trim());
		final Double corrente = Double.valueOf(dadosConsumo[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim());
		final LocalDateTime data = LocalDateTime.parse(dadosConsumo[ConsumoPayloadEnum.DATA.getPosicao()].trim(), 
						DateTimeFormatter.ofPattern(appConfig.getFormatoDataHora()));
		
		return Consumo.builder().equipamento(equipamento).tensao(tensao).corrente(corrente).data(data).build();
	}

}
