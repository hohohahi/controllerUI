package com.bottle.business.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.business.template.service.ITemplateManager;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;

@Service
public class ProductionDataManager extends AbstractBaseBean implements IProductionDataManager {
	private final List<ProductionDataVO> dataList = new ArrayList<ProductionDataVO>();
	private RealtimeStasticDataVO realtimeStasticDataVO = new RealtimeStasticDataVO();
	
	@Autowired
	private ITemplateManager templateManager;
	
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
	
	public void fullfilTemplateInfo_ToProductionVO(final ProductionDataVO productionDataVO) {
		super.validateObject(productionDataVO);
		final String barCode = productionDataVO.getBarCode();
		final TemplateVO templateVO = templateManager.getTemplateByBarCode(barCode);
		super.validateObject(templateVO);
		
		productionDataVO.setTemplateName(templateVO.getName());
		productionDataVO.setPrice(templateVO.getPrice());
	}

	public RealtimeStasticDataVO getRealtimeStasticDataVO() {
		return realtimeStasticDataVO;
	}
	
	public void recalculateRealtimeStatisticData() {
		super.validateObject(dataList);
		realtimeStasticDataVO.reset();
		
		for (final ProductionDataVO element : dataList) {
			super.validateObject(element);
			
			long totalValidNum = realtimeStasticDataVO.getTotalInvalidNum();
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
	
	
}
