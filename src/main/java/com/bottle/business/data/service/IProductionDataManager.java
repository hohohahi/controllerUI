package com.bottle.business.data.service;

import java.util.List;

import com.bottle.business.common.vo.AdminVO;
import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.business.template.vo.TemplateVO;

public interface IProductionDataManager {
	void push(final ProductionDataVO vo);
	void fullfilTemplateInfo_ToProductionVO(final ProductionDataVO productionDataVO);
	void reset();
	RealtimeStasticDataVO getRealtimeStasticDataVO();
	List<ProductionDataVO> getHistoryRealtimeStasticData();
	void addLearnedTemplate(final TemplateVO templateVO);
	TemplateVO getNewestLearnedTemplate();
	boolean isLearned();
	boolean getIsSerialPortInitialized();
	void setIsSerialPortInitialized(boolean isSerialPortInitialized);
	void setAdminVO(AdminVO adminVO); 
	void setTemplateList(List<TemplateVO> templateList);
}
