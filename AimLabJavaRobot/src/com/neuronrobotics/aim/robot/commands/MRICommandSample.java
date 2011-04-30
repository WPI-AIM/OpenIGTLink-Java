package com.neuronrobotics.aim.robot.commands;

import com.neuronrobotics.sdk.common.BowlerAbstractCommand;
import com.neuronrobotics.sdk.common.BowlerMethod;

public class MRICommandSample extends BowlerAbstractCommand {
	public MRICommandSample(){
		setOpCode("test");
		setMethod(BowlerMethod.GET);
	}
}
