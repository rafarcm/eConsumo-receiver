package br.com.econsumo.api.receiver.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.econsumo.api.receiver.config.AppConfig;
import br.com.econsumo.api.receiver.config.MensagensConfig;
import br.com.econsumo.api.receiver.enums.ConsumoPayloadEnum;
import br.com.econsumo.api.receiver.exception.ConsumoException;

@Service
public class ConsumoValidator implements Validator {
	
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
		
		if (dadosConsumoArray.length != QTDE_INFORMACOES_PAYLOAD) {
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
			throw new ConsumoException(mensagensConfig.getDataNull());
		}
		
		if(!isTensaoValida(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getTensaoInvalida() + " " + dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim());
		}
		
		if(!isCorrenteValida(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getCorrenteInvalida() + " " + dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim());
		}
		
		if(!isDataValida(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getDataInvalida() + " " + dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim());
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
			final SimpleDateFormat sdf = new SimpleDateFormat(appConfig.getFormatoDataHora());
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
