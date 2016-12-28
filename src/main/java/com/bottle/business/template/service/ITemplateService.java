package com.bottle.business.template.service;

import java.util.List;

import com.bottle.business.template.vo.TemplateVO;

public interface ITemplateService {
	TemplateVO getTemplateByBarCode(final String barCode);
	List<TemplateVO> getTemplateList();
	List<TemplateVO> getMachineTemplateList();
	void uploadTemplate(final TemplateVO template);
	void deleteTemplateById(final long id);
	TemplateVO getDefaultTemplate();
	void addBottleTemplateMap(final long templateId);
	void removeBottleTemplateMap(final long templateId);
}
