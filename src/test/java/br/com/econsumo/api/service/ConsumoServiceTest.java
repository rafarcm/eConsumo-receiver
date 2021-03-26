package br.com.econsumo.api.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.econsumo.api.config.AppConfig;
import br.com.econsumo.api.config.MensagensConfig;
import br.com.econsumo.api.exception.ConsumoException;
import br.com.econsumo.api.model.entity.Consumo;
import br.com.econsumo.api.model.repository.ConsumoRepository;
import br.com.econsumo.api.service.impl.ConsumoServiceImpl;
import br.com.econsumo.api.validator.ConsumoValidator;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {AppConfig.class, MensagensConfig.class, ConsumoValidator.class})
@TestPropertySource(locations = {"classpath:application-test.properties", "classpath:ValidationMessages.properties"})
public class ConsumoServiceTest {
	
	@SpyBean
	private ConsumoServiceImpl service;
	
	@MockBean
	private ConsumoRepository repository;
	
	@SpyBean
	private MensagensConfig mensagensConfig;
	
	private static Consumo consumoBaseDados;
	private static String payloadValido;
	
	@BeforeAll
	public static void setUp() {
		consumoBaseDados = Consumo.builder().id("1").equipamento("123").tensao(127.0).corrente(50.0).data(LocalDateTime.now()).build();
		payloadValido = "123;127.0;50.0;27-05-2020 18:55:56";
	}
	
	@Test
	public void deveSalvarConsumo() {
		//Cenário
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação
		final Consumo consumoSalvo = service.salvarConsumo(new Consumo());
		
		//Verificação
		Assertions.assertThat(consumoSalvo).isNotNull().isEqualTo(consumoBaseDados);
	}
	
	@Test
	public void deveSalvarConsumoPayload() {
		//Cenário
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação
		final Consumo consumoSalvo = service.salvarConsumo(payloadValido);
		
		//Verificação
		Assertions.assertThat(consumoSalvo).isNotNull().isEqualTo(consumoBaseDados);
	}
	
	@Test
	public void deveRetornarErroDadoConsumoNaoInformadoAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getDadosConsumoNull()));
	}
	
	@Test
	public void deveRetornarErroEquipamentoNaoInformadoAoSalvarConsumoPayload() {
		//Cenário
		final String payload = ";127.0;50.0;27-05-2020 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getEquipamentoNull()));
	}
	
	@Test
	public void deveRetornarErroTensaoNaoInformadaAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;;50.0;27-05-2020 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getTensaoNull()));
	}
	
	@Test
	public void deveRetornarErroCorrenteNaoInformadaAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;;27-05-2020 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getCorrenteNull()));
	}
	
	@Test
	public void deveRetornarErroDataNaoInformadaAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;50.0; ";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getDataNull()));
	}
		
	@Test
	public void deveRetornarErroDadoConsumoInvalidoAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;50.0;27-05-2020 18:55:56;123";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getDadosConsumoInvalida()));
	}
	
	@Test
	public void deveRetornarErroTensaoInvalidaAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;T;50.0;27-05-2020 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getTensaoInvalida()));
	}
	
	@Test
	public void deveRetornarErroCorrenteInvalidaAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;C;27-05-2020 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getCorrenteInvalida()));
	}
	
	@Test
	public void deveRetornarErroDataInvalidaExisteAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;50.0;D";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getDataInvalida()));
	}
	
	@Test
	public void deveRetornarErroDataInvalidaComDataQueNaoExisteAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;50.0;30-02-2020 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getDataInvalida()));
	}
	
	@Test
	public void deveRetornarErroDataInvalidaComDataQMaiorQueHojeAoSalvarConsumoPayload() {
		//Cenário
		final String payload = "123;127.0;50.0;20-05-2500 18:55:56";
		Mockito.when(repository.save(Mockito.any(Consumo.class))).thenReturn(consumoBaseDados);
		
		//Ação + Verificação
		final ConsumoException thrown = assertThrows(ConsumoException.class, () -> service.salvarConsumo(payload));
		assertTrue(thrown.getMessage().contains(mensagensConfig.getDataInvalida()));
	}
	
	@Test
	public void deveBuscarConsumos(@Autowired ConsumoRepository consumoRepository) {
		// Cenário
		final List<Consumo> consumosBaseDados = new ArrayList<Consumo>();
		consumosBaseDados.add(consumoBaseDados);
		Mockito.when(repository.findByEquipamentoAndDataBetween(Mockito.anyString(), Mockito.any(LocalDateTime.class),
				Mockito.any(LocalDateTime.class))).thenReturn(consumosBaseDados);

		// Ação
		final List<Consumo> consumos = service.buscar("123", LocalDate.now());

		// Verificação
		Assertions.assertThat(consumos).isNotNull().isNotEmpty().contains(consumoBaseDados);
	}

}
