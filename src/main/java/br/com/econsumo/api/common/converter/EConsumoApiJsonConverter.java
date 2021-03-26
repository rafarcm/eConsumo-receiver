package br.com.econsumo.api.common.converter;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import br.com.econsumo.api.common.entity.EConsumoApiEntity;
import br.com.econsumo.api.common.json.EConsumoApiJson;

public interface EConsumoApiJsonConverter<T extends EConsumoApiEntity, K extends EConsumoApiJson>
		extends Function<T, K> {

	T toEntity(K json);
	
	K toJson(T entity);
	
	List<T> toListEntity(List<K> jsons);
	
	List<K> toListJson(List<T> entitys);
	
	Set<T> toSetEntity(Set<K> jsons);
	
	Set<K> toSetJson(Set<T> entitys);
	
	default K apply(T entity) {
		return toJson(entity);
	}
}
