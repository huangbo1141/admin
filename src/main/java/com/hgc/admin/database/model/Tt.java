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
@Table(name="tbl_tt")
public class Tt {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

  private int time_value;
private int time_type;
private int ta;
private int oee;

	
	public void setTime_value(int time_value) {
this.time_value = time_value;
}
public void setTime_type(int time_type) {
this.time_type = time_type;
}
public void setTa(int ta) {
this.ta = ta;
}
public void setOee(int oee) {
this.oee = oee;
}

}
