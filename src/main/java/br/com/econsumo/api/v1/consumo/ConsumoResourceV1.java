package br.com.econsumo.api.v1.consumo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.config.MensagensConfig;
import br.com.econsumo.api.exception.ConsumoException;
import br.com.econsumo.api.service.ConsumoService;

@RestController
@RequestMapping("/econsumo-api/v1/consumos")
@Validated
public class ConsumoResourceV1 {
	
	@Autowired
	private ConsumoService service;
	
	@Autowired
	private ConsumoConverterV1 converter;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private MensagensConfig mensagensConfig;
	
	@RequestMapping(method = RequestMethod.GET, path = "/equipamento/{equipamento}/data/{data}")
	@SuppressWarnings("rawtypes")
	public ResponseEntity getByEquipamentoAndData(
			@PathVariable("equipamento") @NotBlank(message = "{equipamento.not.empty}") @Valid final String equipamento, 
			@PathVariable("data") @NotBlank(message = "{data.not.null}") @Valid final String data) {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(appConfig.getFormatoData());
			sdf.setLenient(false);
			final Date dataLeitura = sdf.parse(data);
			final List<ConsumoJson> consumos = this.converter.toListJson(
					this.service.buscar(equipamento, dataLeitura.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
			
			if(CollectionUtils.isEmpty(consumos)) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(consumos);
		} catch (ConsumoException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		} catch (ParseException e) {
			return ResponseEntity.badRequest().body(mensagensConfig.getDataInvalida());
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}

}
