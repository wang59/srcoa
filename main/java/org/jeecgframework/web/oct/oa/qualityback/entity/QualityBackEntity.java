package org.jeecgframework.web.oct.oa.qualityback.entity;

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

/***
 * 质量反馈单实体类
 * @author fany
 *
 */
@Entity
@Table(name = "qualityback")
public class QualityBackEntity extends IdEntity implements java.io.Serializable {
	private String orgcode;//反馈部门
	private String serialnumber;
	private Date createdate;
	private String createname;
	private OctEngineerManage octEngineerManage; 
	private OctProjectManage octProjectManage;
	private String content;//问题描述
	private String departmentname;//涉及部门
	private String yesOrNo;//同类项目是否需处理
	private String departmentid;//涉及部门id
	private Date deadline;//要求完成时间
	private String dodepart;
	private String dodepartid;
	@Column(name ="sys_org_code")
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	@Column(name ="create_date")
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column(name ="serialnumber")
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	@Column(name ="departmentname")
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_ty_val")
	@JsonIgnore
	public OctEngineerManage getOctEngineerManage() {
		return octEngineerManage;
	}
	public void setOctEngineerManage(OctEngineerManage octEngineerManage) {
		this.octEngineerManage = octEngineerManage;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_it_val")
	@JsonIgnore
	public OctProjectManage getOctProjectManage() {
		return octProjectManage;
	}
	public void setOctProjectManage(OctProjectManage octProjectManage) {
		this.octProjectManage = octProjectManage;
	}
	@Column(name ="yes_no")
	public String getYesOrNo() {
		return yesOrNo;
	}
	public void setYesOrNo(String yesOrNo) {
		this.yesOrNo = yesOrNo;
	}
	@Column(name ="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name ="departmentid")
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	
	@Column(name ="create_name")
	public String getCreatename() {
		return createname;
	}
	public Date getDeadline() {
		return deadline;
	}
	@Column(name ="deadline")
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	@Column(name ="do_depart")
	public String getDodepart() {
		return dodepart;
	}
	public void setDodepart(String dodepart) {
		this.dodepart = dodepart;
	}
	@Column(name ="do_departid")
	public String getDodepartid() {
		return dodepartid;
	}
	public void setDodepartid(String dodepartid) {
		this.dodepartid = dodepartid;
	}
	
	
	
}
