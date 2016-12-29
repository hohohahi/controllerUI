package com.bottle.business.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.service.IHttpClientHelper;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.common.vo.RestResultVO;

@Service
public class TemplateService extends AbstractBaseBean implements ITemplateService {
	final private Map<String, TemplateVO> templateMap = new HashMap<String, TemplateVO>();
	private TemplateVO defaultTemplate = new TemplateVO();
	
	@Autowired
	private IHttpClientHelper httpHelper;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Autowired
	IProductionDataManager productionManager;
	
	@Override
	public void initialize() {
		initSample();		
	}
	
	public void initSample() {
		defaultTemplate.setId(1L);
		defaultTemplate.setName("Default Template");
		defaultTemplate.setBarCode("abcdefg123456");
		defaultTemplate.setPrice(1.77d);
		defaultTemplate.setWeight(50);
		
		
		templateMap.put(defaultTemplate.getBarCode(), defaultTemplate);
	}
	
	public TemplateVO getTemplateByBarCode(final String barCode) {
		super.validateObject(barCode);
		
		TemplateVO vo = templateMap.get(barCode);
		System.out.println("getTemplateByBarCode: barCode:" + barCode);
		if (null == vo) {
			vo = defaultTemplate;
			//throw new NullPointerException("template is null, not existed. barCode:" + barCode);
		}
		
		return vo;
	}
	
	public List<TemplateVO> getTemplateList() {
		final List<TemplateVO> templateList = new ArrayList<TemplateVO>();

		try {
			JSONObject rtnJSON = httpHelper.getJSON(getURL(super.configurationManager.getConfigurationVO().getApi_UI_TemplateList()));
			if (null != rtnJSON) {
				JSONArray array = (JSONArray)rtnJSON.get("data");
				for (Object obj : array) {
					JSONObject json = (JSONObject)obj;
					TemplateVO templateVO = json.toJavaObject(TemplateVO.class);
					templateList.add(templateVO);
				}
				
				String message = "Get template list successful. size:" + templateList.size(); 				
				
				sendSystemInfoMessage(message, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}

		productionManager.setTemplateList(templateList);
		return templateList;
	}
	
	public String getURL(final String servicePath) {
		final StringBuilder buf = new StringBuilder();
		
		buf.append("http://")
		   .append(configurationManager.getConfigurationVO().getServerDomain())		   
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._Server_Name_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_API_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(servicePath);
		
		return buf.toString();
	}

	@Override
	public void uploadTemplate(TemplateVO template) {
		try {
			JSONObject rtnJSON = httpHelper.postJSON(getURL(super.configurationManager.getConfigurationVO().getApi_UI_UploadTemplate()), (JSONObject)JSONObject.toJSON(template));
			if (null != rtnJSON) {
				RestResultVO result = rtnJSON.toJavaObject(RestResultVO.class);
				
				String message = "";
				boolean isSuccessful = false;
				if (0 == result.getErrorCode()) {
					isSuccessful = true;
					message = "Upload template successfully. barCode:" + template.getBarCode(); 
				}
				else {
					isSuccessful = false;
					message = result.getErrorMessage() + "--" + result.getExtraMessage(); 
				}								
				
				sendSystemInfoMessage(message, isSuccessful);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}
	}

	@Override
	public void deleteTemplateById(long id) {
		try {
			final TemplateVO template = new TemplateVO();
			template.setId(id);
			JSONObject rtnJSON = httpHelper.postJSON(getURL(super.configurationManager.getConfigurationVO().getApi_UI_DeleteTemplate()), (JSONObject)JSONObject.toJSON(template));
			if (null != rtnJSON) {
				RestResultVO result = rtnJSON.toJavaObject(RestResultVO.class);
				
				String message = "";
				boolean isSuccessful = false;
				if (0 == result.getErrorCode()) {
					isSuccessful = true;
					message = "Delete template successfully. id:" + id; 
				}
				else {
					isSuccessful = false;
					message = "Failed to delete template. id:" + id + "--message:" + result.getErrorMessage(); 
				}								
				
				sendSystemInfoMessage(message, isSuccessful);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}
	}
	/*
	{
		"identifier": "40ec2351-af21-4d1c-9a92-85629f43a0bc",
		"templateId":"4"
		}
		*/
	@Override
	public void addBottleTemplateMap(final long templateId) {
		try {
			JSONObject jsonParameter = new JSONObject();
			jsonParameter.put("identifier", ICommonConstants._identifier);
			jsonParameter.put("templateId", templateId);
			JSONObject rtnJSON = httpHelper.postJSON(getURL(super.configurationManager.getConfigurationVO().getApi_UI_AddBottleTemplate()), jsonParameter);
			if (null != rtnJSON) {
				RestResultVO result = rtnJSON.toJavaObject(RestResultVO.class);
				
				String message = "";
				boolean isSuccessful = false;
				if (0 == result.getErrorCode()) {
					isSuccessful = true;
					message = "add bottle-template successfully. templateId:" + templateId; 
				}
				else {
					isSuccessful = false;
					message = "Failed to add bottle-template. templateId:" + templateId + "--message:" + result.getErrorMessage(); 
				}								
				
				sendSystemInfoMessage(message, isSuccessful);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}
	}
	
	public void sendSystemInfoMessage(final String message, final boolean isSuccessful) {
		final MessageVO systemInfoMessage = new MessageVO();								
		systemInfoMessage.setBooleanParam3(isSuccessful);
		systemInfoMessage.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
		systemInfoMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SystemInfoLog_);
		systemInfoMessage.setMessage(message);
		messageManager.push(systemInfoMessage);
	}

	@Override
	public TemplateVO getDefaultTemplate() {
		return defaultTemplate;
	}

	@Override
	public List<TemplateVO> getMachineTemplateList() {
		final List<TemplateVO> templateList = new ArrayList<TemplateVO>();

		try {
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("identifier", ICommonConstants._identifier);
			JSONObject rtnJSON = httpHelper.postJSON(getURL(super.configurationManager.getConfigurationVO().getApi_UI_BottleTemplateList()), jsonParam);
			if (null != rtnJSON) {
				JSONArray array = (JSONArray)rtnJSON.get("data");
				for (Object obj : array) {
					JSONObject json = (JSONObject)obj;
					TemplateVO templateVO = json.toJavaObject(TemplateVO.class);
					templateList.add(templateVO);
				}
				
				String message = "Get machine template list successful. size:" + templateList.size(); 				
				
				sendSystemInfoMessage(message, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}

		productionManager.setTemplateList(templateList);
		return templateList;
	}
	
	@Override
	public void removeBottleTemplateMap(final long templateId) {
		try {
			JSONObject jsonParameter = new JSONObject();
			jsonParameter.put("identifier", ICommonConstants._identifier);
			jsonParameter.put("templateId", templateId);
			JSONObject rtnJSON = httpHelper.postJSON(getURL(super.configurationManager.getConfigurationVO().getApi_UI_RemoveBottleTemplate()), jsonParameter);
			if (null != rtnJSON) {
				RestResultVO result = rtnJSON.toJavaObject(RestResultVO.class);
				
				String message = "";
				boolean isSuccessful = false;
				if (0 == result.getErrorCode()) {
					isSuccessful = true;
					message = "remove bottle-template successfully. templateId:" + templateId; 
				}
				else {
					isSuccessful = false;
					message = "Failed to remove bottle-template. templateId:" + templateId + "--message:" + result.getErrorMessage(); 
				}								
				
				sendSystemInfoMessage(message, isSuccessful);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}
	}
}
