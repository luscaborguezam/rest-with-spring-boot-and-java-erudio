package br.com.erudio.exceptions.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * @ControllerAdvice Utilizada para concentrar um tratamento espalhada para todos os controlers
 * Se uma exceção acontecer em qualquer controller e nã oestiver um tratamento especifico, 
 * a excessão vai cair no tratamento global do AdviceController.
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler{

	
}
