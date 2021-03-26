package br.com.econsumo.api.enums;

import lombok.Getter;

/**
 * Enum com as posições de cada informação recebida no Payload de Consumo
 * 
 * @author Rafael Moraes
 * @since 29/05/2020
 */
@Getter
public enum ConsumoReceiverPayloadEnum {
	
	EQUIPAMENTO(0),
	TENSAO(1),
	CORRENTE(2),
	DATA(3);
	
	private int posicao;
	
	ConsumoReceiverPayloadEnum(int posicao) {
		this.posicao = posicao;
	}
	
}
