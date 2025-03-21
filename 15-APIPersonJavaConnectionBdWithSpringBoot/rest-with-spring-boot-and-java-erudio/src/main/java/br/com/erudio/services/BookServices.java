package br.com.erudio.services;


import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.erudio.controllers.BookController;
import br.com.erudio.dto.BookDTO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;


@Service
public class BookServices {

	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;

	/**
	 * METODO BUSCA TODOS OS LIVROS CADASTRADOS
	 * @return
	 */
	public List<BookDTO> findAll(){
		logger.info("Find all Books!");
		var books = DozerMapper.parseListObjects(repository.findAll(), BookDTO.class);
		books.forEach(this::addHateoasLinks);
		return books;
		
	}
	
	/**
	 * METODO BUSCA DADOS DE UM LIVRO PELO ID
	 * @param id
	 * @return
	 */
	public BookDTO findById(Long id) {
		logger.info("find Book by id!");
		var book = DozerMapper.parseObject(repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this id")), 
				BookDTO.class);
		addHateoasLinks(book);
		return book;
	}

	/**
	 * METODO CRIA UM REGISTRO DE UM LIVRO
	 * @param book
	 * @return
	 */
	public BookDTO create(BookDTO book) {
		logger.info("Create Book");
		
		if(book == null) throw new RequiredObjectIsNullException();
		
		var entity = DozerMapper.parseObject(book, Book.class);
		var bookDto = DozerMapper.parseObject(repository.save(entity), BookDTO.class);
		addHateoasLinks(bookDto);
		return bookDto;
	}
	
	/**
	 * METODO ALTERA O REGISTRO DE UM LIVRO
	 * @param book
	 * @return
	 */
	public BookDTO update(BookDTO book) {
		logger.info("Update Book registred!");
		
		if(book == null) throw new RequiredObjectIsNullException();
		
		var entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var bookDto = DozerMapper.parseObject(repository.save(entity), BookDTO.class);
		addHateoasLinks(bookDto);
		return bookDto;
		
	}
	
	/**
	 * METODO DELETA UM LIVRO REGISTRADO
	 * @param id
	 */
	public void delete(Long id) {
		logger.info("Deleting one Book!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records foundthis id"));
		repository.delete(entity);
	}
	
	
	public void addHateoasLinks(BookDTO dto) {
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel().withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
		dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
		dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
		dto.add(linkTo(methodOn(BookController.class).delete(dto.getKey())).withRel("delete").withType("DELETE"));
	}
	
	
}
