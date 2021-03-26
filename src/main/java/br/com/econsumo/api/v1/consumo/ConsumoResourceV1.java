package br.com.econsumo.api.v1.consumo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.config.MensagensConfig;
import br.com.econsumo.api.error.CreateErrorResponse;
import br.com.econsumo.api.exception.ConsumoException;
import br.com.econsumo.api.model.entity.Consumo;
import br.com.econsumo.api.service.ConsumoService;

@Validated
@RestController
@RequestMapping("/econsumo-api/v1/consumos")
public class ConsumoResourceV1 {
	
	@Autowired
	private ConsumoService consumoService;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private MensagensConfig mensagensConfig;
	
	@GetMapping
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity buscar(
			@RequestParam(required = false) @NotEmpty(message = "{equipamento.not.empty}") @Valid String equipamento, 
			@RequestParam(required = false) @NotEmpty(message = "{data.not.null}") @Valid String data) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(appConfig.getFormatoData());
			sdf.setLenient(false);
			final Date dataLeitura = sdf.parse(data);
			final List<Consumo> consumos = consumoService.buscar(equipamento, dataLeitura.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			return new ResponseEntity(consumos, HttpStatus.OK);
		} catch (ConsumoException ex) {
			return CreateErrorResponse.createErrorResponseEntity(ex, HttpStatus.BAD_REQUEST);
		} catch (ParseException e) {
			return CreateErrorResponse.createErrorResponseEntity(new ConsumoException(mensagensConfig.getDataInvalida()), HttpStatus.BAD_REQUEST, data);
		}
	}

}
