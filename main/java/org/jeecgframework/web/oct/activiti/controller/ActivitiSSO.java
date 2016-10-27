package org.jeecgframework.web.oct.activiti.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: ActivitiSSO Controller
 * @Description: activiti登陆
 * @author 汪旭军
 * @date 2016-05-23 13:45
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/activitiSSO")
public class ActivitiSSO {
	
	@RequestMapping(params = "loginActiviti")
	public ModelAndView loginActiviti(HttpServletRequest request) {
		return new ModelAndView("oct/activiti/login/loginActiviti");
	}

}
