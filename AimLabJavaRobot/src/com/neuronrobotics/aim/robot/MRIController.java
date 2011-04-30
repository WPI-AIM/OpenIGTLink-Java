package com.neuronrobotics.aim.robot;

import com.neuronrobotics.sdk.genericdevice.GenericPIDDevice;

public class MRIController extends GenericPIDDevice {

	@Override
	public boolean connect(){
		super.connect();
		System.out.println("Starting MRI Controller");
		startHeartBeat(2000);
		return isAvailable();
	}
}
