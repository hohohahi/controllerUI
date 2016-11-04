package com.bottle.ui.components.admin;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class AdminPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public AdminPanel() {
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(47, 0, 329, 47);
		add(tabbedPane);

		OperationLogPanel operatorLogPanel = new OperationLogPanel();
		tabbedPane.add("Operation Log", operatorLogPanel);
		
		ParameterConfigPanel parameterConfigPanel = new ParameterConfigPanel();
		tabbedPane.addTab("Parameter Config", parameterConfigPanel);
	}
}
