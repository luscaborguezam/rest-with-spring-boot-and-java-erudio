package br.com.erudio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*Quando a aplicação subir o contexto ao iniciar, ele precisa ler essa classe 
 * porque nela tem configurações sobre o comportamento da aplicação vai encontrar
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		/* Via Extension. http://localhost:8080/person/v1.xml DEPRECATED ON SpringBoot 2.6(ou algo assim) */
		
		/* VIA QUERY PARAM http://localhost:8080/person/v1?mediaType=xml */
		
		configurer.favorParameter(true)

		.parameterName("mediaType").ignoreAcceptHeader(true)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML);
	}
	

	
}
