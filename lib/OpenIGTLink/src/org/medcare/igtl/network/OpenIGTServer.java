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

  Edited By Nirav Patel(napatel@wpi.edu) on Aug 10 2013
  ---Making server run and listen for multiple devices and maintain server status information
  
=========================================================================*/

package org.medcare.igtl.network;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;

import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Status;
import org.medcare.igtl.util.Header;

import com.neuronrobotics.sdk.common.Log;
import com.neuronrobotics.sdk.util.ThreadUtil;


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
        public ErrorManager errorManager;
        ServerSocket socket = null;
        private ServerThread thread;
        private boolean keepAlive = true;
        private int port;
        
        public static enum ServerStatus {STOPPED, LISTENING, CONNECTED }; //possible server states
        ServerStatus currentStatus = ServerStatus.STOPPED; //start as stopped status
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
            this.errorManager = errorManager;
            this.port = port;
            currentStatus = ServerStatus.STOPPED;
            startServer(port);
        }
        
        public void startServer(int port) throws IOException{
        	Log.debug("Starting IGTLink Server");
        	stopServer();
            try {
            	ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
                socket = serverSocketFactory.createServerSocket(this.port);
                Log.debug("Server Socket created");
            } catch (IOException e) {
                    errorManager.error("OpenIGTServer Could not listen on port: " + this.port, e, ErrorManager.OPENIGTSERVER_IO_EXCEPTION);
                    throw e;
            }
            server s = new server();
            setKeepAlive(true);
            s.start();
        }

		public void stopServer(){
			if(getServerThread()!=null)
				getServerThread().interrupt();
			if(socket!= null)
				try {
					setKeepAlive(false);
					socket.close();
				} catch (IOException e) {}
			currentStatus = ServerStatus.STOPPED;
		}
       
        private class server extends Thread{
        	public void run(){
        		while(getKeepAlive()){
            		try {
    					startIGT();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (Exception e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
            		Log.debug("Processing requests from a client and waiting for another one");
    	        	/*while (!socket.isClosed()){
    	        		try {
    	        			System.out.println("Waiting for closing the server...");
    						Thread.sleep(100);
    					} catch (InterruptedException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
            		}*/
          		}
        	}
        }
        /**
         * This method waits until a client connects to the server port
         * @throws IOException
         * @throws Exception
         */
        private void startIGT() throws IOException, Exception{
         	 Log.debug("IGTLink server Waiting for connection from client");
        	try {
        		 startServer(port);
        		 try{
		         	 currentStatus = ServerStatus.LISTENING;
		        	 setServerThread(new ServerThread(socket.accept(), this));
	        		 getServerThread().start();
	        		 currentStatus = ServerStatus.CONNECTED;
	        		 Log.debug("IGTLink client connected");
        		 }catch(Exception e){
        			 Log.debug("Seems server socket got closed.");
        			 e.printStackTrace();
        		 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.debug("Error startinng the IGTLink sevrer");
				e.printStackTrace();
			}
    }

         /**
         * Sends a message up the link
         * @param messageHandler
         * @throws Exception 
         */
        public void sendMessage(OpenIGTMessage message){
        	if(getServerThread()!=null){
        		
        		try {
    				//TODO before sending message it should be packed, should this be done here or in Message itself? 
					getServerThread().sendMessage(message);
				} catch (Exception e) {
					stopServer();
				}
        		
        		//Log.info("Pushing upstream IGTLink packet "+message);
        	}else{
        		Log.debug("No clients connected");
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
		/**
		 * @return the port
		 */
		public int getPort() {
			return port;
		}

		/**
		 * @param port the port to set
		 */
		public void setPort(int port) {
			this.port = port;
		}

		/**
		 * @return the killAlive
		 */
		public boolean getKeepAlive() {
			return keepAlive;
		}
		/**
		 * @param killAlive the killAlive to set
		 */
		public void setKeepAlive(boolean keepAlive) {
			this.keepAlive = keepAlive;
		}

		/**
		 * @return the currentStatus
		 */
		public ServerStatus getCurrentStatus() {
			return currentStatus;
		}

		/**
		 * @param currentStatus the currentStatus to set
		 */
		public void setCurrentStatus(ServerStatus currentStatus) {
			this.currentStatus = currentStatus;
		}
		
		public boolean isConnected(){
			if( currentStatus == ServerStatus.CONNECTED ){
				return true;
			}
			else{
				return false;
			}
		}
}
