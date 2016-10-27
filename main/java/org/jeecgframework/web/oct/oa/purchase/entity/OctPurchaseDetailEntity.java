package org.jeecgframework.web.oct.oa.purchase.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;


/**
 * 
 * 
 */
@Entity
@Table(name="oct_purchase_details")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
@ExcelTarget("octPurchaseDetailEntity")
public class OctPurchaseDetailEntity extends IdEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;

	private String count;

	private String figure;

	private String foreignkey;
	
	@Excel(name = "材料名称",orderNum="1")
	private String material;

	@Excel(name = "规格型号",orderNum="2")
	private String model;
	
	@Excel(name = "预购数量",orderNum="3")
	private String advance;
	
	@Excel(name = "库存数量",orderNum="4")
	private String stock;
	
	@Excel(name = "实购数量",orderNum="5")
	private String realoptions;
	
	@Excel(name = "单位",orderNum="6")
	private String unit;
	
	@Excel(name = "单价",orderNum="7")
	private String price;
	
	@Excel(name = "备注",orderNum="9")
	private String remark;

	private String pattern;

	public OctPurchaseDetailEntity() {
	}


	public String getAdvance() {
		return this.advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCount() {
		return this.count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFigure() {
		return this.figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

	public String getForeignkey() {
		return this.foreignkey;
	}

	public void setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRealoptions() {
		return this.realoptions;
	}

	public void setRealoptions(String realoptions) {
		this.realoptions = realoptions;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStock() {
		return this.stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}