package org.medcare.igtl.tests.client;

import java.io.IOException;
import java.net.UnknownHostException;

import org.medcare.igtl.network.OpenIGTClient;
import org.medcare.igtl.network.ResponseHandler;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Header;

public class MyOpenIGTClient extends OpenIGTClient {

        public MyOpenIGTClient(String host, int port, ErrorManager errorManager) throws UnknownHostException, IOException {
                super(host, port, errorManager);
        }

        @Override
        public ResponseHandler getResponseHandler(Header header, byte[] bodyBuf) {
                return new MyResponseHandler(header, bodyBuf, this);
        }
}
