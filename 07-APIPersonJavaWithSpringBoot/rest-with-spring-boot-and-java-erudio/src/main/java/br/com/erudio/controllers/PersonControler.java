package br.com.erudio.controllers;

import br.com.erudio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll(){
		return service.findAll();
	}

	/**
	 * TODO: MOCADO
	 * MÉTODO QUE PROCURA NA BASE DADOS DE UMA PERSON PELO SEU ID POR MEIO DO GET HTTP
	 * @param id
	 * @return OBJETO PERSON EM JSON
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findByid(@PathVariable(value = "id") String id) throws Exception {
		return service.findById(id);
	}//sum()

	/**
	 * TODO: MOCADO
	 * MÉTODO CRIA UM PERSON NA BASE POR MEIO DO POST HTTP
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(
			method=RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person) throws Exception {
		return service.create(person);
	}//sum()

	/**
	 * TODO: MOCADO
	 * MÉTODO ALTERA UM PERSON NA BASE POR MEIO DO PUT HTTP
	 * @param person
	 * @return person -> dados alterados
	 * @throws Exception
	 */
	@RequestMapping(
			method=RequestMethod.PUT,
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
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public void update(@PathVariable( value = "id") String id) throws Exception {
		service.delete(id);
	}
	
}//PersonControler{}
