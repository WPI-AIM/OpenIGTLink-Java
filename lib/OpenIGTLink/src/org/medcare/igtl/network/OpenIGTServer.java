/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/network/SocketServer.java $
  Language:  java
  Date:      $Date: 2010-08-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.network;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;

import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Status;
import org.medcare.igtl.util.Header;


/**
 * <p>
 * The class can be used to create a server listening a port Messages received
 * will be queued and proceed
 * <p>
 * 
 * @author <a href="mailto:andleg@osfe.org"> 
 * Andre Charles Legendre </a>
 * @version 0.1a (09/06/2010)
 * 
 */

public abstract class OpenIGTServer {
        private Status status;
        public ErrorManager errorManager;
        ServerSocket socket = null;
        private ServerThread thread;
        private boolean listening = true;
        private boolean running = false;
        /***************************************************************************
         * Default MessageQueueManager constructor.
         * 
         * @param port
         *            port on which this server will be bind
         * @param errorManager
         *            main class running this server
         * @throws Exception 
         * 
         **************************************************************************/
        public OpenIGTServer(int port, ErrorManager errorManager) throws Exception {
        	System.out.println("Starting IGTLink Server");
            this.errorManager = errorManager;
            start( port);
        }
        
        public void start(int port) throws IOException{
        	stopServer();
        	System.out.println("IGTLink client Waiting for connection");
            ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
            try {
                    socket = serverSocketFactory.createServerSocket(port);
            } catch (IOException e) {
                    errorManager.error("OpenIGTServer Could not listen on port: " + port, e, ErrorManager.OPENIGTSERVER_IO_EXCEPTION);
                    throw e;
            }
            server s = new server();
            s.start();
        }
        
        private class server extends Thread{
        	public void run(){
        		setListening(true);
	        	while (isListening()){
	                try {
						startIGT();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
        		}
	        	System.out.println("IGTLink Server Died, exiting");
        	}
        }
        /**
         * This method waits until a client connects to the server port
         * @throws IOException
         * @throws Exception
         */
        private void startIGT() throws IOException, Exception{
        	
        	 setServerThread(new ServerThread(socket.accept(), this));
        	 getServerThread().start();
        	 running=true;
        	 System.out.println("IGTLink client connected");
        }

        /**
         *** To set server status
         * @param status
         *** 
         */
        public void setStatus(Status status) {
                this.status = status;
        }

        /**
         *** To get server status
         *** 
         * @return the status status
         */
        public Status getStatus() {
                return this.status;
        }
        /**
         * Sends a message up the link
         * @param messageHandler
         * @throws Exception 
         */
        public void sendMessage(OpenIGTMessage message){
        	if(getServerThread()!=null){
        		
        		try {
					getServerThread().sendMessage(message);
				} catch (Exception e) {
					stopServer();
				}
        		
        		//Log.info("Pushing upstream IGTLink packet "+message);
        	}else{
        		System.out.println("No clients connected");
        	}
        }

        /**
         *** To get message Handler
         * @param header header of the message received
         * 
         * @param bodyBuf byte array of the body of the message received
         * 
         * @param serverThread serverThread managing connection of client where does come from the message
         *** 
         * @return the message Handler
         */
        public abstract MessageHandler getMessageHandler(Header header, byte[] bodyBuf, ServerThread serverThread);

		public void setServerThread(ServerThread thread) {
			this.thread = thread;
		}

		public ServerThread getServerThread() {
			return thread;
		}
		public void stopServer(){
			if(getServerThread()!=null)
				getServerThread().interrupt();
			setListening(false);
			running=false;
		}

		public void setListening(boolean listening) {
			this.listening = listening;
		}

		public boolean isListening() {
			return listening;
		}
		
		public boolean isConnected() {
			return running;
		}
}
