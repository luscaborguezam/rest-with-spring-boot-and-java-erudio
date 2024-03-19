package br.com.erudio.services;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

/**
 * Objeto que será injetado em tempo de execução em outras classes da aplicação.
 * Não precisa Ficar instanciando manualmente.
 * 
 * Manualmente: PersonServices ps = new PersonServices();
 * 
 * o próprio spring fará essa instancia, basta usar a anotation @Autowired adima da variavel.
 * exemplo: 
 * 
 * @AutoWired
 * private PersonServices ps;
 * 
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
}
