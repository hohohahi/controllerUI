package com.bottle.hardware.rxtx;

import com.bottle.hardware.rxtx.exception.ReadDataFromSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SendDataToSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SerialPortInputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortOutputStreamCloseFailure;

public interface ISerialCommConnector {
	void send(byte[] order) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure;
	byte[] read() throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure;
}
