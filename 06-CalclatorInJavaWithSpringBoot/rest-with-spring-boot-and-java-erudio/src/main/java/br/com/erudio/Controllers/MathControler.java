package br.com.erudio.Controllers;

import java.util.concurrent.atomic.AtomicLong;

import javax.swing.DefaultRowSorter;

import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.converters.NumberConverter;
import br.com.erudio.exceptions.UnsupportedMathOperationException;
import br.com.erudio.math.Math;

/**
 * Classe criada para fazer o mapeamento e controle dos endpoints de sum, sub, div e mult.
 * Este controlador é responsável por fazer o controle de endpoints que fazem uma operação básica de matemática.
 * 
 * @author Lucas Borguezam
 * @since 09 de Março de 2024
 */

//Anotation para manipular endpoints
@RestController
public class MathControler {
	/**
	 * counter: id mocado (Ficticio)
	 */
	private final AtomicLong counter = new AtomicLong();
	
	Math math = new Math();
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

	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double subtraction(@PathVariable(value="numberOne") String numOne, @PathVariable(value="numberTwo") String numTwo) throws Exception{
		/**
		 * Esse método retorna a subtração entre numOne e numTwo
		 * 
		 * @param numOne -> primeiro número para a subtração
		 * @param numTwo -> segundo número para a subtração.
		 * 
		 * @return Retorna o valor da subtração entre o parametro numberOne e numberTwo.
		 * */
		
		if(!NumberConverter.isNumeric(numOne) || !NumberConverter.isNumeric(numTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return math.subtraction(NumberConverter.convertToDouble(numOne), NumberConverter.convertToDouble(numTwo));
		
	}//sub()
	
	
	@RequestMapping(value = "/multiplication/{n1}/{n2}")
	public Double multiplication(
			@PathVariable(value="n1") String n1, 
			@PathVariable(value="n2") String n2 ) 
	{
		/**
		 * Esse método retorna a multiplicação entre n1 e n2
		 * 
		 * @param n1 -> primeiro número para a multiplicação
		 * @param n2-> segundo número para a multiplicação.
		 * 
		 * @return Retorna o valor da multiplicação entre o parametro n1 e n2.
		 * */
		
		if(!NumberConverter.isNumeric(n1) || !NumberConverter.isNumeric(n2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return math.multiplication(NumberConverter.convertToDouble(n1), NumberConverter.convertToDouble(n2));
	}
	
	@RequestMapping(value = "/division/{n1}/{n2}")
	public Double division(
			@PathVariable(value="n1") String n1,
			@PathVariable(value="n2") String n2 )
	{
		/**
		 * Esse método retorna a divisão entre n1 e n2
		 * 
		 * @param n1 -> primeiro número para a divisão
		 * @param n2-> segundo número para a divisão.
		 * 
		 * @return Retorna o valor da divisão entre o parametro n1 e n2.
		 * */
		
		if(!NumberConverter.isNumeric(n1) || !NumberConverter.isNumeric(n2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		if(n2.equals("0")) {
			throw new UnsupportedMathOperationException("Number zero can't is divided");
		} 
		return math.division(NumberConverter.convertToDouble(n1), NumberConverter.convertToDouble(n2));
	}
	
	@RequestMapping(value="/mean/{n1}/{n2}")
	public Double mean(
			@PathVariable(value="n1") String n1,
			@PathVariable(value="n2") String n2 ) 
	{
		/**
		 * Esse método retorna a média entre n1 e n2
		 * 
		 * @param n1 -> primeiro número para a fase somatória da média
		 * @param n2 -> segundo número para a fase somatória da média
		 * 
		 * @return Retorna a média da soma dos parâmetros n1 n2
		 */
		if(!NumberConverter.isNumeric(n1) || !NumberConverter.isNumeric(n2)){
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		
		return math.mean(NumberConverter.convertToDouble(n1), NumberConverter.convertToDouble(n2));

	}
	
	@RequestMapping(value="/potentiation/{n1}/{n2}")
	public Double potentiation(
			@PathVariable(value="n1") String n1,
			@PathVariable(value="n2") String n2 ) 
	{
		/**
		 * Esse método retorna a potencia de n1 elevado a n2
		 * 
		 * @param n1 -> é a base da operação de potencialização. 
		 * @param n2 -> é o expoente da operação (número de vezes em que a base é multiplicada por ela mesma)
		 * 
		 * @return Retorna a média da soma dos parâmetros n1 n2
		 */
		
		if(!NumberConverter.isNumeric(n1) || !NumberConverter.isNumeric(n2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		if(!NumberConverter.isIntegerNumeric(n2)) {
			throw new UnableToSendNotificationException("Please set an integer numeric in second path variable!");
		}
		
		return math.potentiation(NumberConverter.convertToDouble(n1), NumberConverter.convertToInteger(n2));
	}
	
	@RequestMapping(value="/squereRoot/{n1}")
	public Double squereRoot(
			@PathVariable(value="n1") String n1
			) 
	{
		/**
		 * Esse método retorna a potencia de n1 elevado a n2
		 * 
		 * @param n1 -> Número para a radiciação
		 * 
		 * @return Retorna a raiz de n1
		 */
		
		if(!NumberConverter.isNumeric(n1)){
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		
		return math.squereRoot(NumberConverter.convertToDouble(n1));
		
	}
	
}//MathControler{}
