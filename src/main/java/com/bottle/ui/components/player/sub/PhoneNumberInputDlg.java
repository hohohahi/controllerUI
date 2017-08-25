package com.bottle.ui.components.player.sub;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberInputDlg extends MyBaseDialog{
	private static final long serialVersionUID = 1L;
	private MyPhoneNumberPanel keyPopup = new MyPhoneNumberPanel();
	private BarCodePicturePanel machineBarCodePicturePanel = new BarCodePicturePanel("machineBarCode250.jpg", 250, 250);
	private BarCodePicturePanel downloadBarCodePicturePanel = new BarCodePicturePanel("machineBarCode250.jpg", 250, 250);
	
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

	public void setPhoneNumber(long phoneNumber) {
		textField.setText(String.valueOf(phoneNumber));
		textField.invalidate();
		textField.repaint();
	}
	
	public void reset() {
		textField.setText("");
	}
	
	public String getPhoneNumber() {
		return textField.getText();
	}

	public void windowClosing(WindowEvent e) { 
		textField.setText("");
		this.setVisible(false);
    } 
	
	public void showAlertDialog(final String warnMessage) {
		AlertDialog dlg = new AlertDialog(warnMessage);
		dlg.setModal(true);
		dlg.setVisible(true);
	}
	
	public PhoneNumberInputDlg() {
		this.setModal(true);
		setBounds(100, 100, 706, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.setTitle("Please input your phone number:");
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
		
		machineBarCodePicturePanel.setBounds(20, 131, 250, 250);
		getContentPane().add(machineBarCodePicturePanel);
		
		downloadBarCodePicturePanel.setBounds(20, 401, 250, 250);
		getContentPane().add(downloadBarCodePicturePanel);
	}
}