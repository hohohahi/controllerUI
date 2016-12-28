package com.bottle.ui.components.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.bottle.business.common.vo.PositionInfoVO;

public class GraphicsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private List<PositionInfoVO> positionList = new ArrayList<PositionInfoVO>();
	
	int x = 100;
	int y = 100;
	
	public GraphicsPanel() {

	}

	public void setPositionList(List<PositionInfoVO> positionList) {
		this.positionList = positionList;
	}
	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		paintCordinate(g);
		paintGraph(g);
		g.dispose();
	}
	
	public void paintGraph(Graphics g) {
		g.setColor(Color.RED);
		Graphics2D g2 = (Graphics2D)g; 
		g2.setStroke(new BasicStroke(3.0f));  
		
		int abOffset_X = 100;
		int abOffset_Y = 600;
		
		int size = positionList.size();
		for (int i = 0; i < (size - 1); i++) {
			g.setColor(Color.RED);
			PositionInfoVO pos1 = positionList.get(i);
			PositionInfoVO pos2 = positionList.get(i+1);
			int x1 = ((int) getValueByFactor(pos1.getX()) + (abOffset_X));
			int y1 = (abOffset_Y - getValueByFactor((int)pos1.getY()));						

			int x2 = ((int) getValueByFactor(pos2.getX()) + (abOffset_X));
			int y2 = (abOffset_Y - getValueByFactor((int)pos2.getY()));
			g.drawLine(x1,y1,x2,y2);
			
			g.setColor(Color.BLUE);
			g.fillOval(x1-5, y1-5, 10, 10);
			g.fillOval(x2-5, y2-5, 10, 10);
		} 
	}
	
	public int getValueByFactor(long originalValue) {
		return (int)(originalValue*100/67);
	}
	public void paintCordinate(Graphics g)
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
	}
}
