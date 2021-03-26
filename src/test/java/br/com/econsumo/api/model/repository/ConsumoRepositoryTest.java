package br.com.econsumo.api.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.econsumo.api.model.entity.ConsumoEntity;
import br.com.econsumo.api.model.repository.ConsumoRepository;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ConsumoRepositoryTest {

	@Test
	public void deveBuscarConsumosPorEquipamentoEData(@Autowired ConsumoRepository consumoRepository) {
		// Cenário
		final LocalDateTime data = LocalDateTime.now();
		final ConsumoEntity consumoSave = consumoRepository.save(
				ConsumoEntity.builder().equipamento("123").tensao(127.0).corrente(50.0).data(data).build());
		final ConsumoEntity consumoCompare = consumoRepository.findById(consumoSave.getId()).get();
		
		// Ação
		final List<ConsumoEntity> consumos = consumoRepository.findByEquipamentoAndDataBetween("123",
				data.toLocalDate().atStartOfDay(), data.toLocalDate().atTime(23, 59, 59, 9999));

		// Verificação
		Assertions.assertThat(consumos).isNotNull().isNotEmpty().contains(consumoCompare);
	}

}
