package org.jeecgframework.web.oct.oa.qualityback.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.oct.activiti.entity.ActApproveLog;
import org.jeecgframework.web.oct.activiti.entity.ActTaskBusiness;
import org.jeecgframework.web.oct.oa.qualityback.entity.QualityBackEntity;
import org.jeecgframework.web.oct.oa.qualityback.service.interf.QualityBackService;
import org.jeecgframework.web.oct.oa.qualityback.vo.QualityBackEntityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("qualityBackService")
@Transactional
public class QualityBackServiceImpl implements QualityBackService{
	@Autowired
	private CommonService commonService;
	@Override
	public List<QualityBackEntityVo> getData(List<QualityBackEntity> list) {
		List<QualityBackEntityVo> qlist=new ArrayList();
		for(QualityBackEntity QualityBackEntity:list){
			QualityBackEntityVo vo=new QualityBackEntityVo();
			vo.setSerialnumber(QualityBackEntity.getSerialnumber());
			vo.setCreatedate(QualityBackEntity.getCreatedate());
			vo.setCreatename(QualityBackEntity.getCreatename());
			if(QualityBackEntity.getOctEngineerManage()!=null){
			  vo.setProject(QualityBackEntity.getOctEngineerManage().getEngineerName());
			}
		    if(QualityBackEntity.getOctProjectManage()!=null){
			  vo.setItem(QualityBackEntity.getOctProjectManage().getProjectName());
			}
			vo.setDepartmentname(QualityBackEntity.getDepartmentname());
			vo.setContent(QualityBackEntity.getContent());
			String comment=getcomment(QualityBackEntity.getId(),QualityBackEntity.getDepartmentid());
			vo.setDepartmentid(comment);
			String comment2=getcomment(QualityBackEntity.getId(),QualityBackEntity.getDodepartid());
			vo.setDodepartid(comment2);
			String yesorno=QualityBackEntity.getYesOrNo();
			if("0".equals(yesorno)){
				yesorno="否";
			}else if("1".equals(yesorno)){
				yesorno="是";
			}
			vo.setYesOrNo(yesorno);
			vo.setDeadline(QualityBackEntity.getDeadline());
			//状态
			String status=getstatus(QualityBackEntity.getId());
			vo.setStatus(status);
			qlist.add(vo);
		}
		return qlist;
		
	}
    public String getcomment(String id,String userName){
    	String comment="";
    	try{
    	String[] names=userName.split(";");
    	for(String name:names){
    		String hql=" from ActApproveLog  where businessId=? and approverId=? order by createTime desc";
    		List<ActApproveLog> list=commonService.findHql(hql, id,name);
    		if(list.size()>0){
    			comment+=list.get(0).getComment()+",";
    		}
    	}
    	}catch (Exception e) {
			//e.printStackTrace();			
		}
    	return comment;
    }
	public String getstatus(String id){
		String status="";
		List<ActTaskBusiness> list=commonService.findByProperty(ActTaskBusiness.class, "businessId", id);
		if(list.size()>0){
			status=list.get(0).getStatus();
			if("1".equals(status)){
				status="进行中";
			}else if("2".equals(status)){
				status="拒绝";
			}else if("3".equals(status)){
				status="已结束";
			}else if("4".equals(status)){
				status="申请人撤销";
			}else{
				status="草稿";
			}
		}
		return status;
	}

}
