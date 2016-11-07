package com.bottle.ui.components.common;

import java.awt.Component;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.bottle.business.common.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;

public abstract class AbstractBasePanel extends JPanel {
	protected static final long serialVersionUID = 1L;
	
	@Autowired
	protected IMessageQueueManager messageManager;
	
	public void sendSystemInfo(final String message) {
		final MessageVO vo = new MessageVO();
		
		vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
		vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SystemInfoLog_);
		vo.setMessage(message);
		messageManager.push(vo);
	}
	
	abstract public void processChildMessage(MessageVO vo);
	abstract public void setParent(Component parent);
	
}
