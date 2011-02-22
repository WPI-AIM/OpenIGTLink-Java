package org.medcare.igtl.tests.client;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.medcare.igtl.util.ErrorManager;

public class MyClientErrorManager extends ErrorManager {

		public MyClientErrorManager() {
		super();
		lm = LogManager.getLogManager();
		try {
			//10 log files limited to 1 Mbytes each one
			fh = new FileHandler("%h/OpenIgtClientLOG%g.log", 1000000, 10);
			logger = Logger.getLogger("OpenIgtClient");
			lm.addLogger(logger);
			logger.setLevel(Level.INFO);
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			//suppress line below if you want error message to the standard error
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			System.err.println("Client ErrorManager SecurityException");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Client ErrorManager IOException");
			e.printStackTrace();
		}
	}

		@Override
		public void error (String message, Exception exception, int errorCode) {
            String text = "Client LOGGER ErrorManager: " + errorCode;
            if (message != null) {
                  text = text + ": " + message;
            }
            if (exception != null) {
            	logger.log(Level.SEVERE, text, exception);
            } else {
            	logger.log(Level.WARNING, text);
            }
            System.err.println(text);
    }
}
