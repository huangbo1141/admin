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
@Table(name="tbl_order_relation")
public class OrderRelation {

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

  private Integer sender_id;
private Integer receiver_id;
private Integer order_id;
private String s_time;
private String r_time;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public Integer getSender_id() {
return sender_id;
}
public void setSender_id(Integer sender_id) {
this.sender_id = sender_id;
}
public Integer getReceiver_id() {
return receiver_id;
}
public void setReceiver_id(Integer receiver_id) {
this.receiver_id = receiver_id;
}
public Integer getOrder_id() {
return order_id;
}
public void setOrder_id(Integer order_id) {
this.order_id = order_id;
}
public String getS_time() {
return s_time;
}
public void setS_time(String s_time) {
this.s_time = s_time;
}
public String getR_time() {
return r_time;
}
public void setR_time(String r_time) {
this.r_time = r_time;
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
