package com.bottle.hardware.camera;

import com.bottle.ui.components.VerifyJPanel;

public interface ICameraConnector {
	void start();
	void stop();
	void setPanel(VerifyJPanel panel);
}
