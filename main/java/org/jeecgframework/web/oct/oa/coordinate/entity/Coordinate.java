package org.jeecgframework.web.oct.oa.coordinate.entity;

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
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBackEntity;

/***
 * 工程项目关系表
 * @author fany
 *
 */
@Entity
@Table(name = "oct_act_task_business")
public class Coordinate extends IdEntity implements java.io.Serializable {
	/** 申请单创建人账号 */
	private String businessCreateBy;
	/** 申请创建人姓名 */
	private String businessCreateName;
	/** 创建人提交申请时间  */
	private Timestamp businessCreateTime;
	/** 流程实例ID */
	private String processInstanceId;
	private String status;//0：草稿；1已启动;2；拒绝;3:已结束;4:撤销
	private String currentTodo;//当前审批人
	private String serialnumber;//流水号
	private TSProcess tSProcess;
	private CoordinateEntity coordinateEntity;
	@Column(name ="business_create_by")
	public String getBusinessCreateBy() {
		return businessCreateBy;
	}
	public void setBusinessCreateBy(String businessCreateBy) {
		this.businessCreateBy = businessCreateBy;
	}
	@Column(name ="business_create_name",nullable=false)
	public String getBusinessCreateName() {
		return businessCreateName;
	}
	public void setBusinessCreateName(String businessCreateName) {
		this.businessCreateName = businessCreateName;
	}
	@Column(name ="business_create_time",nullable=false)
	public Timestamp getBusinessCreateTime() {
		return businessCreateTime;
	}
	public void setBusinessCreateTime(Timestamp businessCreateTime) {
		this.businessCreateTime = businessCreateTime;
	}
	@Column(name ="process_instance_id",nullable=false)
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	@Column(name ="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getCurrentTodo() {
		return currentTodo;
	}
	public void setCurrentTodo(String currentTodo) {
		this.currentTodo = currentTodo;
	}
	@Column(name ="serialnumber")
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id")
	@JsonIgnore
	public TSProcess gettSProcess() {
		return tSProcess;
	}
	public void settSProcess(TSProcess tSProcess) {
		this.tSProcess = tSProcess;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_id")
	@JsonIgnore
	public CoordinateEntity getCoordinateEntity() {
		return coordinateEntity;
	}
	public void setCoordinateEntity(CoordinateEntity coordinateEntity) {
		this.coordinateEntity = coordinateEntity;
	}
	
	
}
