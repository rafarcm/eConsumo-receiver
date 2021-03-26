package br.com.econsumo.api.receiver.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.econsumo.api.receiver.config.AppConfig;
import br.com.econsumo.api.receiver.enums.ConsumoPayloadEnum;
import br.com.econsumo.api.receiver.model.entity.Consumo;
import br.com.econsumo.api.receiver.model.repository.ConsumoRepository;
import br.com.econsumo.api.receiver.service.ConsumoService;
import br.com.econsumo.api.receiver.validator.Validator;

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
		return consumoRepository
				.findByEquipamentoAndDataBetween(equipamento, data.atStartOfDay(), data.atTime(23, 59, 59, 9999))
				.stream().sorted(Comparator.comparing(Consumo::getData)).collect(Collectors.toList());
	}

	private Consumo parsePayloadToConsumo(final String payload) {
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
