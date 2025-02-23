package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.erudio.model.Person;

/**
 * Interface disponibiliza o CRUD para Person pelo JpaRepository.
 */
public interface PersonRepository extends JpaRepository<Person, Long>{
	

}
