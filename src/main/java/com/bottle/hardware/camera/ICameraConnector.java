package com.bottle.hardware.camera;

import com.bottle.ui.components.verify.VideoBarCodeVerifyPanel;

public interface ICameraConnector {
	void start();
	void stop();
	void setPanel(VideoBarCodeVerifyPanel panel);
}
