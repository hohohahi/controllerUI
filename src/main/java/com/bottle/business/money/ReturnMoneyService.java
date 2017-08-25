package com.bottle.business.money;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.service.IHttpClientHelper;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.CheckRecordVO;
import com.bottle.business.data.vo.ProductionDataVO;
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
	public void pay(final String playerPhoneNumberStr, final ICommonConstants.CashModeEnum cachModeEnum) {
		final RealtimeStasticDataVO realVO = productionDataManager.getRealtimeStasticDataVO();
		super.validateObject(realVO);
		
		double amount = realVO.getTotalMoney();
		
		final String url = getReturnMoney();
    	JSONObject json = new JSONObject();
    	json.put(ICommonConstants._UI_PhoneNumber_Key_, playerPhoneNumberStr);
    	json.put(ICommonConstants._UI_Amount_Key_, amount);
    	json.put(ICommonConstants._UI_MachineIdentifier_Key_, ICommonConstants._identifier);
    	json.put(ICommonConstants._UI_CashMode_Key_, cachModeEnum.getId());
    	json.put(ICommonConstants._UI_CheckResultList_Key_, getCheckRecordVOListFromRealtimeData(realVO));
    	
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
	
	public List<CheckRecordVO> getCheckRecordVOListFromRealtimeData(final RealtimeStasticDataVO realVO) {
		List<CheckRecordVO> rtnList = new ArrayList<CheckRecordVO>();
		
		final List<ProductionDataVO> productionVOList = realVO.getDataList();
		
		final int size = productionVOList.size();
		for (int index=0; index<size; index++) {
			final ProductionDataVO subVO = productionVOList.get(index);
			final CheckRecordVO recordVO = new CheckRecordVO();
			recordVO.setOrderIndex(index+1);
			recordVO.setPrice(subVO.getPrice());
			recordVO.setResultId(0L);
			recordVO.setTemplateId(subVO.getTemplateId());
			recordVO.setTemplateName(subVO.getTemplateName());
			
			rtnList.add(recordVO);
		}
		
		return rtnList;
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
	
	
	public static void main(String [] args) {
		final RealtimeStasticDataVO realVO = new RealtimeStasticDataVO();
		final List<ProductionDataVO> dataList = new ArrayList<ProductionDataVO>();
		final ProductionDataVO element1 = new ProductionDataVO();
		element1.setBarCode("abcdefghijk123456");
		element1.setErrorCode(0L);
		element1.setPrice(1.1d);
		element1.setIsSuccessful(true);
		element1.setTemplateId(55L);
		element1.setTemplateName("formal 1");
		dataList.add(element1);
		
		final ProductionDataVO element2 = new ProductionDataVO();
		element2.setBarCode("123456abcdefghijk");
		element2.setErrorCode(0L);
		element2.setPrice(2.1d);
		element2.setIsSuccessful(true);
		element2.setTemplateId(66L);
		element2.setTemplateName("formal 2");
		dataList.add(element2);
		
		realVO.setDataList(dataList);
		
		JSONObject json = new JSONObject();
    	json.put(ICommonConstants._UI_PhoneNumber_Key_, "18975811415");
    	json.put(ICommonConstants._UI_Amount_Key_, 1.2d);
    	
    	final ReturnMoneyService service = new ReturnMoneyService();
    	json.put(ICommonConstants._UI_CheckResultList_Key_, service.getCheckRecordVOListFromRealtimeData(realVO));
    	System.out.println(json);
	}
}
