package org.jeecgframework.web.oct.activiti.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**
 * 运行时任务数据表,activiti系统表
 * @author 汪旭军
 * @since  2016-05-17
 */
@Entity
@Table(name = "act_ru_task")
public class ActRuTask  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Integer rev;
	/** 执行实例的id */
	private String executionId;
	/** 流程实例的id */
	private String procInstId;
	/** 流程定义的id,对应act_re_procdef 的id_ */
	private String procDefId;
	/** 任务名称，对应 ***task 的name */
	private String name;
	/** 对应父任务  */
	private String parentTaskId;
	/** 描述 */
	private String description;
	/** task 的id */
	private String taskDefKey;
	/** 发起人 */
	private String owner;
	/** 分配到任务的人 */
	private String assignee;
	/** 委托人 */
	private String delegation;
	/** 紧急程度 */
	private String priority;
	/** 发起时间 */
	private Timestamp createTime;
	/** 审批期限 */
	private Date dueDate;
	/**  */
	private String category;
	/**  */
	private String suspensionState;
	/**  */
	private String tenantId;
	/** 外置表单路径 */
	private String formKey;
	/** 申请单和任务对应关系 */
	private ActTaskBusiness actTaskBusiness=new ActTaskBusiness(); 
	
	private TSBaseUser tSBaseUser = new TSBaseUser();
	
	private ActReProcdef actReProcdef;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROC_DEF_ID_",referencedColumnName = "ID_", insertable = false, updatable = false)
	@JsonIgnore
	public ActReProcdef getActReProcdef() {
		return this.actReProcdef;
	}

	public void setActReProcdef(ActReProcdef actReProcdef) {
		this.actReProcdef = actReProcdef;
	}

	/**
	 * @return the ID
	 */
	@Id
	@Column(name ="ID_",nullable=false)
	public String getId() {
		return id;
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
	
    @ManyToOne
    @JoinColumn(name="ASSIGNEE_", referencedColumnName = "username", insertable = false, updatable = false)
    @JsonIgnore
	public TSBaseUser gettSBaseUser() {
		return tSBaseUser;
	}

	public void settSBaseUser(TSBaseUser tSBaseUser) {
		this.tSBaseUser = tSBaseUser;
	}
	
	/**
	 * @param ID the ID to set
	 */
	public void setId(String id) {
		this.id = id;
		
	}
	/**
	 * @return the rev
	 */
	@Column(name ="REV_")
	public Integer getRev() {
		return rev;
	}
	/**
	 * @param rev the rev to set
	 */
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	/**
	 * @return the executionId
	 */
	@Column(name ="EXECUTION_ID_")
	public String getExecutionId() {
		return executionId;
	}
	/**
	 * @param executionId the executionId to set
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	/**
	 * @return the procInstId
	 */
	@Column(name ="PROC_INST_ID_")
	public String getProcInstId() {
		return procInstId;
	}
	/**
	 * @param procInstId the procInstId to set
	 */
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	/**
	 * @return the procDefId
	 */
	@Column(name ="PROC_DEF_ID_")
	public String getProcDefId() {
		return procDefId;
	}
	/**
	 * @param procDefId the procDefId to set
	 */
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	/**
	 * @return the name
	 */
	@Column(name ="NAME_")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parentTaskId
	 */
	@Column(name ="PARENT_TASK_ID_")
	public String getParentTaskId() {
		return parentTaskId;
	}
	/**
	 * @param parentTaskId the parentTaskId to set
	 */
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	/**
	 * @return the description
	 */
	@Column(name ="DESCRIPTION_")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the taskDefKey
	 */
	@Column(name ="TASK_DEF_KEY_")
	public String getTaskDefKey() {
		return taskDefKey;
	}
	/**
	 * @param taskDefKey the taskDefKey to set
	 */
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	/**
	 * @return the owner
	 */
	@Column(name ="OWNER_")
	public String getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	/**
	 * @return the assignee
	 */
	@Column(name ="ASSIGNEE_")
	public String getAssignee() {
		return assignee;
	}
	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	/**
	 * @return the delegation
	 */
	@Column(name ="DELEGATION_")
	public String getDelegation() {
		return delegation;
	}
	/**
	 * @param delegation the delegation to set
	 */
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	/**
	 * @return the priority
	 */
	@Column(name ="PRIORITY_")
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the create_time
	 */
	@Column(name ="CREATE_TIME_")
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the dueDate
	 */
	@Column(name ="DUE_DATE_")
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the category
	 */
	@Column(name ="CATEGORY_")
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the suspensionState
	 */
	@Column(name ="SUSPENSION_STATE_")
	public String getSuspensionState() {
		return suspensionState;
	}
	/**
	 * @param suspensionState the suspensionState to set
	 */
	public void setSuspensionState(String suspensionState) {
		this.suspensionState = suspensionState;
	}
	/**
	 * @return the tenantId
	 */
	@Column(name ="TENANT_ID_")
	public String getTenantId() {
		return tenantId;
	}
	/**
	 * @param tenantId the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	 * @return the formKey
	 */
	@Column(name ="FORM_KEY_")
	public String getFormKey() {
		return formKey;
	}
	/**
	 * @param formKey the formKey to set
	 */
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
}
