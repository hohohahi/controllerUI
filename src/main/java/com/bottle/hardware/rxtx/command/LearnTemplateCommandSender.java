package com.bottle.hardware.rxtx.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.AbstractBaseSerialManager;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class LearnTemplateCommandSender extends AbstractBaseSerialManager {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void onReceive(final RxTxResponseVO responseVO) {
		if (null == responseVO) {
			throw new NullPointerException("responseVO is null.");
		}
		
		final MessageVO vo = new MessageVO();
		
		vo.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_);
		vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_LearnTemplateButton_);
		messageManager.push(vo);
		
		if (false == responseVO.getIsSuccess()) {
			final String message = "error code:" + responseVO.getErrorCode() + "--message:" + responseVO.getErrorMessage(); 
			final MessageVO systemInfoMessage = new MessageVO();
			
			systemInfoMessage.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
			systemInfoMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SystemInfoLog_);
			systemInfoMessage.setMessage(message);
			messageManager.push(systemInfoMessage);
		}
	}

	@Override
	public MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_LearnTemplate_;
	}
}
