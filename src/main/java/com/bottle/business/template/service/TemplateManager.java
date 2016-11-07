package com.bottle.business.template.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;

@Service
public class TemplateManager extends AbstractBaseBean implements ITemplateManager {
	final private Map<String, TemplateVO> templateMap = new HashMap<String, TemplateVO>();
	private TemplateVO sample = new TemplateVO();
	@Override
	public void initialize() {
		initSample();		
	}
	
	public void initSample() {
		sample.setId(1L);
		sample.setName("Test Template");
		sample.setBarCode("6921168559176");
		sample.setPrice(0.4d);
		sample.setWeight(50d);
		sample.setOutlineData(new byte[2]);
		
		templateMap.put(sample.getBarCode(), sample);
	}
	
	public TemplateVO getTemplateByBarCode(final String barCode) {
		super.validateObject(barCode);
		
		TemplateVO vo = templateMap.get(barCode);
		
		if (null == vo) {
			vo = sample;
			//throw new NullPointerException("template is null, not existed. barCode:" + barCode);
		}
		
		return vo;
	}
}
