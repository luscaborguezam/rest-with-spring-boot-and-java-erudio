package br.com.erudio.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.erudio.serialization.converter.YamlJackson2HttpMesageConverter;

/*Quando a aplicação subir o contexto ao iniciar, ele precisa ler essa classe 
 * porque nela tem configurações sobre o comportamento da aplicação vai encontrar
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	/**
	 * Busca as propriedades definidas no "cors.originsPatterns"
	 */
	@Value("${cors.originPatterns}")
	private String corsOriginPatterns = "";
	
	
	/**
	 * Método de confiuuração para gerenciar cors globalmente
	 * -> Refactor -> implements methods -> addCorsMappings
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		/*Lista de origens permitidas*/
		var allowedOrigins = corsOriginPatterns.split(",");
		
		//Regras de mapeamento para cors
		registry.addMapping("/**")//todas requisições vão ser filtradas por cors
			.allowedOrigins(allowedOrigins)
			//.allowedMethods("POST","POST", "PUT", "DELETE", "OPTIONS")
			//     OU
			.allowedMethods("*")
				.allowCredentials(true);
	}

	public static final MediaType MEDIA_TYPE_APLICATION_YAML = MediaType.valueOf("application/x-yaml");
	
	/**
	 * METODO NECESSÁRIO PARA REALIZAR A CONVERSÃO PARA YAML
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMesageConverter());
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		/* VIA HEADER PARAM http://localhost:8080/person/v1?mediaType=xml */
		configurer.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("json", MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("x-yaml", MEDIA_TYPE_APLICATION_YAML);
	}

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		// TODO Auto-generated method stub
//		WebMvcConfigurer.super.addCorsMappings(registry);
//	}
	

	
}
