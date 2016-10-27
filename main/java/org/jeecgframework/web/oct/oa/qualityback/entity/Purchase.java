package org.jeecgframework.web.oct.oa.qualityback.entity;

import java.sql.Timestamp;

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
 * 物料导入类
 * @author fany
 *
 */
@Entity
@Table(name = "oct_purchase")
public class Purchase extends IdEntity implements java.io.Serializable {
	@Excel(name = "材料名称")
	private String meterial;
	@Excel(name = "规格型号")
	private String model;
	@Excel(name = "预购数量")
	private String advance;
	@Excel(name = "库存数量")
	private String stock;
	@Excel(name = "实购数量")
	private String realoptions;
	@Excel(name = "单位")
	private String unit;
	@Excel(name = "单价")
	private String price;
	@Excel(name = "总价金额")
	private String count;
	@Excel(name = "备注")
	private String remark;
	public String getMeterial() {
		return meterial;
	}
	public void setMeterial(String meterial) {
		this.meterial = meterial;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getRealoptions() {
		return realoptions;
	}
	public void setRealoptions(String realoptions) {
		this.realoptions = realoptions;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
