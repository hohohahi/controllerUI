package com.bottle.business.ping;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.IHttpClientHelper;
import com.bottle.business.common.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;

@Service
public class PingService extends AbstractBaseBean implements IPingService {
	@Autowired
	private IHttpClientHelper httpHelper;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void initialize() {
		super.initialize();
		initExecutor();
	}
	
	public void initExecutor() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable( ) {  
            public void run() {
            	final String url = getPingURL();
            	JSONObject json = new JSONObject();
            	json.put(ICommonConstants._UI_Identifier_Key_, ICommonConstants._UI_Identifier_Key_);
            	            	
            	try {
            		JSONObject rtnJSON = httpHelper.postJSON(url, json);
            		if (null != rtnJSON) {
            			final MessageVO vo = new MessageVO();
            			//messageManager.push(vo);
            			System.out.println(rtnJSON);
            		}
				} catch (Exception e) {
					
					logErrorAndStack(e, e.getMessage());
				}
            }  
        },  
        0, 30, TimeUnit.SECONDS);
	}
	
	protected void logErrorAndStack(final Throwable e, final String errorMessage){
		this.loggerHelper.logging(logger, e, Level.ERROR, errorMessage);
	}
	
	public String getPingURL() {
		final StringBuilder buf = new StringBuilder();
		
		buf.append("http://")
		   .append(ICommonConstants._Server_IP_)
		   .append(":")
		   .append(ICommonConstants._Server_Port_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._Server_Name_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_API_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_UI_Ping);
		
		return buf.toString();
	}
	
	public static void main(String [] args) {
		PingService service = new PingService();
		System.out.println(service.getPingURL());
	}
}
