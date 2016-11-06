package com.bottle.ui.components.common;

import java.awt.Font;

import javax.swing.JButton;

public class CommandButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	public CommandButton(String name) {
		super(name);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
}
