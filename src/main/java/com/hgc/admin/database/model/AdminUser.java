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
@Table(name="tbl_admin_user")
public class AdminUser {

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

  private String name;
private String phone;
private String log_time;
private Integer role;
private String username;
private String password;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getPhone() {
return phone;
}
public void setPhone(String phone) {
this.phone = phone;
}
public String getLog_time() {
return log_time;
}
public void setLog_time(String log_time) {
this.log_time = log_time;
}
public Integer getRole() {
return role;
}
public void setRole(Integer role) {
this.role = role;
}
public String getUsername() {
return username;
}
public void setUsername(String username) {
this.username = username;
}
public String getPassword() {
return password;
}
public void setPassword(String password) {
this.password = password;
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
