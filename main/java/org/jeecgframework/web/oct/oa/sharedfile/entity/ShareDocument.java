package org.jeecgframework.web.oct.oa.sharedfile.entity;
import java.util.Date;
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

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSAttachment;

/**   
 * @Title: Entity
 * @Description: 在线文档分类
 * @author onlineGenerator
 * @date 2016-03-20 11:46:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "oct_act_share_document")
public class ShareDocument extends TSAttachment implements java.io.Serializable {
	private String docname;
	private String type;
    private String documentId;
    private Date createTime;
    private String createname;
    private String createid;
    @Column(name ="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name ="name")
	public String getDocname() {
		return docname;
	}
	public void setDocname(String docname) {
		this.docname = docname;
	}
	@Column(name ="type")
	public String getType() {
		return type;
	}	
	public void setType(String type) {
		this.type = type;
	}
	@Column(name ="share_file_id")
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	@Column(name ="create_name")
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	@Column(name ="create_id")
	public String getCreateid() {
		return createid;
	}
	public void setCreateid(String createid) {
		this.createid = createid;
	}
	
}

