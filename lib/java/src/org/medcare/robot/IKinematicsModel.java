package org.medcare.robot;

import com.neuronrobotics.sdk.pid.IPIDControl;

public interface IKinematicsModel {
	public double[] positionVector= new double[3];
	public double[][] rotationMatrix= new double[3][3];
	public double[][] transformMatrix= new double[4][4];
	
	// Linear encoder convention ratio: 1 inch has 500 ticks=25.4/500=0.0508
	public static double encoderticks2mm= 0.0508;
	// Rotary encoder convention ratio: 1 round has 500 ticks=360/500=0.72 
	// TODO list: check this ratio
	public static double encoderticks2degree= 0.72;
	
	public void setDevice(IPIDControl device);
	
	public void setFrameTransformation(FrameTransformation frame);
	
	public void setPosition(CartesianSpacePosition pos);
	public CartesianSpacePosition getPosition();
	
	public void setPosition(RasSpacePosition pos);
	public RasSpacePosition getRasPosition();
}
