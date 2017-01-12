package com.bottle.ui.components.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


class CircleButton extends JButton {
	private static final long serialVersionUID = 1L;
	private Shape shape;
	private Color normalColor;
	private Color clickedColor;
	public CircleButton(String label, Color normalColor, Color clickedColor, Dimension size) {
		super(label);
		this.normalColor = normalColor;
		this.clickedColor = clickedColor;
		
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);
		this.setBackground(normalColor);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 48));
		this.setForeground(Color.WHITE);
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(clickedColor);
		}
		else {
			g.setColor(normalColor);
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width - 1,
				getSize().height - 1);
	}
	
	public boolean contains(int x, int y) {
		if (shape == null ||
				!shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0,
					getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}


	public static void main(String[] args) {
		JButton button = new CircleButton("Jackpot", Color.BLUE, Color.GRAY, new Dimension(150, 150));
		ImageIcon ic = new ImageIcon("E://clientForMssql//Icons//item_group.gif");
		JButton button2 = new JButton(ic);
		button.setBackground(Color.BLUE);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setBackground(Color.GRAY);
		frame.getContentPane().add(button);
		frame.getContentPane().add(button2);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setSize(200, 200);
		frame.setVisible(true);
	}
}
