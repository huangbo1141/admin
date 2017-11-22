package com.hgc.admin.database.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 * @author hgc
 *
 */
@Entity
@Table(name="tbl_menu")
public class Menu {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

  private String name;
private int sort;
private String action;
private int parent;
private String term;

	
	public void setName(String name) {
this.name = name;
}
public void setSort(int sort) {
this.sort = sort;
}
public void setAction(String action) {
this.action = action;
}
public void setParent(int parent) {
this.parent = parent;
}
public void setTerm(String term) {
this.term = term;
}

}
