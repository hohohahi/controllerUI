package com.bottle.business.ping;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.service.IHttpClientHelper;
import com.bottle.business.common.service.IMessageListener;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.ISerialCommConnector;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;

@Service
public class PingService extends AbstractBaseBean implements IPingService, IMessageListener {
	private boolean machineStatusFlag = false;
	
	@Autowired
	private IHttpClientHelper httpHelper;
	
	@Autowired
	private IMessageQueueManager messageManager;	
	
	@Autowired
	private ICommandSelector commandSelector;
	
	@Autowired
	private ISerialCommConnector serialCommConnector;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Override
	public void initialize() {
		super.initialize();
		initExecutor();
		messageManager.addListener(this);
	}
	
	public void initExecutor() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		final long inteval = configurationManager.getConfigurationVO().getPingInteval_InSecond();
		scheduler.scheduleAtFixedRate(new Runnable( ) {  
            public void run() {
            	//pingServer();
            	//pingMachine();
            }  
        },  
        0, inteval, TimeUnit.SECONDS);
	}
	
	public void updateMachineStatusUI() {
		final MessageVO vo = new MessageVO();		
		vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
		vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_MachineStatus_);
		
		long machineStatus = ICommonConstants._ConnectionStatus_Online_;
		if (true == machineStatusFlag) {
			machineStatus = ICommonConstants._ConnectionStatus_Online_;						
		}
		else {
			machineStatus = ICommonConstants._ConnectionStatus_Offline_;
		}
		
		vo.setParam1(machineStatus);	
		messageManager.push(vo);
	}
	
	public void pingMachine() {
		try {
			//1. update ui
			updateMachineStatusUI();
			
			//2. set flag to false
			machineStatusFlag = false;
			
			//3. ping machine
			if (true == serialCommConnector.getIsSerialPortReady()) {
				final IMachineCommandSender sender = commandSelector.select(MachineCommandEnum._MachineCommand_Ping_);
				sender.send();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pingServer() {
		final String url = getPingURL();
    	JSONObject json = new JSONObject();
    	json.put(ICommonConstants._UI_Identifier_Key_, ICommonConstants._UI_Identifier_Key_);
    	
    	boolean isConnected = false;
    	try {
    		JSONObject rtnJSON = httpHelper.postJSON(url, json);
    		if (null != rtnJSON) {
    			isConnected = true;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			logErrorAndStack(e, e.getMessage());
		}
    	
    	final MessageVO vo = new MessageVO();
    	if (true == isConnected) {            		
			vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_ServerStatus_);
			vo.setParam1(ICommonConstants._ConnectionStatus_Online_);   			
    	}
    	else {
    		vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_ServerStatus_);
			vo.setParam1(ICommonConstants._ConnectionStatus_Offline_);
    	}
    	
    	messageManager.push(vo);
	}
	
	protected void logErrorAndStack(final Throwable e, final String errorMessage){
		this.loggerHelper.logging(logger, e, Level.ERROR, errorMessage);
	}
	
	public String getPingURL() {
		final StringBuilder buf = new StringBuilder();
		
		buf.append("http://")
		   .append(configurationManager.getConfigurationVO().getIPAndPortAsAdress())
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._Server_Name_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_API_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_UI_Ping);
		
		return buf.toString();
	}
	
	public static void main(String [] args) {
		PingService service = new PingService();
		System.out.println(service.getPingURL());
	}

	@Override
	public void process(MessageVO vo) {
		if (null == vo) {
			throw new NullPointerException("vo is null.");
		}
		
		final MessageSourceEnum messageSource = vo.getMessageSource();
		if (false == messageSource.equals(getMessageType())) {
			return;
		}
		
		machineStatusFlag = true;
		
		updateMachineStatusUI();
	}

	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_PingService_;
	}
}
