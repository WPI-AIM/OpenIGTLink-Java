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
        boolean listening = true;
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
                ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
                try {
                        socket = serverSocketFactory.createServerSocket(port);
                } catch (IOException e) {
                        errorManager.error("OpenIGTServer Could not listen on port: " + port, e, ErrorManager.OPENIGTSERVER_IO_EXCEPTION);
                        throw e;
                }
                //There are not violent enough curses for this sort of thing...
//                while (listening)
//                        new ServerThread(socket.accept(), this).start();
//                socket.close();
                server s = new server();
                s.start();
        }
        private class server extends Thread{
        	public void run(){
	        	while (listening){
	                try {
						startIGT();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
        		}
        	}
        }
        private void startIGT() throws IOException, Exception{
        	new ServerThread(socket.accept(), this).start();
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
}
