package com.bottle.hardware.rxtx.command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.DebugCommandEnum;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.common.constants.ILanguageConstants;

@Service
public class SerialCommandSenderManager extends AbstractBaseBean implements ISerialCommandSenderManager {
	private List<IMachineCommandSender> commandSenderList = new ArrayList<IMachineCommandSender>();
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void initialize() {
		initThread();
	}
	
	public void initThread() {
		final long period = super.configurationManager.getConfigurationVO().getCheckSerialTimeoutPeriod_InMilliSecond();
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable( ) {  
            public void run() {
            	List<IMachineCommandSender> toBeRemovedList = new ArrayList<IMachineCommandSender>();
            	for (IMachineCommandSender sender : commandSenderList) {
            		if (true == sender.isTimeout()) {
            			toBeRemovedList.add(sender);
            			updateUIOnTimeout(sender);
            		}
            	}
            	
            	commandSenderList.removeAll(toBeRemovedList);
            }  
        },  
        0, period, TimeUnit.MILLISECONDS);
	}
	
	public void updateUIOnTimeout(final IMachineCommandSender sender) {
		final MessageVO buttonMessage = new MessageVO();
		final MessageVO systemInfoMessage = new MessageVO();
		systemInfoMessage.setBooleanParam3(false);
		systemInfoMessage.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
		systemInfoMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SystemInfoLog_);
		systemInfoMessage.setMessage(ILanguageConstants._SystemLogMessage_SerialCommandTimeout__);	
		
		if (true == MachineCommandEnum._MachineCommand_LearnTemplate_.equals(sender.getCommandType())) {
			buttonMessage.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_);
			buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_LearnTemplateButton_);
											
			systemInfoMessage.setMessage(ILanguageConstants._SystemLogMessage_SerialCommandTimeout__);			
		}
		else if (true == MachineCommandEnum._MachineCommand_DownloadTemplate_.equals(sender.getCommandType())) {
			buttonMessage.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_);
			buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_DownloadTemplateButton_);
											
			systemInfoMessage.setMessage(ILanguageConstants._SystemLogMessage_SerialCommandTimeout__);			
		}
		else if (true == MachineCommandEnum._MachineCommand_DeleteTemplate_.equals(sender.getCommandType())) {
			buttonMessage.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_);
			buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_DeleteTemplateButton_);
											
			systemInfoMessage.setMessage(ILanguageConstants._SystemLogMessage_SerialCommandTimeout__);			
		}
		else if (true == MachineCommandEnum._MachineCommand_QueryTemplateList_.equals(sender.getCommandType())) {
			buttonMessage.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_);
			buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_QueryTemplateListButton_);
											
			systemInfoMessage.setMessage(ILanguageConstants._SystemLogMessage_SerialCommandTimeout__);			
		}
		else if (true == MachineCommandEnum._MachineCommand_Debug_Command_.equals(sender.getCommandType())) {		
			buttonMessage.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_DebugCommandPanel_);		
			//vo.setParam1(responseVO.getErrorCode());
			
			if (DebugCommandEnum._OpenDoor_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_OpenDoorButton_);
			}
			else if (DebugCommandEnum._CloseDoor_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_CloseDoorButton_);
			}
			else if (DebugCommandEnum._PlatformDown_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_PlatformDownButton_);
			}
			else if (DebugCommandEnum._PlatformUp_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_PlatformUpButton_);
			}
			else if (DebugCommandEnum._MovePositive_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MovePositive_);
			}
			else if (DebugCommandEnum._MoveNegative_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MoveNegative_);
			}
			else if (DebugCommandEnum._StopMove_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_StopMove_);
			}
			else if (DebugCommandEnum._MoveWheel_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MoveWheel_);
			}
			else if (DebugCommandEnum._StopWheel_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_StopWheel_);
			}
			else if (DebugCommandEnum._OpenLight_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_OpenLight_);	
			}
			else if (DebugCommandEnum._CloseLight_.getAid() == sender.getAid()) {
				buttonMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_CloseLight_);				
			}
		}
		
		messageManager.push(buttonMessage);
		messageManager.push(systemInfoMessage);
	}
	@Override
	public boolean isSenderAlreadyInListenerList(final IMachineCommandSender commandSender) {
    	if (null == commandSenderList) {
    		throw new NullPointerException("commandSenderList is null.");
    	}
    	
    	return commandSenderList.contains(commandSender);
    }
	
	@Override
	public void addCommandResponseListener(IMachineCommandSender commandSender) {
		if (null == commandSender) {
			throw new NullPointerException("command sender is null.");
		}
		
		commandSenderList.add(commandSender);
	}
	
	@Override
	public IMachineCommandSender getCommandListenerAndRemoveIt(ICommonConstants.MachineCommandEnum commandType) {
		if (null == commandSenderList) {
			throw new NullPointerException("commandSenderList is null.");
		}
		
		IMachineCommandSender rtnSender = null;
		for (IMachineCommandSender commandSender : commandSenderList) {
			final ICommonConstants.MachineCommandEnum currentCommandType = commandSender.getCommandType();
			if (true == currentCommandType.equals(commandType)) {
				rtnSender = commandSender;
				break;
			}
		}
		
		if (null == rtnSender) {
			if (false == ICommonConstants.MachineCommandEnum._MachineCommand_ReturnResult_.equals(commandType)) {
				//no listener for this "Return  Result" command.  So exclude it
				throw new RuntimeException("there is no listener avaliable. commandType:" + commandType);
			}			
		}
		else {
			commandSenderList.remove(rtnSender);
		}
		
		return rtnSender;
	}
}
