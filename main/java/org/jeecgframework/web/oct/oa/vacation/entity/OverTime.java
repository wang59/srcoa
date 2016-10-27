package  org.jeecgframework.web.oct.oa.vacation.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
/**
 * 加班记录
 * @author 凡艺
 * @since  2016-09-12
 */
@Entity
@Table(name = "overtimeapplication")
public class OverTime extends IdEntity implements java.io.Serializable{
	private Date startTime;
	private Date endTime;
	private String counthour;//请假时间
	private Double sur_overtime;//剩余加班时长
	private String reasion;//请假事由
	@Column(name ="begin_time")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column(name ="end_time")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name ="counthour")
	public String getCounthour() {
		return counthour;
	}
	public void setCounthour(String counthour) {
		this.counthour = counthour;
	}
	@Column(name ="sur_overtime")
	public Double getSur_overtime() {
		return sur_overtime;
	}
	public void setSur_overtime(Double sur_overtime) {
		this.sur_overtime = sur_overtime;
	}
	@Column(name ="reasion")
	public String getReasion() {
		return reasion;
	}
	public void setReasion(String reasion) {
		this.reasion = reasion;
	}
	
}
