package com.bottle.ui.components.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.bottle.ui.components.admin.serialdebug.SerialDebugTableModel;
import com.bottle.ui.components.common.OscarCellRenderers.RowRenderer;

import sun.swing.table.DefaultTableCellHeaderRenderer;

public class MyTableWrapper {
	private Color[] rowColors;
	private AbstractBaseTableModel tableModel;
	private JTable table;
	
	public MyTableWrapper(List<String> columnTileList, List<Integer> widthList, AbstractBaseTableModel model) {
		initTableModel();
		tableModel = model;
		table = new JTable(tableModel);
		
		table.setColumnModel(createColumnModel(columnTileList, widthList));
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(36);
		table.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 16));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(0);
		table.setIntercellSpacing(new Dimension(0, 0));
		
	    DefaultTableCellHeaderRenderer render = (DefaultTableCellHeaderRenderer) table.getTableHeader().getDefaultRenderer();
	    render.setHorizontalAlignment(JLabel.CENTER);
	}
	
	public JTable getTable() {
		return table;
	}

	public void initTableModel() {
		tableModel = new SerialDebugTableModel();				
	}
	
	public void add(final BaseTableCandidate element) {
		tableModel.add(element);	
	}
	
	public BaseTableCandidate getElementAtIndex(int index) {
		return tableModel.getCandidate(index);
	}
	
	public void setTableModel(AbstractBaseTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void clear() {
		tableModel.clear();
	}
	
	protected TableColumnModel createColumnModel(List<String> columnTileList, List<Integer> widthList)
	{
		if (null == columnTileList || null == widthList) {
			throw new NullPointerException("columnTileList or widthList is null.");
		}
		
		if (columnTileList.size() != widthList.size()) {
			throw new RuntimeException("columnTileList and widthList, have different size. columnTileList size:" + columnTileList.size() + "--widthList size:" + widthList.size());
		}
		
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
		
		RowRenderer cellRenderer = new OscarCellRenderers.RowRenderer(getTableRowColors());

		final int size = columnTileList.size();
		for (int index = 0; index < size; index++){
			TableColumn column = new TableColumn();
			column.setModelIndex(index);
			column.sizeWidthToFit();
			column.setHeaderValue(columnTileList.get(index));
			column.setPreferredWidth(widthList.get(index));
			column.setCellRenderer(cellRenderer);
			
			if (index != size -1) {
				cellRenderer.setHorizontalAlignment(JLabel.CENTER);
			}			

			columnModel.addColumn(column);
		}

		return columnModel;
	}
	
	private Color[] getTableRowColors() {
		if (this.rowColors == null) {
			this.rowColors = new Color[2];
			this.rowColors[0] = UIManager.getColor("Table.background");
			this.rowColors[1] = 
					new Color((int)(this.rowColors[0].getRed() * 0.9D), 
							(int)(this.rowColors[0].getGreen() * 0.9D), 
							(int)(this.rowColors[0].getBlue() * 0.9D));
		}
		return this.rowColors;
	}
}
