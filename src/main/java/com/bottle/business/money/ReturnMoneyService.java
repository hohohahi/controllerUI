package com.bottle.business.money;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.service.IHttpClientHelper;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;

@Service
public class ReturnMoneyService extends AbstractBaseBean implements IReturnMoneyService {
	@Autowired
	private IHttpClientHelper httpHelper;
	
	@Autowired
	private IMessageQueueManager messageManager;

	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Override
	public void pay(final String phoneNumberStr) {
		final RealtimeStasticDataVO realVO = productionDataManager.getRealtimeStasticDataVO();
		super.validateObject(realVO);
		
		double amount = realVO.getTotalMoney();
		
		final String url = getReturnMoney();
    	JSONObject json = new JSONObject();
    	json.put(ICommonConstants._UI_PhoneNumber_Key_, phoneNumberStr);
    	json.put(ICommonConstants._UI_Amount_Key_, amount);
    	
    	try {
    		JSONObject rtnJSON = httpHelper.postJSON(url, json);
    		if (null != rtnJSON) {
    			productionDataManager.reset();
    			
    			final MessageVO vo = new MessageVO();
    			
    			vo.setMessageSource(MessageSourceEnum._MessageSource_PlayerPanel_);
    			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_PlayerPanel_RealProductionInfoPanel_);
    			
    			messageManager.push(vo);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}
    	 
	}
	
	public String getReturnMoney() {
		final StringBuilder buf = new StringBuilder();
		
		buf.append("http://")
		   .append(configurationManager.getConfigurationVO().getServerDomain())
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._Server_Name_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_API_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_UI_ReturnMoney);
		
		return buf.toString();
	}
	
}
