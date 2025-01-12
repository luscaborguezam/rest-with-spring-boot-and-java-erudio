package br.com.erudio.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.model.Person;

/**
 * CLASSE CRIADA PARA CONVERTER VOV2 DE PERSON EM ENTITY OU O CONTRÁRIO.
 * PORQUE O VOV2 É DIFERENTE DA CLASSE ENTITY
 */
@Service
public class PersonMapper {

	/**
	 * METODO TRASFORMA PARA OS DADOS DO OBJETO ENTITY Person PARA O PersonVOV2 
	 * @param person
	 * @return
	 */
	public PersonVOV2 convertEntityToVo(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setAddress(person.getAddress());
		vo.setGender(person.getGender());
		vo.setBirthDay(new Date());
		return vo;

	}
	
	
	/**
	 * METODO TRASFORMA PARA OS DADOS DO OBJETO PersonVOV2 PARA O ENTITY Person 
	 * @param person
	 * @return
	 */
	public Person convertVoToEntity(PersonVOV2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		//vo.setBirthDay(new Date());
		return entity;

	}
	
	
	
}
