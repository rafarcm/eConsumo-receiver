package br.com.econsumo.api.common.converter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import br.com.econsumo.api.common.entity.EConsumoApiEntity;
import br.com.econsumo.api.common.json.EConsumoApiJson;
/**
 * Classe abstrata que realiza a conversão de Objetos Entity e Json
 * 
 * @author Rafael Moraes
 * @since 26/03/2021
 *
 * @param <T> - Object Entity
 * @param <K> - Object Json
 */
public abstract class AbstractEConsumoApiJsonConverter<T extends EConsumoApiEntity, K extends EConsumoApiJson>
		implements EConsumoApiJsonConverter<T, K> {

	@Override
	public T toEntity(K json) {
		throw new UnsupportedOperationException("Operação não disponível");
	}

	@Override
	public K toJson(T entity) {
		throw new UnsupportedOperationException("Operação não disponível");
	}

	@Override
	public List<T> toListEntity(List<K> jsons) {
		if (CollectionUtils.isEmpty(jsons)) {
			return Collections.emptyList();
		}
		return jsons.stream().map(this::toEntity).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public List<K> toListJson(List<T> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return Collections.emptyList();
		}
		return entitys.stream().map(this::toJson).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public Set<T> toSetEntity(Set<K> jsons) {
		if (CollectionUtils.isEmpty(jsons)) {
			return Collections.emptySet();
		}
		return jsons.stream().map(this::toEntity).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	@Override
	public Set<K> toSetJson(Set<T> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return Collections.emptySet();
		}
		return entitys.stream().map(this::toJson).filter(Objects::nonNull).collect(Collectors.toSet());
	}
	
	

}
