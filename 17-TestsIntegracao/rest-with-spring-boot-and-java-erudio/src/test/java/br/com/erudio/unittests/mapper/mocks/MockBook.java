package br.com.erudio.unittests.mapper.mocks;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.erudio.dto.BookDTO;
import br.com.erudio.model.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
        	books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockVOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
        	books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
    	Book book = new Book();
    	book.setId(number.longValue());
        book.setAuthor("Author Test " + number);
        book.setLaunchDate(new Date(Date.valueOf("2000-01-01").getTime()));
        book.setPrice(number.doubleValue());
        book.setTitle("Title Test " + number);
        return book;
    }

    public BookDTO mockVO(Integer number) {
    	BookDTO book = new BookDTO();
    	book.setKey(number.longValue());
        book.setAuthor("Author Test " + number);
        book.setLaunchDate(new Date(Date.valueOf("2000-01-01").getTime()));
        book.setPrice(number.doubleValue());
        book.setTitle("Title Test " + number);
        return book;
    }

}
