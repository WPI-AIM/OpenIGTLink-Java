package org.medcare.igtl.tests.server;

import org.medcare.robot.CartesianSpacePosition;
import org.medcare.robot.FrameTransformation;
import org.medcare.robot.IKinematicsModel;
import org.medcare.robot.RasSpacePosition;

//import Jama.Matrix;

import com.neuronrobotics.sdk.pid.IPIDControl;

public class HaosKinematicModel implements IKinematicsModel {
	private double[][] rotationMatrix= new double[3][3];
	private double[] positionVector= new double[3];
	private double[][] transformMatrix= new double[4][4];
	
	private double[][] ZFrameTransformMatrix = new double[4][4];
	private double[] rasTargetVector = new double[3];
	private double[][] rasTargetTransformMatrix = new double[4][4];
	
	private boolean targetFlag=false;
	private boolean zFrameFlag=false;
	
	@SuppressWarnings("unused")
	private FrameTransformation frameTransform =null;
	private CartesianSpacePosition cartesianSpace = new CartesianSpacePosition();
	private RasSpacePosition rasSpace = new RasSpacePosition();
	private IPIDControl device =null;
	
/*	//this is not necessary, can I use system.out.println("matrixC")
	double[][] array = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}}; 
    Matrix matrixA = new Matrix(array); 
    Matrix matrixB = new Matrix(array); 
    Matrix matrixC = matrixA.times(matrixB);
  //  System.out.println("hello");*/
    
    // constructor to assign the values of transformation matrix
	public void HaosKinematicsModel(double rotationMatrix[][],double position[]){
		this.setRotationMatrix(rotationMatrix);
		this.setPositionVector(position);
		this.transformMatrix[0][0] = rotationMatrix[0][0];
		this.transformMatrix[1][0] = rotationMatrix[1][0];
		this.transformMatrix[2][0] = rotationMatrix[2][0];
		this.transformMatrix[0][1] = rotationMatrix[0][1];
		this.transformMatrix[1][1] = rotationMatrix[1][1];
		this.transformMatrix[2][1] = rotationMatrix[2][1];
		this.transformMatrix[0][2] = rotationMatrix[0][2];
		this.transformMatrix[1][2] = rotationMatrix[1][2];
		this.transformMatrix[2][2] = rotationMatrix[2][2];
		this.transformMatrix[0][3] = position[0];
		this.transformMatrix[1][3] = position[1];
		this.transformMatrix[3][0] = 0.0;
		this.transformMatrix[3][1] = 0.0;
		this.transformMatrix[3][2] = 0.0;
		this.transformMatrix[3][3] = 1.0;
		}
	
	@Override
	public void setDevice(IPIDControl device) {
		this.device=device;
	}

	@Override
	public void setFrameTransformation(FrameTransformation frame) {
		this.frameTransform = frame;
	}
	
	@Override
	public void setPosition(CartesianSpacePosition pos) {
		this.cartesianSpace = pos;
		//call conversion
		//send converted values
	}

	@Override
	public CartesianSpacePosition getPosition() {
		return cartesianSpace;
	}

	@Override
	public void setPosition(RasSpacePosition pos) {
		this.rasSpace = pos;
		}

	@Override
	public RasSpacePosition getRasPosition() {
		return rasSpace;
	}
	
	public void Cartesian2JointSpace(CartesianSpacePosition pos) {
		this.cartesianSpace = pos;
		}
	
	public void Joint2CartesianSpace(CartesianSpacePosition pos) {
		this.cartesianSpace = pos;
		}
	
	private int [] setpoints;
	@SuppressWarnings("unused")
	private void sendToMotors(int xTicks, int yTicks, int zTicks, int rotationTicks, double time){
		if(device != null){
			if(setpoints == null)
				setpoints = device.GetAllPIDPosition();
			setpoints[0]=xTicks;
			setpoints[1]=yTicks;
			setpoints[2]=zTicks;
			setpoints[3]=rotationTicks;	
			device.SetAllPIDSetPoint(setpoints, time);
		}else{
			throw new RuntimeException("DyIO is Null!");
		}
	}

	public void setTargetFlag(boolean targetFlag) {
		this.targetFlag = targetFlag;
	}

	public boolean isTargetFlag() {
		return targetFlag;
	}
	

	public void setzFrameFlag(boolean zFrameFlag) {
		this.zFrameFlag = zFrameFlag;
	}
	public boolean iszFrameFlag() {
		return zFrameFlag;
	}
	public void setRotationMatrix(double[][] rotationMatrix) {
		this.rotationMatrix = rotationMatrix;
	}
	public double[][] getRotationMatrix() {
		return rotationMatrix;
	}
	public void setPositionVector(double[] positionVector) {
		this.positionVector = positionVector;
	}
	public double[] getPositionVector() {
		return positionVector;
	}

	public void setZFrameTransformMatrix(double[][] zFrameTransformMatrix) {
		ZFrameTransformMatrix = zFrameTransformMatrix;
	}

	public double[][] getZFrameTransformMatrix() {
		return ZFrameTransformMatrix;
	}

	public void setRasTargetVector(double[] rasTargetVector) {
		this.rasTargetVector = rasTargetVector;
	}

	public double[] getRasTargetVector() {
		return rasTargetVector;
	}

	public void setRasTargetTransformMatrix(double[] rasTargetVector) {
		this.rasTargetTransformMatrix[0][3] = rasTargetVector[0];
		this.rasTargetTransformMatrix[1][3] = rasTargetVector[1];
		this.rasTargetTransformMatrix[2][3] = rasTargetVector[2];
		
		this.rasTargetTransformMatrix[0][0] = 1.0;
		this.rasTargetTransformMatrix[0][1] = 0.0;
		this.rasTargetTransformMatrix[0][2] = 0.0;
		this.rasTargetTransformMatrix[1][0] = 0.0;
		this.rasTargetTransformMatrix[1][1] = 1.0;
		this.rasTargetTransformMatrix[1][2] = 0.0;
		this.rasTargetTransformMatrix[2][0] = 0.0;
		this.rasTargetTransformMatrix[2][1] = 0.0;
		this.rasTargetTransformMatrix[2][2] = 1.0;
		
		this.rasTargetTransformMatrix[3][0] = 0.0;
		this.rasTargetTransformMatrix[3][1] = 0.0;
		this.rasTargetTransformMatrix[3][2] = 0.0;
		this.rasTargetTransformMatrix[3][3] = 1.0;
	}

	public double[][] getRasTargetTransformMatrix() {
		return rasTargetTransformMatrix;
	}
}
