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
 * 流程定义表
 * @author 凡艺
 * @since  2016-06-17
 */
@Entity
@Table(name = "act_re_procdef")
public class ActreProcess  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String key;
	private String version;
	
	@Id
	@Column(name = "ID_", unique = true, nullable = false, length = 64)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "VERSION_")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Column(name = "KEY_")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	

	
	
	
}
