package org.jeecgframework.web.oct.oa.coordinate.vo;

import java.sql.Timestamp;
import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

public class CoordinateVo  {
	@Excel(name = "流水号")
	private String serialnumber;//流水号
	@Excel(name = "提单时间")
	private Timestamp businessCreateTime;
	@Excel(name = "提单人")
	private String businessCreateName;
	@Excel(name = "项目名称")
	private String projectname;//项目名称
	@Excel(name = "任务描述")
	private String explain;//任务详细说明
	@Excel(name = "涉及部门")
	private String depart;
	@Excel(name = "负责人")
	private String doman;
	@Excel(name = "要求完成日期")
	private Date neesdate;
	@Excel(name = "状态")
	private String status;
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public Timestamp getBusinessCreateTime() {
		return businessCreateTime;
	}
	public void setBusinessCreateTime(Timestamp businessCreateTime) {
		this.businessCreateTime = businessCreateTime;
	}
	public String getBusinessCreateName() {
		return businessCreateName;
	}
	public void setBusinessCreateName(String businessCreateName) {
		this.businessCreateName = businessCreateName;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getDoman() {
		return doman;
	}
	public void setDoman(String doman) {
		this.doman = doman;
	}
	public Date getNeesdate() {
		return neesdate;
	}
	public void setNeesdate(Date neesdate) {
		this.neesdate = neesdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
