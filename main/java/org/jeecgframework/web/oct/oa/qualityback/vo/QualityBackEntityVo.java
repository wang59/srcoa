package org.jeecgframework.web.oct.oa.qualityback.vo;

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
import org.jeecgframework.web.oct.oa.qualityback.entity.OctEngineerManage;
import org.jeecgframework.web.oct.oa.qualityback.entity.OctProjectManage;

/***
 * 质量反馈单实体类
 * @author fany
 *
 */
public class QualityBackEntityVo extends IdEntity implements java.io.Serializable {
	private String orgcode;//反馈部门
	@Excel(name = "流水号")
	private String serialnumber;
	@Excel(name = "提单时间")
	private Date createdate;
	@Excel(name = "提单人")
	private String createname;
	@Excel(name = "项目归属")
	private String project;
	@Excel(name = "项目名称")
	private String item;
	@Excel(name = "问题描述")
	private String content;//问题描述
	@Excel(name = "处理部门")
	private String departmentname;//涉及部门
	@Excel(name = "处理部门意见")
	private String departmentid;//涉及部门id
	@Excel(name = "实施部门")
	private String dodepart;//涉及部门
	@Excel(name = "实施部门意见")
	private String dodepartid;//涉及部门id
	@Excel(name = "同类项目是否需处理")
	private String yesOrNo;//同类项目是否需处理
	@Excel(name = "要求完成时间")
	private Date deadline;//要求完成时间
	@Excel(name = "状态")
	private String status;//状态
	private OctEngineerManage octEngineerManage; 
	private OctProjectManage octProjectManage;
	public String getOrgcode() {
		return orgcode;
	}
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
	public String getYesOrNo() {
		return yesOrNo;
	}
	public void setYesOrNo(String yesOrNo) {
		this.yesOrNo = yesOrNo;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OctEngineerManage getOctEngineerManage() {
		return octEngineerManage;
	}
	public void setOctEngineerManage(OctEngineerManage octEngineerManage) {
		this.octEngineerManage = octEngineerManage;
	}
	public OctProjectManage getOctProjectManage() {
		return octProjectManage;
	}
	public void setOctProjectManage(OctProjectManage octProjectManage) {
		this.octProjectManage = octProjectManage;
	}
	public String getDodepart() {
		return dodepart;
	}
	public void setDodepart(String dodepart) {
		this.dodepart = dodepart;
	}
	public String getDodepartid() {
		return dodepartid;
	}
	public void setDodepartid(String dodepartid) {
		this.dodepartid = dodepartid;
	}
	
}
