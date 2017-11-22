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
@Table(name="tbl_user")
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

  private String name;
private String serial;
private int type;
private int dan;

	
	public void setName(String name) {
this.name = name;
}
public void setSerial(String serial) {
this.serial = serial;
}
public void setType(int type) {
this.type = type;
}
public void setDan(int dan) {
this.dan = dan;
}

}
