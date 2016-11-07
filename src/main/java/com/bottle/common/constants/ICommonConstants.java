package com.bottle.common.constants;

public interface ICommonConstants {
	String _Server_IP_ = "109.205.93.209";
	//String _Server_IP_ = "10.0.5.42";
	long _Server_Port_ = 8585;
	//long _Server_Port_ = 8080;
	String _Server_Name_ = "Bottle_CloudServer";

	//Serial Port
	String _SerialPort_Name_ = "COM2";
	int _SerialPort_BauDRate_ = 38400;
	byte _SerialComm_Data_StartByte_ = 0x68;
	byte _SerialComm_Data_EndByte_ = 0x16;
	
	String _URL_Seperator_ = "/";
	String _URL_API_ = "api";
	String _URL_UI_Ping = "ui/ping";
	String _URL_UI_ReturnMoney = "ui/money";
	
	String _UI_Identifier_Key_ = "identifier";
	String _UI_PhoneNumber_Key_ = "phoneNumber";
	String _UI_Amount_Key_ = "amount";
	
	
	
	int _PingService_Period_ = 30;
	
	byte _Zero_Byte_ = (byte)0x00;
	
	enum DebugCommandEnum {
		_OpenDoor_(0x01, 0x02),
		_CloseDoor_(0x03, 0x04),
		_PlatformDown_(0x05, 0x06),
		_PlatformUp_(0x07, 0x08),
		_MovePositive_(0x09, 0x0a),
		_MoveNegative_(0x0a, 0x0b),
		_StopMove_(0x0c, 0x0d),
		_MoveWheel_(0x11, 0x12),
		_StopWheel_(0x13, 0x14),
		_OpenLight_(0x15, 0x16),
		_CloseLight_(0x17, 0x18);
		
		byte aid = _Zero_Byte_;
		byte aid_Return = _Zero_Byte_;
		public byte getAid() {
			return aid;
		}
		public void setAid(byte aid) {
			this.aid = aid;
		}
		public byte getAid_Return() {
			return aid_Return;
		}
		public void setAid_Return(byte aid_Return) {
			this.aid_Return = aid_Return;
		}
		
		DebugCommandEnum(int aid, int aid_Return) {
			this.aid = (byte)aid;
			this.aid_Return = (byte)aid_Return;
		}
	}
	
	enum MachineCommandEnum {
		_MachineCommand_None_(_Zero_Byte_, _Zero_Byte_, _Zero_Byte_, _Zero_Byte_),
		_MachineCommand_Ping_(0x50, 0x10, 0x11, _Zero_Byte_),
		_MachineCommand_Start_(0x10, 0x10, 0x11, 0x12),
		_MachineCommand_Stop_(0x11, 0x10, 0x11, 0x12),
		_MachineCommand_ReturnResult_(0x20, _Zero_Byte_, 0x10, _Zero_Byte_),
		_MachineCommand_DownloadTemplate_(0x30, 0x10, 0x11, 0x12),
		_MachineCommand_Debug_Command_(0x40, _Zero_Byte_, _Zero_Byte_, _Zero_Byte_);
		
		byte pid = _Zero_Byte_;
		byte aid = _Zero_Byte_;
		byte aid_Success = _Zero_Byte_;
		byte aid_Failure = _Zero_Byte_;
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
		_MessageSource_AdminPanel_,
		_MessageSource_PingService_
	}
	
	enum SubMessageTypeEnum {
		_SubMessageType_None_,
		_SubMessageType_MainFrame_ServerStatus_,
		_SubMessageType_MainFrame_MachineStatus_,
		_SubMessageType_MainFrame_SystemInfoLog_,
		_SubMessageType_MainFrame_Panel_,
		_SubMessageType_PlayerPanel_StartCommandButton_,
		_SubMessageType_PlayerPanel_StopCommandButton_,
		_SubMessageType_PlayerPanel_RealProductionInfoPanel_,
		_SubMessageType_AdminPanel_OpenDoorButton_,
		_SubMessageType_AdminPanel_CloseDoorButton_,
		_SubMessageType_AdminPanel_PlatformDownButton_,
		_SubMessageType_AdminPanel_PlatformUpButton_,
		_SubMessageType_AdminPanel_MovePositive_,
		_SubMessageType_AdminPanel_MoveNegative_,
		_SubMessageType_AdminPanel_StopMove_,
		_SubMessageType_AdminPanel_MoveWheel_,
		_SubMessageType_AdminPanel_StopWheel_,
		_SubMessageType_AdminPanel_OpenLight_,
		_SubMessageType_AdminPanel_CloseLight_
	}
	
	final long _ConnectionStatus_Online_ = 0L;
	final long _ConnectionStatus_Offline_ = 1L;
}
