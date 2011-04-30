package com.neuronrobotics.aim.robot;

import com.neuronrobotics.sdk.pid.IPIDControl;

public interface IKinematicsModel {
	//æŽ¥å?£å®šä¹‰çš„å?˜é‡?éƒ½æ˜¯public final staticï¼Œä¸?èƒ½æ”¹å?˜
	//æŽ¥å?£æ²¡æœ‰æž„é€ å‡½æ•°
	
/*	public double[] positionVector= new double[3];
	public double[][] rotationMatrix= new double[3][3];
	public double[][] transformMatrix= new double[4][4];*/
	
	
	public void setDevice(IPIDControl device);
	
	public void setFrameTransformation(FrameTransformation frame);
	
	public void setPosition(CartesianSpacePosition pos);
	public CartesianSpacePosition getPosition();
	
	public void setPosition(RasSpacePosition pos);
	public RasSpacePosition getRasPosition();
}
