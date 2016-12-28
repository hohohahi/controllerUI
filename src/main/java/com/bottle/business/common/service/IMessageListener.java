package com.bottle.business.common.service;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;

public interface IMessageListener {
	void process(final MessageVO vo);
	ICommonConstants.MessageSourceEnum getMessageType();
}
