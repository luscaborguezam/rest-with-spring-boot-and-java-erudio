package br.com.erudio.Entitis;

/**
 * Classe criada para codificar o objeto de saudação que recebe o responseBody do end point greeting
 * 
 * @author Lucas Borguezam
 * @since 06 de Fevereiro de 2024
 * */
public class Greeting {
	
	private final long ID;
	private final String CONTENT;
	
	public Greeting(long id, String content) {
		this.ID = id;
		this.CONTENT = content;
	}

	public long getID() {
		return ID;
	}

	public String getCONTENT() {
		return CONTENT;
	}

}//Greeting