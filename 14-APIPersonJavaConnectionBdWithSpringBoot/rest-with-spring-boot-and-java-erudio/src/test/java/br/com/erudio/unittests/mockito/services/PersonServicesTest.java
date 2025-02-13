package br.com.erudio.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unittests.mapper.mocks.MockPerson;

/**
 * Classe de teste, utiliza dados mockados cirados com Mockito.
 * 
 * Obs.: A Lógica de cada método é extraida do comportamento que o metodo da classe PersonService faz.
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

	
	/*Objeto de mock*/
	MockPerson input;
	
	/*Essa classe junto com as bibliotecas, faz mock de tudo que a classe PersonServices, 
	 * e o @InjectMocks faz a injeção de mocks para as classes dentro dela, 
	 * principalmente o PersonRepository que é o objeto que representa os registros da base de dados.*/
	@InjectMocks 
	private PersonServices service; /*Injeção dos mocks em person service*/
	
	/*Mock de repository. Mock da instancia de repository*/
	@Mock
	PersonRepository repository;
	
	/**
	 * Metodo colcoa os valores no objeto mockado
	 * @throws Exception
	 */
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}
	
	/**
	 * Metodo de teste que busca verificar se o médoto finfById está adicionando 
	 * Link HATEOAS, ou seja, espéra o link com um padrão específico
	 */
	@Test
	void testFindById() {
		/* Simular a busca de dados conforme o método findById.
		 * O método lida com uma entity já persistida, ou seja , játem o id
		 * faremos essa simulação:
		 * 1 - criar objeto com dados já persistidos.
		 */
		Person entity = input.mockEntity(1); 
		//input.mockEntity() Retorna new Person mas não define o id por isso é necessário mockar um.
		entity.setId(1L);
		
		/*Quando o service do teste chamar o repository.findById recebendo o parâmetro,
		 * então vai ser retornado o Optional de person = input.mockEntity(1)*/
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		/*Verificações*/
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		//System.out.println(result.toString()); -> use para achar o padrão para a comparação abaixo
		assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockPerson.mockEntity()
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}

	/* Verificar em posições específicas da lista de entidades, para garantir que tenham ids e os valores não sejam nulos
	 * 1- Mockar Lista de objetos 
	 * 2- Verificar se a lista é nula (Não deve ser)
	 * 3- Verificar se o tamnho é igual ao que o método retorna
	 * 4- Verificar objetos em posições aleatórias e fixas da lista 1,4,7
	 */
	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList(); 
		
		when(repository.findAll()).thenReturn(list);
		
		var peaple = service.findAll();
		assertNotNull(peaple);
		assertEquals(14, peaple.size());
		
	
		var peapleOne = peaple.get(1);
		/*Verificações*/
		assertNotNull(peapleOne);
		assertNotNull(peapleOne.getKey());
		assertNotNull(peapleOne.getLinks());
		assertTrue(peapleOne.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockPerson.mockEntity()
		assertEquals("Addres Test1", peapleOne.getAddress());
		assertEquals("First Name Test1", peapleOne.getFirstName());
		assertEquals("Last Name Test1", peapleOne.getLastName());
		assertEquals("Female", peapleOne.getGender());
		
		var peapleFour = peaple.get(4);
		/*Verificações*/
		assertNotNull(peapleFour);
		assertNotNull(peapleFour.getKey());
		assertNotNull(peapleFour.getLinks());
		assertTrue(peapleFour.toString().contains("links: [</person/v1/4>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockPerson.mockEntity()
		assertEquals("Addres Test4", peapleFour.getAddress());
		assertEquals("First Name Test4", peapleFour.getFirstName());
		assertEquals("Last Name Test4", peapleFour.getLastName());
		assertEquals("Male", peapleFour.getGender());
		
		var peapleSeven = peaple.get(7);
		/*Verificações*/
		assertNotNull(peapleSeven);
		assertNotNull(peapleSeven.getKey());
		assertNotNull(peapleSeven.getLinks());
		assertTrue(peapleSeven.toString().contains("links: [</person/v1/7>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockPerson.mockEntity()
		assertEquals("Addres Test7", peapleSeven.getAddress());
		assertEquals("First Name Test7", peapleSeven.getFirstName());
		assertEquals("Last Name Test7", peapleSeven.getLastName());
		assertEquals("Female", peapleSeven.getGender());
	}


	/* Simular a persistencia do dado conforme o metodo create, 
	 * o mock não cria o id, e o id só é criado após persistir o dado, faremos essa simulação
	 * 1 - criar objeto com dados (Entidade antes de chamar o repositório)
	 * 2 - Criar outro objeto que recebe o objeto mockado e adicionar o id. (Entidade após chamar o repositório)
	 * 
	 * Essa lógica é extraida do comportamento que o metodo da classe PersonService faz
	 */
	@Test
	void testCreate() {
		Person entity = input.mockEntity(1); 
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		
		/*Quando o service do teste chamar o repository.save(entity),
		 * então vai ser retornado a entidade persistida*/
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		/*Verificações*/
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockPerson.mockEntity()
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
		
	}

	/* Simular se o retorno da exceção ao tentar criar um usuário com o objeto nulo está retornando a menssagem esperada. 
	 * 
	 *  1- Objeto é null então é necessário criar a exceção ao rodar create(null)
	 *  2- Validar se a menssagem atual contem a menssagem experada
	 */
	@Test
	void createWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "Its not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	/* Simular a persistencia do dados conforme o método update.
	 * O método lida com uma entity já persistida, ou seja , játem o id
	 * faremos essa simulação:
	 * 1 - criar objeto com dados já persistidos
	 * 2 - mock de findById, pois o método faz a busca.
	 */
	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1); 
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setKey(1L);
		
		/*Quando service.update do teste chamar o repository.findById(1L) retornar a entity*/
		when(repository.findById(1L)).thenReturn(Optional.of(entity));		
		/*Quando o service do teste chamar o repository.save(entity),
		 * então vai ser retornado a entidade persistida*/
		when(repository.save(persisted)).thenReturn(persisted);
		
		var result = service.update(vo);
		/*Verificações*/
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</person/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockPerson.mockEntity()
		assertEquals("Addres Test1", result.getAddress());
		assertEquals("First Name Test1", result.getFirstName());
		assertEquals("Last Name Test1", result.getLastName());
		assertEquals("Female", result.getGender());
	}
	
	/* Simular se o retorno da exceção ao tentar criar um usuário com o objeto nulo está retornando a menssagem esperada. 
	 * 
	 *  1- Objeto é null então é necessário criar a exceção ao rodar create(null)
	 *  2- Validar se a menssagem atual contem a menssagem experada
	 */
	@Test
	void updateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.update(null);
		});
		
		String expectedMessage = "Its not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	/*
	 * A validação de um delete só precisa ser feita se houver regras de negócios envolvida, 
	 * pois o método é void
	 *
	 * Simular a deleção de dados conforme o método delete.
	 * O método lida com uma entity já persistida, ou seja , já tem o id
	 * faremos essa simulação:
	 * 1 - criar objeto com dados já persistidos
	 * 2 - mock de findById, pois o método faz a busca.
	 * 3 - deleção
	 */
	@Test
	void testDelete() {
		Person entity = input.mockEntity(1); 
		entity.setId(1L);
		
		/*Quando o service do teste chamar o repository.findById recebendo o parâmetro,
		 * então vai ser retornado o Optional de person = input.mockEntity(1)*/
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

}
