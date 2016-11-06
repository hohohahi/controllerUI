package com.bottle.hardware.rxtx.parser;

public interface IParserSelector {
	ICommandParser select(byte pid); 
}
