package org.medcare.igtl.tests.server;

import org.medcare.robot.CartesianSpacePosition;
import org.medcare.robot.FrameTransformation;
import org.medcare.robot.IKinematicsModel;
import org.medcare.robot.RasSpacePosition;

import com.neuronrobotics.sdk.pid.IPIDControl;

public class HaosKinematicModel implements IKinematicsModel {
// constructor to setup the transform matrix which is consists of the rotationMatrix and positionVector.
	public HaosKinematicModel(){
		
		IKinematicsModel.transformMatrix[0][0] = IKinematicsModel.rotationMatrix[0][0];
		IKinematicsModel.transformMatrix[1][0] = IKinematicsModel.rotationMatrix[1][0];
		IKinematicsModel.transformMatrix[2][0] = IKinematicsModel.rotationMatrix[2][0];
		IKinematicsModel.transformMatrix[0][1] = IKinematicsModel.rotationMatrix[0][1];
		IKinematicsModel.transformMatrix[1][1] = IKinematicsModel.rotationMatrix[1][1];
		IKinematicsModel.transformMatrix[2][1] = IKinematicsModel.rotationMatrix[2][1];
		IKinematicsModel.transformMatrix[0][2] = IKinematicsModel.rotationMatrix[0][2];
		IKinematicsModel.transformMatrix[1][2] = IKinematicsModel.rotationMatrix[1][2];
		IKinematicsModel.transformMatrix[2][2] = IKinematicsModel.rotationMatrix[2][2];
		IKinematicsModel.transformMatrix[0][3] = IKinematicsModel.positionVector[0];
		IKinematicsModel.transformMatrix[1][3] = IKinematicsModel.positionVector[1];
		IKinematicsModel.transformMatrix[3][0] = 0.0;
		IKinematicsModel.transformMatrix[3][1] = 0.0;
		IKinematicsModel.transformMatrix[3][2] = 0.0;
		IKinematicsModel.transformMatrix[3][3] = 1.0;
        }
	@SuppressWarnings("unused")
	private FrameTransformation frameTransform =null;
	private CartesianSpacePosition cartesianSpace = new CartesianSpacePosition();
	private RasSpacePosition rasSpace = new RasSpacePosition();
	private IPIDControl device =null;
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
}
