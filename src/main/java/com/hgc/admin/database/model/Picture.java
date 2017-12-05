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
@Table(name="tbl_picture")
public class Picture {

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

  private String filename;
private Integer type;
private Integer ref_id1;
private String ref_type1;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public String getFilename() {
return filename;
}
public void setFilename(String filename) {
this.filename = filename;
}
public Integer getType() {
return type;
}
public void setType(Integer type) {
this.type = type;
}
public Integer getRef_id1() {
return ref_id1;
}
public void setRef_id1(Integer ref_id1) {
this.ref_id1 = ref_id1;
}
public String getRef_type1() {
return ref_type1;
}
public void setRef_type1(String ref_type1) {
this.ref_type1 = ref_type1;
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
