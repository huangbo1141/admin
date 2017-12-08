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
@Table(name="tbl_report")
public class Report {

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

  private Integer user_id;
private String first_load_time;
private String last_unload_time;
private double lunch_time;
private double wait_time;
private double output;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public Integer getUser_id() {
return user_id;
}
public void setUser_id(Integer user_id) {
this.user_id = user_id;
}
public String getFirst_load_time() {
return first_load_time;
}
public void setFirst_load_time(String first_load_time) {
this.first_load_time = first_load_time;
}
public String getLast_unload_time() {
return last_unload_time;
}
public void setLast_unload_time(String last_unload_time) {
this.last_unload_time = last_unload_time;
}
public double getLunch_time() {
return lunch_time;
}
public void setLunch_time(double lunch_time) {
this.lunch_time = lunch_time;
}
public double getWait_time() {
return wait_time;
}
public void setWait_time(double wait_time) {
this.wait_time = wait_time;
}
public double getOutput() {
return output;
}
public void setOutput(double output) {
this.output = output;
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
