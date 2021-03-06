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
import org.jeecgframework.web.oct.activiti.entity.TSProcess;

/***
 * 工程项目关系表
 * @author fany
 *
 */
@Entity
@Table(name = "oct_engineer_manage ")
public class OctEngineerManage extends IdEntity implements java.io.Serializable {
     private String createName;
     private String createBy;
     private Date createDate;
     private String updateName;
     private String engineerName;
     @Column(name ="create_name")
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	@Column(name ="create_by")
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Column(name ="create_date")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name ="update_name")
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	@Column(name ="engineer_name",nullable = true)
	public String getEngineerName() {
		return engineerName;
	}
	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}
	
	


}
