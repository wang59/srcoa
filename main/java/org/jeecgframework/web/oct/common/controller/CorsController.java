package org.jeecgframework.web.oct.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.web.oct.common.util.HttpClientUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.util.IMServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sun.istack.logging.Logger;

/**   
 * @Title: Controller
 * @Description: 跨域访问类
 * @author 吴思亮
 * @date 2016-09-14 17:12:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/corsController")
public class CorsController {
	private static Logger logger = Logger.getLogger(CorsController.class);
	private SystemService systemService;
	
	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	 /**
		*功能描述：BBS讨论话题
		*@author 吴思亮
		*@since 2016-09-14
	*/
	@RequestMapping(params = "acehomeBbs")
	@ResponseBody
	public Map<String,Object> acehomeBbs(HttpServletRequest request, HttpServletResponse res){
		logger.info("获取bbs论坛信息Controller");
		Map<String,Object> retMap = new HashMap<String, Object>();
		JSONObject jsonObj = JSONObject.parseObject(HttpClientUtil.doPost("http://oa.octvision.com/sys/getBbsInfo.action", null));
		if(jsonObj.isEmpty()){
			retMap.put("error", jsonObj);
		}
		else{
			retMap.put("success", jsonObj);
		}
		return retMap;
	}
	
	 /**
		*功能描述：知识文档
		*@author 吴思亮
		*@since 2016-09-18
	*/
	@RequestMapping(params = "acehomeDoc")
	@ResponseBody
	public Map<String, Object> acehomeDoc(HttpServletRequest request, HttpServletResponse response){
		logger.info("获取知识文档Controller");
		Map<String,Object> retMap = new HashMap<String,Object>();
		JSONObject jsonObj = JSONObject.parseObject(HttpClientUtil.doPost("http://oa.octvision.com/document/homePageDocument.action", null));
		if(jsonObj.isEmpty()){
			retMap.put("error", jsonObj);
		}
		else{
			retMap.put("success", jsonObj);
		}
		return retMap;
	}
	
	/**
	 * 功能描述：获取PM任务代办/代申数量
	 * @author 吴思亮
	 * @since 2016-09-19
	 */
	@RequestMapping(params = "queryPmSize")
	@ResponseBody
	public Map<String, Object> queryPmSize(HttpServletRequest request, HttpServletResponse response){
		logger.info("获取PM任务代办/代申数量");
		Map<String,Object> retMap = new HashMap<String,Object>();
		Map<String, String> params = new HashMap<String,String>();
		TSUser user=ResourceUtil.getSessionUserName();
		String oaUserId = getImUserId(user.getId(), user.getUserName());
		params.put("imUserId", oaUserId);
		String jsonObj = HttpClientUtil.doPost(IMServiceUtils.oa + "pm/queryPmSize.action",params);
		if(jsonObj.isEmpty()){
			retMap.put("error", jsonObj);
		}
		else{
			retMap.put("success", jsonObj);
		}
		return retMap;
	}
	
	private String getImUserId(String id, String userName){
		String sql = "select * from t_s_base_user where ID = ? and username = ?";
		String str = null;
		if(StringUtils.isEmpty(id) || StringUtils.isEmpty(userName)){
			logger.info("调用方法getImUserId 参数 ：\t id = "  + id + ", userName = " + userName );
		}
		List<Map<String, Object>> resMap = null;
		List<Map<String, Object>> list = systemService.findForJdbc(sql, id, userName);
		if(null == list || 0 == list.size())
			return str;
		else{
			str = list.get(0).get("nid").toString();
		}
		return str;
	}
	
/*	public static void main(String[] args){
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("status", "1");
		String jsonObj = HttpClientUtil.doPost("http://192.168.1.11:8081/OCTPM/project/projectAction.action?method=queryNewMyTask", postParams);
		System.out.println(jsonObj);;
	}*/
	
}
