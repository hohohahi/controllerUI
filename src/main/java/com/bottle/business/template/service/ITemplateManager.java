package com.bottle.business.template.service;

import com.bottle.business.template.vo.TemplateVO;

public interface ITemplateManager {
	TemplateVO getTemplateByBarCode(final String barCode);
}
