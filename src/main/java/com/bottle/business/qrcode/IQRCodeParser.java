package com.bottle.business.qrcode;

import java.awt.image.BufferedImage;

public interface IQRCodeParser {
	String parse(BufferedImage image); 
}
