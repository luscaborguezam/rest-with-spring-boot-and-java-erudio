package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

/**
 * CLASSE CONTEM OS SERVIÇOS PARA O OBJETO PERSON
 */
@Service
public class PersonServices {
	/**
	 * counter: id mocado (Ficticio)
	 */
	private final AtomicLong counter = new AtomicLong();

	/**
	 * Objeto Logger, que é usado para registrar mensagens de log em uma aplicação Java.
	 *  Logger.getLogger(PersonServices.class.getName()): Este é o método estático de fábrica para obter uma instância de Logger. 
	 *  Ele recebe um parâmetro que é o nome da classe para a qual você deseja obter o logger. 
	 *  PersonServices.class.getName() retorna o nome da classe PersonServices, que é usado como nome do logger. 
	 *  O nome da classe é geralmente usado como nome do logger para identificar a origem das mensagens de log.
	 */
	private Logger  logger = Logger.getLogger(PersonServices.class.getName());

	/**
	 * MÉTODO MOC
	 * @return LIST PERSON MOKADO
	 */
	public List<Person> findAll(){
		logger.info("Finding all people");

		List<Person> persons  = new ArrayList<>();
		for(int i = 0; i < 8; i++){
			persons.add(mokedPerson(i));
		}
		return persons;
	}

	/**
	 * TODO: MÉTODO MOCADO
	 * BUSCA PERSON PELO ID
	 *
	 * @param id
	 * @return
	 */
	public Person findById(String id){
		logger.info("Finding one person!");

		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Lusca");
		person.setLastName("Borguezam");
		person.setAddress("R. JJ, 000, Bairro, Cidade, SP, Brasil");
		person.setGender("Male");
		return person;
	}

	/**
	 * TODO: MOCADO
	 * MÉTODO RETORNA OBJETO PERSON MOCADO
	 * @param i
	 * @return person -> OBJETO PERSON
	 */
	private Person mokedPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Name person "+i);
		person.setLastName("Last name Person "+i);
		person.setAddress("Some address in Brasil "+i);
		person.setGender("Male");
		return person;
	}

	public Person create(Person person){
		logger.info("Creating one persons!");
		return person;
	}

	public Person update(Person person){
		logger.info("Update one persons!");
		return person;
	}

	public void delete(String id){
		logger.info("Delete the persons "+id+"!");

	}
}