package br.com.erudio.controllers;

import br.com.erudio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.erudio.services.PersonServices;

import java.util.List;


/**
 * Classe criada para fazer o mapeamento e controle dos endpoints de PERSON
 * Este controlador é responsável por fazer o controle de endpoints que fazem CRUD
 *
 * TODO: ESSA VERSÃO TEM USO DO MOC (ESTRUTURAS FICTICIAS OU CONSTANTES)
 * 
 * @author Lucas Borguezam
 * @since 23 de Março de 2024
 */

//Anotation para manipular endpoints
@RestController
@RequestMapping("/person")
public class PersonControler {	
	
	@Autowired
	private PersonServices service;
	
	/**
	 * Retornar uma lista de objetos
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll(){
		return service.findAll();
	}

	/**
	 * MÉTODO QUE PROCURA NA BASE DADOS DE UMA PERSON PELO SEU ID POR MEIO DO GET HTTP
	 * @param id
	 * @return OBJETO PERSON EM JSON
	 * @throws Exception
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findByid(@PathVariable(value = "id") Long id) throws Exception {
		
		return service.findById(id);
	}//sum()

	/**
	 * MÉTODO CRIA UM PERSON NA BASE POR MEIO DO POST HTTP
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) throws Exception {
		return service.create(person);
	}//sum()

	/**
	 * MÉTODO ALTERA UM PERSON NA BASE POR MEIO DO PUT HTTP
	 * @param person
	 * @return person -> dados alterados
	 * @throws Exception
	 */
	@PutMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@RequestBody Person person) throws Exception {
		return service.update(person);
	}

	/**
	 * TODO: MOCADO
	 * MÉTODO DELETA UM PERSON NA BASE POR MEIO DO DELETE HTTP
	 * @param id
	 * @throws Exception
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable( value = "id") Long id) throws Exception {
		service.delete(id);
		
		/*O seu retorno está com status code 200, mas deve retornar 204_NO_CONTENT 
		 *quando deletar o registro, para isso:
		 */
		return ResponseEntity.noContent().build();
	}
	
}//PersonControler{}
