package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * @ResponseStatus define o código do status http que ele vai retornar
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException{
	
	public UnsupportedMathOperationException(String ex) {
		//Retorna uma menssagem de erro
		super(ex);
	}
	
	private static final long serialVersionUID = 1L;

}
