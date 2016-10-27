package org.jeecgframework.web.oct.activiti.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ActReProcdef entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "act_re_procdef", catalog = "octoa", uniqueConstraints = @UniqueConstraint(columnNames = { "KEY_",
		"VERSION_", "TENANT_ID_" }))
public class ActReProcdef implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/* 流程ID */
	private String id;
	private Integer rev;
	/* 流程命名空间 */
	private String category;
	/* 流程名称 */
	private String name;
	/* 流程编号 */
	private String key;
	/* 流程版本号 */
	private Integer version;
	/* 部署编号 */
	private String deploymentId;
	/* 资源文件名称 */
	private String resourceName;
	/* 图片资源文件名称 */
	private String dgrmResourceName;
	private String description;
	/* 是否有Start From Key */
	private Short hasStartFormKey;
	private Short hasGraphicalNotation;
	private Integer suspensionState;
	private String tenantId;
	private Set<ActRuTask> actRuTasks = new HashSet<ActRuTask>(0);
	private Set<ActTaskBusiness>  actTaskBusiness=new HashSet<ActTaskBusiness>(0); 
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReProcdef")
	public Set<ActTaskBusiness> getActTaskBusiness() {
		return actTaskBusiness;
	}

	public void setActTaskBusiness(Set<ActTaskBusiness> actTaskBusiness) {
		this.actTaskBusiness = actTaskBusiness;
	}

	/** default constructor */
	public ActReProcdef() {
	}

	/** minimal constructor */
	public ActReProcdef(String id, String key, Integer version) {
		this.id = id;
		this.key = key;
		this.version = version;
	}

	/** full constructor */
	public ActReProcdef(String id, Integer rev, String category, String name, String key, Integer version,
			String deploymentId, String resourceName, String dgrmResourceName, String description,
			Short hasStartFormKey, Short hasGraphicalNotation, Integer suspensionState, String tenantId,
			Set<ActRuTask> actRuTasks) {
		this.id = id;
		this.rev = rev;
		this.category = category;
		this.name = name;
		this.key = key;
		this.version = version;
		this.deploymentId = deploymentId;
		this.resourceName = resourceName;
		this.dgrmResourceName = dgrmResourceName;
		this.description = description;
		this.hasStartFormKey = hasStartFormKey;
		this.hasGraphicalNotation = hasGraphicalNotation;
		this.suspensionState = suspensionState;
		this.tenantId = tenantId;
		this.actRuTasks = actRuTasks;
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

	@Column(name = "REV_")
	public Integer getRev() {
		return this.rev;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	@Column(name = "CATEGORY_")
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "NAME_")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "KEY_", nullable = false)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VERSION_", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "DEPLOYMENT_ID_", length = 64)
	public String getDeploymentId() {
		return this.deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	@Column(name = "RESOURCE_NAME_", length = 4000)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "DGRM_RESOURCE_NAME_", length = 4000)
	public String getDgrmResourceName() {
		return this.dgrmResourceName;
	}

	public void setDgrmResourceName(String dgrmResourceName) {
		this.dgrmResourceName = dgrmResourceName;
	}

	@Column(name = "DESCRIPTION_", length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "HAS_START_FORM_KEY_")
	public Short getHasStartFormKey() {
		return this.hasStartFormKey;
	}

	public void setHasStartFormKey(Short hasStartFormKey) {
		this.hasStartFormKey = hasStartFormKey;
	}

	@Column(name = "HAS_GRAPHICAL_NOTATION_")
	public Short getHasGraphicalNotation() {
		return this.hasGraphicalNotation;
	}

	public void setHasGraphicalNotation(Short hasGraphicalNotation) {
		this.hasGraphicalNotation = hasGraphicalNotation;
	}

	@Column(name = "SUSPENSION_STATE_")
	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	@Column(name = "TENANT_ID_")
	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "actReProcdef")
	public Set<ActRuTask> getActRuTasks() {
		return this.actRuTasks;
	}

	public void setActRuTasks(Set<ActRuTask> actRuTasks) {
		this.actRuTasks = actRuTasks;
	}

}