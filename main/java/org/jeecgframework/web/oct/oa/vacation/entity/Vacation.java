package  org.jeecgframework.web.oct.oa.vacation.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
/**
 * 请假记录
 * @author 凡艺
 * @since  2016-09-12
 */
@Entity
@Table(name = "test_vacation")
public class Vacation extends IdEntity implements java.io.Serializable{
	private String vacation;
	private String type;//0：事假 1：病假 2：丧假 3：婚假 4：产假 5：陪产假 6：调休
	private Date starttime;
	private Date endtime;
	private String associate;
	
	@Column(name ="vacation")
	public String getVacation() {
		return vacation;
	}
	public void setVacation(String vacation) {
		this.vacation = vacation;
	}
	@Column(name ="vacationtype")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name ="starttime")
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	@Column(name ="endtime")
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	@Column(name ="associate")
	public String getAssociate() {
		return associate;
	}
	public void setAssociate(String associate) {
		this.associate = associate;
	}
	
}
