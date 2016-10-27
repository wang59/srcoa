package  org.jeecgframework.web.oct.oa.vacation.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
/**
 * 节假日记录
 * @author 凡艺
 * @since  2016-09-12
 */
@Entity
@Table(name = "oct_attendance_holiday")
public class AttendanceHoliDayConfig extends IdEntity implements java.io.Serializable{
	private Integer holidayType;
	private String holidayName;
	private Date startDate;
	private Date endDate;
	private Integer holidayCount;
	private Integer startDay;
	@Column(name ="start_day")
	public Integer getStartDay() {
		return startDay;
	}
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}
	@Column(name ="holiday_type")
	public Integer getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(Integer holidayType) {
		this.holidayType = holidayType;
	}
	@Column(name ="holiday_name")
	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	@Column(name ="start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Column(name ="end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(name ="holiday_count")
	public Integer getHolidayCount() {
		return holidayCount;
	}

	public void setHolidayCount(Integer holidayCount) {
		this.holidayCount = holidayCount;
	}

}
