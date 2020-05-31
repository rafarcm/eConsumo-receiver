package br.com.econsumoreceiver.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.econsumoreceiver.config.AppConfig;
import br.com.econsumoreceiver.config.MensagensConfig;
import br.com.econsumoreceiver.enums.ConsumoPayloadEnum;
import br.com.econsumoreceiver.exception.ConsumoException;

@Service
public class ConsumoPayloadValidator implements PayloadValidator {
	
	private static final int QTDE_INFORMACOES_PAYLOAD = 4;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private MensagensConfig mensagensConfig;

	@Override
	public void validar(String payload) {
		
		if (StringUtils.isEmpty(payload)) {
			throw new ConsumoException(mensagensConfig.getDadosConsumoNull());
		}
		final String[] dadosConsumoArray = payload.split(appConfig.getSeparadorPayload());
		
		if (dadosConsumoArray == null || dadosConsumoArray.length != QTDE_INFORMACOES_PAYLOAD) {
			throw new ConsumoException(mensagensConfig.getDadosConsumoInvalida());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.EQUIPAMENTO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getEquipamentoNull());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getTensaoNull());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getCorrenteNull());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getDataLeituraNull());
		}
		
		if(!isTensaoValida(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getTensaoInvalida() + " " + dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim());
		}
		
		if(!isCorrenteValida(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getCorrenteInvalida() + " " + dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim());
		}
		
		if(!isDataValida(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getDataLeituraInvalida() + " " + dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim());
		}
	}
	
	private boolean isTensaoValida(String tensao) {
		try {
			Double.parseDouble(tensao);
			return true;
		} catch (Exception ex) {
            return false;
        }
	}
		
	private boolean isCorrenteValida(String corrente) {
		try {
			Double.parseDouble(corrente);
			return true;
		} catch (Exception ex) {
            return false;
        }
	}
	
	private boolean isDataValida(String data) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(appConfig.getFormatoData());
			sdf.setLenient(false);
			final Date dataLeitura = sdf.parse(data);
			return dataLeitura.toInstant()
				      .atZone(ZoneId.systemDefault())
				      .toLocalDateTime().compareTo(LocalDateTime.now()) <= 0;
		} catch (ParseException ex) {
            return false;
        }
	}
}
