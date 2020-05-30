package br.com.econsumoreceiver.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.util.StringUtils;

import br.com.econsumoreceiver.enums.ConsumoPayloadEnum;
import br.com.econsumoreceiver.exception.ConsumoException;

public class ConsumoPayloadValidator implements PayloadValidator {
	
	private static final String FORMATO_DATA = "dd-MM-yyyy HH:mm:ss"; 

	@Override
	public void validar(String payload) {
		
		if (StringUtils.isEmpty(payload)) {
			throw new ConsumoException("Dados de consumo não informado");
		}
		final String[] dadosConsumoArray = payload.split(";");
		
		if (dadosConsumoArray == null || dadosConsumoArray.length != 4) {
			throw new ConsumoException("Dados de consumo inválidos");
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.EQUIPAMENTO.getPosicao()].trim())) {
			throw new ConsumoException("Id do equipamento não informado");
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException("Tensão não informada");
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException("Corrente não informada");
		}
		
		if(StringUtils.isEmpty(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException("Data de leitura não informada");
		}
		
		if(!isTensaoValida(dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim())) {
			throw new ConsumoException("Tensão inválida " + dadosConsumoArray[ConsumoPayloadEnum.TENSAO.getPosicao()].trim());
		}
		
		if(!isCorrenteValida(dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim())) {
			throw new ConsumoException("Corrente inválida " + dadosConsumoArray[ConsumoPayloadEnum.CORRENTE.getPosicao()].trim());
		}
		
		if(!isDataValida(dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim())) {
			throw new ConsumoException("Data de leitura inválida " + dadosConsumoArray[ConsumoPayloadEnum.DATA.getPosicao()].trim());
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
			final SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA);
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
