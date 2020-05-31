package br.com.econsumoreceiver.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.econsumoreceiver.config.AppConfig;
import br.com.econsumoreceiver.config.MensagensConfig;
import br.com.econsumoreceiver.enums.ConsumoPayloadEnum;
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
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private MensagensConfig mensagensConfig;
	
	@Override
	public void salvarConsumo(String dadosConsumo) {
		final String[] dadosConsumoArray = dadosConsumo.split(appConfig.getSeparadorPayload());
		final String equipamento = dadosConsumoArray[ConsumoPayloadEnum.EQUIPAMENTO.getPosicao()].trim();
		final Double tensao = Double.valueOf(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim());
		final Double corrente = Double.valueOf(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim());
		final LocalDateTime data = LocalDateTime.parse(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim(), 
						DateTimeFormatter.ofPattern(appConfig.getFormatoData()));
		
		consumoRepository.save(Consumo.builder().equipamento(equipamento).tensao(tensao).corrente(corrente).data(data).build());
	}

	@Override
	public List<Consumo> buscar(String equipamento, LocalDate dataInicial, LocalDate dataFinal) {
		validarPeriodo(equipamento, dataInicial, dataFinal);
		return consumoRepository.findByEquipamentoAndDataBetween(equipamento, dataInicial, dataFinal);
	}

	/*
	 * Realiza a validação do período
	 */
	private void validarPeriodo(String equipamento, LocalDate dataInicial, LocalDate dataFinal) {
		
		long periodoDias = ChronoUnit.DAYS.between(dataInicial, dataFinal);
		if(periodoDias < 0) {
			throw new ConsumoException(mensagensConfig.getDataInicialFinalInvalida());
		}

		long periodoMeses = ChronoUnit.MONTHS.between(dataInicial, dataFinal);
		if(periodoMeses > 0) {
			throw new ConsumoException(mensagensConfig.getPeriodoInvalido());
		}
	}

}
