package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * CLASSE CONTEM OS SERVIÇOS PARA O OBJETO PERSON
 */
@Service
public class PersonServices {

	/**
	 * Objeto Logger, que é usado para registrar mensagens de log em uma aplicação Java.
	 *  Logger.getLogger(PersonServices.class.getName()): Este é o método estático de fábrica para obter uma instância de Logger. 
	 *  Ele recebe um parâmetro que é o nome da classe para a qual você deseja obter o logger. 
	 *  PersonServices.class.getName() retorna o nome da classe PersonServices, que é usado como nome do logger. 
	 *  O nome da classe é geralmente usado como nome do logger para identificar a origem das mensagens de log.
	 */
	private Logger  logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	/**
	 * MÉTODO MOC
	 * @return LIST PERSON MOKADO
	 */
	public List<Person> findAll(){
		logger.info("Finding all people");

		return repository.findAll();
	}

	/**
	 * BUSCA PERSON NA BASE DE DADOS PELO ID UTILIZANDO REPOSITORY.
	 * @param id
	 * @return
	 */
	public Person findById(Long id){
		logger.info("Finding one person!");

		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not records found fir this ID"));
				/*Quando não encontrar o ID, vai lançar com uma função lambida uma exceção de "Não encontrado"*/
	}

	/**
	 * SALVA PERSON NA BASE DE DADOS UTILIZANDO REPOSITORY
	 * @param person
	 * @return
	 */
	public Person create(Person person){
		logger.info("Creating one persons!");
		
		return repository.save(person);
	}

	/**
	 * ALTERA PERSON NA BASE DE DADOS UTILIZANDO REPOSITORY
	 * @param person
	 * @return
	 */
	public Person update(Person person){
		logger.info("Update one persons!");
		
		/*Para realizar o update, precisa dos dados anteriores*/
		//Pode utilizar a classe person ou var
		var entity = repository.findById(person.getId())
		.orElseThrow(() -> new ResourceNotFoundException("Not records found fir this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		
		return repository.save(person);
	}

	/**
	 * DELETA PERSON NA BASE DE DADOS UTILIZANDO REPOSITORY
	 * @param id
	 */
	public void delete(Long id){
		logger.info("Delete the persons "+id+"!");
		
		/*Ao rceber somente o id, não se sabe qual objeto se quer deletar, 
		 * por isso é necessário recuperar o objeto para que o repository possa apagar
		 */
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not records found fir this ID"));
		
		repository.delete(entity);
		
	}
	
	
	
	
	/*Códigos substituídos*/
	/**
	 * TODO: MOCADO
	 * MÉTODO RETORNA OBJETO PERSON MOCADO
	 * @param i
	 * @return person -> OBJETO PERSON
	 */
	/*
	private Person mokedPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Name person "+i);
		person.setLastName("Last name Person "+i);
		person.setAddress("Some address in Brasil "+i);
		person.setGender("Male");
		return person;
	}
	*/
	
	
}