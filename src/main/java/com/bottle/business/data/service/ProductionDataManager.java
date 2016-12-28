package com.bottle.business.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.AdminVO;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.business.template.service.ITemplateService;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;

@Service
public class ProductionDataManager extends AbstractBaseBean implements IProductionDataManager {
	private final List<ProductionDataVO> dataList = new ArrayList<ProductionDataVO>();
	private RealtimeStasticDataVO realtimeStasticDataVO = new RealtimeStasticDataVO();
	private List<TemplateVO> learnedTemplateList = new ArrayList<TemplateVO>();
	private boolean isSerialPortInitialized = false;
	private AdminVO adminVO;
	private List<TemplateVO> templateList = new ArrayList<TemplateVO>();
	
	@Autowired
	private ITemplateService templateManager;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void initialize() {
		super.initialize();
		//templateList = templateManager.getTemplateList();
	}
	
	@Override
	public void push(ProductionDataVO vo) {
		super.validateObject(vo);
		
		dataList.add(vo);
		recalculateRealtimeStatisticData();
	}
	
	public void reset() {
		realtimeStasticDataVO.reset();
		dataList.clear();
	}
	
	public List<TemplateVO> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<TemplateVO> templateList) {
		this.templateList = templateList;
	}

	public TemplateVO getTemplateByBarCode(final String barCode) {
		String subBarCode = barCode.substring(1, barCode.length());
		System.out.println("getTemplateByBarCode:" + subBarCode);
		TemplateVO rtnTempate = null;
		for (final TemplateVO template : templateList) {
			System.out.println("getTemplateByBarCode: template" + template.getBarCode());
			super.validateObject(template);
			
			if (true == template.getBarCode().equals(subBarCode)) {
				rtnTempate = template;
				continue;
			}
		}
		
		if (null == rtnTempate) {
			throw new RuntimeException("rtnTempate can not be found. barCode:" + barCode);
		}
		
		return rtnTempate;
	}
	
	public void fullfilTemplateInfo_ToProductionVO(final ProductionDataVO productionDataVO) {
		super.validateObject(productionDataVO);
		final String barCode = productionDataVO.getBarCode();
		TemplateVO templateVO;
		try {
			templateVO = getTemplateByBarCode(barCode);
		} catch (Exception e) {
			templateVO = templateManager.getDefaultTemplate();
			
			final MessageVO systemInfoMessage = new MessageVO();								
			systemInfoMessage.setBooleanParam3(false);
			systemInfoMessage.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
			systemInfoMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SystemInfoLog_);
			systemInfoMessage.setMessage("\u4E3B\u63A7\u4E0A\u4F20\u7684\u6A21\u7248\u4E0D\u5B58\u5728. \u4E8C\u7EF4\u7801\u662F" + barCode);
			messageManager.push(systemInfoMessage);
		}
		
		super.validateObject(templateVO);
		
		productionDataVO.setBarCode(templateVO.getBarCode());
		productionDataVO.setTemplateName(templateVO.getName());
		productionDataVO.setPrice(templateVO.getPrice());
		productionDataVO.setWeight(templateVO.getWeight());
	}

	public RealtimeStasticDataVO getRealtimeStasticDataVO() {
		return realtimeStasticDataVO;
	}
	
	public void recalculateRealtimeStatisticData() {
		super.validateObject(dataList);
		realtimeStasticDataVO.reset();
		
		for (final ProductionDataVO element : dataList) {
			super.validateObject(element);
			
			long totalValidNum = realtimeStasticDataVO.getTotalValidNum();
			long totalInvalidNum = realtimeStasticDataVO.getTotalInvalidNum();
			long totalNum = realtimeStasticDataVO.getTotalNum();
			double totalMoney = realtimeStasticDataVO.getTotalMoney();
			if (true == element.getIsSuccessful()) {
				totalNum++;
				totalValidNum++;
				totalMoney += element.getPrice();
				totalMoney = super.dataTypeHelper.roundD_OneBit(totalMoney);
			}
			else {
				totalNum++;
				totalInvalidNum++;
			}
			
			realtimeStasticDataVO.setTotalNum(totalNum);
			realtimeStasticDataVO.setTotalValidNum(totalValidNum);
			realtimeStasticDataVO.setTotalInvalidNum(totalInvalidNum);
			realtimeStasticDataVO.setTotalMoney(totalMoney);
		}
	}

	
	public boolean getIsSerialPortInitialized() {
		return isSerialPortInitialized;
	}

	public void setIsSerialPortInitialized(boolean isSerialPortInitialized) {
		this.isSerialPortInitialized = isSerialPortInitialized;
	}

	@Override
	public void addLearnedTemplate(TemplateVO templateVO) {
		super.validateObject(templateVO);
		learnedTemplateList.add(templateVO);
	}

	@Override
	public TemplateVO getNewestLearnedTemplate() {
		super.validateObject(learnedTemplateList);
		if (0 == learnedTemplateList.size()) {
			throw new RuntimeException("learnedTemplateList is empty.");
		}
		
		return learnedTemplateList.get(learnedTemplateList.size()-1);
	}

	@Override
	public boolean isLearned() {
		return !learnedTemplateList.isEmpty();
	}

	public AdminVO getAdminVO() {
		return adminVO;
	}

	public void setAdminVO(AdminVO adminVO) {
		this.adminVO = adminVO;
	}

	@Override
	public List<ProductionDataVO> getHistoryRealtimeStasticData() {
		return dataList;
	}
}
