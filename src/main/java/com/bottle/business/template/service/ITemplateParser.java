package com.bottle.business.template.service;

import com.bottle.business.template.vo.TemplateVO;

public interface ITemplateParser {
	TemplateVO getTemplateFromByteArray(final byte [] dataArray);
	byte[] getByteArrayFromTemplate(final TemplateVO template);
	byte [] getLowBitAndHighBitFromNumber(long number) ;
}
