package org.medcare.robot;

import com.neuronrobotics.sdk.pid.IPIDControl;

public interface IKinematicsModel {
	public void setDevice(IPIDControl device);
	public void setFrameTransformation(FrameTransformation frame);
	
	public void setPosition(CartesianSpacePosition pos);
	public CartesianSpacePosition getPosition();
	
	public void setPosition(RasSpacePosition pos);
	public RasSpacePosition getRasPosition();
}
