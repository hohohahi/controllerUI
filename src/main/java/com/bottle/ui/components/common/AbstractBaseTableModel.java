package com.bottle.ui.components.common;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractBaseTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	abstract public int getRowCount();
	abstract public int getColumnCount();
	abstract public Object getValueAt(int rowIndex, int columnIndex);
	abstract public BaseTableCandidate getCandidate(int row);
	abstract public void add(BaseTableCandidate candidate);
	abstract public void clear();
}
