package com.bottle.common.constants;

public interface ICommonConstants {
	String _Server_IP_ = "109.205.93.209";
	//String _Server_IP_ = "10.0.5.42";
	long _Server_Port_ = 8585;
	//long _Server_Port_ = 8080;
	String _Server_Name_ = "Bottle_CloudServer";

	//Serial Port
	String _SerialPort_Name_ = "COM3";
	int _SerialPort_BauDRate_ = 38400;
	byte _SerialComm_Data_StartByte_ = 0x68;
	byte _SerialComm_Data_EndByte_ = 0x16;
	
	String _URL_Seperator_ = "/";
	String _URL_API_ = "api";
	String _URL_UI_Ping = "ui/ping";
	
	String _UI_Identifier_Key_ = "identifier";
	
	int _PingService_Period_ = 30;
	
	byte _Zero_Byte_ = 0x00;
	
	enum MachineCommandEnum {
		_MachineCommand_None_(_Zero_Byte_, _Zero_Byte_, _Zero_Byte_, _Zero_Byte_),
		_MachineCommand_Ping_(0x50, 0x10, 0x11, _Zero_Byte_),
		_MachineCommand_Start_(0x10, 0x10, 0x11, 0x12),
		_MachineCommand_Stop_(0x11, 0x10, 0x11, 0x12),
		_MachineCommand_ReturnResult_(0x20, _Zero_Byte_, 0x10, _Zero_Byte_),
		_MachineCommand_DownloadTemplate_(0x30, 0x10, 0x11, 0x12);
		
		byte pid = (int)_Zero_Byte_;
		byte aid = (int)_Zero_Byte_;
		byte aid_Success = (int)_Zero_Byte_;
		byte aid_Failure = (int)_Zero_Byte_;
		MachineCommandEnum(int pid, int aid, int aid_Success, int aid_Failure){
			this.pid = (byte)pid;
			this.aid = (byte)aid;
			this.aid_Success = (byte)aid_Success;
			this.aid_Failure = (byte)aid_Failure;
		}
		public byte getPid() {
			return pid;
		}		
		public byte getAid() {
			return aid;
		}
		public byte getAid_Success() {
			return aid_Success;
		}
		public byte getAid_Failure() {
			return aid_Failure;
		}
	}
	
	enum MainFrameActivePanelEnum {
		_MainFrame_ActivePanel_None_(0L),
		_MainFrame_ActivePanel_Welcome_(1L),
		_MainFrame_ActivePanel_Player_(2L),
		_MainFrame_ActivePanel_Verify_(3L),
		_MainFrame_ActivePanel_Admin_(4L),
		_MainFrame_ActivePanel_SuperAdmin_(5L);
		
		private long id = 0L;
		
		public long getId() {
			return id;
		}
		
		MainFrameActivePanelEnum(final long id) {
			this.id = id;
		}
		
		public static MainFrameActivePanelEnum getActivePanelEnumById(final long id) {
			MainFrameActivePanelEnum rtnEnum = _MainFrame_ActivePanel_None_;
			for (MainFrameActivePanelEnum element : MainFrameActivePanelEnum.values()) {
				if (element.getId() == id) {
					rtnEnum = element;
					break;
				}
			}
			
			if (true == _MainFrame_ActivePanel_None_.equals(rtnEnum)) {
				throw new RuntimeException("can not find valid enum by id. id:" + id);
			}
	            
			return rtnEnum;
		}		  
	}
	
	enum MessageSourceEnum {
		_MessageSource_None,
		_MessageSource_MainFrame_,
		_MessageSource_PlayerPanel_,
		_MessageSource_PingService_
	}
	
	enum SubMessageTypeEnum {
		_SubMessageType_None_,
		_SubMessageType_MainFrame_ServerStatus_,
		_SubMessageType_MainFrame_MachineStatus_,
		_SubMessageType_MainFrame_SystemInfoLog_,
		_SubMessageType_MainFrame_Panel_,
		_SubMessageType_PlayerPanel_StartCommandButton_,
		_SubMessageType_PlayerPanel_StopCommandButton_
	}
	
	final long _ConnectionStatus_Online_ = 0L;
	final long _ConnectionStatus_Offline_ = 1L;
}
