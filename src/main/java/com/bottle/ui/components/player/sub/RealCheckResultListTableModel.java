package com.bottle.ui.components.player.sub;

import java.util.ArrayList;
import java.util.List;

import com.bottle.ui.components.common.AbstractBaseTableModel;
import com.bottle.ui.components.common.BaseTableCandidate;

public class RealCheckResultListTableModel extends AbstractBaseTableModel
{
	private static final long serialVersionUID = 1L;
	private final List<BaseTableCandidate> candidates = new ArrayList<BaseTableCandidate>();

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
	public RealCheckResultTableCandidate getCandidate(int row) {
		return (RealCheckResultTableCandidate)this.candidates.get(row);
	}

	@Override
	public Object getValueAt(int row, int column) {
		RealCheckResultTableCandidate oscarCandidate = (RealCheckResultTableCandidate)this.candidates.get(row);
		switch (column) {
		case 0:
			return oscarCandidate.getId();
		case 1:
			return oscarCandidate.getTimestamp();
		case 2:
			return oscarCandidate.getName();
		case 3:
			return oscarCandidate.getBarCode();
		case 4:
			return oscarCandidate.getPrice();
		}
		
		return null;
	}

	@Override
	public void add(BaseTableCandidate candidate) {
		int index = this.candidates.size();
		this.candidates.add(0, candidate);
		fireTableRowsInserted(index, index);
	}

	@Override
	public void clear() {
		candidates.clear();
	}
}