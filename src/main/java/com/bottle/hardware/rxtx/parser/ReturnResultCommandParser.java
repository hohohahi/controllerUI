package com.bottle.hardware.rxtx.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class ReturnResultCommandParser extends AbstractBaseCommandParser {
	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_ReturnResult_;
	}
	
	@Override
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		super.validateObject(dataArea);
		RxTxResponseVO vo = super.run(aid, dataArea);
		
		final int length = dataArea.length;
		if (0 == length) {
			throw new RuntimeException("ReturnResultCommandParser::run: length of dataArea is 0.");
		}
		
		byte firstElement = dataArea[0];
		vo.setErrorCode((long)firstElement);
		
		ProductionDataVO productionDataVO = new ProductionDataVO();
		productionDataVO.setTimestampStr(super.dateConverter.getCurrentTimestampInNineteenBitsInGMT());
		
		if (ICommonConstants._Zero_Byte_ == firstElement) {
			if (length == 1) {
				throw new RuntimeException("Command:ReturnResult: length of dataArea is 1, when first element is 0.");
			}
			else {
				String barCode = super.dataTypeHelper.convert_byte_String(dataArea);
				vo.setResponse(barCode);
								
				productionDataVO.setBarCode(barCode);
				productionDataVO.setIsSuccessful(true);
				productionDataManager.fullfilTemplateInfo_ToProductionVO(productionDataVO);				
			}
		}
		else {
			if (length > 1) {
				throw new RuntimeException("Command:ReturnResult: length of dataArea is greater than 1, when first element is not 0.");
			}
			
			productionDataVO.setErrorCode(firstElement);
			productionDataVO.setIsSuccessful(false);
		}
		
		productionDataManager.push(productionDataVO);
		updateRealtimeUI();
		
		return vo;
	}
	
	public void updateRealtimeUI() {
		final MessageVO vo = new MessageVO();
		
		vo.setMessageSource(MessageSourceEnum._MessageSource_PlayerPanel_);
		vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_PlayerPanel_RealProductionInfoPanel_);
		
		messageManager.push(vo);
	}
}
