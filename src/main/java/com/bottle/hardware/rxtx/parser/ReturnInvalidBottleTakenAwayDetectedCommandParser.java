package com.bottle.hardware.rxtx.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;
import com.bottle.ui.components.player.PlayerPanel;

@Service
public class ReturnInvalidBottleTakenAwayDetectedCommandParser extends AbstractBaseCommandParser {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Autowired
	private PlayerPanel playerPanel;
	
	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_ReturnResult_InvalidBottleTakenAwayDetected;
	}
	
	@Override
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		super.validateObject(dataArea);
		RxTxResponseVO vo = super.run(aid, dataArea);
		
		playerPanel.detectInvalidBottleTakenAwayAction();
		
		return vo;
	}
}
