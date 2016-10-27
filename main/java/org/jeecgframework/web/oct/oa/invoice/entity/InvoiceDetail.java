package org.jeecgframework.web.oct.oa.invoice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "oct_invoice_detail")
public class InvoiceDetail extends IdEntity implements Serializable{


	@Excel(name = "材料/设备名称")
	private String stuffName;

	@Excel(name = "规格型号")
	private String stuffSpecs;

	@Excel(name = "单位")
	private String stuffUnit;

	@Excel(name = "发货数量")
	private String stuffNums;

	@Excel(name = "所属项目名称")
	private String projectCode;

	@Excel(name = "预计到货日期")
	private String expectedReach;

	@Excel(name = "备注")
	private String remark;


	public String getStuffName(){
	  return stuffName;
	} 

	public void setStuffName(String stuffName){
	  this.stuffName = stuffName;
	}

	public String getStuffSpecs(){
	  return stuffSpecs;
	} 

	public void setStuffSpecs(String stuffSpecs){
	  this.stuffSpecs = stuffSpecs;
	}

	public String getStuffUnit(){
	  return stuffUnit;
	} 

	public void setStuffUnit(String stuffUnit){
	  this.stuffUnit = stuffUnit;
	}

	public String getStuffNums(){
	  return stuffNums;
	} 

	public void setStuffNums(String stuffNums){
	  this.stuffNums = stuffNums;
	}

	public String getProjectCode(){
	  return projectCode;
	} 

	public void setProjectCode(String projectCode){
	  this.projectCode = projectCode;
	}

	public String getExpectedReach(){
	  return expectedReach;
	} 

	public void setExpectedReach(String expectedReach){
	  this.expectedReach = expectedReach;
	}

	public String getRemark(){
	  return remark;
	} 

	public void setRemark(String remark){
	  this.remark = remark;
	}



}
