package com.bottle.ui.components.common;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class OscarCellRenderers
{
	public static class RowRenderer extends DefaultTableCellRenderer
	{
		private static final long serialVersionUID = 1L;
		private Color[] rowColors;

		public RowRenderer()
		{
			this.rowColors = new Color[1];
			this.rowColors[0] = UIManager.getColor("Table.background");
		}

		public RowRenderer(Color[] colors)
		{
			setRowColors(colors);
		}

		public void setRowColors(Color[] colors) {
			this.rowColors = colors;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setText(value != null ? value.toString() : "unknown");
			if (!isSelected) {
				setBackground(this.rowColors[(row % this.rowColors.length)]);
			}
			return this;
		}

		public boolean isOpaque() {
			return true;
		}
	}
}