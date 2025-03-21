package br.com.erudio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Date;
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

import br.com.erudio.dto.BookDTO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Book;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.unittests.mapper.mocks.MockBook;

/**
 * Classe de teste, utiliza dados mockados cirados com Mockito.
 * 
 * Obs.: A Lógica de cada método é extraida do comportamento que o metodo da classe PersonService faz.
 */
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

	
	/*Objeto de mock*/
	MockBook input;
	
	/*Essa classe junto com as bibliotecas, faz mock de tudo que a classe BookServices, 
	 * e o @InjectMocks faz a injeção de mocks para as classes dentro dela, 
	 * principalmente o PersonRepository que é o objeto que representa os registros da base de dados.*/
	@InjectMocks 
	private BookServices service; /*Injeção dos mocks em book service*/
	
	/*Mock de repository. Mock da instancia de repository*/
	@Mock
	BookRepository repository;
	
	/**
	 * Metodo colcoa os valores no objeto mockado
	 * @throws Exception
	 */
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
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
		//Mocks
		Book entity = input.mockEntity(1); 
		//input.mockEntity() Retorna new Book mas não define o id por isso é necessário mockar um.
		entity.setId(1L);
		/*Quando o service do teste chamar o repository.findById recebendo o parâmetro,
		 * então vai ser retornado o Optional de book = input.mockEntity(1)*/
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		
		/*Verificações*/
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("POST")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("PUT")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("DELETE")
					));
		
//System.out.println(result.toString()); -> use para achar o padrão para a comparação abaixo
		//assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockBook.mockEntity()
		assertEquals("Author Test 1", result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertEquals(1D, result.getPrice());
		assertEquals("Title Test 1", result.getTitle());
		
	}

	/* Verificar em posições específicas da lista de entidades, para garantir que tenham ids e os valores não sejam nulos
	 * 1- Mockar Lista de objetos 
	 * 2- Verificar se a lista é nula (Não deve ser)
	 * 3- Verificar se o tamnho é igual ao que o método retorna
	 * 4- Verificar objetos em posições aleatórias e fixas da lista 1,4,7
	 */
	@Test
	void testFindAll() {
		List<Book> list = input.mockEntityList(); 
		
		when(repository.findAll()).thenReturn(list);
		List<BookDTO> book = service.findAll();
		
		/*Verificações*/
		assertNotNull(book);
		assertEquals(14, book.size());
		
	
		BookDTO bookOne = book.get(1);
		/*Verificações*/
		assertNotNull(bookOne);
		assertNotNull(bookOne.getKey());
		assertNotNull(bookOne.getLinks());
		assertNotNull(bookOne.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(bookOne.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("GET")
					));
		assertNotNull(bookOne.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("POST")
					));
		assertNotNull(bookOne.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("PUT")
					));
		assertNotNull(bookOne.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("DELETE")
					));
		//As strings esperada é definida em -> MockBook.mockEntity()
		assertEquals("Author Test 1", bookOne.getAuthor());
		assertNotNull(bookOne.getLaunchDate());
		assertEquals(1D, bookOne.getPrice());
		assertEquals("Title Test 1", bookOne.getTitle());
		
		var bookFour = book.get(4);
		/*Verificações*/
		assertNotNull(bookFour);
		assertNotNull(bookFour.getKey());
		assertNotNull(bookFour.getLinks());
		assertNotNull(bookFour.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("api/book/v1/4")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(bookFour.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(bookFour.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("POST")
					));
		
		assertNotNull(bookFour.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("PUT")
					));
		
		assertNotNull(bookFour.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("api/book/v1/4")
						&& link.getType().equals("DELETE")
					));
		//As strings esperada é definida em -> MockBook.mockEntity()
		assertEquals("Author Test 4", bookFour.getAuthor());
		assertNotNull(bookFour.getLaunchDate());
		assertEquals(4D, bookFour.getPrice());
		assertEquals("Title Test 4", bookFour.getTitle());
		
		var bookSeven = book.get(7);
		/*Verificações*/
		assertNotNull(bookSeven);
		assertNotNull(bookSeven.getKey());
		assertNotNull(bookSeven.getLinks());
		assertNotNull(bookSeven.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("api/book/v1/7")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(bookSeven.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(bookSeven.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("POST")
					));
		
		assertNotNull(bookSeven.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("PUT")
					));
		
		assertNotNull(bookSeven.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("api/book/v1/7")
						&& link.getType().equals("DELETE")
					));
		//As strings esperada é definida em -> MockBook.mockEntity()
		assertEquals("Author Test 7", bookSeven.getAuthor());
		assertNotNull(bookSeven.getLaunchDate());
		assertEquals(7D, bookSeven.getPrice());
		assertEquals("Title Test 7", bookSeven.getTitle());
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
		Book entity = input.mockEntity(1); 
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookDTO vo = input.mockVO(1);
		
		/*Quando o service do teste chamar o repository.save(entity),
		 * então vai ser retornado a entidade persistida*/
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		/*Verificações*/
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		//assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockBook.mockEntity()
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("POST")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("PUT")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("DELETE")
					));
		
		assertEquals("Author Test 1", result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertEquals(1D, result.getPrice());
		assertEquals("Title Test 1", result.getTitle());
		
	}

	/* Simular se o retorno da exceção ao tentar criar um usuário com o objeto nulo está retornando a menssagem esperada. 
	 * 
	 *  1- Objeto é null então é necessário criar a exceção ao rodar create(null)
	 *  2- Validar se a menssagem atual contem a menssagem experada
	 */
	@Test
	void createWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class,
		() -> {
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
		Book entity = input.mockEntity(1); 
		
		Book persisted = entity;
		persisted.setId(1L);
		
		BookDTO vo = input.mockVO(1);
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
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("self")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("findAll")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("GET")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("create")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("POST")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("update")
						&& link.getHref().endsWith("api/book/v1")
						&& link.getType().equals("PUT")
					));
		
		assertNotNull(result.getLinks().stream()
				.anyMatch(link -> link.getRel().value().equals("delete")
						&& link.getHref().endsWith("api/book/v1/1")
						&& link.getType().equals("DELETE")
					));
		
		//assertTrue(result.toString().contains("links: [</book/v1/1>;rel=\"self\"]"));
		//As strings esperada é definida em -> MockBook.mockEntity()
		assertEquals("Author Test 1", result.getAuthor());
		assertNotNull(result.getLaunchDate());
		assertEquals(1D, result.getPrice());
		assertEquals("Title Test 1", result.getTitle());
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
	 * 4 - Verificar que o findById() e do delete() foi chamado e que foi chamado somente uma vez
	 * 5 - não teve mais interações no repositório.
	 */
	@Test
	void testDelete() {
		Book entity = input.mockEntity(1); 
		entity.setId(1L);
		
		/*Quando o service do teste chamar o repository.findById recebendo o parâmetro,
		 * então vai ser retornado o Optional de book = input.mockEntity(1)*/
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
		//Garantir que o findById() e do delete() foi chamado e que foi chamado somente uma vez
		verify(repository, times(1)).findById(anyLong());
		verify(repository, times(1)).delete(any());
		verifyNoMoreInteractions(repository);
		
		
	}

}
