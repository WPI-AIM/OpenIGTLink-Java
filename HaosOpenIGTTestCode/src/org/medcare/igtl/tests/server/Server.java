package org.medcare.igtl.tests.server;

//import java.io.IOException;
import org.medcare.igtl.tests.client.MyClientErrorManager;
//import org.medcare.*;
import org.medcare.igtl.util.ErrorManager;

//import /com.neuronrobotics.sdk.dyio.DyIO;
import com.neuronrobotics.sdk.common.Log;
import com.neuronrobotics.sdk.genericdevice.GenericPIDDevice;
//import com.neuronrobotics.sdk.genericdevice.GenericPIDDevice;
// import com.neuronrobotics.sdk.serial.SerialConnection;
//import com.neuronrobotics.sdk.ui.ConnectionDialog;
//import com.neuronrobotics.sdk.serial.SerialConnection;
import com.neuronrobotics.sdk.ui.ConnectionDialog;

public class Server {

        public static HaosOpenIGTServer openIGTServer;
        private static MyClientErrorManager errorManager;
        /**
         * @param args
         */
        public static void main(String[] args) {
        	try{
        		Log.enableDebugPrint(true);
        		GenericPIDDevice d = new GenericPIDDevice();
	        	if(!ConnectionDialog.getBowlerDevice(d)){
	        		throw new RuntimeException("Failed to connect");
	        	}
	        	
	        	System.out.println("Starting with PID device");
	            int port = 8001; //Default value for port number
	            if (args.length > 0) {
	                  for (int index = 0; index < args.length; index++) {
	                          String arg = args[index].trim();
	                          if (index == 0)
	                                  port = Integer.parseInt(arg);
	                  }
	            }
	            errorManager = new MyClientErrorManager();
	            try {
	            	System.out.println("Starting IGT server");
	                // MessageHandler.perform can a answer by using ServerThread.sendBytes in perform method of MessageHandler
	               openIGTServer = new HaosOpenIGTServer(port, errorManager, null);
	               System.out.println("Started IGT server");  
	            }catch (Exception e) {
	            	e.printStackTrace();
	                errorManager.error("Server on port : " + port + " Get an exception", e, ErrorManager.APPLICATION_EXCEPTION);
	            }
	            
        	}catch (Exception e){
        		e.printStackTrace();
        		System.err.println("Exiting..");
        		System.exit(2);
        	}
        }
}
