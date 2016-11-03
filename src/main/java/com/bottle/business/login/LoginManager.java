package com.bottle.business.login;

import java.awt.image.BufferedImage;

import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.qrcode.IQRCodeParser;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.IUserConstants;

@Service
public class LoginManager extends AbstractBaseBean implements ILoginManager {
	@Autowired
	private IQRCodeParser parser;

	@Override
	public long login(BufferedImage image) {
		long role = IUserConstants._Role_None_;
		
		try {
			String rtnString = parser.parse(image);
			
			if (true == StringUtils.isNotEmpty(rtnString)) {
				String [] rtnArray = getStringArrayFromMessage(rtnString);
				
				if ((null != rtnArray) 
						&& rtnArray.length == IUserConstants._SubString_Num_) {
					final String username = rtnArray[0];
					final String password = rtnArray[1];
					
					if ((true == IUserConstants._Admin_Username_.equals(username))
							&& (true == IUserConstants._Admin_Password_.equals(password))){
						role = IUserConstants._Role_Admin_;
					} else if ((true == IUserConstants._SuperAdmin_Username_.equals(username))
							&& (true == IUserConstants._SuperAdmin_Password_.equals(password))) {
						role = IUserConstants._Role_SuperAdmin_;
					}
				}
			}
		} catch (Exception e) {
			super.logErrorAndStack(e, "exception happens. set role to none, message:" + e.getMessage());
			role = IUserConstants._Role_None_;
		}
		
		return role;
	}
	
	public String [] getStringArrayFromMessage(final String message) {
		if (null == message) {
			throw new NullPointerException(message);
		}
		
		String [] rtnArray = message.split(IUserConstants._Splitter_QRCode_);
		
		return rtnArray;
	}
}
