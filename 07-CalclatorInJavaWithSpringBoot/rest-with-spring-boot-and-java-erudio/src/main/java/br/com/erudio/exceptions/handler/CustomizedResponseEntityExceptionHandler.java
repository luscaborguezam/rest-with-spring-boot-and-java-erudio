package br.com.erudio.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.exceptions.ExceptionsResponse;

/**
 * Essa classe é um manipulador de exceções.
 * Trata exceções lançadas durante a execução de controladores REST e fornece respostas personalizadas
 */



/*
 * @ControllerAdvice Utilizada para concentrar um tratamento espalhada para todos os controlers.
 * Se uma exceção acontecer em qualquer controller e não há alguem fornecendo um tratamento especifico, 
 * a excessão vai cair no tratamento global (AdviceController).
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler{

	/*
	 * Iniciamos com o erro mais genérica, no java é o Exception class,
	 * no rest é o status code 500 (Internal Server Error).
	 */
	
	//O Erro 500 causa no java é o exception.class
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionsResponse> handleAllEception(Exception ex, WebRequest request){
		/**
		 * Este método trata todas as exceções não tratadas e 
		 * retorna uma resposta HTTP 500 (Internal Server Error)
		 */
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*
	 * Excesções mais específicas.
	 */
	
	//Erro 400 no rest é o UnsupportedOperationException no java
	@ExceptionHandler(UnsupportedOperationException.class)
	public final ResponseEntity<ExceptionsResponse> handleBadRequestExceptions(Exception ex, WebRequest request){
		/**
		 *  Este método trata exceções do tipo UnsupportedOperationException 
		 *  e retorna uma resposta HTTP 400 (Bad Request).
		 */
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
}//CustomizedResponseEntityExceptionHandler{}
