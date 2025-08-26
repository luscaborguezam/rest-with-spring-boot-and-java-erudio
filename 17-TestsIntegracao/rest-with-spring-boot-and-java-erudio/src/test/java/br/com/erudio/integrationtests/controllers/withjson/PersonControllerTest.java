package br.com.erudio.integrationtests.controllers.withjson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.dto.PersonDTO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Criar ordem de teste
class PersonControllerTest extends AbstractIntegrationTest{


	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static PersonDTO person;
	
	/**
	 * Metodo inicializa tudo que é necessário antes dos testes iniciarem
	 * @throws Exception
	 */
	//@BeforeEach // Cada teste cria uma nova instancia
	@BeforeAll //Uma instancia para todos os testes
	static void setUp() throws Exception {
		objectMapper = new ObjectMapper();
		//Desabilitar verificação de atributos desconhecidos no json
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		person = new PersonDTO();
	}
	
	@Test
	@Order(1)
	void create() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		//Configurar request
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ACCEPT)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Logs de request
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))//Logs de response
				.build();
		
		//
		var content = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(person)
				// Quando ocorrer um post ...
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		//Desserializar e converter para objeto a resposta no body
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		/*Validações*/
		//is null?
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		//é verdadeiro?
		assertTrue(createdPerson.getId() > 0);
		//Os dados são iguais ao esperado?
		assertEquals("Richard",createdPerson.getFirstName());
		assertEquals("Stallman",createdPerson.getLastName());
		assertEquals("New york City - New York - USA",createdPerson.getAddress());
		assertEquals("Male",createdPerson.getGender());
		
	}

	
	@Test
	@Order(2)
	void createWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		
		//Configurar request
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_NOT_ACCEPT)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Logs de request
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))//Logs de response
				.build();
		
		//
		var content = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(person)
				// Quando ocorrer um post ...
				.when()
					.post()
				.then()
					.statusCode(403)
				.extract()
					.body()
					.asString();
		
		/*Validações*/
		//Os dados são iguais ao esperado?
		assertEquals("Invalid CORS request", content);
		
	}

	@Test
	@Order(3)
	void findById() throws JsonMappingException, JsonProcessingException {
		//Configurar request
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Logs de request
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))//Logs de response
				.build();
		
		//
		var content = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.pathParam("id", person.getId())
				// Quando ocorrer um get ...
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		//Desserializar e converter para objeto a resposta no body
		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		person = createdPerson;
		
		/*Validações*/
		//is null?
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		//é verdadeiro?
		assertTrue(createdPerson.getId() > 0);
		//Os dados são iguais ao esperado?
		assertEquals("Richard",createdPerson.getFirstName());
		assertEquals("Stallman",createdPerson.getLastName());
		assertEquals("New york City - New York - USA",createdPerson.getAddress());
		assertEquals("Male",createdPerson.getGender());
	}
	
	@Test
	@Order(4)
	void findByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		//Configurar request
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_NOT_ACCEPT)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL)) //Logs de request
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))//Logs de response
				.build();
				
		//
		var content = given(specification)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.pathParam("id", person.getId())
				// Quando ocorrer um get ...
				.when()
					.get()
				.then()
					.statusCode(403)
				.extract()
					.body()
					.asString();
		
		
		/*Validações*/
		assertEquals("Invalid CORS request", content);
	}

	
	@Test
	void update() {
		
	}
	
	@Test
	void delete() {
		
	}
	
	@Test
	void findAll() {
		
	}

	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New york City - New York - USA");
		person.setGender("Male");
		
	}
}
