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
@Table(name="tbl_ct")
public class Ct {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

  private String name;
private double v1;
private double v2;

	
	public void setName(String name) {
this.name = name;
}
public void setV1(double v1) {
this.v1 = v1;
}
public void setV2(double v2) {
this.v2 = v2;
}

}
