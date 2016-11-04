package com.bottle.business.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
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
					System.out.println("--------------------------------------");  
					System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
					System.out.println("--------------------------------------");  
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
