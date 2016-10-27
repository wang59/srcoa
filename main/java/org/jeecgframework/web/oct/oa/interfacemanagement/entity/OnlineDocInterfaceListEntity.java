package org.jeecgframework.web.oct.oa.interfacemanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 接口返回的VO
 * @author lizh
 *
 */
import java.util.List;

@SuppressWarnings("serial")
public class OnlineDocInterfaceListEntity implements Serializable {

	
	private List<OnlineDocInterfaceEntity> onlineDocInterfaceEntityList=new ArrayList<OnlineDocInterfaceEntity>();

	public List<OnlineDocInterfaceEntity> getOnlineDocInterfaceEntityList() {
		return onlineDocInterfaceEntityList;
	}

	public void setOnlineDocInterfaceEntityList(List<OnlineDocInterfaceEntity> onlineDocInterfaceEntityList) {
		this.onlineDocInterfaceEntityList = onlineDocInterfaceEntityList;
	}

	
}
