package org.jeecgframework.web.oct.activiti.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;

/**
 * ActHiTaskinst entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "act_hi_taskinst", catalog = "octoa")
public class ActHiTaskinst implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/*标识*/
	private String id;
	/* 流程定义id*/
	private String procDefId;
	/*任务定义id*/
	private String taskDefKey;
	/*流程实例id*/
	private String procInstId;
	/*执行实例id*/
	private String executionId;
	/*任务名称*/
	private String name;
	/* 父任务id*/
	private String parentTaskId;
	/*说明*/
	private String description;
	/*拥有人（发起人）*/
	private String owner;
	/*分配到任务的人*/
	private String assignee;
	/*开始任务时间*/
	private Timestamp startTime;
	private Timestamp claimTime;
	/* 结束任务时间*/
	private Timestamp endTime;
	/*时长*/
	private Long duration;
	/*从运行时任务表中删除的原因*/
	private String deleteReason;
	/*紧急程度*/
	private Integer priority;
	private Timestamp dueDate;
	private String formKey;
	private String category;
	private String tenantId;
	private ActTaskBusiness actTaskBusiness=new ActTaskBusiness(); 

	private TSBaseUser tSBaseUser = new TSBaseUser();

	// Constructors
	 @ManyToOne
	 @JoinColumn(name="ASSIGNEE_", referencedColumnName = "username", insertable = false, updatable = false)
	public TSBaseUser gettSBaseUser() {
		return tSBaseUser;
	}

	public void settSBaseUser(TSBaseUser tSBaseUser) {
		this.tSBaseUser = tSBaseUser;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="PROC_INST_ID_", referencedColumnName = "process_instance_id", insertable = false, updatable = false)
	public ActTaskBusiness getActTaskBusiness() {
		return actTaskBusiness;
	}

	public void setActTaskBusiness(ActTaskBusiness actTaskBusiness) {
		this.actTaskBusiness = actTaskBusiness;
	}

	/** default constructor */
	public ActHiTaskinst() {
	}

	/** minimal constructor */
	public ActHiTaskinst(String id, Timestamp startTime) {
		this.id = id;
		this.startTime = startTime;
	}

	/** full constructor */
	public ActHiTaskinst(String id, String procDefId, String taskDefKey,
			String procInstId, String executionId, String name,
			String parentTaskId, String description, String owner,
			String assignee, Timestamp startTime, Timestamp claimTime,
			Timestamp endTime, Long duration, String deleteReason,
			Integer priority, Timestamp dueDate, String formKey,
			String category, String tenantId) {
		this.id = id;
		this.procDefId = procDefId;
		this.taskDefKey = taskDefKey;
		this.procInstId = procInstId;
		this.executionId = executionId;
		this.name = name;
		this.parentTaskId = parentTaskId;
		this.description = description;
		this.owner = owner;
		this.assignee = assignee;
		this.startTime = startTime;
		this.claimTime = claimTime;
		this.endTime = endTime;
		this.duration = duration;
		this.deleteReason = deleteReason;
		this.priority = priority;
		this.dueDate = dueDate;
		this.formKey = formKey;
		this.category = category;
		this.tenantId = tenantId;
	}

	// Property accessors
	@Id
	@Column(name = "ID_", unique = true, nullable = false, length = 64)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "PROC_DEF_ID_", length = 64)
	public String getProcDefId() {
		return this.procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Column(name = "TASK_DEF_KEY_")
	public String getTaskDefKey() {
		return this.taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	@Column(name = "PROC_INST_ID_", length = 64)
	public String getProcInstId() {
		return this.procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "EXECUTION_ID_", length = 64)
	public String getExecutionId() {
		return this.executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	@Column(name = "NAME_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PARENT_TASK_ID_", length = 64)
	public String getParentTaskId() {
		return this.parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	@Column(name = "DESCRIPTION_", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "OWNER_")
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Column(name = "ASSIGNEE_")
	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	@Column(name = "START_TIME_", nullable = false, length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "CLAIM_TIME_", length = 19)
	public Timestamp getClaimTime() {
		return this.claimTime;
	}

	public void setClaimTime(Timestamp claimTime) {
		this.claimTime = claimTime;
	}

	@Column(name = "END_TIME_", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "DURATION_")
	public Long getDuration() {
		return this.duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Column(name = "DELETE_REASON_", length = 4000)
	public String getDeleteReason() {
		return this.deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	@Column(name = "PRIORITY_")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "DUE_DATE_", length = 19)
	public Timestamp getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}

	@Column(name = "FORM_KEY_")
	public String getFormKey() {
		return this.formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	@Column(name = "CATEGORY_")
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "TENANT_ID_")
	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}