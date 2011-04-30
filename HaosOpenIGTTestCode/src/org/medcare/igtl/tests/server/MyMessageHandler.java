package org.medcare.igtl.tests.server;

import org.medcare.igtl.messages.GetCapabilityMessage;
import org.medcare.igtl.messages.ImageMessage;
import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.messages.PositionMessage;
import org.medcare.igtl.messages.StatusMessage;
import org.medcare.igtl.messages.TransformMessage;
import org.medcare.igtl.network.MessageHandler;
import org.medcare.igtl.network.ServerThread;
import org.medcare.igtl.util.Header;
//import org.medcare.robot.FrameTransformation;
//import org.medcare.robot.IKinematicsModel;
//import org.medcare.robot.RasSpacePosition;

//import com.neuronrobotics.sdk.common.ByteList;
//import com.neuronrobotics.sdk.dyio.DyIO;
//import com.neuronrobotics.sdk.dyio.DyIO;
import Jama.Matrix;

import com.neuronrobotics.sdk.common.ByteList;
//import com.neuronrobotics.sdk.dyio.DyIOChannelMode;
//import com.neuronrobotics.sdk.dyio.dypid.DyPIDConfiguration;
//import com.neuronrobotics.sdk.pid.IPIDControl;
//import com.neuronrobotics.sdk.pid.*;

//GET_CAPABIL, GET_IMAGE, GET_IMGMETA, GET_LBMETA, GET_STATUS, GET_TRAJ, CAPABILITY, COLORTABLE, IMAGE, IMGMETA, POINT, POSITION, STATUS, STP_TDATA, STT_TDATA, TDATA, TRAJ, TRANSFORM
//import com.neuronrobotics.sdk.serial.SerialConnection;
//import com.neuronrobotics.sdk.pid.PIDConfiguration;

public class MyMessageHandler extends MessageHandler {
	public OpenIGTMessage openIGTMessage;
	private HaosKinematicModel model;
	
	public MyMessageHandler(Header header, byte[] body,
		ServerThread serverThread, HaosKinematicModel m) {
		super(header, body, serverThread);
		capabilityList.add("GET_CAPABIL");
		capabilityList.add("TRANSFORM");
		capabilityList.add("POSITION");
		capabilityList.add("IMAGE");
		capabilityList.add("STATUS");
		capabilityList.add("MOVE_TO");
		/*// important commands
		capabilityList.add("INITIALIZE");
		capabilityList.add("HOME");
		capabilityList.add("GET_COORDINATE");
		capabilityList.add("GET_STATUS");
		// commands to be implemented
		capabilityList.add("SET_Z_FRAME");
		capabilityList.add("START_UP");
		capabilityList.add("TARGETING "); 
		capabilityList.add("INSERT"); 
		capabilityList.add("BIOPSY"); 
		capabilityList.add("EMERGENCY");*/
				 
		if (m == null)
			System.out.println("PID device is null!!");
		model = m;
		}

	@Override 
	public void manageError(String message, Exception exception, int errorCode) {
		serverThread.errorManager.error(message, exception, errorCode);
	}

	@Override
	public boolean perform(String messageType) throws Exception {
		try {
			System.out.println("perform messageType : " + messageType);
			if (messageType.equals("GET_CAPABIL")) {
				openIGTMessage = new GetCapabilityMessage(header, body);
			} else if (messageType.equals("TRANSFORM")) {
				
				model.transformCallback(header, body, openIGTMessage);
						

			} else if (messageType.equals("MOVE_TO")) {
				model.moveToCallback(header, body, openIGTMessage);
				
				} else if (messageType.equals("IMAGE")) {
				openIGTMessage = new ImageMessage(header, body);
			} else if (messageType.equals("STATUS")) {
				openIGTMessage = new StatusMessage(header, body);
			} else {
				System.out.println("Perform messageType : " + messageType
						+ " not implemented");
				return false;
			}
			// System.out.println("Perform messageType : " + messageType +
			// " content : " + openIGTMessage.toString());
			// PositionMessage positionMessage = new PositionMessage("Traker");
			// double[] quaternion = {0.0, 0.6666666666, 0.577350269189626,
			// 0.6666666666};
			// int quaternionSize = PositionMessage.ALL;
			// double[] position = {0.0, 50.0, 50.0};
			// positionMessage.setPositionData(position, quaternion,
			// quaternionSize);
			// System.out.println("perform messageType SendBytes");
			// serverThread.sendBytes(positionMessage.PackBody());
			// System.out.println("perform messageType SendBytes done");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
