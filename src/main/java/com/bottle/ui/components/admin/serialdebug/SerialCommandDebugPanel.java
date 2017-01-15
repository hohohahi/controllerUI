package com.bottle.ui.components.admin.serialdebug;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IOperationRecorder;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.common.vo.OperationVO;
import com.bottle.business.common.vo.SerialCommandOperationVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.DebugCommandEnum;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.OperationTypeEnum;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.common.MyTableWrapper;

@Component
public class SerialCommandDebugPanel extends AbstractBasePanel {
	private static final long serialVersionUID = 1L;

	private FontButton openDoorButton = new FontButton("\u6253\u5F00\u8231\u95E8");
	private FontButton closeDoorButton = new FontButton("\u5173\u95ED\u8231\u95E8");
	private FontButton platformDownButton = new FontButton("\u76AE\u5E26\u5E73\u53F0\u4E0B\u964D");
	private FontButton platformUpButton = new FontButton("\u76AE\u5E26\u5E73\u53F0\u4E0A\u5347");
	private FontButton movePositiveButton = new FontButton("\u76AE\u5E26\u6B63\u5411\u8FD0\u884C");
	private FontButton moveNegativeButton = new FontButton("\u76AE\u5E26\u53CD\u5411\u8FD0\u884C");
	private FontButton stopMoveButton = new FontButton("\u76AE\u5E26\u505C\u6B62");
	private FontButton moveWheelButton = new FontButton("\u6EDA\u8F6E\u8F6C\u52A8");
	private FontButton stopWheelButton = new FontButton("\u6EDA\u8F6E\u505C\u6B62");
	private FontButton openLightButton = new FontButton("\u6253\u5F00\u5149\u6E90");
	private FontButton closeLightButton = new FontButton("\u5173\u95ED\u5149\u6E90");
	
	private JTable resultTable;
	private MyTableWrapper tableWrapper;
	private JScrollPane scrollPane;
	private ButtonGroup logFilterButtonGroup = new ButtonGroup();
	private JRadioButton noPingLogRadioButton = new JRadioButton("\u4E0D\u5305\u542B\u5FC3\u8DF3\u65E5\u5FD7", true);
	private JRadioButton allLogRadioButton = new JRadioButton("\u5168\u90E8\u65E5\u5FD7", false);
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	@Autowired
	private IOperationRecorder operationRecorder;
	
	@PostConstruct
	public void initialize() {
		addMessageListener();
	}
	
	public SerialCommandDebugPanel() {
		initTableWrapper();
	    this.resultTable = tableWrapper.getTable();
	    resultTable.setBounds(430, 48, 536, 388);
	    
	    scrollPane = new JScrollPane(resultTable);
	    scrollPane.setSize(754, 1254);
	    scrollPane.setLocation(10, 313);
	    this.add(scrollPane);  

	    
		openDoorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._OpenDoor_.getAid());
				updateTable();
				openDoorButton.setEnabled(false);
			}
		});
		setLayout(null);
		
		openDoorButton.setBounds(10, 24, 153, 53);
		add(openDoorButton);
		closeDoorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._CloseDoor_.getAid());
				updateTable();
				closeDoorButton.setEnabled(false);
			}
		});
							
		closeDoorButton.setBounds(173, 24, 153, 53);
		add(closeDoorButton);
		
		
		platformDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._PlatformDown_.getAid());
				updateTable();
				platformDownButton.setEnabled(false);
			}
		});
		platformDownButton.setBounds(448, 24, 153, 53);
		add(platformDownButton);
		
		
		platformUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._PlatformUp_.getAid());
				updateTable();
				platformUpButton.setEnabled(false);
			}
		});
		platformUpButton.setBounds(611, 24, 153, 53);
		add(platformUpButton);
		
		movePositiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._MovePositive_.getAid());
				updateTable();
				movePositiveButton.setEnabled(false);
			}
		});
		movePositiveButton.setBounds(10, 183, 153, 53);
		add(movePositiveButton);
		
		moveNegativeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._MoveNegative_.getAid());
				updateTable();
				moveNegativeButton.setEnabled(false);
			}
		});
		moveNegativeButton.setBounds(173, 183, 153, 53);
		add(moveNegativeButton);
		
		stopMoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._StopMove_.getAid());
				updateTable();
				stopMoveButton.setEnabled(false);
			}
		});		
		stopMoveButton.setBounds(336, 183, 170, 53);
		add(stopMoveButton);
		
		moveWheelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._MoveWheel_.getAid());
				updateTable();
				moveWheelButton.setEnabled(false);
			}
		});
		moveWheelButton.setBounds(10, 102, 153, 53);
		add(moveWheelButton);
		
		stopWheelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._StopWheel_.getAid());
				updateTable();
				stopWheelButton.setEnabled(false);
			}
		});
		stopWheelButton.setBounds(173, 102, 153, 53);
		add(stopWheelButton);
		
		openLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._OpenLight_.getAid());
				updateTable();
				openLightButton.setEnabled(false);
			}
		});		
		openLightButton.setBounds(448, 102, 153, 53);
		add(openLightButton);
		
		closeLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._CloseLight_.getAid());
				updateTable();
				closeLightButton.setEnabled(false);
			}
		});		
		closeLightButton.setBounds(611, 102, 153, 53);
		add(closeLightButton);		
		
		FontButton commandButton = new FontButton("\u5173\u95ED\u5149\u6E90");
		commandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		commandButton.setText("\u5237\u65B0");
		commandButton.setBounds(10, 263, 252, 39);
		add(commandButton);
		
		allLogRadioButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		allLogRadioButton.setBounds(331, 263, 133, 39);
		add(allLogRadioButton);
		
		noPingLogRadioButton.setBounds(482, 263, 196, 39);
		noPingLogRadioButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		add(noPingLogRadioButton);
		
		logFilterButtonGroup.add(allLogRadioButton);
		logFilterButtonGroup.add(noPingLogRadioButton);
	}

	@SuppressWarnings("serial")
	public void initTableWrapper() {
		tableWrapper = new MyTableWrapper(new ArrayList<String>(){{add("Timestamp"); add("Direction"); add("PID"); add("AID"); add("Bytes");}}, 
							              new ArrayList<Integer>(){{add(180); add(60); add(30); add(30);add(470);}}, new SerialDebugTableModel());
	}
	
	public void updateTable() {
		boolean isShowingPingLog = false;
		 Enumeration<AbstractButton> enu = logFilterButtonGroup.getElements();
         while (enu.hasMoreElements()) {
             AbstractButton radioButton = enu.nextElement();
             
             if (true == radioButton.isSelected()) {
            	 if (true == radioButton.equals(noPingLogRadioButton)) {
            		 isShowingPingLog = false;
            	 }
            	 else {
            		 isShowingPingLog = true;
            	 }
             }          
         }
         
		tableWrapper.clear();
		final List<OperationVO> operationList = operationRecorder.getOperationListByType(OperationTypeEnum._Operation_Type_SerialCommand_);
		
		for (OperationVO operation : operationList) {
			SerialCommandOperationVO serialCommandOperationVO = (SerialCommandOperationVO)operation;
			if (isShowingPingLog == false) {
				if (MachineCommandEnum._MachineCommand_Ping_.getPid() == serialCommandOperationVO.getPid()){
					continue;
				}
			}
			
			SerailDebugTableCandidate element = new SerailDebugTableCandidate("2016-11-14 00:01:59", "DN", "10", "10", "01 01");
			element.setPid(bytesToHexString(new byte[]{serialCommandOperationVO.getPid()}));
			element.setAid(bytesToHexString(new byte[]{serialCommandOperationVO.getAid()}));
			element.setDirection(serialCommandOperationVO.getDirection().getName());
			element.setTimestamp(serialCommandOperationVO.getTimestampStr());
			element.setBytesStr(bytesToHexString(serialCommandOperationVO.getData()));
			
			
			tableWrapper.add(element);
		}
		
		resultTable.invalidate();
		scrollPane.invalidate();
	}
	
	public String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	
	@Override
	public void process(MessageVO vo) {		
		if (null == vo) {
			throw new NullPointerException("vo is null.");
		}
		
		final ICommonConstants.MessageSourceEnum messageSource =vo.getMessageSource();
		if (false == messageSource.equals(getMessageType())) {
			return;
		}
		
		System.out.println("debug panel entered. vo:" + vo);
		
		final ICommonConstants.SubMessageTypeEnum subMessageType = vo.getSubMessageType();
		if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_OpenDoorButton_.equals(subMessageType)) {
			openDoorButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_CloseDoorButton_.equals(subMessageType)) {
			closeDoorButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_PlatformDownButton_.equals(subMessageType)) {
			platformDownButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_PlatformUpButton_.equals(subMessageType)) {
			platformUpButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MovePositive_.equals(subMessageType)) {
			movePositiveButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MoveNegative_.equals(subMessageType)) {
			moveNegativeButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_StopMove_.equals(subMessageType)) {
			stopMoveButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MoveWheel_.equals(subMessageType)) {
			moveWheelButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_StopWheel_.equals(subMessageType)) {
			stopWheelButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_OpenLight_.equals(subMessageType)) {
			openLightButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_CloseLight_.equals(subMessageType)) {
			closeLightButton.setEnabled(true);
		}
		
		updateTable();
	}
	
	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_AdminPanel_Tab_DebugCommandPanel_;
	}

	@Override
	public void processChildMessage(MessageVO vo) {

	}

	@Override
	public void setParent(java.awt.Component parent) {
		// TODO Auto-generated method stub

	}
}
