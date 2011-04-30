package org.medcare.igtl.tests.server;

import java.lang.Math;

import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.messages.PositionMessage;
import org.medcare.igtl.messages.TransformMessage;
import org.medcare.igtl.util.Header;
import org.medcare.robot.CartesianSpacePosition;
import org.medcare.robot.FrameTransformation;
import org.medcare.robot.IKinematicsModel;
import org.medcare.robot.RasSpacePosition;

//import Jama.Matrix;

import Jama.Matrix;

import com.neuronrobotics.sdk.common.ByteList;
import com.neuronrobotics.sdk.pid.IPIDControl;

public class HaosKinematicModel implements IKinematicsModel {
	private double[][] rotationMatrix= new double[3][3];
	private double[] positionVector= new double[3];
	private double[][] transformMatrix= new double[4][4];
	
	private double[][] ZFrameTransformMatrix = new double[4][4];
	private double[] rasTargetVector = new double[3];
	private double[][] rasTargetMatrix = new double[4][4];
	private double[] tipInBaseVector= new double[3];
	private double[][] TipInBaseMatrix = new double[4][4];
	 
	private boolean targetFlag=false;
	private boolean zFrameFlag=false;
	
	////////////////////////////////////////////////////////////
	// these data are from calibration or SolidWorks
	// find this vector from calibration (the number below are from SolidWorks)
	public final double[] baseinZFrameVector= {0.,-177.133,-94.150};
	public final double[][] baseinZFrameMatrix= {{1.,0.,0.,baseinZFrameVector[0]},{0.,1.,0.,baseinZFrameVector[1]},{0.,0.,1.,baseinZFrameVector[2]},{0.,0.,0.,1.0}};
	// the initial height of {Robot} in {Base} from SolidWokrs
	public final double Y0=151.133;
	// the initial Z direction offset of {Robot} in {Base} from SolidWokrs
	public final double Z01=0.0;
	// the initial needle length in {Robot} measure from real needle
	public final double Z02=120.0;
	// L1 the initial link length of the bottom bar
	public final double L0=69.03;
	// L1 the link length of the lower bar(80mm in total)
	public final double L1=40.0;
	
	////////////////////////////////////////////////////////////

	// Linear encoder convention ratio: 1 inch has 500 ticks=25.4/500=0.0508
	public final double encoderticks2mm= 0.0508;
	// Rotary encoder convention ratio: 1 round has 500 ticks=360/500=0.72, pitch is 2mm/rotation=2mm/360degree
	// TODO list: check this ratio
	public final double encoderticks2degree= 2/360*0.72;
	
	@SuppressWarnings("unused")
	private FrameTransformation frameTransform =null;
	private CartesianSpacePosition cartesianSpace = new CartesianSpacePosition();
	private RasSpacePosition rasSpace = new RasSpacePosition();
	private IPIDControl device =null;
	
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
	
	// suhao:  this function must throws exception because "PositionMessage" throws Exception
	public void moveToResponse (Header header, byte[] body, OpenIGTMessage openIGTMessage) throws Exception{
		System.out.println("new function++++++++++++++++++++");
		
		System.out.println("perform  MOVE_TO");
		openIGTMessage = new PositionMessage(header, body);
		PositionMessage pos = (PositionMessage) openIGTMessage;
		pos.UnpackBody();
		double[] position = pos.getPosition();
		// double [] quad = pos.getQuaternion();
		System.out.println("##############Setting BowlerDevice Position: "+ position[0]);
		System.out.println("Byte data: " + pos);

		try {
			if (this == null)
				System.out.println("PID device is null!!!!!!!!!!!!!!!!!");
			if(position[1]>1023)
				position[1] = 1023;
			if(position[1]<0)
				position[1] = 0;
			
//			DyPIDConfiguration dypid = new DyPIDConfiguration(0,12,DyIOChannelMode.ANALOG_IN,11,DyIOChannelMode.SERVO_OUT);
//			PIDConfiguration pid =new PIDConfiguration (0,true,true,true,1,0,0);
			//dyio.ConfigureDynamicPIDChannels(dypid);
//			dyio.ConfigurePIDController(pid);
//			
			//dyio.SetPIDSetPoint(0, (int) position[1], 0.0);
			//RasSpacePosition ras= new RasSpacePosition(null,position);
			//model.setPosition(ras);
			
			this.setTargetFlag(true);
			this.setRasTargetVector(position);
			
			//TODO 
			if ((this.isTargetFlag()==true)&&(this.iszFrameFlag()==true)){
				// construct the matrix
				 double[][] zFrameMatrixArray =  this.getZFrameTransformMatrix();
				 this.setRasTargetMatrix(position);
				 double[][] rasTargetMatrixArray =  this.getRasTargetMatrix();
				
				 // construct the matrix array
				 Matrix rasTargetMatrix = new Matrix(rasTargetMatrixArray);
				 Matrix zFrameMatrix = new Matrix(zFrameMatrixArray);		
				 Matrix baseInZFrameMatrix= new Matrix(this.baseinZFrameMatrix);
				
				 //multiplication and inverse
				 Matrix baseInImgMatrix= zFrameMatrix.times(baseInZFrameMatrix);
				 Matrix tipInBaseMatrix= rasTargetMatrix.times(baseInImgMatrix.inverse());
				 double[][] tipInBaseMatrixArray= tipInBaseMatrix.getArray();
				 
				 // find the tip in base vector
				 this.setTipInBaseMatrix(tipInBaseMatrixArray);
				 double[] tipInBaseVector= this.getPositionVector(this.getTipInBaseMatrix());
				 System.out.println("Robot motion vector is "+ tipInBaseVector);
				 double[] jointSpaceVector= this.Cartesian2JointSpace(tipInBaseVector);
				 double [] encoderTickVector = this.JointSpace2EncoderTicks(jointSpaceVector);
				 
				 this.sendToMotors((int) encoderTickVector[0],(int) encoderTickVector[1],(int) encoderTickVector[2],1,10.0);
				 
				 this.setzFrameFlag(false);
				 this.setTargetFlag(false);
				// double[] cartesianPositionVector =robotMotionVector; 
				
			}
			System.out.println(" PID device Servoing to "+position[1] );
		} catch (Exception e) {
			System.err.println("#*#*#*#*Failed to set position");
			e.printStackTrace();
		}
		System.out
				.println("##############Setting BowlerDevice Position ok");
	}
	
	public void transformResponse (Header header, byte[] body, OpenIGTMessage openIGTMessage) throws Exception {
		System.out.println("*********************************");
		
		openIGTMessage = new TransformMessage(header, body);
		TransformMessage transfm = (TransformMessage) openIGTMessage;
		transfm.Unpack();
		// double[][] position=transfm.GetMatrix();
	
		double[] position = transfm.GetOrigin();
		// double[][] rotation=transfm.GetNormals();
		System.out.println("#@# Body data: "
				+ new ByteList(openIGTMessage.body));
		System.out.println("reading transform matrix from the client");
		for (int i = 0; i < position.length; i++) {
			System.out.println("XYZ Byte data: " + position[i]);
		}
		double[][] rotation=transfm.GetNormals();
		System.out.println("*********" + transfm.deviceName);
		System.out.println("Transform body Byte data: " + transfm);
		System.out.println("Rotation data: " + rotation);
		
		if (transfm.deviceName.equals("ZFrameTransform"))
		{
			this.setzFrameFlag(true);
		//	this.setFrameTransformation(new FrameTransformation(null));   
			this.setZFrameTransformMatrix(transfm.GetMatrix());
			System.out.println("Z Frame transform initilized");
		
		}
	  	/*try {
			if (dyio != null) {
				dyio.SetPIDSetPoint(0, (int) position[0], 0.0);
				System.out.println("\n X position" + position[0]);
			} else {
				System.out.println("NO DIYO");
			}
		} catch (Exception e) {
			System.err.println("#*#*#*#*Failed to set position");
			e.printStackTrace();
		}
		System.out
				.println("##############Setting BowlerDevice Position ok");*/
	}
	
	// inverse kinematics
	public double[] Cartesian2JointSpace(double[] cartesianSpaceVector) {
		double[] jointSpaceVector=new double[3];
		//joint x
		jointSpaceVector[0]=cartesianSpaceVector[0];
		jointSpaceVector[1]=L0-2*Math.sqrt(Math.pow(L1, 2)-Math.pow(4/7*(cartesianSpaceVector[1]-Y0), 2));
		jointSpaceVector[2]=cartesianSpaceVector[2]-Z01-Z02;
		return jointSpaceVector;
		}
	
	public void Cartesian2JointSpace(CartesianSpacePosition pos) {
		this.cartesianSpace = pos;
		}
	
	public void Joint2CartesianSpace(CartesianSpacePosition pos) {
		this.cartesianSpace = pos;
		}
	
	// forward kinematics
	public double[] Joint2CartesianSpace(double[] cartesianSpaceVector) {
		double[] jointSpaceVector=new double[3];
		//joint x
		jointSpaceVector[0]=cartesianSpaceVector[0];
		jointSpaceVector[1]=L0-2*Math.sqrt(Math.pow(L1, 2)-Math.pow(4/7*(cartesianSpaceVector[1]-Y0), 2));
		jointSpaceVector[2]=cartesianSpaceVector[2]-Z01-Z02;
		return jointSpaceVector;
		}
	
	
	private int [] setpoints;
	
	public void sendToMotors(int xTicks, int yTicks, int zTicks, int rotationTicks, double time){
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

	public void setRasTargetMatrix(double[] rasTargetVector) {
		this.rasTargetMatrix[0][3] = rasTargetVector[0];
		this.rasTargetMatrix[1][3] = rasTargetVector[1];
		this.rasTargetMatrix[2][3] = rasTargetVector[2];
		
		this.rasTargetMatrix[0][0] = 1.0;
		this.rasTargetMatrix[0][1] = 0.0;
		this.rasTargetMatrix[0][2] = 0.0;
		this.rasTargetMatrix[1][0] = 0.0;
		this.rasTargetMatrix[1][1] = 1.0;
		this.rasTargetMatrix[1][2] = 0.0;
		this.rasTargetMatrix[2][0] = 0.0;
		this.rasTargetMatrix[2][1] = 0.0;
		this.rasTargetMatrix[2][2] = 1.0;
		
		this.rasTargetMatrix[3][0] = 0.0;
		this.rasTargetMatrix[3][1] = 0.0;
		this.rasTargetMatrix[3][2] = 0.0;
		this.rasTargetMatrix[3][3] = 1.0;
	}

	public double[][] getRasTargetMatrix() {
		return rasTargetMatrix;
	}

	public void setTipInBaseVector(double[] tipInBaseVector) {
		this.tipInBaseVector = tipInBaseVector;
	}

	public double[] getTipInBaseVector() {
		return tipInBaseVector;
	}
	
	// this function is not actually used, the used one is getPositionVector
	public double[] getTipInBaseVector(double[][] matrixArray) {
		double[] tipInBaseVector=new double[3];
		if(matrixArray.length!=4){
			System.out.println("Matrix demension should be 4!");
			tipInBaseVector[0]=0;
			tipInBaseVector[1]=300;
			tipInBaseVector[2]=160;
		}
		else{
			tipInBaseVector[0]=matrixArray[0][3];
			tipInBaseVector[1]=matrixArray[1][3];
			tipInBaseVector[2]=matrixArray[2][3];
		}
		return tipInBaseVector;
	}

	public void setTipInBaseMatrix(double[][] tipInBaseMatrix) {
		TipInBaseMatrix = tipInBaseMatrix;
	}

	public double[][] getTipInBaseMatrix() {
		return TipInBaseMatrix;
	}

	public double[] getPositionVector(double[][] matrixArray) {
		double[] tipInBaseVector=new double[3];
		if(matrixArray.length!=4){
			System.out.println("Matrix demension should be 4!");
			tipInBaseVector[0]=0;
			tipInBaseVector[1]=300;
			tipInBaseVector[2]=160;
		}
		else{
			tipInBaseVector[0]=matrixArray[0][3];
			tipInBaseVector[1]=matrixArray[1][3];
			tipInBaseVector[2]=matrixArray[2][3];
		}
		return tipInBaseVector;
	}
	
	public double[] JointSpace2EncoderTicks(double[] jointSpaceVector) {
		double[] encoderTicks=new double[3];
		encoderTicks[0]=jointSpaceVector[0]/encoderticks2mm;
		encoderTicks[1]=jointSpaceVector[1]/encoderticks2degree;
		encoderTicks[2]=jointSpaceVector[2]/encoderticks2mm;
		
		return encoderTicks;
		}
	
}
