package org.jeecgframework.web.oct.oa.oldoa.service.impl;

import org.jeecgframework.web.oct.oa.oldoa.service.interf.OldUserService;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.HttpClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author  fany
 *
 */
@Service("oldUserService")
@Transactional
public class OldUserServiceImpl  implements OldUserService {

	@Override
	public String updatePSD(String userName, String psd) {
		String url="http://oa.octvision.com/local/modifyUserPwdnew.action";
		Map<String,String> map=new HashMap();
		 map.put("userInfo.password",psd );
		 map.put("userName",userName );
		String value=HttpClientUtil.post(url, map);
		return value;
	}
	
}
