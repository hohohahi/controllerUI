package com.bottle.hardware.camera;

import com.bottle.ui.components.VerifyPanel;

public interface ICameraConnector {
	void start();
	void stop();
	void setPanel(VerifyPanel panel);
}
