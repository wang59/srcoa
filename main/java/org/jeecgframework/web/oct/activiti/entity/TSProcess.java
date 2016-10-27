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
@Table(name = "t_s_pro")
public class TSProcess  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	//流程名
	private String name;
	//1、行政类；2、人事类；3、信息中心
	private ProcessClass type;
	//点击后对应的url地址
	private String url;
	//绑定流程
	private String processdefineid;
	//绑定表单
	private String form;
    //是否常用流程
    private String common;
    //排序
    private String sort;
    private String processdescribe;
    //临时字段，表用户收藏
    private boolean collection;
    private String status;//0：流程废弃，1：正在使用
    private String prefix;//流水号前缀
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
	 @Column(name="pro_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	 @Column(name="pro_url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pro_type")
	@NotFound(action=NotFoundAction.IGNORE)
	public ProcessClass getType() {
		return type;
	}
	public void setType(ProcessClass type) {
		this.type = type;
	}
	 @Column(name="processdefineid")
	public String getProcessdefineid() {
		return processdefineid;
	}
	public void setProcessdefineid(String processdefineid) {
		this.processdefineid = processdefineid;
	}
	 @Column(name="form")
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	
	 @Column(name="common")
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	 @Column(name="processdescribe")
	public String getProcessdescribe() {
		return processdescribe;
	}
	 @Column(name="sort")
	public void setProcessdescribe(String processdescribe) {
		this.processdescribe = processdescribe;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	 @Transient
	public boolean isCollection() {
		return collection;
	}
	public void setCollection(boolean collection) {
		this.collection = collection;
	}
	 @Column(name="prefix")
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
	
}
