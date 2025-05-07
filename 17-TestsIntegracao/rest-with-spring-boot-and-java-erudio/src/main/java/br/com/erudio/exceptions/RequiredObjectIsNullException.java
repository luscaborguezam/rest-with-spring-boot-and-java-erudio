package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * @ResponseStatus define o código do status http que ele vai retornar
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public RequiredObjectIsNullException() {
		//Retorna uma menssagem de erro
		super("Its not allowed to persist a null object!");
	}

	public RequiredObjectIsNullException(String ex) {
		//Retorna uma menssagem de erro
		super(ex);
	}
	
}
