package com.bottle.ui.components.player.sub;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class WelcomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public WelcomePanel() {
		setLayout(null);
		
		JLabel lblWelcomeToUse = new JLabel("Welcome To Use Bottle System");
		lblWelcomeToUse.setBounds(467, 135, 257, 77);
		add(lblWelcomeToUse);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(401, 338, 89, 23);
		add(btnStart);

	}
}
