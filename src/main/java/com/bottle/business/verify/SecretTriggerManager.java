package com.bottle.business.verify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;

@Service
public class SecretTriggerManager extends AbstractBaseBean implements ISecretTriggerManager{
	private boolean isTimerStarted = false;
	private int expiredTime = 0;
	private List<Integer> keyList = new ArrayList<Integer>();
	private Timer timer;
	private int expireTimeThreshold_InMillisecond = 0;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void initialize() {
		super.initialize();		
	}
	
	@Override
	public void push(int pos) {
		keyList.add(pos);
		if (false == isTimerStarted) {
			initExpireTimer();
		}
		else {
			expiredTime = 0;
		}
	}
	
	public void initExpireTimer() {
		expireTimeThreshold_InMillisecond = configurationManager.getConfigurationVO().getSecretTriggerExpireTime_InMillisecond();
		expiredTime = 0;
		timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {              	            	
            	if (expireTimeThreshold_InMillisecond == expiredTime) {
            		reset();
            	}
            	
            	expiredTime++;
            	verifyPassword();
            }  
        }, 0, 100); 
        
        isTimerStarted = true;
	}
	
	public void verifyPassword() {
		String curValue = "";
		for (Integer pos : keyList) {
			curValue += pos;
		}
		
		if (true == "123".equals(curValue)) {			
			MessageVO vo = new MessageVO();
			vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
			vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_VerifyDialog_);
			messageManager.push(vo);
			
			reset();
		}
		else if (true == "321".equals(curValue)) {			
			MessageVO vo = new MessageVO();
			vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
			vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_ExitDlg_);
			messageManager.push(vo);
			
			reset();
		}
	}
	
	public void reset() {
		if (null != timer) {
			timer.cancel();
			timer = null;
		}
		
		keyList.clear();
		isTimerStarted = false;
		expiredTime = 0;
	}
	
	public String getKey() {
		Calendar t1 = Calendar.getInstance();
		int dayOfWeek = t1.get(Calendar.DAY_OF_WEEK);
		int key = dayOfWeek%3;
		if (key == 0) {
			key = 3;
		}
		
		String password = "";
		if (key == 1) {
			password = "1123";
		}
		else if (key == 2) {
			password = "2231";
		}
		else if (key == 3) {
			password = "3312";
		}
		else {
			throw new RuntimeException("key is not valid. key:" + key);
		}
			 
		return password;
	}
}
