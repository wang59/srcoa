package org.jeecgframework.web.oct.activiti.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.sun.istack.logging.Logger;


/**   
 * @Title: Util
 * @Description: HttpClient工具类
 * @author 吴思亮
 * @date 2016-09-18 9:32:40
 * @version V1.0   
 *
 */
public class HttpClientUtil {
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	public static String doPost(String url, Map<String,String> params){
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		logger.info("start HttpClient doPost method,Url：" + url + ",params:" + params);
		BufferedReader buffReader = null;
		try{
			if(null != params && 0 != params.size()){
				post.setEntity(new UrlEncodedFormEntity(map2List(params), "UTF-8"));
			}
			HttpResponse response = client.execute(post);
			HttpEntity entry = response.getEntity();
			InputStream in = entry.getContent();
			buffReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			StringBuffer strBuf = new StringBuffer();
			String strTemp = null;
			while(null != (strTemp = buffReader.readLine())){
				strBuf.append(strTemp);
				strTemp = null;
			}
			return (strBuf.toString());
		}
		catch(Exception ex){
			ex.printStackTrace();
			logger.info("exception in HttpClient doPost method" + ex.getMessage());
			return new String();
		}
		finally{
			logger.info("end HttpClient doPost method, Url: " + url + ",params: " + params);
			if(null != buffReader){
				try {
					buffReader.close();
					client.getConnectionManager().shutdown();
				} catch (IOException e) {
					logger.info("exception in BufferedReader call close method : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String doGet(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		logger.info("start HttpClient doGet method,Url：" + url );
		if(StringUtils.isEmpty(url)){
			logger.info("url is null in HttpClientUtil doGet method");
			return null;
		}
		BufferedReader buffReader = null;
		try{
			HttpResponse response = client.execute(get);
			HttpEntity entry = response.getEntity();
			InputStream in = entry.getContent();
			buffReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			StringBuffer strBuf = new StringBuffer();
			String strTemp = null;
			while(null != (strTemp = buffReader.readLine())){
				strBuf.append(strTemp);
				strTemp = null;
			}
			return (strBuf.toString());
		}
		catch(Exception ex){
			logger.info("exception in HttpClient doGet method" + ex.getMessage());
			ex.printStackTrace();
			return new String();
		}
		finally{
			logger.info("end HttpClient doGet method, Url: " + url );
			if(null != buffReader){
				try {
					buffReader.close();
					client.getConnectionManager().shutdown();
				} catch (IOException e) {
					logger.info("exception in BufferedReader call close method : " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	private static List<NameValuePair> map2List(Map<String,String> params){
		if(null == params || 0 == params.size()){
			return null;
		}
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		Set<Entry<String, String>> entrySet = params.entrySet();
		for(Entry<String,String> entry : entrySet){
			NameValuePair pair = new BasicNameValuePair(entry.getKey(),entry.getValue());
			listPair.add(pair);
			pair = null;
		}
		return listPair;
	}
	
/*	public static void main(String[] args){
		String url = "http://192.168.1.15:9996/Announcement.html";
		Map<String,String> params = new HashMap<String,String>();
		//oa=1&title=oa&url=www.baidu.com&receiveUser=402883e857408a830157408dbc400000&type=4&description=test1111
		params.put("oa", "1");
		params.put("title", "1");
		params.put("url", "www.baidu.com");
		params.put("receiveUser", "402883e857408a830157408dbc400000");
		params.put("type", "4");
		params.put("description", "test1111");
		doPost(url, params);
	}*/
}
