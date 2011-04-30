package com.neuronrobotics.aim.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class EventResponseTable extends JTable {

	private static final long serialVersionUID = 1L;

	public boolean isDouble(String string){
   	 try{
 			Double.parseDouble(string);
 			}catch(NumberFormatException e){
 			  System.out.println("The "+string+" isn't a number");
 		   	return false;
 			}
 			System.out.println("The "+string+" is a number");
 			return true;	
      }
	
	public EventResponseTable(double[][] tableElements, int[] bounds) {
		this.setBorder(new CompoundBorder());
		this.setModel(new DefaultTableModel(new Object[][] {
				{ tableElements[0][0], tableElements[0][1],
						tableElements[0][2], tableElements[0][3] },
				{ tableElements[1][0], tableElements[1][1],
						tableElements[1][2], tableElements[1][3] },
				{ tableElements[2][0], tableElements[2][1],
						tableElements[2][2], tableElements[2][3] },
				{ tableElements[3][0], tableElements[3][1],
						tableElements[3][2], tableElements[3][3] }, },
				new String[] { "t", "s", "n", "Position" }) {
			private static final long serialVersionUID = -2115861646443740429L;
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { Double.class, Double.class,
					Double.class, Double.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
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
		//this.setBounds(10, 94, 200, 62);
		this.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		// begin table event listener
		this.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				
				int row = e.getFirstRow();
				int column = e.getColumn();
				TableModel model = (TableModel) e.getSource();
				Object data = model.getValueAt(row, column);
				String dataString = data.toString();
				
			//	System.out.println(data);	
			//	boolean allStringAreNumbers = true; 
				// If we find a non-digit character
//				for (int i = 0; i < dataString.length(); i++) {
//					if (!Character.isDigit(dataString.charAt(i))) {
//						allStringAreNumbers = false;
//						String message = "\"The Comedy of Errors\"\n";
//						JOptionPane.showMessageDialog(new JFrame(), message,
//								"Dialog", JOptionPane.YES_NO_OPTION);
//						break;
//					}
//				}
//				allStringAreNumbers = true;
//				if (allStringAreNumbers) {
//					double aDouble = Double.parseDouble(dataString);
//					System.out.println("Column " + column + "  Row " + row
//							+ "  " + aDouble);
//				}
				boolean isDouble = isDouble(dataString);
				if (isDouble==false)
				{
					String message = "\"The table element must be numbers\"\n";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.YES_NO_OPTION);
				}
				else {
					double aDouble = Double.parseDouble(dataString);
					System.out.println("Column "  + "  Row " + "  " + aDouble);
				}
				
			}
		});
		// end of table event listener
	}

}
