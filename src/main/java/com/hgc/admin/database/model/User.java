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
	private Integer id;
	public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

  private String name;
private String serial;
private String password;
private Integer type;
private Integer dan;
private Integer part;
private Integer deleted;
private String create_datetime;
private String modify_datetime;
private String phone;
private String head;
private String token;


	public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getSerial() {
return serial;
}
public void setSerial(String serial) {
this.serial = serial;
}
public String getPassword() {
return password;
}
public void setPassword(String password) {
this.password = password;
}
public Integer getType() {
return type;
}
public void setType(Integer type) {
this.type = type;
}
public Integer getDan() {
return dan;
}
public void setDan(Integer dan) {
this.dan = dan;
}
public Integer getPart() {
return part;
}
public void setPart(Integer part) {
this.part = part;
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
public String getPhone() {
return phone;
}
public void setPhone(String phone) {
this.phone = phone;
}
public String getHead() {
return head;
}
public void setHead(String head) {
this.head = head;
}
public String getToken() {
return token;
}
public void setToken(String token) {
this.token = token;
}

}
