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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 流程代理书记表
 * @author 凡艺
 * @since  2016-06-17
 */
@Entity
@Table(name = "t_s_agent")
public class ProcessAgent  extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private TSUser creater;//创建人
	private TSUser agent;//代理人
	private String style;//是否代理已有代办事项1:是，0否
	private String type;//流程类型
	private TSProcess path;//流程名
	private java.util.Date begintime;//代理开始时间
	private java.util.Date endtime;//代理结束时间
	private String status;//0:未开始，1:代理中，4:已撤销
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creater")
	@NotFound(action=NotFoundAction.IGNORE)
	public TSUser getCreater() {
		return creater;
	}
	public void setCreater(TSUser creater) {
		this.creater = creater;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent")
	@NotFound(action=NotFoundAction.IGNORE)
	public TSUser getAgent() {
		return agent;
	}
	public void setAgent(TSUser agent) {
		this.agent = agent;
	}
	@Column(name ="style",length=32)
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	@Column(name ="type",length=32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "path")
	@NotFound(action=NotFoundAction.IGNORE)
	public TSProcess getPath() {
		return path;
	}
	public void setPath(TSProcess path) {
		this.path = path;
	}
	@Column(name ="begintime",length=32)
	public java.util.Date getBegintime() {
		return begintime;
	}
	public void setBegintime(java.util.Date begintime) {
		this.begintime = begintime;
	}
	@Column(name ="endtime",length=32)
	public java.util.Date getEndtime() {
		return endtime;
	}
	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}
	@Column(name ="status",length=32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
