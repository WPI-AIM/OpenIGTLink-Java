package haosu.test.examples;

public class CheckIfStringIsDoubleType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s="1236547890.2j";
		boolean mat = s.matches("\\d+");
		System.out.println(mat);
		
		try{
			Double.parseDouble(s);
			}catch(NumberFormatException e){
			  System.out.println("The "+s+" isn't a number");
			}
//		finally{
//			System.out.println("The "+s+" is a number");
//			}
			

	}

}
