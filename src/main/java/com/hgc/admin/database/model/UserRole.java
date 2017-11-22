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
@Table(name="tbl_user_role")
public class UserRole {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

  private String name;
private String tr_name;
private int active;

	
	public void setName(String name) {
this.name = name;
}
public void setTr_name(String tr_name) {
this.tr_name = tr_name;
}
public void setActive(int active) {
this.active = active;
}

}
