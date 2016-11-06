package com.bottle.hardware.rxtx;

import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

public interface IByteDataParser {
	RxTxResponseVO parse(byte [] data);
}
