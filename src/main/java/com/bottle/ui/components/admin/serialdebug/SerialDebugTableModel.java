package com.bottle.ui.components.admin.serialdebug;

import java.util.ArrayList;
import java.util.List;

import com.bottle.ui.components.common.AbstractBaseTableModel;
import com.bottle.ui.components.common.BaseTableCandidate;

public class SerialDebugTableModel extends AbstractBaseTableModel
{
	private static final long serialVersionUID = 1L;
	private final List<SerailDebugTableCandidate> candidates = new ArrayList<SerailDebugTableCandidate>();

	@Override
	public int getRowCount() {
		return this.candidates.size();
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Class<? extends Object> getColumnClass(int column)
	{
		return getValueAt(0, column).getClass();
	}

	@Override
	public SerailDebugTableCandidate getCandidate(int row) {
		return (SerailDebugTableCandidate)this.candidates.get(row);
	}

	@Override
	public Object getValueAt(int row, int column) {
		SerailDebugTableCandidate oscarCandidate = (SerailDebugTableCandidate)this.candidates.get(row);
		switch (column) {
		case 0:
			return oscarCandidate.getTimestamp();
		case 1:
			return oscarCandidate.getDirection();
		case 2:
			return oscarCandidate.getPid();
		case 3:
			return oscarCandidate.getAid();
		case 4:
			return oscarCandidate.getBytesStr();
		}
		
		return null;
	}

	@Override
	public void add(BaseTableCandidate candidate) {
		int index = this.candidates.size();
		this.candidates.add(0, (SerailDebugTableCandidate)candidate);
		fireTableRowsInserted(index, index);
	}

	@Override
	public void clear() {
		candidates.clear();
	}
}