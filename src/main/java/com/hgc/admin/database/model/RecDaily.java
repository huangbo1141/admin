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
@Table(name="tbl_rec_daily")
public class RecDaily {

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

  private String time;
private Integer acnt;
private Integer pcnt;
private Integer qcnt;
private Integer ncnt;
private double ta;
private double oee;
private double output;
private String content;
private Integer deleted;
private String create_datetime;
private String modify_datetime;


	public String getTime() {
return time;
}
public void setTime(String time) {
this.time = time;
}
public Integer getAcnt() {
return acnt;
}
public void setAcnt(Integer acnt) {
this.acnt = acnt;
}
public Integer getPcnt() {
return pcnt;
}
public void setPcnt(Integer pcnt) {
this.pcnt = pcnt;
}
public Integer getQcnt() {
return qcnt;
}
public void setQcnt(Integer qcnt) {
this.qcnt = qcnt;
}
public Integer getNcnt() {
return ncnt;
}
public void setNcnt(Integer ncnt) {
this.ncnt = ncnt;
}
public double getTa() {
return ta;
}
public void setTa(double ta) {
this.ta = ta;
}
public double getOee() {
return oee;
}
public void setOee(double oee) {
this.oee = oee;
}
public double getOutput() {
return output;
}
public void setOutput(double output) {
this.output = output;
}
public String getContent() {
return content;
}
public void setContent(String content) {
this.content = content;
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
