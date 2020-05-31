package br.com.econsumoreceiver.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.econsumoreceiver.api.dto.ConsumoDTO;
import br.com.econsumoreceiver.error.CreateErrorResponse;
import br.com.econsumoreceiver.exception.ConsumoException;
import br.com.econsumoreceiver.model.entity.Consumo;
import br.com.econsumoreceiver.service.ConsumoService;

@RestController
@RequestMapping("/econsumoreceiver/api/consumo")
public class ConsumoController {
	
	@Autowired
	private ConsumoService consumoService;
	
	@GetMapping
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity buscar(@RequestBody @Valid ConsumoDTO dto) {
		try {
			final List<Consumo> consumos = consumoService.buscar(dto.getEquipamento(), dto.getDataInicial(), dto.getDataFinal());
			return new ResponseEntity(consumos, HttpStatus.OK);
		} catch (ConsumoException ex) {
			return CreateErrorResponse.createErrorResponseEntity(ex, ConsumoDTO.class, HttpStatus.BAD_REQUEST);
		}
	}

}
