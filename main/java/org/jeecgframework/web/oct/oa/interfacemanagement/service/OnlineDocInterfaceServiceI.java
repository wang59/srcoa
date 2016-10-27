package org.jeecgframework.web.oct.oa.interfacemanagement.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceSubdirectoryEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.OnlineDocInterfaceEntity;
import org.jeecgframework.web.onlinedoc.entity.OnlineDocEntity;


public interface OnlineDocInterfaceServiceI extends CommonService{
     
	
	
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(DocInterfaceSubdirectoryEntity onlineDoc,List<OnlineDocInterfaceEntity> onlineDocInterfaceEntity) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(DocInterfaceSubdirectoryEntity onlineDoc,List<OnlineDocInterfaceEntity> onlineDocInterfaceEntity) ;
	
	/**
	 * 删除
	 * @param onlineDoc
	 */
	public void delMain(DocInterfaceSubdirectoryEntity onlineDoc);

}
