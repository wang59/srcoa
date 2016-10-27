package org.jeecgframework.web.oct.oa.vacation.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.ReflectHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.oct.oa.sharedfile.entity.ShareDocument;
import org.jeecgframework.web.oct.oa.sharedfile.entity.ShareFile;
import org.jeecgframework.web.oct.oa.vacation.entity.AttendanceHoliDayConfig;
import org.jeecgframework.web.oct.oa.vacation.entity.OverTime;
import org.jeecgframework.web.oct.oa.vacation.entity.Vacation;
import org.jeecgframework.web.onlinedocsort.entity.OnlineDocSortEntity;
import org.jeecgframework.web.system.pojo.base.TSCategoryEntity;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
/***
 * 请假控制器
 * @author fany
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/vacationController")
public class VacationController {
	@Autowired
	private SystemService systemService;
	
	//计算请假时间
	@RequestMapping(params = "sumVacationDays")
	@ResponseBody
		public AjaxJson sumVacationDays(Date startDate,Date endDate,Integer difDay,Integer vacType) {
		    AjaxJson j=new AjaxJson();
		    
		    
		  
		    Map<String, Object> resultMap = new LinkedMap();
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				Calendar calendarB = Calendar.getInstance();
				calendarB.setTime(endDate);
				Calendar calendarC = Calendar.getInstance();
				
				Float vacationDays = new Float(0.0);
				
				
				if(difDay>=1){
					
					for(int i=0;i<=difDay;i++){
						//过虑星期六和星期日
						//如果是事假 或者 病假 调休 则按工作日来算
						if(vacType.intValue()==0 || vacType.intValue()==1||vacType.intValue()==6){
							Date time = calendar.getTime();
							time.setHours(0);
							time.setMinutes(0);
							time.setSeconds(0);
							if(calendar.get(Calendar.DAY_OF_WEEK)==1 || calendar.get(Calendar.DAY_OF_WEEK)==7){
								//如果为周末 则判断时间范围是否要上班
								String hql="from  AttendanceHoliDayConfig where holidayType=? and  ?>=startDate and ?<=endDate";
								List<AttendanceHoliDayConfig> ahdList =systemService.findHql(hql, 2,time,time);
								if(ahdList.size()<1){
									calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
									continue;
								}
							}else {  //如不为周末 则计算是否为节假日放假
								String hql="from  AttendanceHoliDayConfig where holidayType=? and  ?>=startDate and ?<=endDate";
								List<AttendanceHoliDayConfig> ahdList =systemService.findHql(hql, 1,time,time);
								if(ahdList!=null&&ahdList.size()>0){  //如放假则跳出
									calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
									continue;
								}
							}
						}
						//计算第一天
						if(i==0){
							calendarC.setTime(startDate);
							calendarC.set(calendarC.HOUR_OF_DAY, 18);
							calendarC.set(calendarC.MINUTE, 30);
							
							vacationDays = vacationDays + sumOneDay(calendar,calendarC);
							
							calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
							continue;
						}
						//计算最后一天
						if(i==difDay){
							calendarC.setTime(endDate);
							calendarC.set(calendar.HOUR_OF_DAY, 9);
							calendarC.set(calendar.MINUTE, 0);

							vacationDays = vacationDays + sumOneDay(calendarC,calendarB);
							
							calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
							continue;
						}
						//中间请假天数直接算一天
						vacationDays = vacationDays + 480;
						calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
					}
				}else{
					
					if(vacType.intValue()==0 || vacType.intValue()==1||vacType.intValue()==6){
						Date time = calendar.getTime();
						time.setHours(0);
						time.setMinutes(0);
						time.setSeconds(0);
						
						if(calendar.get(Calendar.DAY_OF_WEEK)!=1 && calendar.get(Calendar.DAY_OF_WEEK)!=7){
							 //如不为周末 则计算是否为节假日放假
							String hql="from  AttendanceHoliDayConfig where holidayType=? and  ?>=startDate and ?<=endDate";
							List<AttendanceHoliDayConfig> ahdList =systemService.findHql(hql, 1,time,time);
							if(ahdList!=null&&ahdList.size()>0){  //如放假则跳出
							}else {
								vacationDays = sumOneDay(calendar,calendarB);
							}
						}else {
							//如果为周末 则判断时间范围是否要上班
							String hql="from  AttendanceHoliDayConfig where holidayType=? and  ?>=startDate and ?<=endDate";
							List<AttendanceHoliDayConfig> ahdList =systemService.findHql(hql, 2,time,time);
							if(ahdList.size()<1){
							}else {
								vacationDays = sumOneDay(calendar,calendarB);
							}
						}
					}else {
						vacationDays = sumOneDay(calendar,calendarB);
					}
				
				}
				Float minute = vacationDays%60;
				Integer hour = (int) (vacationDays / 60);
				vacationDays = hour.floatValue();
				//System.out.println(minute);
				if(minute>=31){
					vacationDays = vacationDays+1;
				}else if(minute>1){
					vacationDays = vacationDays+0.5f;
				}
				resultMap.put("result", true);
				resultMap.put("days", vacationDays);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultMap.put("result", false);
			}
			j.setAttributes(resultMap);
			return j;
		}
		//计算一天的请假时间
		private Float sumOneDay(Calendar startTime,Calendar endTime){
			
			Float vacationOneDays = new Float(0.0);
			
			//早上上班时间分钟数
			Float amWorkTimeA = (float) 540.0; 
			Float amWorkTimeB = (float) 750.0;
			//下午上班时间分钟数
			Float pmWorkTimeA = (float) 840.0;
			Float pmWorkTimeB = (float) 1110.0;
					
			//开始请假时间转分钟数
			Float vTimeA  = (float) (startTime.get(Calendar.HOUR_OF_DAY) * 60.0) + startTime.get(Calendar.MINUTE);
			//结束请假时间转分钟数
			Float vTimeB  = (float) (endTime.get(Calendar.HOUR_OF_DAY) * 60.0) + endTime.get(Calendar.MINUTE);
			
			Float vCountA = (float)0.0;
			Float vCountB = (float)0.0;
			
			//计算上午请假时间分钟数
			if(vTimeA < amWorkTimeA){
				vTimeA = amWorkTimeA;
				vCountA = amWorkTimeB - vTimeA;
				//System.out.println("A in 1");
			}else if (vTimeA >=amWorkTimeA && vTimeA <= amWorkTimeB ) {
				vCountA = amWorkTimeB - vTimeA;
				//System.out.println("A in 2");
			}else if(vTimeA > pmWorkTimeA){
				pmWorkTimeA = vTimeA;
				//System.out.println("A in 3");
			}
			//System.out.println(vCountA);
			
			//计算下午请假时间分钟数
			if(vTimeB>=pmWorkTimeA && vTimeB <=pmWorkTimeB){
				vCountB = vTimeB - pmWorkTimeA;
				//System.out.println("B in 1");
			}else if(vTimeB <=amWorkTimeB){
				vCountB = vTimeB - amWorkTimeB;
				//System.out.println("B in 2");
			}else if(vTimeB >pmWorkTimeB){
				vTimeB = pmWorkTimeB;
				vCountB = vTimeB - pmWorkTimeA;
				//System.out.println("B in 3");
			}
			
			vacationOneDays = vCountA + vCountB;
			
			return vacationOneDays;
		}
		/**
		 * 查询加班申请列表用于请假调休显示-add 2015-12-8
		 * @return
		 */
		@RequestMapping(params = "queryOverTimeList")
		@ResponseBody
		public AjaxJson queryOverTimeList(HttpServletRequest req)
		{
			String userName=req.getParameter("username");
			AjaxJson j=new AjaxJson();			
			String hql="select o from OverTime o,ActTaskBusiness a where o.id=a.businessId and a.status=? and a.businessCreateBy=? and o.sur_overtime>?";
			//获取已结束的加班记录
			List<OverTime> olist=systemService.findHql(hql, "3",userName,0.0);			
			Map map=new LinkedMap();
			Double totle=0.0;//总可调休时间
				for(OverTime o:olist){
					map.put(o.getId(), o);
					totle+=o.getSur_overtime();
				}
				//获取进行中的调休记录
			String hql2="select v from Vacation v,ActTaskBusiness a where v.id=a.businessId and a.status=? and a.businessCreateBy=? and v.type=?";
			List<Vacation> vacations=systemService.findHql(hql2, "1",userName,"6");	
			Double remove=0.0;//已占用调休时间
			for(Vacation v:vacations){
				remove+=Double.parseDouble(v.getVacation());
			}
			totle-=remove;
			if(totle>0){
				j.setMsg(remove.toString());
				j.setObj(totle);
			}else{
				j.setMsg(remove.toString());
				j.setObj(0.0);
			}
				j.setAttributes(map);
				
			return j;
		}
		/**
		 * 获取请假记录
		 * @return
		 */
		@RequestMapping(params = "queryVacaList")
		@ResponseBody
		public AjaxJson queryVacaList(HttpServletRequest req)
		{
			AjaxJson j=new AjaxJson();
			String userName=req.getParameter("username");
			String hql="select o from Vacation o,ActTaskBusiness a where o.id=a.businessId and a.status=? and a.businessCreateBy=?";
			List<Vacation> olist=systemService.findHql(hql,"3",userName);	
			List list=new ArrayList();
			for(Vacation va:olist){
				Map map=new HashMap();
				Date start=va.getStarttime();
				Date end=va.getEndtime();			
				map.put("time", start.toString()+"至"+end.toString());
				map.put("type", getVaType(va.getType()));
				map.put("hour", va.getVacation());
				map.put("reasion", va.getAssociate());
				list.add(map);
			}
			j.setObj(list);
			return j;
			
		}
		public String getVaType(String type){
			String a="";
			switch(Integer.parseInt(type)){
			case 0:a="事假";break;
			case 1:a="病假";break;
			case 2:a="丧假";break;
			case 3:a="婚假";break;
			case 4:a="产假";break;
			case 5:a="陪产假";break;
			case 6:a="调休";break;
			case 7:a="年假";break;
			default : a="异常";
			}
			return a;
		}
}
