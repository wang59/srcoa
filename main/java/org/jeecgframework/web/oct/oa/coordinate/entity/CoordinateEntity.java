package org.jeecgframework.web.oct.oa.coordinate.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.oct.activiti.entity.TSProcess;

/***
 * 工程项目关系表
 * @author fany
 *
 */
@Entity
@Table(name = "oct_coordinate")
public class CoordinateEntity extends IdEntity implements java.io.Serializable {	
	private String depart;//部门
	private String projectname;//项目名称
	private String taskname;//任务名称
	private String dostatus;//处理状态
	private String explain;
	private String receivedep;//任务接收部门
	private String coordinate_id;//负责人
	private Date neesdate;
	@Column(name ="sys_org_code")
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	@Column(name ="project_name")
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	@Column(name ="task_bane")
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	@Column(name ="dostatus")
	public String getDostatus() {
		return dostatus;
	}
	public void setDostatus(String dostatus) {
		this.dostatus = dostatus;
	}
	@Column(name ="explain_task")
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	@Column(name ="receive_dep")
	public String getReceivedep() {
		return receivedep;
	}
	public void setReceivedep(String receivedep) {
		this.receivedep = receivedep;
	}
	@Column(name ="coordinate_id")
	public String getCoordinate_id() {
		return coordinate_id;
	}
	public void setCoordinate_id(String coordinate_id) {
		this.coordinate_id = coordinate_id;
	}
	@Column(name ="need_time")
	public Date getNeesdate() {
		return neesdate;
	}
	public void setNeesdate(Date neesdate) {
		this.neesdate = neesdate;
	}
	
}
