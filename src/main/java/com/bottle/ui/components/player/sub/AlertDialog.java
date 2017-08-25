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

import com.bottle.common.constants.ILanguageConstants;
import com.bottle.ui.components.common.FontLabel;

public class AlertDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	public static void main(String[] args) {
		try {
			AlertDialog dialog = new AlertDialog(ILanguageConstants._ErrorMessage_PleaseEnterValidPhoneNumber_);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			System.out.println("t123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AlertDialog(final String message) {
		AlertDialog thisHandle = this;
		this.setModal(true);
		setUndecorated(true);
		setBounds(100, 100, 450, 140);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
				
		//setTitle("\u8FD4\u5229\u5230\u4E91\u8D26\u6237");
		
		contentPanel.setBounds(0, 0, 450, 140);
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));

		getContentPane().setLayout(null);
		
		//contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			FontLabel lblNewLabel = new FontLabel(message);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(28, 11, 400, 45);
			lblNewLabel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 30));
			lblNewLabel.setForeground(Color.RED);
			contentPanel.add(lblNewLabel);
		}
				
		JButton okButton = new JButton(ILanguageConstants._UI_Confirm_);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisHandle.setVisible(false);
				thisHandle.dispose();
			}
		});
		
		okButton.setBounds(160, 85, 142, 38);
		okButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 16));
		okButton.setActionCommand("OK");
		contentPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);		
	}
}
