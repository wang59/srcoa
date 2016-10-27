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
 * 工程项目关系表
 * @author fany
 *
 */
@Entity
@Table(name = "oct_act_task_business")
public class QualityBack extends IdEntity implements java.io.Serializable {
	/** 申请单创建人账号 */
	private String businessCreateBy;//business_create_by;
	/** 申请创建人姓名 */
	private String businessCreateName;//business_create_name;
	/** 创建人提交申请时间  */
	private Timestamp businessCreateTime;//business_create_time
	private String status;//0：草稿；1已启动;2；拒绝;3:已结束;4:撤销
	/** 流程实例ID */
	private String processInstanceId;//process_instance_id;
	private QualityBackEntity qualityBackEntity;
	private String currentTodo;//当前审批人
	private TSProcess tSProcess;//绑定流程/流程定义的对象
	@Column(name ="business_create_by")
	public String getBusinessCreateBy() {
		return businessCreateBy;
	}
	public void setBusinessCreateBy(String businessCreateBy) {
		this.businessCreateBy = businessCreateBy;
	}
	@Column(name ="business_create_name")
	public String getBusinessCreateName() {
		return businessCreateName;
	}
	public void setBusinessCreateName(String businessCreateName) {
		this.businessCreateName = businessCreateName;
	}
	@Column(name ="business_create_time")
	public Timestamp getBusinessCreateTime() {
		return businessCreateTime;
	}
	public void setBusinessCreateTime(Timestamp businessCreateTime) {
		this.businessCreateTime = businessCreateTime;
	}
	@Column(name ="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name ="process_instance_id")
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_id")
	@JsonIgnore
	public QualityBackEntity getQualityBackEntity() {
		return qualityBackEntity;
	}
	public void setQualityBackEntity(QualityBackEntity qualityBackEntity) {
		this.qualityBackEntity = qualityBackEntity;
	}
	@Transient
	public String getCurrentTodo() {
		return currentTodo;
	}
	public void setCurrentTodo(String currentTodo) {
		this.currentTodo = currentTodo;
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



}
