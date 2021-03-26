package br.com.econsumo.api.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.config.MensagensConfig;
import br.com.econsumo.api.enums.ConsumoReceiverPayloadEnum;
import br.com.econsumo.api.exception.ConsumoException;

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
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoReceiverPayloadEnum.EQUIPAMENTO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getEquipamentoNull());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoReceiverPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getTensaoNull());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoReceiverPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getCorrenteNull());
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoReceiverPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getDataNull());
		}
		
		if(!isTensaoValida(dadosConsumoArray[ConsumoReceiverPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getTensaoInvalida() + " " + dadosConsumoArray[ConsumoReceiverPayloadEnum.TENSAO.getPosicao()].trim());
		}
		
		if(!isCorrenteValida(dadosConsumoArray[ConsumoReceiverPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getCorrenteInvalida() + " " + dadosConsumoArray[ConsumoReceiverPayloadEnum.CORRENTE.getPosicao()].trim());
		}
		
		if(!isDataValida(dadosConsumoArray[ConsumoReceiverPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException(mensagensConfig.getDataInvalida() + " " + dadosConsumoArray[ConsumoReceiverPayloadEnum.DATA.getPosicao()].trim());
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
