package br.com.econsumo.api.receiver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * Classe responsável pela configuração do Mongo
 * <br>Utilizado para remover o campo _class do banco de dados
 * 
 * @author Rafael Moraes
 * @since 27/05/2020
 */
@Configuration
public class MongoAppConfig {

	@Autowired
	private MongoDatabaseFactory mongoDbFactory;
	@Autowired
	private MongoMappingContext mongoMappingContext;

	@Bean
	public MappingMongoConverter mappingMongoConverter() {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		//remove o campo _class do banco de dados
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return converter;
	}
}
