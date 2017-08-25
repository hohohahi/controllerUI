package com.bottle.ui.components.player.sub;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.ui.components.common.FontLabel;

public class ConfirmDialog extends MyBaseDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	public static void main(String[] args) {
		try {
			ConfirmDialog dialog = new ConfirmDialog(ILanguageConstants._ErrorMessage_InvalidPhoneNumberConfirm_);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			System.out.println("t123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ConfirmDialog(final String message) {
		ConfirmDialog thisHandle = this;
		this.setModal(true);
		setUndecorated(true);
		setBounds(100, 100, 800, 140);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
				
		//setTitle("\u8FD4\u5229\u5230\u4E91\u8D26\u6237");
		
		contentPanel.setBounds(0, 0, 760, 140);
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));

		getContentPane().setLayout(null);
		
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			FontLabel lblNewLabel = new FontLabel(message);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(28, 11, 760, 45);
			lblNewLabel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 30));
			lblNewLabel.setForeground(Color.RED);
			contentPanel.add(lblNewLabel);
		}
				
		JButton okButton = new JButton(ILanguageConstants._UI_Confirm_);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisHandle.setRtnValue(ICommonConstants.DialogReturnValueEnum._DialogReturn_Confirm_);
				thisHandle.setVisible(false);
				thisHandle.dispose();
			}
		});
		
		okButton.setBounds(200, 85, 142, 38);
		okButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 16));
		contentPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);	
		
		JButton cancelButton = new JButton(ILanguageConstants._UI_Cancel_);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisHandle.setRtnValue(ICommonConstants.DialogReturnValueEnum._DialogReturn_Cancel_);
				thisHandle.setVisible(false);
				thisHandle.dispose();
			}
		});
		
		cancelButton.setBounds(400, 85, 142, 38);
		cancelButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 16));
		contentPanel.add(cancelButton);
		getRootPane().setDefaultButton(cancelButton);
	}
}
