package com.hgc.admin.database.dao;

import java.util.List;

import com.hgc.admin.database.model.Person;

public interface PersonDAO {

	public Integer addPerson(Person p);
	public void updatePerson(Person p);
	public List<Person> listPersons();
	public Person getPersonById(int id);
	public void removePerson(int id);
}
