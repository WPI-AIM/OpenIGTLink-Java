package org.medcare.igtl.tests.server;

import org.medcare.igtl.network.MessageHandler;
import org.medcare.igtl.network.OpenIGTServer;
import org.medcare.igtl.network.ServerThread;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Header;
//import org.medcare.robot.IKinematicsModel;

// import com.neuronrobotics.sdk.dyio.DyIO;
import com.neuronrobotics.sdk.common.ByteList;
//import com.neuronrobotics.sdk.pid.IPIDControl;

public class HaosOpenIGTServer extends OpenIGTServer {
		private HaosKinematicModel model;
        public HaosOpenIGTServer(int port, ErrorManager errorManager,HaosKinematicModel m) throws Exception {
                super(port, errorManager);
                System.out.println("Starting Hao's server");
                model=m;
        }

        @Override
        public MessageHandler getMessageHandler(Header header, byte[] bodyBuf,ServerThread serverThread) {
        	System.out.println("Got packet:\n"+header+" \n body: "+new ByteList(bodyBuf));
            return new MyMessageHandler(header, bodyBuf, serverThread, model);
        }
}
