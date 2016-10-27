package org.jeecgframework.web.oct.common.util;

import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.jeecgframework.p3.core.util.oConvertUtils;
import org.jeecgframework.web.system.pojo.base.TSUser;

/**   
 * @Description: 组织工具类，获取用户的领导和组织等信息。
 * @author 汪旭军
 * @date 2016-05-13 17:12:40
 * @version V1.0   
 *
 */
public class OrgUtil {
	private TSUser user;
	private String actUpOneLevel;//上一级领导
	private String actUpSecondLevel;//上二级领导
	private String actUpThirdLevel;//上三级领导
	private String actTopOneLevel;//申请人的一级领导
	private String actTopSecondLevel;//申请人的二级领导
	private String actTopThirdLevel;//申请人的三级领导

	public OrgUtil(TSUser user){
		this.user=user;
		actUpOneLevel="";
		actUpSecondLevel="";
		actUpThirdLevel="";
		actTopOneLevel="";
		actTopSecondLevel="";
		actTopThirdLevel="";
		if(user !=null){
			computerUserLeader();
		}
	}


	public TSUser getUser() {
		return user;
	}

	public void setUser(TSUser user) {
		this.user = user;
	}

	/**
	 *功能描述：获取用户的上一级领导。
	 *@author 汪旭军
	 *@since 2016-07-12
	 */
	public String getactUpOneLevel() {
		return actUpOneLevel;
	}

	/**
	 *功能描述：获取用户的上二级领导。
	 *@author 汪旭军
	 *@since 2016-07-12
	 */
	public String getactUpSecondLevel() {
		return actUpSecondLevel;
	}

	/**
	 *功能描述：获取用户的上三级领导。
	 *@author 汪旭军
	 *@since 2016-07-12
	 */
	public String getactUpThirdLevel() {
		return actUpThirdLevel;
	}

	/**
	 *功能描述：获取用户的一级领导。
	 *@author 汪旭军
	 *@since 2016-07-12
	 */
	public String getactTopOneLevel() {
		return actTopOneLevel;
	}

	/**
	 *功能描述：获取用户的二级领导。
	 *@author 汪旭军
	 *@since 2016-07-12
	 */
	public String getactTopSecondLevel() {
		return actTopSecondLevel;
	}

	/**
	 *功能描述：获取用户的三级领导。
	 *@author 汪旭军
	 *@since 2016-07-12
	 */
	public String getactTopThirdLevel() {
		return actTopThirdLevel;
	}

	/**
	 *功能描述：根据个人信息表计算用户的领导。
	 *@author 汪旭军
	 *@since 2016-07-11
	 */
	private void computerUserLeader(){
		String [] leaders = new String[6];
		//部门负责人（副）
		if(oConvertUtils.isEmpty(user.getLeader())){
			leaders[0]="";//部门负责人（副）
		}else{
			leaders[0]=user.getLeader().getUserName();//部门负责人（副）
		}
		//部门负责人（正）
		if(oConvertUtils.isEmpty(user.getLeader_id())){
			leaders[1]="";
		}else{
			leaders[1]=user.getLeader_id().getUserName();//部门负责人（正）
		}
		//中心负责人（副）
		if(oConvertUtils.isEmpty(user.getManager())){
			leaders[2]="";
		}else{
			leaders[2]=user.getManager().getUserName();//中心负责人（副）
		}
		//中心负责人（正）
		if(oConvertUtils.isEmpty(user.getManager_id())){
			leaders[3]="";
		}else{
			leaders[3]=user.getManager_id().getUserName();//中心负责人（正）
		}
		//分管经理
		if(oConvertUtils.isEmpty(user.getDep_leader())){
			leaders[4]="";
		}else{
			leaders[4]=user.getDep_leader().getUserName();//分管经理
		}
		//总经理
		if(oConvertUtils.isEmpty(user.getDep_leader_id())){
			leaders[5]="";
		}else{
			leaders[5]=user.getDep_leader_id().getUserName();//总经理
		}
		
		//获取上一级领导，上二级领导，上三级领导
		for(String leader1:leaders){
			if(!leader1.equals("")){
				if(actUpOneLevel.equals("")){
					actUpOneLevel=leader1;//上一级领导
				}else if(actUpSecondLevel.equals("")){
					actUpSecondLevel=leader1;//上二级领导
				}else if(actUpThirdLevel.equals("")){
					actUpThirdLevel=leader1;//上三级领导
				}
			}
		}

		//获取一级领导，二级领导，三级领导
		for(int i=5;i>-1;i--){
			if(!leaders[i].equals("")){
				if(actTopOneLevel.equals("")){
					actTopOneLevel=leaders[i];//一级领导
				}else if(actTopSecondLevel.equals("")){
					actTopSecondLevel=leaders[i];//二级领导
				}else if(actTopThirdLevel.equals("")){
					actTopThirdLevel=leaders[i];//三级领导
				}
			}
		}

	}

	/**
	 *功能描述：往变量里面增加所有的领导信息，上一级领导，上二级领导，上三级领导，一级领导，二级领导，三级领导，
	 * @param variables - 流程
	 * @author 汪旭军
	 * @since 2016-07-13
	 */
	public void putAllLeaders(Map<String, Object> activitiVariables){
		activitiVariables.put("actUpOneLevel",ConvertUtils.convert(actUpOneLevel,String.class));
		activitiVariables.put("actUpSecondLevel",ConvertUtils.convert(actUpSecondLevel,String.class));
		activitiVariables.put("actUpThirdLevel",ConvertUtils.convert(actUpThirdLevel,String.class));
		activitiVariables.put("actTopOneLevel",ConvertUtils.convert(actTopOneLevel,String.class));
		activitiVariables.put("actTopSecondLevel",ConvertUtils.convert(actTopSecondLevel,String.class));
		activitiVariables.put("actTopThirdLevel",ConvertUtils.convert(actTopThirdLevel,String.class));
	}
}
