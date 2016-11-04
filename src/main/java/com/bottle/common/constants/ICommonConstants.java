package com.bottle.common.constants;

public interface ICommonConstants {
	//String _Server_IP_ = "109.205.93.209";
	String _Server_IP_ = "10.0.5.42";
	//long _Server_Port_ = 8585;
	long _Server_Port_ = 8080;
	String _Server_Name_ = "Bottle_CloudServer";
	
	String _URL_Seperator_ = "/";
	String _URL_API_ = "api";
	String _URL_UI_Ping = "ui/ping";
	
	String _UI_Identifier_Key_ = "identifier";
	
	enum MessageSourceEnum {
		_MessageSource_None,
		_MessageSource_MainFrame_
	}
	
	enum SubMessageTypeEnum {
		_SubMessageType_None_,
		_SubMessageType_MainFrame_ServerStatus_,
		_SubMessageType_MainFrame_Panel_
	}
}
