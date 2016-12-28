package com.bottle.hardware.rxtx;

import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.hardware.rxtx.exception.ReadDataFromSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SendDataToSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SerialPortInputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortOutputStreamCloseFailure;

import gnu.io.SerialPortEvent;

public interface ISerialCommConnector {
	void send(byte[] order, final IMachineCommandSender commandSender) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure;
	byte[] read() throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure;
	boolean  getIsSerialPortReady();
	void initSerialPort();
	void serialEvent(final SerialPortEvent event);
}
