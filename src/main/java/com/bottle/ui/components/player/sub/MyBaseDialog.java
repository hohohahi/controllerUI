package com.bottle.ui.components.player.sub;

import javax.swing.JDialog;

import com.bottle.common.constants.ICommonConstants.DialogReturnValueEnum;

public class MyBaseDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
	private DialogReturnValueEnum rtnValue = DialogReturnValueEnum._DialogReturn_Confirm_;
	public MyBaseDialog() {
		
	}
	
	public DialogReturnValueEnum getRtnValue() {
		return rtnValue;
	}

	public void setRtnValue(DialogReturnValueEnum rtnValue) {
		this.rtnValue = rtnValue;
	}

	public void setTitleBackgroupColor() {
		
	}
}
