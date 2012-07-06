package org.medcare.igtl.network;

import org.medcare.igtl.messages.ImageMessage;
import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.messages.PositionMessage;
import org.medcare.igtl.messages.TransformMessage;
import org.medcare.igtl.util.Header;

import com.neuronrobotics.sdk.addons.kinematics.math.RotationNR;
import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;

public class GenericMessageNodeHandler {
	
	public OpenIGTMessage openIGTMessage;

	public OpenIGTMessage perform(String messageType,Header head, byte[] body, IOpenIgtPacketListener node) throws Exception {
        //System.out.println("perform messageType : " + messageType);
		//Log.info("Recived IGTLink packet, header="+head+" body="+new ByteList( body));
        
		//TODO - GSF: Need to add complete set of new IGTLInk commands for BRP robot
		//http://wiki.ncigt.org/index.php/P41:Prostate:BRP:MRI_New_BRP_OpenIGTLink_Protocol_2012_Mar
		//Should support both TRANSFORM and QTRANSFORM packets
		
		if  (messageType.equals("TRANSFORM")) {
                openIGTMessage = new TransformMessage(head, body);   
        		TransformMessage transform = (TransformMessage) openIGTMessage;
        		transform.Unpack();
        		// Position vector and rotation matrix from the received transform
        		double[] position = transform.getPosition();
        		double[][] rotation=transform.getRotationMatrixArray(); 	
        		TransformNR t =new TransformNR(position, rotation);
        		node.onRxTransform(openIGTMessage.getDeviceName(), t);
        } else if (messageType.equals("POSITION") || messageType.equals("MOVE_TO")) {
                System.out.println("perform POSITION");
                openIGTMessage = new PositionMessage(head, body);   
        		PositionMessage transform = (PositionMessage) openIGTMessage;
        		transform.Unpack();
        		// Position vector and rotation matrix from the received transform
        		double[] position = transform.getPosition();
        		RotationNR rotation=transform.getQuaternion(); 	
        		TransformNR t =new TransformNR(position, rotation);
        		node.onRxTransform(openIGTMessage.getDeviceName(), t);
        } else if (messageType.equals("IMAGE")) {
        	ImageMessage imgMesg = new ImageMessage(head, body);
			openIGTMessage =(OpenIGTMessage)imgMesg;
			imgMesg.Unpack();
			node.onRxImage(openIGTMessage.getDeviceName(),imgMesg);
        }else {
                System.out.println("Perform messageType : " + messageType + " not implemented");
                return null;
        }
        return openIGTMessage;
	}
}
