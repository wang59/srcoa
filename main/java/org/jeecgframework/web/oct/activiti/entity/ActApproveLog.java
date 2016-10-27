package org.jeecgframework.web.oct.activiti.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * 审批记录表
 * @author 汪旭军
 * @since  2016-06-23
 */
@Entity
@Table(name = "oct_act_approve_log")
public class ActApproveLog  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private java.lang.String id;
	   /** 环节名称 */
	private java.lang.String nodeName;//node_name;
	   /** 环节ID */
	private java.lang.String nodeId;//node_id;
	   /** 审批人姓名 */
	private java.lang.String approverName;//approver_name;
	   /** 审批人ID */
	private java.lang.String approverId;//approver_id;
	   /** 操作时间 */
	private Timestamp createTime;//create_time;
	   /** 操作 */
	private java.lang.String action;//action;
	   /** 审批意见 */
	private java.lang.String comment;//comment;
	   /** 申请单的ID */
	private java.lang.String businessId;//business_id;
	
	
	
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
	
	@Column(name ="node_name",nullable=true)
	public java.lang.String getNodeName() {
		return nodeName;
	}
	public void setNodeName(java.lang.String nodeName) {
		this.nodeName = nodeName;
	}
	
	@Column(name ="node_id",nullable=false)
	public java.lang.String getNodeId() {
		return nodeId;
	}
	public void setNodeId(java.lang.String nodeId) {
		this.nodeId = nodeId;
	}
	
	@Column(name ="approver_name",nullable=true)
	public java.lang.String getApproverName() {
		return approverName;
	}
	public void setApproverName(java.lang.String approverName) {
		this.approverName = approverName;
	}
	
	@Column(name ="approver_id",nullable=false)
	public java.lang.String getApproverId() {
		return approverId;
	}
	public void setApproverId(java.lang.String approverId) {
		this.approverId = approverId;
	}
	
	@Column(name ="create_time",nullable=false)
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name ="action",nullable=true)
	public java.lang.String getAction() {
		return action;
	}
	public void setAction(java.lang.String action) {
		this.action = action;
	}
	
	@Column(name ="comment",nullable=true)
	public java.lang.String getComment() {
		return comment;
	}
	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}
	
	@Column(name ="business_id",nullable=false)
	public java.lang.String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(java.lang.String businessId) {
		this.businessId = businessId;
	}
	

}
