package org.jeecgframework.web.oct.activiti.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 业务表单和待办任务关系表
 * @author 汪旭军
 * @since  2016-05-17
 */
@Entity
@Table(name = "oct_act_task_business")
public class ActTaskBusiness  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/** 流程实例ID */
	private String processInstanceId;//process_instance_id;
	/** 业务申请单ID*/
	private String businessId;//business_id;
	/** 申请单创建人账号 */
	private String businessCreateBy;//business_create_by;
	/** 申请创建人姓名 */
	private String businessCreateName;//business_create_name;
	/** 创建人提交申请时间  */
	private Timestamp businessCreateTime;//business_create_time
	/** 业务单标题*/
	private String businessTitle;//business_title
	/** 业务单审批URL */
	private String approvePath;//approve_path
	/**/
	private String  processDefId;
	private ActReProcdef actReProcdef;
	private TSProcess tSProcess;//绑定流程/流程定义的对象
	private String status;//0：草稿；1已启动;2；拒绝;3:已结束;4:撤销
	private String formName;//自定义表单名
	private String serialnumber;//流水号
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_def_id",referencedColumnName = "ID_", insertable = false, updatable = false)
	public ActReProcdef getActReProcdef() {
		return actReProcdef;
	}
	public void setActReProcdef(ActReProcdef actReProcdef) {
		this.actReProcdef = actReProcdef;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pro_id")
	public TSProcess getTSProcess() {
		return tSProcess;
	}
	public void setTSProcess(TSProcess tSProcess) {
		this.tSProcess = tSProcess;
	}
	@Column(name ="process_def_id")
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	/** 审批任务 */
	private List<ActRuTask> actRuTask = new ArrayList<ActRuTask>();
	
	/**已办事宜**/
	private List<ActHiTaskinst> actHiTaskinsts=new ArrayList<ActHiTaskinst>();
	
	@JsonIgnore
    @OneToMany(mappedBy = "actTaskBusiness")
    public List<ActHiTaskinst> getActHiTaskinsts() {
		return actHiTaskinsts;
	}
	public void setActHiTaskinsts(List<ActHiTaskinst> actHiTaskinsts) {
		this.actHiTaskinsts = actHiTaskinsts;
	}
	@JsonIgnore
    @OneToMany(mappedBy = "actTaskBusiness")
	public List<ActRuTask> getActRuTask() {
		return actRuTask;
	}
	public void setActRuTask(List<ActRuTask> actRuTask) {
		this.actRuTask = actRuTask;
	}
	/**
	 * @return the ID
	 */
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Id
	@Column(name ="id",nullable=false)
	public String getId() {
		return id;
	}
	/**
	 * @param ID the ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name ="process_instance_id",nullable=false)
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	@Column(name ="business_id",nullable=false)
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	@Column(name ="business_create_by",nullable=false)
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
	
	@Column(name ="business_title",nullable=false)
	public String getBusinessTitle() {
		return businessTitle;
	}
	public void setBusinessTitle(String businessTitle) {
		this.businessTitle = businessTitle;
	}
	
	@Column(name ="approve_path",nullable=true)
	public String getApprovePath() {
		return approvePath;
	}
	public void setApprovePath(String approvePath) {
		this.approvePath = approvePath;
	}
	@Column(name ="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name ="formName")
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	@Column(name ="serialnumber")
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

}
