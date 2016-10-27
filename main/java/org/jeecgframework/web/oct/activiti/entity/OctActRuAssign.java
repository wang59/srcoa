package org.jeecgframework.web.oct.activiti.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 协办实体类
 * @author 汪旭军
 * @since  2016-07-06
 */
@Entity
@Table(name = "oct_act_ru_assign")
public class OctActRuAssign extends IdEntity implements java.io.Serializable{
	private String owner;//发起协办的人员
	private String assignee;//指定的协办人
	private String taskId;//task_id 协办对应的任务ID。
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	@Column(name ="task_id")
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
