package org.jeecgframework.web.oct.oa.interfacemanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/***
 * 描述返回的字段类
 * @author lizh
 *
 */
@Entity
@Table(name = "t_s_online_doc_interface", schema = "")
public class OnlineDocInterfaceEntity implements Serializable{
	private static final long serialVersionUID = 1L; 
	//主键
	private String id;
	//属性名
	private String propertyName;
	//字段类型
	private String fieldType;
	//字段说明
	private String fieldDescription;
	//与主表关联的编码
	private String code;
	
	
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name ="property_name",nullable=true,length=50)
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Column(name ="field_type",nullable=true,length=50)
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	@Column(name ="field_description",nullable=true,length=50)
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
	@Column(name ="code",nullable=true,length=36)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
