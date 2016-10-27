package org.jeecgframework.web.oct.common;

import org.jeecgframework.core.util.ResourceUtil;

/**
 * @Todo		全局静态常量(尽量都使用String类型，防止中间传递出现类型转换)
 * @author		kuangy
 * @lastTime	2016-6-16
 */
public class CommConstant {
	
	/**
	 * 前端用户部门主键
	 */
	public static String PARK_DEPARTMENT = ResourceUtil.getConfigByName("park_department");
	
	
	/*
	 * 景点类型
	 */
	/**
	 * 主体公园
	 */
	public static final String ATTRACTIONS_HOST = "1";
	/**
	 * 其他景点
	 */
	public static final String ATTRACTIONS_OTHERS = "2";
	
	/*
	 * 用户状态属性 
	 */
	/**
	 * 未验证/临时账户
	 */
	public static final String USER_TEMP = "0";
	/**
	 * 已验证用户/正常用户
	 */
	public static final String USER_OK = "1";
	/**
	 * 冻结账户
	 */
	public static final String USER_FREEZE = "2";
	/**
	 * 注销账户
	 */
	public static final String USER_CANCEL = "3";
	
	/*
	 * 预约排队票状态
	 */
	/**
	 * 预约成功
	 */
	public static final String ORDER_OK= "1";
	/**
	 * 已使用
	 */
	public static final String ORDER_OVER= "2";
	/**
	 * 已过期
	 */
	public static final String ORDER_PAST= "3";
	
	/*
	 * UserMessage属性
	 */
	/**
	 * 客户反馈
	 */
	public static final String MESSAGE_FEEDBACK = "1";
	
	
	/**
	 * 客户已提交
	 */
	public static final String MESSAGE_OK = "1";
	
	
	
	/*
	 * 会员添加好友：0正在验证 ,1验证通过, 2拒绝
	 */
	/**
	 * 正在验证
	 */
	public static final String FRIEND_VILD_REQUEST = "0";
	/**
	 * 验证通过
	 */
	public static final String FRIEND_VILD_OK = "1";
	/**
	 * 
	 */
	public static final String FRIEND_VILD_NO = "2";


}
