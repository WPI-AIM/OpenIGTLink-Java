package org.medcare.igtl.tests.server;

import org.medcare.igtl.network.MessageHandler;
import org.medcare.igtl.network.OpenIGTServer;
import org.medcare.igtl.network.ServerThread;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Header;

// import com.neuronrobotics.sdk.dyio.DyIO;
import com.neuronrobotics.sdk.pid.IPIDControl;

public class HaosOpenIGTServer extends OpenIGTServer {
		private IPIDControl dyio;
        public HaosOpenIGTServer(int port, ErrorManager errorManager,IPIDControl d) throws Exception {
                super(port, errorManager);
                System.out.println("Starting Haos server");
                if(d == null){
                	System.err.println("Dyio is NULL!");
    	        	throw new RuntimeException("PID device is null!!");
                }
                System.out.println("IGT server started ok");
                dyio = d;
        }

        @Override
        public MessageHandler getMessageHandler(Header header, byte[] bodyBuf,ServerThread serverThread) {
        	if(dyio == null)
	        	throw new RuntimeException("PID device is null!!");
            return new MyMessageHandler(header, bodyBuf, serverThread, dyio);
        }
}
