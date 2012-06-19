package org.medcare.igtl.util;

import java.awt.image.BufferedImage;

import org.medcare.igtl.messages.ImageMessage;

import edu.wpi.robotics.aim.core.math.Transform;

public class IGTImage {
	ImageMessage message;
	BufferedImage image = null;
	Transform transform = new Transform(); 
	public IGTImage(ImageMessage m){
		message=m;
		//TODO Satya, populate all fields.
		image = null;
		transform = new Transform(); 
	}
	
	public Transform getTransform(){
		
		return transform;
	}
	
	public BufferedImage getBufferedImage(){
		return image;
	}
}
