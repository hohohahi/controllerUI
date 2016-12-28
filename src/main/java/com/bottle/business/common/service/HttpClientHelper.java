package com.bottle.business.common.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.common.AbstractBaseBean;

@Service
public class HttpClientHelper extends AbstractBaseBean implements IHttpClientHelper {
	@Override
	public JSONObject postJSON(String url, final JSONObject json) {
		JSONObject rtnJSON = new JSONObject();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);

		try {  
			StringEntity stringEntity = new StringEntity(json.toString(),"utf-8"); 
			stringEntity.setContentEncoding("UTF-8");    
			stringEntity.setContentType("application/json");             		              
            
			httppost.setEntity(stringEntity);  
			  
			CloseableHttpResponse response = httpclient.execute(httppost);  
			try {  
				HttpEntity entity = response.getEntity();  
				if (entity != null) {  
					String retSrc = EntityUtils.toString(entity);
                	rtnJSON = (JSONObject) JSONObject.parse(retSrc);
				}  
			} finally {  
				response.close();  
			}  
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		} finally {  
			try {  
				httpclient.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		} 
		
		return rtnJSON;
	}

	@Override
	public JSONObject getJSON(String url) {
		JSONObject rtnJSON = new JSONObject();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);

		try {    			  
			CloseableHttpResponse response = httpclient.execute(httpget);  
			try {  
				HttpEntity entity = response.getEntity();  				
                if (entity != null) {  
                	String retSrc = EntityUtils.toString(entity);
                	rtnJSON = (JSONObject) JSONObject.parse(retSrc);
                }
			} finally {  
				response.close();  
			}  
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		} finally {  
			try {  
				httpclient.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		} 
		
		return rtnJSON;
	}
}
