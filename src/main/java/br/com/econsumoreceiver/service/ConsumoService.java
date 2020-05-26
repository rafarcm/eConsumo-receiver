package br.com.econsumoreceiver.service;

import br.com.econsumoreceiver.exception.ConsumoException;

public interface ConsumoService {
	
	public void salvarConsumo(String dadosConsumo) throws ConsumoException;

}
