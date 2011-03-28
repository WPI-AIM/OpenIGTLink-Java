package org.medcare.robot;

import com.neuronrobotics.sdk.pid.IPIDControl;

public interface IKinematicsModel {
	//接口定义的变量都是public final static，不能改变
	//接口没有构造函数
	
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
