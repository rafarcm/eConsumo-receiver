package br.com.econsumo.api.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.enums.ConsumoReceiverPayloadEnum;
import br.com.econsumo.api.model.entity.ConsumoEntity;
import br.com.econsumo.api.model.repository.ConsumoRepository;
import br.com.econsumo.api.service.ConsumoService;
import br.com.econsumo.api.validator.Validator;

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
	public ConsumoEntity salvarConsumo(String payload) {
		return salvarConsumo(parsePayloadToConsumo(payload));
	}

	@Override
	@Transactional
	public ConsumoEntity salvarConsumo(ConsumoEntity consumo) {
		return consumoRepository.save(consumo);
	}

	@Override
	public List<ConsumoEntity> buscar(String equipamento, LocalDate data) {
		return consumoRepository
				.findByEquipamentoAndDataBetween(equipamento, data.atStartOfDay(), data.atTime(23, 59, 59, 9999))
				.stream().sorted(Comparator.comparing(ConsumoEntity::getData)).collect(Collectors.toList());
	}

	private ConsumoEntity parsePayloadToConsumo(final String payload) {
		validator.validar(payload);
		final String[] dadosConsumo = payload.split(appConfig.getSeparadorPayload());
		final String equipamento = dadosConsumo[ConsumoReceiverPayloadEnum.EQUIPAMENTO.getPosicao()].trim();
		final Double tensao = Double.valueOf(dadosConsumo[ConsumoReceiverPayloadEnum.TENSAO.getPosicao()].trim());
		final Double corrente = Double.valueOf(dadosConsumo[ConsumoReceiverPayloadEnum.CORRENTE.getPosicao()].trim());
		final LocalDateTime data = LocalDateTime.parse(dadosConsumo[ConsumoReceiverPayloadEnum.DATA.getPosicao()].trim(),
				DateTimeFormatter.ofPattern(appConfig.getFormatoDataHora()));

		return ConsumoEntity.builder().equipamento(equipamento).tensao(tensao).corrente(corrente).data(data).build();
	}

}
