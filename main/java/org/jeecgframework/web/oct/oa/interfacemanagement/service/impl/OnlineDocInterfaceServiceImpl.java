package org.jeecgframework.web.oct.oa.interfacemanagement.service.impl;

import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.DocInterfaceSubdirectoryEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.entity.OnlineDocInterfaceEntity;
import org.jeecgframework.web.oct.oa.interfacemanagement.service.OnlineDocInterfaceServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("onlineDocInterfaceService")
@Transactional
public class OnlineDocInterfaceServiceImpl extends CommonServiceImpl implements OnlineDocInterfaceServiceI {




	public void addMain(DocInterfaceSubdirectoryEntity onlineDoc, List<OnlineDocInterfaceEntity> onlineDocInterfaceEntity) {

		//保存接口主信息
		this.save(onlineDoc);
		
		for (OnlineDocInterfaceEntity onlineDocInterfaceEntity2 : onlineDocInterfaceEntity) {
			//外键设置
			onlineDocInterfaceEntity2.setCode(onlineDoc.getId());
			//保存接口返回的数据说明
			this.save(onlineDocInterfaceEntity2);
		}
		
		
	
		
	}

	
	public void updateMain(DocInterfaceSubdirectoryEntity onlineDoc,List<OnlineDocInterfaceEntity> onlineDocInterfaceEntity) {
		//修改接口主信息
		this.updateEntitie(onlineDoc);
		//同时删除接口返回属性说明表
		List<OnlineDocInterfaceEntity> list=this.findByProperty(OnlineDocInterfaceEntity.class, "code", onlineDoc.getId());
	    this.deleteAllEntitie(list);
		for (OnlineDocInterfaceEntity onlineDocInterfaceEntity2 : onlineDocInterfaceEntity) {
		   	//外键设置
			onlineDocInterfaceEntity2.setCode(onlineDoc.getId());
			//保存接口返回的数据说明
			this.save(onlineDocInterfaceEntity2);
		}
	}


	@Override
	public void delMain(DocInterfaceSubdirectoryEntity onlineDoc) {
		//删除接口主表时，同时删除接口返回属性说明表
		this.deleteEntityById(DocInterfaceSubdirectoryEntity.class, onlineDoc.getId());
		List<OnlineDocInterfaceEntity> list=this.findByProperty(OnlineDocInterfaceEntity.class, "code", onlineDoc.getId());
	    this.deleteAllEntitie(list);
	
	}



}
