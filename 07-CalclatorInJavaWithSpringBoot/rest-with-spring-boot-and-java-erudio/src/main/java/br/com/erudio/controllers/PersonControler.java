package br.com.erudio.Controllers;

import java.util.concurrent.atomic.AtomicLong;

import javax.swing.DefaultRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.services.PersonServices;
import br.com.erudio.converters.NumberConverter;


/**
 * Classe criada para fazer o mapeamento e controle dos endpoints de ... .
 * Este controlador é responsável por fazer o controle de endpoints que fazem ... .
 * 
 * @author Lucas Borguezam
 * @since 19 de Março de 2024
 */

//Anotation para manipular endpoints
@RestController
public class PersonControler {	
	
	@Autowired
	private PersonServices service;

	/*@RequestMapping: Esta anotação mapeia o método sum() para o endpoint /sum. 
	 * Apenas para solicitações HTTP GET.
	 */
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sum(
			//Define os parâmetros passados na url usando '?name=textodesejado'
			@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo
			) throws Exception {
		/**
		 * Esse método retorna a soma entre numberOne e numberTwo
		 * 
		 * @param numberOne -> primeiro número para a soma
		 * @param numberTwo -> segundo número para a soma.
		 * 
		 * @return Retorna o valor da adição entre o parametro numberOne e numberTwo.
		 * */
		//Verificar se a string é numérica.
		 if(!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
			 throw new UnsupportedMathOperationException("Please set a numeric value!");
		 }
		return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
	
	}//sum()

	
	
}//PersonControler{}
