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
	private Integer id;
	public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

  private Integer time_value;
private Integer time_type;
private Integer ta;
private Integer oee;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public Integer getTime_value() {
return time_value;
}
public void setTime_value(Integer time_value) {
this.time_value = time_value;
}
public Integer getTime_type() {
return time_type;
}
public void setTime_type(Integer time_type) {
this.time_type = time_type;
}
public Integer getTa() {
return ta;
}
public void setTa(Integer ta) {
this.ta = ta;
}
public Integer getOee() {
return oee;
}
public void setOee(Integer oee) {
this.oee = oee;
}
public Integer getDeleted() {
return deleted;
}
public void setDeleted(Integer deleted) {
this.deleted = deleted;
}
public String getCreate_datetime() {
return create_datetime;
}
public void setCreate_datetime(String create_datetime) {
this.create_datetime = create_datetime;
}
public String getModify_datetime() {
return modify_datetime;
}
public void setModify_datetime(String modify_datetime) {
this.modify_datetime = modify_datetime;
}

}
