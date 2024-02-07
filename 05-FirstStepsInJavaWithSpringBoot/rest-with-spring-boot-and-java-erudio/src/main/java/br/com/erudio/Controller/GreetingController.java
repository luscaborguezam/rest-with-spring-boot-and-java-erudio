package br.com.erudio.Controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.Entitis.Greeting;

/**
 * Classe criada para fazer o controle do end point greeting
 * Este controlador é responsável por gerar saudações personalizadas 
 * com base no nome fornecido na solicitação HTTP e retorná-las como objetos JSON. 
 * Ele é acessado por meio do endpoint /greeting
 * 
 * @author Lucas Borguezam
 * @since 06 de Fevereiro de 2024
 */

//Anotation para manipular endpoints
@RestController
public class GreetingController {
	
	/**
	 * TEMPLATE: Representa o padrão da saudação
	 */
	private static final String TEMPLATE = "Hello, %s!";
	/**
	 * counter: id mocado
	 */
	private final AtomicLong counter = new AtomicLong();
	
	/*@RequestMapping: Esta anotação mapeia o método greeting() para o endpoint /greeting. 
	 * Qualquer solicitação HTTP feita para /greeting será manipulada por este método.*/
	@RequestMapping("/greeting")
	public Greeting greeting(
			//Define os parâmetros passados na url usando '?name=textodesejado'
			@RequestParam(value = "name", defaultValue = "world") String name) {
		/**
		 * Esse método retorna o end point greeting.
		 * 
		 * @param name, usa a anotação @RequestParam para receber um parâmetro de consulta 
		 * chamado name da solicitação HTTP. Se nenhum parâmetro name for fornecido, o valor padrão será "world" 
		 * 
		 * @return Retorna uma nova instância de Greeting, onde o ID é gerado automaticamente 
		 * e o conteúdo é preenchido usando o padrão de saudação TEMPLATE com o nome fornecido na solicitação
		 * */
		
		return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
	}
	
}
