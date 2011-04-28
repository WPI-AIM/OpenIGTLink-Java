package haosu.test.codes;

//import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.security.AllPermission;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
// import javax.swing.event.TableModelEvent;
// import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

public class TableModelListenerDemo {
	public static void main(String args[]) {

		final Object rowData[][] = { { "1", "one", "I" }, { "2", "two", "II" },
				{ "3", "three", "III" } };

		final String columnNames[] = { "Number", "English", "Roman" };

		final JTable table = new JTable(rowData, columnNames);

		JPanel content = new JPanel(new MigLayout());
		JScrollPane scrollPane = new JScrollPane(table);
		JButton editableButton = new JButton("Status switch");

		editableButton.addActionListener(new ActionListener() {
			boolean enableTable = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				enableTable = !enableTable;
				table.setEnabled(enableTable);
			}
		});

		content.add(scrollPane);
		content.add(editableButton);

		final JFrame frame = new JFrame("Resizing Table");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(new Dimension(300, 300));
		Dimension windowSize = frame.getSize();

		int windowX = Math.max(0, (screenSize.width - windowSize.width) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		frame.setLocation(windowX, windowY); 
		frame.setContentPane(content);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(scrollPane);
		frame.add(editableButton);
		frame.setSize(300, 150);
		frame.setVisible(true);

		// begin table event listener
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				// System.out.println(e);
				int row = e.getFirstRow();
				int column = e.getColumn();
				TableModel model = (TableModel) e.getSource();
				// String columnName = model.getColumnName(column);
				Object data = model.getValueAt(row, column);
				String dataString = data.toString();
				// Class classType = data.getClass();
				boolean allString = true; // If we find a non-digit character
				for (int i = 0; i < dataString.length(); i++) {

					if (!Character.isDigit(dataString.charAt(i))) {
						allString = false;
						String message = "\"The Comedy of Errors\"\n";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.YES_NO_OPTION);
						break;
					}
					allString = true;
				}

				if (allString) {
					double aDouble = Double.parseDouble(dataString);
//					System.out.println("Column " + column + "  Row " + row
//							+ "  " + data);
//					System.out.println("Class type is  " + aDouble);
					System.out.println("Column " + column + "  Row " + row
							+ "  " + aDouble);
					
				}
			}
		});
		// end of table event listener

	}
}