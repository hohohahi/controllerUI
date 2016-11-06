package com.bottle.hardware.rxtx.parser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;

@Service
public class ParserSelector extends AbstractBaseBean implements IParserSelector {
	@Autowired
	private List<ICommandParser> parserList;

	@Override
	public ICommandParser select(final byte pid) {
		if (null == parserList) {
			throw new NullPointerException("parserList is null.");
		}
		
		ICommandParser rtnParser = null;
		for (ICommandParser parser : parserList) {
			final ICommonConstants.MachineCommandEnum commandType = parser.getCommandType();
			if (null == commandType) {
				throw new NullPointerException("commandType is null in parser");
			}
			
			if (commandType.getPid() == pid) {
				rtnParser = parser;
				break;
			}			
		}
		
		if (null == rtnParser) {
			throw new RuntimeException("rtnParser is null. pid:" + pid);
		}
		
		return rtnParser;
	}
	
}
