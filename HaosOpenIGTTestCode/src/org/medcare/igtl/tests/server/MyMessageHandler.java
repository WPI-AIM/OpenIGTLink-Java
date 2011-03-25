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
import org.medcare.robot.FrameTransformation;
//import org.medcare.robot.IKinematicsModel;
import org.medcare.robot.RasSpacePosition;

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
		// capabilityList.add("POSITION");
		capabilityList.add("IMAGE");
		capabilityList.add("STATUS");
		capabilityList.add("MOVE_TO");
		// important commands
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
		capabilityList.add("EMERGENCY");
				 
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
				openIGTMessage = new TransformMessage(header, body);
				TransformMessage transfm = (TransformMessage) openIGTMessage;
				transfm.Unpack();
				// double[][] position=transfm.GetMatrix();
			
				double[] position = transfm.GetOrigin();
				// double[][] rotation=transfm.GetNormals();
				System.out.println("#@# Body data: "
						+ new ByteList(openIGTMessage.body));
				System.out.println("reading transform matrix from the client");
				for (int i = 0; i < position.length; i++) {
					System.out.println("XYZ Byte data: " + position[i]);
				}
				double[][] rotation=transfm.GetNormals();
				System.out.println("*********" + transfm.deviceName);
				System.out.println("Transform body Byte data: " + transfm);
				System.out.println("Rotation data: " + rotation);
				
				if (transfm.deviceName.equals("ZFrameTransform"))
				{
					model.setzFrameFlag(true);
				//	model.setFrameTransformation(new FrameTransformation(null));   
					model.setZFrameTransformMatrix(transfm.GetMatrix());
				
				}
			   
			    
			    
				/*try {
					if (dyio != null) {
						dyio.SetPIDSetPoint(0, (int) position[0], 0.0);
						System.out.println("\n X position" + position[0]);
					} else {
						System.out.println("NO DIYO");
					}
				} catch (Exception e) {
					System.err.println("#*#*#*#*Failed to set position");
					e.printStackTrace();
				}
				System.out
						.println("##############Setting BowlerDevice Position ok");*/

			} else if (messageType.equals("MOVE_TO")) {
				System.out.println("perform  POSITION");
				openIGTMessage = new PositionMessage(header, body);
				PositionMessage pos = (PositionMessage) openIGTMessage;
				pos.UnpackBody();
				double[] position = pos.getPosition();
				// double [] quad = pos.getQuaternion();
				System.out
						.println("##############Setting BowlerDevice Position: "
								+ position[0]);
				System.out.println("Byte data: " + pos);

				try {
					if (model == null)
						System.out.println("PID device is null!!!!!!!!!!!!!!!!!");
					if(position[1]>1023)
						position[1] = 1023;
					if(position[1]<0)
						position[1] = 0;
					
//					DyPIDConfiguration dypid = new DyPIDConfiguration(0,12,DyIOChannelMode.ANALOG_IN,11,DyIOChannelMode.SERVO_OUT);
//					PIDConfiguration pid =new PIDConfiguration (0,true,true,true,1,0,0);
					//dyio.ConfigureDynamicPIDChannels(dypid);
//					dyio.ConfigurePIDController(pid);
//					
					//dyio.SetPIDSetPoint(0, (int) position[1], 0.0);
					//RasSpacePosition ras= new RasSpacePosition(null,position);
					//model.setPosition(ras);
					
					model.setTargetFlag(true);
					model.setRasTargetVector(position);
					
					//TODO 
					if ((model.isTargetFlag()==true)&&(model.iszFrameFlag()==true)){
						 double[][] matrixAArray =  model.getZFrameTransformMatrix();
						 double[][] matrixBArray =  {{2.,0.,0.},{0.,3.,0.},{0.,0.,4.}};
						 Matrix A = new Matrix(matrixAArray);
						 Matrix B = new Matrix(matrixBArray);
						 Matrix Binv=B.inverse();
						 System.out.println("B inverse is" +Binv);
						 Matrix AB= A.times(B);
						 System.out.println("A times B is" + AB);
						
					}
					
					System.out.println(" PID device Servoing to "+position[1] );
				} catch (Exception e) {
					System.err.println("#*#*#*#*Failed to set position");
					e.printStackTrace();
				}
				System.out
						.println("##############Setting BowlerDevice Position ok");

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
