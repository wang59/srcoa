package org.jeecgframework.web.oct.activiti.controller;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import java.util.HashMap;
import java.util.Map;

/**
 * 请假控制器，包含保存、启动流程
 *
 * @author HenryYan
 */
@Scope("prototype")
@Controller
@RequestMapping("/leaveController")
public class LeaveController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    /**
     * 启动请假流程
     *
     * @param leave
     */
    @RequestMapping(params = "start")
    public ModelAndView startWorkflow(HttpServletRequest request) {
        try {
         
        	Map<String,Object> var1 = new HashMap<String,Object>();
        	var1.put("Creator", "admin");
        	var1.put("user", "admin");
        	System.out.println("111111111111111");
        	runtimeService.startProcessInstanceByKey("test_simple1");
        	// runtimeService.startProcessInstanceByKey("wxjleave",var1);
        	 System.out.println("2222222222222222");
        	    //Task task = taskService.createTaskQuery().singleResult();
        	    System.out.println("33333333");
        	    taskService.complete("120007");
        	    System.out.println("444444444444444444444");
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                logger.warn("没有部署流程!", e);
                System.out.println("error没有部署流程，请在[工作流]->[流程管理]页面点击<重新部署流程>");
            } else {
                logger.error("启动请假流程失败：", e);
                System.out.println("error系统内部错误！");
            }
        } catch (Exception e) {
            logger.error("启动请假流程失败：", e);
            System.out.println("系统内部错误！");
        }
        return new ModelAndView("activiti/process/todo/toDoList");
    }


    /**
     * 读取运行中的流程实例
     *
     * @return
     */
    @RequestMapping(value = "list/running")
    public ModelAndView runningList(HttpServletRequest request) {
        //ModelAndView mav = new ModelAndView("/oa/leave/running");
     /*   Page<Leave> page = new Page<Leave>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);
        workflowService.findRunningProcessInstaces(page, pageParams);
        mav.addObject("page", page);*/
        return new ModelAndView("activiti/process/todo/toDoList");
    }


}
