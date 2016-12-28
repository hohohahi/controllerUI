package com.bottle.business.data.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.bottle.business.data.vo.ConfigurationVO;

@Service
public class ConfigurationManager  implements IConfigurationManager {
	private ConfigurationVO configurationVO = new ConfigurationVO();
	
	public ConfigurationManager() {
		
	}
	
	@PostConstruct
	public void initialize() {
		
	}

	public ConfigurationVO getConfigurationVO() {
		return configurationVO.clone();
	}
}
