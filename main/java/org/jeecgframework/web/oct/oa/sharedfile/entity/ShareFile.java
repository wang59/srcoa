package org.jeecgframework.web.oct.oa.sharedfile.entity;
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

/**   
 * @Title: Entity
 * @Description: 在线文档分类
 * @author onlineGenerator
 * @date 2016-03-20 11:46:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "oct_act_share_file")
public class ShareFile extends IdEntity implements java.io.Serializable {
	private String fileName;
	private String type;
	private ShareFile parent;
	private List<ShareFile> child;
	@Column(name ="file_name")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name ="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid",referencedColumnName = "id")
	public ShareFile getParent() {
		return parent;
	}
	public void setParent(ShareFile parent) {
		this.parent = parent;
	}
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent")
	public List<ShareFile> getChild() {
		return child;
	}
	public void setChild(List<ShareFile> child) {
		this.child = child;
	}
	
}

