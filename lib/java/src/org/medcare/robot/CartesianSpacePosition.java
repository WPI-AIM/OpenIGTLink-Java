package org.medcare.robot;

public class CartesianSpacePosition {
	
	private double orientationMatrix[][] = new double[3][3];
	private double positionVector[] = new double[3];
//	private  double matrix[][] = new double[4][4];
	// private static double CartesianSpacePosition[][]=new double [4][4];
	public CartesianSpacePosition(double orientation[][],double position[]){
		this.orientationMatrix=orientation;
		this.positionVector=position;
	}
	public CartesianSpacePosition(){
		
	}
	public void setFrameTransformation(double orientation[][],double position[]) {
			this.orientationMatrix=orientation;
			this.positionVector=position;
	}

	public double[][] getOrientation() {
		return orientationMatrix;
	}
	
	public double[] getPosition() {
		return positionVector;
	}


}
