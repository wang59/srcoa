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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 流程收藏表
 * @author 凡艺
 * @since  2016-07-01
 */
@Entity
@Table(name = "t_s_pro_user")
public class ProcessUser extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;	
    private TSUser user;    
    private TSProcess process;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public TSUser getUser() {
		return user;
	}
	public void setUser(TSUser user) {
		this.user = user;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pro_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public TSProcess getProcess() {
		return process;
	}		
	public void setProcess(TSProcess process) {
		this.process = process;
	}
	@Column(name ="pro_type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    
	
}
