package com.neuronrobotics.aim.robot.xml;
import java.io.InputStream;

public class MRIXmlFactory {
	
	public static InputStream getDefaultConfigurationStream(String file) {
		return MRIXmlFactory.class.getResourceAsStream(file);
	}
}

