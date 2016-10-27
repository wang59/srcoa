package org.jeecgframework.web.oct.oa.vacation.servicetask;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.collections.map.LinkedMap;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.activiti.service.interf.WorkflowService;
import org.jeecgframework.web.oct.oa.vacation.entity.OverTime;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("vacationService")
public class VacationService implements JavaDelegate,Serializable{
	@Autowired
	private SystemService systemService;
	@Autowired
	private WorkflowService workflowService;
	private Expression vacation;
	private Expression vacationtype;
	public void setTesvacationt(Expression vacation) {
		this.vacation = vacation;
	}

	public void setVacationtype(Expression vacationtype) {
		this.vacationtype = vacationtype;
	}

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("111111111111111");
		String counts=(String) vacation.getValue(execution);
		Double count=Double.parseDouble(counts);
		String vacationtypes=(String) vacationtype.getValue(execution);
		if(!"6".equals(vacationtypes)){
			return;
		}
		TSUser user = ResourceUtil.getSessionUserName();
		String hql="select o from OverTime o,ActTaskBusiness a where o.id=a.businessId and a.status=? and a.businessCreateBy=? and o.sur_overtime>?";
		//获取已结束的加班记录
		List<OverTime> olist=systemService.findHql(hql, "3","fany",0.0);			
		Map map=new LinkedMap(); 
			for(OverTime o:olist){
				Double overtime=o.getSur_overtime();
				if(count>=overtime){
					count-=overtime;
					o.setSur_overtime(0.0);
					systemService.save(o);
				}else{
					o.setSur_overtime(overtime-count);
					systemService.save(o);
					break;
				}
			}
			//修改流程状态
			String instance=execution.getProcessInstanceId();
			List<ActTaskBusiness> list=systemService.findByProperty(ActTaskBusiness.class, "processInstanceId", instance);
			if(list.size()>0){
				String status=list.get(0).getStatus();
				if(StringUtil.isNotEmpty(status)&&!"3".equals(status)){
					ActTaskBusiness actTaskBusiness=list.get(0);
					String procedef=execution.getProcessDefinitionId();
					workflowService.getEndCopyTo(procedef,instance);
					actTaskBusiness.setStatus("3");
					systemService.updateEntitie(actTaskBusiness);
				}
			}
			
			
	}

}
