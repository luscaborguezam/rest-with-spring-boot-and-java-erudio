package br.com.erudio.Controller;

import java.util.concurrent.atomic.AtomicLong;

import javax.swing.DefaultRowSorter;

import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.exceptions.UnsupportedMathOperationException;

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
		 if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			 throw new UnsupportedMathOperationException("Please set a numeric value!");
		 }
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}//sum()

	@RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method=RequestMethod.GET)
	public Double sub(@PathVariable(value="numberOne") String numOne, @PathVariable(value="numberTwo") String numTwo) throws Exception{
		/**
		 * Esse método retorna a subtração entre numOne e numTwo
		 * 
		 * @param numOne -> primeiro número para a subtração
		 * @param numTwo -> segundo número para a subtração.
		 * 
		 * @return Retorna o valor da subtração entre o parametro numberOne e numberTwo.
		 * */
		
		if(!isNumeric(numOne) || !isNumeric(numTwo)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return convertToDouble(numOne) - convertToDouble(numTwo);
		
	}//sub()
	
	
	@RequestMapping(value = "/mult/{n1}/{n2}")
	public Double mult(
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
		
		if(!isNumeric(n1) || !isNumeric(n2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		return convertToDouble(n1) * convertToDouble(n2);
		
	}
	
	@RequestMapping(value = "/div/{n1}/{n2}")
	public Double div(
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
		
		if(!isNumeric(n1) || !isNumeric(n2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		if(n2.equals("0")) {
			throw new UnsupportedMathOperationException("Number zero can't is divided");
		} 
		
		return convertToDouble(n1) / convertToDouble(n2);
	}
	
	@RequestMapping(value="/med/{n1}/{n2}")
	public Double media(
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
		if(!isNumeric(n1) || !isNumeric(n2)){
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		
		return (convertToDouble(n1)+convertToDouble(n2))/2;
	}
	
	@RequestMapping(value="/raiz/{n1}/{n2}")
	public Double raizQuadrada(
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
		
		if(!isNumeric(n1) || !isNumeric(n2)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		if(convertToDouble(n1) != 0D) {
			throw new UnableToSendNotificationException("Please set an integer numeric in second path variable!");
		}
		
		Double total = 1D;
		System.out.println(total);
		for(int i = 0; i<convertToInteger(n2) ; i++) {
			total *= convertToDouble(n1);
			System.out.println(total);
		}
		
		return total;
		
	}
	
	
	
	// Métodos Suplementares __________________________________________________________________________
	
	private Double convertToDouble(String strNumber) {
		// verificar se o valor é nulo
		if(strNumber == null) return 0D;
		//Subistituíndo , por . para evitar erros
		String number = strNumber.replaceAll(",",".");
		// verificar se o valor é numérico para converter e retornar
		if(isNumeric(number)) return Double.parseDouble(number);
		return 0D;
	}
	
	private Integer convertToInteger(String strNumber) {
		// verificar se o valor é nulo
		if(strNumber == null) return 0;
		//Subistituíndo , por . para evitar erros
		String number = strNumber.replaceAll(",",".");
		// verificar se o valor é numérico para converter e retornar
		if(isNumeric(number)) return Integer.parseInt(number);
		return 0;
	}

	private boolean isNumeric(String strNumber) {
		if(strNumber == null) return false;
		String number = strNumber.replaceAll(",",".");
		//Verificar por meio do rejex
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
		/* Explicando a expressão do Rejex:
		 * "[-+]" string vai iniciar com sinal positivo/negativo
		 * "?" indica que a string pode iniciar ou com "-", ou com "+" ou sem nenhum.
		 * "[0-9]" corresponde a qualquer dígito entre 0 e 9.
		 * "*" caractere ou conjunto de caracteres anterior pode aparecer zero ou mais vezes
		 * "\\." caracter que corresponde a qualquer caracter. (O primeiro "\" é usada como caracter de escape para a segunda barra)
		 * "?" significa que o ponto é opcional
		 * "[0-9]" corresponde a qualquer dígito entre 0 e 9.
		 * "+"  indica que o caractere ou conjunto de caracteres anterior deve aparecer pelo menos uma vez. Isso significa que após o ponto opcional (se houver), deve haver pelo menos um dígito.
		 */
	}
	
}
