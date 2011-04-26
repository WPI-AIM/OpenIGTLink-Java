package haosu.test.codes;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.*;
//import javax.swing.table.*;

public class GetRowsAndCols{
	public static void main(String[] args) {
		new GetRowsAndCols();
	}
	public GetRowsAndCols(){
		JFrame frame = new JFrame("Getting Rows and Columns from a Table!");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(new Dimension( 300,  300));
		Dimension windowSize = frame.getSize();

		int windowX = Math.max(0, (screenSize.width  - windowSize.width ) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		frame.setLocation(windowX, windowY);  // Don't use "f." inside constructor.
		
		JPanel panel = new JPanel();
		String data[][] = {{"Vinod","5000","Java"},{"Deepak","14000","developer"},
				{"Sushil","20000","writter"},{"Chandan","15000","Programmer"}};
		String col[] = {"Emp_name","Emp_sal","Depart"};
		JTable table = new JTable(data,col);
		int ncol = table.getColumnCount();
		System.out.println("Number of Columns: "+ ncol);
		System.out.println("Number of Rows: "+ table.getRowCount());
		System.out.println("Name of column:");
		
		
		panel.add(table);
		frame.add(panel);
		frame.setSize(300,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
