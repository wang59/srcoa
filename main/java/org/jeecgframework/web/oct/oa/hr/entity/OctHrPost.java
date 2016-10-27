package org.jeecgframework.web.oct.oa.hr.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import java.util.Date;


/**
 * The persistent class for the oct_hr_posts database table.
 * 
 */
@Entity
@Table(name="oct_hr_posts")
@NamedQuery(name="OctHrPost.findAll", query="SELECT o FROM OctHrPost o")
public class OctHrPost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int count;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_up_date")
	private Date lastUpDate;

	@Column(name="max_count")
	private int maxCount;

	@Column(name="org_center_id")
	private String orgCenterId;

	@Column(name="org_id")
	private String orgId;

	@Column(name="post_name")
	private String postName;

	private int status;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_ids")
	private String userIds;
	
	//private String orgId; 
	//private TSDepart tSPDepart;//所属部门

	public OctHrPost() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getLastUpDate() {
		return this.lastUpDate;
	}

	public void setLastUpDate(Date lastUpDate) {
		this.lastUpDate = lastUpDate;
	}

	public int getMaxCount() {
		return this.maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public String getOrgCenterId() {
		return this.orgCenterId;
	}

	public void setOrgCenterId(String orgCenterId) {
		this.orgCenterId = orgCenterId;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIds() {
		return this.userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

/*    @ManyToOne
    @JoinColumn(name="org_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
	public TSDepart gettSPDepart() {
		return tSPDepart;
	}

	public void settSPDepart(TSDepart tSPDepart) {
		this.tSPDepart = tSPDepart;
	}*/

}