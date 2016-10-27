package org.jeecgframework.web.oct.activiti.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ActRuExecution entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "act_ru_execution", catalog = "octoa")
public class ActRuExecution implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private String id;
	private String procInstId;
	private String parentId;
	private String procDefId;
	private String superExec;
	private Integer rev;
	private String businessKey;
	private String actId;
	private Short isActive;
	private Short isConcurrent;
	private Short isScope;
	private Short isEventScope;
	private Integer suspensionState;
	private Integer cachedEntState;
	private String tenantId;
	private String name;
	private Timestamp lockTime;

	// Constructors

	/** default constructor */
	public ActRuExecution() {
	}

	/** minimal constructor */
	public ActRuExecution(String id) {
		this.id = id;
	}

	/** full constructor */
	public ActRuExecution(String id, String procInstId, String parentId, String procDefId, String superExec,
			Integer rev, String businessKey, String actId, Short isActive, Short isConcurrent, Short isScope,
			Short isEventScope, Integer suspensionState, Integer cachedEntState, String tenantId, String name,
			Timestamp lockTime) {
		this.id = id;
		this.procInstId = procInstId;
		this.superExec = superExec;
		this.procDefId = procDefId;
		this.parentId = parentId;
		this.rev = rev;
		this.businessKey = businessKey;
		this.actId = actId;
		this.isActive = isActive;
		this.isConcurrent = isConcurrent;
		this.isScope = isScope;
		this.isEventScope = isEventScope;
		this.suspensionState = suspensionState;
		this.cachedEntState = cachedEntState;
		this.tenantId = tenantId;
		this.name = name;
		this.lockTime = lockTime;
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

	@Column(name = "PROC_INST_ID_")
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	@Column(name = "PARENT_ID_")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "PROC_DEF_ID_")
	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	@Column(name = "SUPER_EXEC_")
	public String getSuperExec() {
		return superExec;
	}

	public void setSuperExec(String superExec) {
		this.superExec = superExec;
	}

	@Column(name = "REV_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "BUSINESS_KEY_")
	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	@Column(name = "ACT_ID_")
	public String getActId() {
		return this.actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	@Column(name = "IS_ACTIVE_")
	public Short getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	@Column(name = "IS_CONCURRENT_")
	public Short getIsConcurrent() {
		return this.isConcurrent;
	}

	public void setIsConcurrent(Short isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	@Column(name = "IS_SCOPE_")
	public Short getIsScope() {
		return this.isScope;
	}

	public void setIsScope(Short isScope) {
		this.isScope = isScope;
	}

	@Column(name = "IS_EVENT_SCOPE_")
	public Short getIsEventScope() {
		return this.isEventScope;
	}

	public void setIsEventScope(Short isEventScope) {
		this.isEventScope = isEventScope;
	}

	@Column(name = "SUSPENSION_STATE_")
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	@Column(name = "CACHED_ENT_STATE_")
	public Integer getCachedEntState() {
		return this.cachedEntState;
	}

	public void setCachedEntState(Integer cachedEntState) {
		this.cachedEntState = cachedEntState;
	}

	@Column(name = "TENANT_ID_")
	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Column(name = "NAME_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "LOCK_TIME_", length = 19)
	public Timestamp getLockTime() {
		return this.lockTime;
	}

	public void setLockTime(Timestamp lockTime) {
		this.lockTime = lockTime;
	}



}