package org.medcare.igtl.tests.examples;

public class TestNumberArray {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[][] TwoDArray = { { 2, 3, 5, 2.2 }, { 2, 4, 7, 9 } };
		// array length is the number of rows
		System.out.println(TwoDArray.length); // 2 in this case

		// take the first row, then get the length, this is the number of column
		System.out.println(TwoDArray[0].length); // 4 in this case

		// print matrix
		printMatrix(TwoDArray);

	}

	public static void printMatrix(double[][] array) {
		for (int i = 0; i < array.length; i++)
		{
			for (int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + "  ");
			}
		System.out.print("\n");
		}
	}
}
