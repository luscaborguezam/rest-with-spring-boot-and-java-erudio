package br.com.erudio.Controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;

import br.com.erudio.Entitis.Greeting;

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
		 * @return Retorna uma nova instância de Greeting, onde o ID é gerado automaticamente 
		 * e o conteúdo é preenchido usando o padrão de saudação TEMPLATE com o nome fornecido na solicitação
		 * */
		
		//Verificar se a string é numérica.
		 if(!isNumeric(numberOne) || !isNumeric(numberTwo)) {
			 throw new Exception();
		 }		
		return convertToDouble(numberOne) + convertToDouble(numberTwo);
	}

	private Double convertToDouble(String strNumber) {
		// verificar se o valor é nulo
		if(strNumber == null) return 0D;
		//Subistituíndo , por . para evitar erros
		String number = strNumber.replaceAll(",",".");
		// verificar se o valor é numérico para converter e retornar
		if(isNumeric(number)) return Double.parseDouble(number);
		return 0D;
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
