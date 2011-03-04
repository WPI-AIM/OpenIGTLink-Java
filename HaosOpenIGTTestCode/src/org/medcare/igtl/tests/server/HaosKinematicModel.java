package org.medcare.igtl.tests.server;

import org.medcare.robot.CartesianSpacePosition;
import org.medcare.robot.FrameTransformation;
import org.medcare.robot.IKinematicsModel;
import org.medcare.robot.RasSpacePosition;

import com.neuronrobotics.sdk.pid.IPIDControl;

public class HaosKinematicModel implements IKinematicsModel {

	@Override
	public void setDevice(IPIDControl device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFrameTransformation(FrameTransformation frame) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPosition(CartesianSpacePosition pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public CartesianSpacePosition getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(RasSpacePosition pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public RasSpacePosition getRasPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
