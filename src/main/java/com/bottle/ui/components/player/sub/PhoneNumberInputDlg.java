package com.bottle.ui.components.player.sub;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PhoneNumberInputDlg extends JDialog implements WindowListener{
	private static final long serialVersionUID = 1L;
	final MyPhoneNumberPanel keyPopup = new MyPhoneNumberPanel();
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
	
	public PhoneNumberInputDlg() {
		this.setModal(true);
		setBounds(100, 100, 447, 425);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(0, 0, 434, 88);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 62));
		textField.setForeground(Color.RED);
		
		keyPopup.setBounds(0, 89, 434, 353);
		keyPopup.setLayout(new FlowLayout());
		keyPopup.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(keyPopup);	
		keyPopup.setTextField(textField);
		keyPopup.setFatherDlg(this);
		
		this.addWindowListener(this); 
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
