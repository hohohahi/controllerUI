package com.bottle.business.common;

import com.bottle.business.common.vo.MessageVO;

public interface IMessageQueueManager {
	void addListener(IMessageListener listener);
	void push(final MessageVO vo);
}
