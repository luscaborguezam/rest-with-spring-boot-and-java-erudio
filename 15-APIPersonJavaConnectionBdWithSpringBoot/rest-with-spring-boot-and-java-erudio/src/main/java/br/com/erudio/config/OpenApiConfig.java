package br.com.erudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	/*Definir o bin, É um objeto montado e instanciado gerenciado pelo spring IOC(container)*/
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
					.title("REST API's RESTful from 0 with Java, Spring Boot, Kubernetes and Docker")
						.version("V1")
						.description("REST API's RESTful from 0 with Java, Spring Boot, Kubernetes and Docker")
						.termsOfService("https://github.com/luscaborguezam/rest-with-spring-boot-and-java-erudio")
						.license(new License()
								.name("Apache 2.0")
								.url("https://github.com/luscaborguezam/rest-with-spring-boot-and-java-erudio"))
			);
	}
}
