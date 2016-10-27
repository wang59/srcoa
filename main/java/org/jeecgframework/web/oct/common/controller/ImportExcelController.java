package org.jeecgframework.web.oct.common.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ListtoJson;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Title: Controller
 * @Description: 导入EXCEL数据到数据库
 * @author 汪旭军
 * @date 2016-10-20
 * @version V1.0   
 *@sample
 *   crudController.do?query&sqlid=333&p=name,sdf
 */
@Scope("prototype")
@Controller
@RequestMapping("/importExcelController")
public class ImportExcelController {
	private static final Logger logger = Logger.getLogger(ImportExcelController.class);
	/**
	 * 		打开导入EXCEL的页面
	 * @author		汪旭军
	 * @lastTime	2016-10-20
	 * 例如：importExcelController.do?gopage&className=org.jeecgframework.web.oct.oa.purchase.entity.OctPurchaseDetailEntity
	 */
	@RequestMapping(params = "gopage")
	public ModelAndView listTurn(HttpServletRequest request) {
		String className = request.getParameter("className");
		request.setAttribute("className", className);
		String saveToDb = request.getParameter("saveToDb");
		request.setAttribute("saveToDb", saveToDb);
		return new ModelAndView("oct/common/importExcel");
	}

	/**
	 * 		导入EXCEL到数据库。
	 * @author		汪旭军
	 * @lastTime	2016-10-23
	 * 例如：importExcelController.do?importExcel&saveToDb=N&className=org.jeecgframework.web.oct.oa.purchase.entity.OctPurchaseDetailEntity
	 */
	@Autowired
	private CommonService commonService;
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		String saveToDb=request.getParameter("saveToDb");//值为Y，表示保存数据到数据库。
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(1);//表格标题行数,默认0
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				//导入EXCEL数据对应的实体类
				String className=request.getParameter("className");
				Class objClass=Class.forName(className);
				//EXCEL数据导入到List中
				List listExcelData =  ExcelImportUtil.importExcel(file.getInputStream(),objClass,params);
				//数据转为json格式。
				String jsonStr=JSONHelper.toJSONString(listExcelData);
				if(StringUtil.isNotEmpty(saveToDb) && saveToDb.toUpperCase().equals("Y")){
					//保存数据到数据库
					for (Object entityData : listExcelData) {
						if(entityData!=null){
							commonService.saveOrUpdate(entityData);
						}
					}
				}
				//json格式的数据返回前台。
				request.setAttribute("jsonEXcelData", jsonStr);
			} catch (Exception e) {
				logger.error(ExceptionUtil.getExceptionMessage(e));
				return new ModelAndView("oct/common/importExcelError");
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ModelAndView("oct/common/importExcelSucess");
	}

}
