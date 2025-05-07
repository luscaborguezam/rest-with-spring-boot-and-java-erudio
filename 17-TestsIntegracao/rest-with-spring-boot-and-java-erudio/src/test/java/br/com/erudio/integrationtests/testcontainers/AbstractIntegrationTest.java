package br.com.erudio.integrationtests.testcontainers;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;


@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

	/**
	 *  Initializer.class é a classe respopnssável por criar um container do mysql dinamicamente através do testcontainers
	 */
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		//CONTAINER DO MySQL 
		//Especificando versão da image do docker hub
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.3.0");

		//CRIA O CONTAINER DO MYSQL
		private static void startContainers() {
			//Pega os parametros definidos na variável mysql e itera sobre eles
			Startables.deepStart(Stream.of(mysql)).join();
		}
		
		//DEFINIR AS CONFIGURAÇÕES DE CONEXAO COM O BANCO
		//PARTE MAIS SENSÍVEL POIS O BANCO É CRIADO DINAMICAMENTE, A CADA URL O USUÁRIO E SENHA VÃO MUDAR.
		//
		private static Map<String, Object> createConnectionConfiguration() {
			//USAR AS CONFIGURAÇÕES DO APLICATION PROPERTIES/YAML PRINCIPAL DO PROJETO E ADAPTALAS
			return Map.of(
					"spring.datasource.url", mysql.getJdbcUrl(),
					"spring.datasource.username", mysql.getUsername(),
					"spring.datasource.password", mysql.getPassword()
			);
		}
		
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			//OBTEM AS VARIÁVEIS DE AMBIENTE (anotations, classe de configurações, aplication.yaml/properties)
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			
			//CONFIGURAÇÃO DO TESTCONTAINERS, 
			MapPropertySource testcontainers = new MapPropertySource("testcontainers", 
					createConnectionConfiguration());
			
			//ADICIONA AS CONFIGURAÇÕES DEFINIDAS NO testcontainers ANTES DE QUALQUER OUTRA NO AMBIENTE DO SPRING
			environment.getPropertySources().addFirst(testcontainers);			
			
			
		}



	}
}
