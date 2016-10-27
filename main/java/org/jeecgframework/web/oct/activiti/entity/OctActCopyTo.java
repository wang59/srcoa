package org.jeecgframework.web.oct.activiti.entity;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 带阅事宜表
 * @author 凡艺
 * @since  2016-08-16
 */
@Entity
@Table(name = "oct_act_copyto")
public class OctActCopyTo  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	//待阅人
	private TSUser user;
	//创建时间
	private Date createtime;		
	//申请记录表
	private ActTaskBusiness actTaskBusiness;
	//1：已读0：未读
	private String status;
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@JsonIgnore
	public TSUser getUser() {
		return user;
	}
	public void setUser(TSUser user) {
		this.user = user;
	}
	@Column(name ="create_time")
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oct_act_task_business_id")
	@JsonIgnore
	public ActTaskBusiness getActTaskBusiness() {
		return actTaskBusiness;
	}
	public void setActTaskBusiness(ActTaskBusiness actTaskBusiness) {
		this.actTaskBusiness = actTaskBusiness;
	}
	@Column(name ="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
