package com.bottle.business.qrcode;

import java.awt.image.BufferedImage;

import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;

import goja.QRCode;

@Service
public class QRCodeParser extends AbstractBaseBean implements IQRCodeParser {
	@Override
	public String parse(BufferedImage image) {
		String rtnString = "";
		try {
			rtnString = QRCode.from(image);
			
			System.out.println(rtnString);
		} catch (Throwable e) {
			
		}
		
		return rtnString;
	}
}

