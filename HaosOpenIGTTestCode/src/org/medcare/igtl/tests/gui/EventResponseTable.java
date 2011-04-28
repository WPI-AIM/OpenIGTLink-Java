package org.medcare.igtl.tests.examples;

import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;

public class EventResponseTable extends JTable {

	private static final long serialVersionUID = 1L;

	public void EventResponseTable() {
		this.setBorder(new CompoundBorder());
		this.setModel(new DefaultTableModel(new Object[][] {
				{ new Double(1.0), new Double(0.0), new Double(0.0), new Double(0.1) },
				{ new Double(0.0), new Double(1.0), new Double(0.0), new Double(0.1) },
				{ new Double(0.0), new Double(0.0), new Double(1.0), new Double(0.1) },
				{ new Double(0.0), new Double(0.0), new Double(0.0), new Double(1.0) }, }, 
				  new String[] { "t", "s", "n", "Position" }) 
		{
			private static final long serialVersionUID = -2115861646443740429L;
			Class[] columnTypes = new Class[] { Double.class, Double.class,
					Double.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		this.getColumnModel().getColumn(0).setResizable(false);
		this.getColumnModel().getColumn(0).setMaxWidth(100);
		this.getColumnModel().getColumn(1).setResizable(false);
		this.getColumnModel().getColumn(2).setResizable(false);
		this.getColumnModel().getColumn(3).setResizable(false);
		this.getColumnModel().getColumn(3).setMaxWidth(100);
		this.setBounds(10, 94, 200, 62);
	}

	public EventResponseTable(double[][] tableElements) {
		this.setBorder(new CompoundBorder());
		this.setModel(new DefaultTableModel(new Object[][] {
				{ tableElements[0][0], tableElements[0][1], tableElements[0][2], tableElements[0][3] },
				{ tableElements[1][0], tableElements[1][1], tableElements[1][2], tableElements[1][3] },
				{ tableElements[2][0], tableElements[2][1], tableElements[2][2], tableElements[2][3] },
				{ tableElements[3][0], tableElements[3][1], tableElements[3][2], tableElements[3][3] }, }, 
				  new String[] { "t", "s", "n", "Position" }) 				
				{
			private static final long serialVersionUID = -2115861646443740429L;
			Class[] columnTypes = new Class[] { Double.class, Double.class,
					Double.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		this.getColumnModel().getColumn(0).setResizable(false);
		this.getColumnModel().getColumn(0).setMaxWidth(100);
		this.getColumnModel().getColumn(1).setResizable(false);
		this.getColumnModel().getColumn(2).setResizable(false);
		this.getColumnModel().getColumn(3).setResizable(false);
		this.getColumnModel().getColumn(3).setMaxWidth(100);
		this.setBounds(10, 94, 200, 62);
	}
	// public EventResponseTable(Object[][])
	// {
	//
	// }
}
