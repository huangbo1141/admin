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
@Table(name="tbl_dan")
public class Dan {

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

  private Integer line;
private String name;
private Integer sort;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public Integer getLine() {
return line;
}
public void setLine(Integer line) {
this.line = line;
}
public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public Integer getSort() {
return sort;
}
public void setSort(Integer sort) {
this.sort = sort;
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
