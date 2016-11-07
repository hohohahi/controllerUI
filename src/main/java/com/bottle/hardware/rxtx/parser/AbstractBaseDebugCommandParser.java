package com.bottle.hardware.rxtx.parser;

import org.springframework.stereotype.Service;

import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
abstract public class AbstractBaseDebugCommandParser extends AbstractBaseCommandParser {
	@Override
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		super.validateObject(dataArea);
		RxTxResponseVO vo = super.run(aid, dataArea);
		
		final int length = dataArea.length;
		if (1 != length) {
			throw new RuntimeException("AbstractBaseDebugCommandParser::run: length of dataArea is not 1.");
		}
		
		byte element = dataArea[0];
		vo.setErrorCode((long)element);
		vo.setParam1((long)aid);
		vo.setCommandType(getCommandType());
		return vo;
	}
}
