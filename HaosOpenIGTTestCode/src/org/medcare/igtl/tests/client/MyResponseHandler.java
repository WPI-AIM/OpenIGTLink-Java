package org.medcare.igtl.tests.client;

import org.medcare.igtl.messages.GetCapabilityMessage;
import org.medcare.igtl.messages.ImageMessage;
import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.messages.PositionMessage;
import org.medcare.igtl.messages.StatusMessage;
import org.medcare.igtl.messages.TransformMessage;
import org.medcare.igtl.network.OpenIGTClient;
import org.medcare.igtl.network.ResponseHandler;
import org.medcare.igtl.util.Header;

public class MyResponseHandler extends ResponseHandler {

        public OpenIGTMessage openIGTMessage;

        public MyResponseHandler(Header header, byte[] body, OpenIGTClient openIGTClient) {
                super(header, body, openIGTClient);
                capabilityList.add("GET_CAPABIL");
                capabilityList.add("TRANSFORM");
                capabilityList.add("POSITION");
                capabilityList.add("IMAGE");
                capabilityList.add("STATUS");
        }

        @Override
        public void manageError (String message, Exception exception, int errorCode) {
                openIGTClient.errorManager.error(message, exception, errorCode);
        }

        @Override
        public boolean perform(String messageType) throws Exception {
            if (messageType.equals("GET_CAPABIL")) {
                    openIGTMessage = new GetCapabilityMessage(header, body);
            } else if  (messageType.equals("TRANSFORM")) {
                    openIGTMessage = new TransformMessage(header, body);
            } else if (messageType.equals("POSITION")) {
                    openIGTMessage = new PositionMessage(header, body);
            } else if (messageType.equals("IMAGE")) {
                    openIGTMessage = new ImageMessage(header, body);
            } else if (messageType.equals("STATUS")) {
                    openIGTMessage = new StatusMessage(header, body);
            } else {
                    System.out.println("Perform messageType : " + messageType + " not implemented");
                    return false;
            }
            System.out.println("Perform messageType : " + messageType + " content : " + openIGTMessage.toString());
            return true;
        }
}
