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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;


/**
 * 具体流程表
 * @author 凡艺
 * @since  2016-06-17
 */
@Entity
@Table(name = "oct_sys_read_log")
public class SysReadLog  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	//被阅读记录主键UUID
	private String vieweddataid;
	//阅读人姓名
	private String reader;
	//阅览人id
	private String readerId;
	//阅读时间
	private Timestamp readtime;

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
	@Column(name = "viewed_data_id")
	public String getVieweddataid() {
		return vieweddataid;
	}
	public void setVieweddataid(String vieweddataid) {
		this.vieweddataid = vieweddataid;
	}
	@Column(name = "reader")
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	@Column(name = "reader_id")
	public String getReaderId() {
		return readerId;
	}
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	@Column(name = "read_time")
	public Timestamp getReadtime() {
		return readtime;
	}
	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}
	
}
