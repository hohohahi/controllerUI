package com.bottle.business.data.service;

import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;

public interface IProductionDataManager {
	void push(final ProductionDataVO vo);
	void fullfilTemplateInfo_ToProductionVO(final ProductionDataVO productionDataVO);
	void reset();
	RealtimeStasticDataVO getRealtimeStasticDataVO();
}
