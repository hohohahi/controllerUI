package com.bottle.ui.components.player.sub;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ILanguageConstants;

@Component
public class PhoneNumberInputDlg extends MyBaseDialog{
	private static final long serialVersionUID = 1L;
	private MyPhoneNumberPanel keyPopup = new MyPhoneNumberPanel();
	private ICommonConstants.CashModeEnum cashMode = ICommonConstants.CashModeEnum._CacheMode_ReturnMoney_;
	private BarCodePicturePanel machineBarCodePicturePanel = new BarCodePicturePanel("machineBarCode250.jpg", 250, 250);
	//private BarCodePicturePanel downloadBarCodePicturePanel = new BarCodePicturePanel("machineBarCode250.jpg", 250, 250);
	
	private JTextField textField;
	public static void main(String[] args) {
		try {
			PhoneNumberInputDlg dialog = new PhoneNumberInputDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setCashMode(ICommonConstants.CashModeEnum cashMode) {
		this.cashMode = cashMode;
	}

	public ICommonConstants.CashModeEnum getCashMode() {
		return cashMode;
	}

	public void setPhoneNumber(long phoneNumber) {
		textField.setText(String.valueOf(phoneNumber));
		textField.invalidate();
		textField.repaint();
	}
	
	public void reset() {
		textField.setText("");
		
		if (true == ICommonConstants.CashModeEnum._CacheMode_ReturnMoney_.equals(cashMode)) {
			this.setTitle(ILanguageConstants._UI_RealProductionInfoPanel_PleaseInputValidPhoneNumber_);
		}
		else if (true == ICommonConstants.CashModeEnum._CacheMode_Donate_.equals(cashMode)) {
			this.setTitle(ILanguageConstants._UI_RealProductionInfoPanel_PleaseInputValidPhoneNumber_And_Choose_);
		}
		else {
			this.setTitle(ILanguageConstants._UI_RealProductionInfoPanel_InvalidParameter_);
		}
	}
	
	public String getPhoneNumber() {
		return textField.getText();
	}

	public void windowClosing(WindowEvent e) { 
		textField.setText("");
		this.setVisible(false);
    } 
	
	public void showAlertDialog(final ICommonConstants.CashModeEnum cachMode, final String warnMessage) {
		if (true == ICommonConstants.CashModeEnum._CacheMode_ReturnMoney_.equals(cachMode)){
			AlertDialog dlg = new AlertDialog(warnMessage);
			dlg.setModal(true);
			dlg.setVisible(true);
		}		
		else {
			ConfirmDialog dlg = new ConfirmDialog(warnMessage);
			dlg.setModal(true);
			dlg.setVisible(true);
			
			if (true == ICommonConstants.DialogReturnValueEnum._DialogReturn_Confirm_.equals(dlg.getRtnValue())) {
				textField.setText(ICommonConstants._invalidPhoneNumber_);
				this.setVisible(false);
			}
			else if (true == ICommonConstants.DialogReturnValueEnum._DialogReturn_Cancel_.equals(dlg.getRtnValue())) {
				
			}			
		}
	}
	
	public PhoneNumberInputDlg() {
		this.setModal(true);
		setBounds(100, 100, 706, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.setTitle(ILanguageConstants._UI_RealProductionInfoPanel_PleaseInputValidPhoneNumber_);
		textField = new JTextField();
		textField.setBounds(10, 11, 669, 109);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 100));
		textField.setForeground(Color.RED);
		
		keyPopup.setBounds(287, 131, 388, 546);
		keyPopup.setLayout(new FlowLayout());
		keyPopup.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(keyPopup);	
		keyPopup.setTextField(textField);
		keyPopup.setFatherDlg(this);
		
		machineBarCodePicturePanel.setBounds(20, 251, 250, 250);
		getContentPane().add(machineBarCodePicturePanel);
		
//		downloadBarCodePicturePanel.setBounds(20, 401, 250, 250);
//		getContentPane().add(downloadBarCodePicturePanel);
	}
}