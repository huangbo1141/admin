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
@Table(name="tbl_order")
public class Order {

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

  private Integer station_id;
private Integer reason_id;
private String start_t;
private String end_t;
private String p_desc;
private String issue_cause;
private String r_desc;
private String s_desc;
private Integer user_id;
private String complete;
private Integer status;
private Integer error_id;
private String feedback;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public Integer getStation_id() {
return station_id;
}
public void setStation_id(Integer station_id) {
this.station_id = station_id;
}
public Integer getReason_id() {
return reason_id;
}
public void setReason_id(Integer reason_id) {
this.reason_id = reason_id;
}
public String getStart_t() {
return start_t;
}
public void setStart_t(String start_t) {
this.start_t = start_t;
}
public String getEnd_t() {
return end_t;
}
public void setEnd_t(String end_t) {
this.end_t = end_t;
}
public String getP_desc() {
return p_desc;
}
public void setP_desc(String p_desc) {
this.p_desc = p_desc;
}
public String getIssue_cause() {
return issue_cause;
}
public void setIssue_cause(String issue_cause) {
this.issue_cause = issue_cause;
}
public String getR_desc() {
return r_desc;
}
public void setR_desc(String r_desc) {
this.r_desc = r_desc;
}
public String getS_desc() {
return s_desc;
}
public void setS_desc(String s_desc) {
this.s_desc = s_desc;
}
public Integer getUser_id() {
return user_id;
}
public void setUser_id(Integer user_id) {
this.user_id = user_id;
}
public String getComplete() {
return complete;
}
public void setComplete(String complete) {
this.complete = complete;
}
public Integer getStatus() {
return status;
}
public void setStatus(Integer status) {
this.status = status;
}
public Integer getError_id() {
return error_id;
}
public void setError_id(Integer error_id) {
this.error_id = error_id;
}
public String getFeedback() {
return feedback;
}
public void setFeedback(String feedback) {
this.feedback = feedback;
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
