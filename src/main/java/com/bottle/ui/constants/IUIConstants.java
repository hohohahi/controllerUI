package com.bottle.ui.constants;

public interface IUIConstants {
	enum UIWorkModeEnum {
		_WorkMode_Player_,
		_WorkMode_Verify_,
		_WorkMode_Admin_,
		_WorkMode_SuperAdmin_
	};
	
	int _Total_Width_ = 1366;
	int _Total_Height_ = 768;
	int _Banner_Height_ = 64;
	
	int _VideoPanel_Offset_X = 300;
	int _VideoPanel_Width = 650;
	int _VideoPanel_Height = 650;
	int _VerifyPanel_ReturnButton_Offset_X_LeftToVideoPanel_ = 50;
	int _VerifyPanel_ReturnButton_Offset_Y = 100;
	int _VerifyPanel_MessageLabel_Width_ = 300;
	int _VerifyPanel_MessageLabel_Height_ = 100;
	int _VerifyPanel_ExpireTime = 30;
}
