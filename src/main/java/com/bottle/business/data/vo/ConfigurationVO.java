package com.bottle.business.data.vo;

import com.bottle.common.constants.ICommonConstants;

import gnu.io.SerialPort;

public class ConfigurationVO implements Cloneable {
	//identifier
	private String identifier = "40ec2351-af21-4d1c-9a92-85629f43a0bc";
	
	//serial port
	private long serialCommandTimeout_InMilliSecond = 0L;
	private long checkSerialTimeoutPeriod_InMilliSecond = 0L;
	private String serialPortName = "";
	private int serialBaudRate = 0;
	private int serialParity = 0;
	private int serialDataBits = 0;
	private int serialStopBit = 0;
	
	//server
	private String serverIP = "";
	private long serverPort = 0L;
	private String serverDomain = "";
	private int serverSocketPort = 0;
	private String api_UI_UploadTemplate = "";
	private String api_UI_TemplateList = "";
	private String api_UI_DeleteTemplate = "";
	private String api_UI_BottleTemplateList = "";
	private String api_UI_AddBottleTemplate = "";
	private String api_UI_RemoveBottleTemplate = "";
	
	private int hideDelayInSecond_SystemInfoMessage = 0;
	
	//ping
	private boolean isNetworkPingOn = false;
	private boolean isSerialPortPingOn = false;
	private long pingInteval_InSecond = 0L;
	
	//ui
	private int secretTriggerExpireTime_InMillisecond = 0;
	private int exitWarningTime_InSeconds = 0;
	private int playerPanelIdelTime_InSeconds = 0;
	private int playerPanelErrorBottleIdelTime_InSeconds = 0;
	public ConfigurationVO() {
		identifier = ICommonConstants._Bottle_Identifier_;
		serialCommandTimeout_InMilliSecond = 1000*30;
		checkSerialTimeoutPeriod_InMilliSecond = 1000;
		serialPortName = ICommonConstants._SerialPort_Name_;
		serialBaudRate = ICommonConstants._SerialPort_BauDRate_;
		serialDataBits = SerialPort.DATABITS_8;
		serialParity = SerialPort.PARITY_NONE;
		serialStopBit = SerialPort.STOPBITS_1;
		serverIP = ICommonConstants._Server_IP_;
		serverPort = ICommonConstants._Server_Port_;
		serverDomain = ICommonConstants._Server_Domain_;
		serverSocketPort = ICommonConstants._Server_Socket_Port;
		api_UI_UploadTemplate = ICommonConstants._URL_UI_UploadTemplate;
		api_UI_TemplateList = ICommonConstants._URL_UI_TemplateList;
		api_UI_DeleteTemplate = ICommonConstants._URL_UI_DeleteTemplate;
		api_UI_BottleTemplateList = ICommonConstants._URL_UI_BottleTemplateList;
		api_UI_AddBottleTemplate = ICommonConstants._URL_UI_AddBottleTemplateMap;
		api_UI_RemoveBottleTemplate = ICommonConstants._URL_UI_RemoveBottleTemplateMap;
		
		hideDelayInSecond_SystemInfoMessage = ICommonConstants._hideDelayInSecond_SystemInfoMessage_;
		isNetworkPingOn = false;
		isSerialPortPingOn = true;
		pingInteval_InSecond = ICommonConstants._PingService_Period_;
		secretTriggerExpireTime_InMillisecond = ICommonConstants._SecretTriggerExpireTime_InMillisecond;
		
		exitWarningTime_InSeconds = ICommonConstants._exitWarningTime_InSeconds_;
		playerPanelIdelTime_InSeconds = ICommonConstants._playerPanelIdelTime_InSeconds_;
		playerPanelErrorBottleIdelTime_InSeconds = ICommonConstants._playerPanelErrorBottleIdelTime_InSeconds_;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSerialPortName() {
		return serialPortName;
	}
	public void setSerialPortName(String serialPortName) {
		this.serialPortName = serialPortName;
	}
	public int getSerialBaudRate() {
		return serialBaudRate;
	}
	public void setSerialBaudRate(int serialBaudRate) {
		this.serialBaudRate = serialBaudRate;
	}
	public long getSerialCommandTimeout_InMilliSecond() {
		return serialCommandTimeout_InMilliSecond;
	}

	public void setSerialCommandTimeout_InMilliSecond(long serialCommandTimeout_InMilliSecond) {
		this.serialCommandTimeout_InMilliSecond = serialCommandTimeout_InMilliSecond;
	}
	
	public long getCheckSerialTimeoutPeriod_InMilliSecond() {
		return checkSerialTimeoutPeriod_InMilliSecond;
	}
	public void setCheckSerialTimeoutPeriod_InMilliSecond(long checkSerialTimeoutPeriod_InMilliSecond) {
		this.checkSerialTimeoutPeriod_InMilliSecond = checkSerialTimeoutPeriod_InMilliSecond;
	}
	
	public String getApi_UI_UploadTemplate() {
		return api_UI_UploadTemplate;
	}

	public void setApi_UI_UploadTemplate(String api_UI_UploadTemplate) {
		this.api_UI_UploadTemplate = api_UI_UploadTemplate;
	}

	public String getApi_UI_TemplateList() {
		return api_UI_TemplateList;
	}

	public void setApi_UI_TemplateList(String api_UI_TemplateList) {
		this.api_UI_TemplateList = api_UI_TemplateList;
	}

	public String getApi_UI_BottleTemplateList() {
		return api_UI_BottleTemplateList;
	}

	public void setApi_UI_BottleTemplateList(String api_UI_BottleTemplateList) {
		this.api_UI_BottleTemplateList = api_UI_BottleTemplateList;
	}

	public String getApi_UI_AddBottleTemplate() {
		return api_UI_AddBottleTemplate;
	}

	public void setApi_UI_AddBottleTemplate(String api_UI_AddBottleTemplate) {
		this.api_UI_AddBottleTemplate = api_UI_AddBottleTemplate;
	}

	public String getApi_UI_RemoveBottleTemplate() {
		return api_UI_RemoveBottleTemplate;
	}

	public void setApi_UI_RemoveBottleTemplate(String api_UI_RemoveBottleTemplate) {
		this.api_UI_RemoveBottleTemplate = api_UI_RemoveBottleTemplate;
	}

	public String getApi_UI_DeleteTemplate() {
		return api_UI_DeleteTemplate;
	}

	public void setApi_UI_DeleteTemplate(String api_UI_DeleteTemplate) {
		this.api_UI_DeleteTemplate = api_UI_DeleteTemplate;
	}

	public int getHideDelayInSecond_SystemInfoMessage() {
		return hideDelayInSecond_SystemInfoMessage;
	}

	public void setHideDelayInSecond_SystemInfoMessage(int hideDelayInSecond_SystemInfoMessage) {
		this.hideDelayInSecond_SystemInfoMessage = hideDelayInSecond_SystemInfoMessage;
	}
	
	public int getServerSocketPort() {
		return serverSocketPort;
	}

	public void setServerSocketPort(int serverSocketPort) {
		this.serverSocketPort = serverSocketPort;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public long getServerPort() {
		return serverPort;
	}
	
	public String getServerDomain() {
		return serverDomain;
	}

	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
	}

	public void setServerPort(long serverPort) {
		this.serverPort = serverPort;
	}

	
	public boolean getIsNetworkPingOn() {
		return isNetworkPingOn;
	}

	public void setIsNetworkPingOn(boolean isNetworkPingOn) {
		this.isNetworkPingOn = isNetworkPingOn;
	}

	public boolean getIsSerialPortPingOn() {
		return isSerialPortPingOn;
	}

	public void setIsSerialPortPingOn(boolean isSerialPortPingOn) {
		this.isSerialPortPingOn = isSerialPortPingOn;
	}

	public long getPingInteval_InSecond() {
		return pingInteval_InSecond;
	}

	public void setPingInteval_InSecond(long pingInteval_InSecond) {
		this.pingInteval_InSecond = pingInteval_InSecond;
	}

	public int getSerialParity() {
		return serialParity;
	}

	public void setSerialParity(int serialParity) {
		this.serialParity = serialParity;
	}

	public int getSerialDataBits() {
		return serialDataBits;
	}

	public void setSerialDataBits(int serialDataBits) {
		this.serialDataBits = serialDataBits;
	}

	public int getSerialStopBits() {
		return serialStopBit;
	}

	public void setSerialStopBit(int serialStopBit) {
		this.serialStopBit = serialStopBit;
	}

	public int getSecretTriggerExpireTime_InMillisecond() {
		return secretTriggerExpireTime_InMillisecond;
	}

	public void setSecretTriggerExpireTime_InMillisecond(int secretTriggerExpireTime_InMillisecond) {
		this.secretTriggerExpireTime_InMillisecond = secretTriggerExpireTime_InMillisecond;
	}
	
	public int getExitWarningTime_InSeconds() {
		return exitWarningTime_InSeconds;
	}

	public void setExitWarningTime_InSeconds(int exitWarningTime_InSeconds) {
		this.exitWarningTime_InSeconds = exitWarningTime_InSeconds;
	}

	public int getPlayerPanelIdelTime_InSeconds() {
		return playerPanelIdelTime_InSeconds;
	}

	public void setPlayerPanelIdelTime_InSeconds(int playerPanelIdelTime_InSeconds) {
		this.playerPanelIdelTime_InSeconds = playerPanelIdelTime_InSeconds;
	}

	public int getPlayerPanelErrorBottleIdelTime_InSeconds() {
		return playerPanelErrorBottleIdelTime_InSeconds;
	}

	public void setPlayerPanelErrorBottleIdelTime_InSeconds(int playerPanelErrorBottleIdelTime_InSeconds) {
		this.playerPanelErrorBottleIdelTime_InSeconds = playerPanelErrorBottleIdelTime_InSeconds;
	}

	@Override
	public ConfigurationVO clone() {
		ConfigurationVO cloneObject = new ConfigurationVO(); 
		try {
			Object obj = super.clone();
			if (false == (obj instanceof ConfigurationVO)) {
				throw new RuntimeException("obj is not instantce of ConfigurationVO.");
			}
			
			cloneObject = (ConfigurationVO)obj;
		} catch (Throwable  e) {
			e.printStackTrace();
		}
		return cloneObject;
	}
}
