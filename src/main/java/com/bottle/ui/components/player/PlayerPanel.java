package com.bottle.ui.components.player;

import javax.swing.JPanel;

import com.bottle.ui.components.player.sub.WelcomePanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

public class PlayerPanel extends JPanel {

	private WelcomePanel welcomePanel;

	public PlayerPanel() {
		setLayout(null);
		welcomePanel = new WelcomePanel();
		welcomePanel.setBounds(0, 64, 1000, 600);
		this.add(welcomePanel);
		welcomePanel.setLayout(null);
	}
}
