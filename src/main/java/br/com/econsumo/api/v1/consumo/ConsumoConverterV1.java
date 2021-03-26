package br.com.econsumo.api.v1.consumo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.econsumo.api.common.converter.AbstractEConsumoApiJsonConverter;
import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.model.entity.ConsumoEntity;

@Component
public class ConsumoConverterV1 extends AbstractEConsumoApiJsonConverter<ConsumoEntity, ConsumoJson> {

	@Autowired
	private AppConfig appConfig;

	@Override
	public ConsumoEntity toEntity(ConsumoJson json) {
		if (json == null) {
			return null;
		}

		return ConsumoEntity.builder()
				.equipamento(json.getEquipamento())
				.tensao(Double.parseDouble(json.getTensao()))
				.corrente(Double.parseDouble(json.getCorrente()))
				.data(LocalDateTime.parse(json.getData(), DateTimeFormatter.ofPattern(appConfig.getFormatoDataHora())))
				.build();
	}

	@Override
	public ConsumoJson toJson(ConsumoEntity entity) {
		if (entity == null) {
			return null;
		}

		return ConsumoJson.builder()
				.equipamento(entity.getEquipamento())
				.tensao(entity.getTensao().toString())
				.corrente(entity.getCorrente().toString())
				.data(entity.getData().format(DateTimeFormatter.ofPattern(appConfig.getFormatoDataHora())))
				.build();
	}

}
