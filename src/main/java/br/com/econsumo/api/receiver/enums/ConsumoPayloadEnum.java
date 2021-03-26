package br.com.econsumo.api.receiver.enums;

import lombok.Getter;

/**
 * Enum com as posições de cada informação recebida no Payload de Consumo
 * 
 * @author Rafael Moraes
 * @since 29/05/2020
 */
@Getter
public enum ConsumoPayloadEnum {
	
	EQUIPAMENTO(0),
	TENSAO(1),
	CORRENTE(2),
	DATA(3);
	
	private int posicao;
	
	ConsumoPayloadEnum(int posicao) {
		this.posicao = posicao;
	}
	
}
