package com.hgc.admin.database.service;

import java.util.List;

import com.hgc.admin.database.model.Person;

public interface PersonService {

	public Integer addPerson(Person p);
	public void updatePerson(Person p);
	public List<Person> listPersons();
	public Person getPersonById(int id);
	public void removePerson(int id);
	
}
