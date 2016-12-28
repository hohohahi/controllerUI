package com.bottle.ui.components.common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestGraphics extends JPanel
{
	int x = 100;
	int y = 100;
	
	public TestGraphics()
	{
		setSize(1000, 600);
	}

	public void paint(Graphics g)
	{
		//100, 600
		//int abOffset_X = 80;
		//int abOffset_Y = 620;
		int abOffset_X = 100;
		int abOffset_Y = 600;
		int yOffset = 500;
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(99, 60, 2, yOffset+42);
		g2d.fillRect(99, yOffset+100, yOffset+220, 2);
		
		g2d.drawString("0", 80, 620);
		
		
		g2d.setColor(new Color(125, 125, 125));
		for (int i = 0; i < 49; i++)
		{
			g2d.drawLine(100 + i * 15, yOffset + y, x + i * 15, y - 45);
			
			if (i < 37){
				g2d.drawLine(100, yOffset + y - i * 15, x + 725, y + yOffset - i * 15);
			}
			
			
			if (i % 2 == 0 && i / 2 != 0)
			{
				
				g2d.drawString(String.valueOf(i *10), x - 10 + i / 2 * 30, 120 + yOffset);	
				if (i < 37){
					g2d.drawString(String.valueOf(i*10 ), x - 30, yOffset+105 - i / 2 * 30);
				}
				
			}
		}

		g2d.dispose();

	}

	public static void main(String[] args)
	{
		JFrame jf = new JFrame();
		jf.setSize(1000, 800);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(3);
		jf.getContentPane().add(new TestGraphics());
	}
}