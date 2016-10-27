package org.jeecgframework.web.oct.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Todo		通联短信接口工具类
 * @author		kuangy
 * @lastTime	2016-7-13
 */
public class SmsUtil {
	
	private static final Logger log = LoggerFactory.getLogger(SmsUtil.class);

	private static String POSTURL = "http://cf.51welink.com/submitdata/Service.asmx/g_Submit";//请求路径
	private static String SNAME = "dl-xujm";//账号
	private static String SPWD = "hjy521123";//密码
	private static String SCORPID = "";//企业代码
	private static String SPRDID = "1012888";//产品编码
	private static String SIGNATURE ="【柳州公园】";//公司签名
	
	private static SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);//注入service
	
	/**
	 * @Todo		发送短信
	 * @author		kuangy
	 * @lastTime	2016-7-13
	 */
	public static boolean sendSMS(String phone, String data) {
		String logContent = new StringBuffer("调用短信接口,phone:").append(phone).append(",内容:").append(data).toString();
		systemService.addLog(logContent, Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
		log.info(logContent);
		BufferedReader in = null;
		OutputStreamWriter out = null;
		StringBuffer result = new StringBuffer();
		try {
			// 发送POST请求
			URL url = new URL(POSTURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			StringBuffer postData = new StringBuffer();
			postData.append("sname=").append(SNAME).append("&spwd=").append(SPWD).append("&scorpid=").append(SCORPID).append("&sprdid=").append(SPRDID)
					.append("&sdst=").append(phone)
					.append("&smsg=").append(java.net.URLEncoder.encode(data + SIGNATURE,"utf-8"));
			conn.setRequestProperty("Content-Length", "" + postData.length());
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(postData.toString());
			out.flush();
			out.close();

			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				log.info("通联短信接口远程调用没有响应!");
//				throw new Exception("短信发送失败:远程服务器没有响应!");
				return false;
			}
			// 获取响应内容体
			String line;
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result.append(line);//.append("\n");
			}
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (in != null) {in.close();in=null;}
				if(out != null){out.close();out=null;}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		log.info(result.toString());
		
		Pattern p = Pattern.compile("_kedFile\\[([^\\]]+)\\]");
		if(StringUtil.getUniqueContext(result.toString(), "State").equals("0")){
			return true;
		}else{
			log.info("短信发送失败:\n{}", result.toString());
			systemService.addLog(result.toString(), Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
//			throw new Exception("短信发送失败:" + StringUtil.getUniqueContext(result.toString(), "MsgState"));
			return false;
		}
	}
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		/*
<?xml version="1.0" encoding="utf-8"?>
<CSubmitState xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <State>1032</State>
  <MsgID>0</MsgID>
  <MsgState>自由签名的产品101288803,签名格式不正确</MsgState>
  <Reserve>0</Reserve>
</CSubmitState>	
		
<?xml version="1.0" encoding="utf-8"?>
<CSubmitState xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
  <State>0</State>
  <MsgID>1606131122249784101</MsgID>
  <MsgState>提交成功</MsgState>
  <Reserve>0</Reserve>
</CSubmitState>
		*/	
//		sendSMS("15879041340", "hello，这是一条测试短信，请不要理会！");
		
//		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
//						"<CSubmitState xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://tempuri.org/\">"+
//						  "<State>0</State>"+
//						  "<MsgID>1606131122249784101</MsgID>"+
//						  "<MsgState>提交成功</MsgState>"+
//						  "<Reserve>0</Reserve>"+
//						"</CSubmitState>";
//		
//		System.out.println(StringUtils.getUniqueContext(result, "State").equals("0"));
//		System.out.println(StringUtils.getUniqueContext(result, "MsgID"));
//		System.out.println(StringUtils.getUniqueContext(result, "MsgState"));
//		System.out.println(StringUtils.getUniqueContext(result, "Reserve"));
	}

}
