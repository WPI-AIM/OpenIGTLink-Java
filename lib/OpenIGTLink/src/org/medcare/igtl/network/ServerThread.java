/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/network/ServerThread.java $
  Language:  java
  Date:      $Date: 2010-08-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.medcare.igtl.messages.OpenIGTMessage;
import org.medcare.igtl.network.OpenIGTServer.ServerStatus;
//import org.medcare.igtl.util.BytesArray;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Header;
import org.medcare.igtl.util.Status;

import com.neuronrobotics.sdk.common.Log;

/**
 * OpenIGTServer create one ServerThread for each client making a connection.
 * ServerThread will add messages received from client to the messageQueue to be
 * treated then MessageQueueManager will use its ServerThread to send answer to
 * messages.
 * 
 * @author Andre Charles Legendre
 */
public class ServerThread extends Thread {
        private java.net.Socket socket = null;
        private OpenIGTServer openIGTServer;
        private OutputStream outstr;
        private InputStream instr;
        private MessageQueueManager messageQueue = null;
        private boolean alive;
        public ErrorManager errorManager;
        public static enum ClientStatus {CONNECTED, DISCONNECTED }; //possible client states
        private ClientStatus currentStatus = ClientStatus.DISCONNECTED; //start as stopped status
        /***************************************************************************
         * Default ServerThread constructor.
         * 
         * @param socket
         *            to listen to
         * @throws IOException 
         * 
         **************************************************************************/
        public ServerThread(java.net.Socket socket, OpenIGTServer openIGTServer) throws Exception {
                super("ServerThread");
                this.socket = socket;
                this.openIGTServer = openIGTServer;
                this.errorManager = openIGTServer.errorManager;
                this.messageQueue = new MessageQueueManager(this);
                this.messageQueue.start();
                this.outstr = socket.getOutputStream();
                this.instr = socket.getInputStream();
                
                this.currentStatus = ClientStatus.CONNECTED;
        }

        /***************************************************************************
         * Reader thread. Reads messages from the socket and add them to the
         * MessageQueueManager
         * 
         **************************************************************************/
        public void run() {
                this.alive = true;

                try {
                        int ret_read = 0;
                        byte[] headerBuff = new byte[Header.LENGTH];
                        do {
                        		//First check if still someone is connected with this socket and havent closd read-end point
                        		if( this.socket.isClosed() || this.socket.isInputShutdown() ){
                        			break;
                        		}
                                //ret_read = instr.read(headerBuff);
                                headerBuff = readNBytesWithTimeout(instr, Header.LENGTH, 200);
                                if (headerBuff != null) {
                                        Header header = new Header(headerBuff);
                                        //Log.debug("ServerThread Header deviceName : " + header.getDeviceName() + " Type : " + header.getDataType() + " bodySize " + header.getBody_size() + "\n");
                                      //  byte[] bodyBuf = new byte[(int) header.getBody_size()];
                                        byte[] bodyBuf= readNBytesWithTimeout(instr, (int) header.getBody_size(), 200);
                                    	if( bodyBuf!=null){
                                    		
                                           // Log.debug("red message body with size=" + bodyBuf.length);
                                            if(bodyBuf.length != (int) header.getBody_size()){
                                                errorManager.error("ServerThread bodyBuf in ServerThread ret_read = " + ret_read, new Exception("Abnormal return from reading"), ErrorManager.SERVERTHREAD_ABNORMAL_ANSWER);
                                                Log.debug("ServerThread bodyBuf in ServerThread ret_read = " + ret_read + " While expecting " +(int) header.getBody_size() + " number of bytes" + " Abnormal return from reading " + ErrorManager.SERVERTHREAD_ABNORMAL_ANSWER);
                                            }else{
                                            	//Log.debug("red message body with size=" + bodyBuf.length);
                                            }
                                            /*if ((int) header.getBody_size() > 0) {
                                                    ret_read = (new BufferedInputStream(instr)).read(bodyBuf, 0, (int) header.getBody_size());
                                                    if (ret_read !=header.getBody_size()) {
                                                            errorManager.error("ServerThread bodyBuf in ServerThread ret_read = " + ret_read, new Exception("Abnormal return from reading"), ErrorManager.SERVERTHREAD_ABNORMAL_ANSWER);
                                                            Log.debug("ServerThread bodyBuf in ServerThread ret_read = " + ret_read + " While expecting " +(int) header.getBody_size() + " number of bytes" + " Abnormal return from reading " + ErrorManager.SERVERTHREAD_ABNORMAL_ANSWER);
                                                    }
                                            }*/
//                                            Log.debug("New Header: "+header);
//                                            BytesArray b = new BytesArray(); 
//                                            b.putBytes(bodyBuf);
//                                            Log.debug("New Body: "+b);
                                            messageQueue.addMessage(openIGTServer.getMessageHandler(header, bodyBuf, this));
                                    	}else{
                                    		Log.debug("Error reading message Body");
                                    	}
                                }else{
                                	//Log.debug("Error getting message Header");
                                }
                        } while (alive && ret_read >= 0);
                        outstr.close();
                        instr.close();
                        socket.close();
                        Log.debug("IGTLink client got disconnected, will set alive=false to inform SocketServer");
                } catch (IOException e) {
                		e.printStackTrace();
                        errorManager.error("ServerThread IOException", e, ErrorManager.SERVERTHREAD_IO_EXCEPTION);
                }
                this.interrupt();
        }
        public byte[] readNBytesWithTimeout(InputStream in, int N, int timeout) throws IOException{
        	byte[] data = new byte[N];
        	if (N<=0){
        		return data;
        	}
        	//Log.debug("Reading " + N + " bytes with timeout");
        	int index = 0;
        	long time = System.currentTimeMillis();
        	do{
               		int bytesToRead = N-index;
               		byte[] buf = new byte[bytesToRead];
            		int ret_read=instr.read(buf, 0, bytesToRead);
            		if(ret_read == -1){
                    	//Log.debug("EOF encountered");
                    	if((System.currentTimeMillis()-time)<timeout)
                    		continue;
                    	else
                    		break;
            		}else if(ret_read==0){
                    	//Log.debug("Red Zero bytes");
                    	if((System.currentTimeMillis()-time)<timeout)
                    		continue;
                    	else
                    		break;
            		}else{
            			System.arraycopy(buf, 0, data, index, ret_read);
            			index +=ret_read;
            			time = System.currentTimeMillis();
                    	//Log.debug("While loop index=" + index + " N- " + N);
            		}
            	//Log.debug("While loop index=" + index + " N= " + N);
        	}while(index!=N && (System.currentTimeMillis()-time)<timeout);
        	//Log.debug("Finished reading  " + (index+1) + " bytes of " + N + "bytes");
        	if(index!=N){
        		return null;
        	}
        	else{
        		return data;
        	}
        }
		public void sendMessage(OpenIGTMessage message) throws Exception {
			// TODO Auto-generated method stub
			sendMessage(message.getHeader(), message.getBody());
			//System.out.println("Message: Header=" + message.getHeader().toString() + " Body=" + message.getBody().toString());
		}
		public void sendMessage(Header header, byte[] body) throws Exception {
			sendBytes(header.getBytes());
			sendBytes(body);
			//Log.debug("Sending Message: Header=" + header.toString() + " Body=" + body.toString());
		}

        /***************************************************************************
         * Sends bytes
         * <p>
         * 
         * @throws IOException
         *             - Exception in I/O.
         *             <p>
         * @param bytes
         *            - byte[] array.
         **************************************************************************/
        final public synchronized void sendBytes(byte[] bytes) throws IOException {
                outstr.write(bytes);
                outstr.flush();
        }

        /***************************************************************************
         * Interrupt this thread
         **************************************************************************/
        public void interrupt() {
        		this.currentStatus  = ClientStatus.DISCONNECTED;
                alive = false;
                messageQueue.destroy();
                try {
					outstr.close();
					instr.close();
	                socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }

        /**
         *** To set server status
         * @param status
         *** 
         */
        public void setStatus(ServerStatus status) {
                this.openIGTServer.setCurrentStatus(status);
        }

        /**
         *** To set server status
         *** 
         * @return the status status
         */
        public ServerStatus getStatus() {
                return this.openIGTServer.getCurrentStatus();
        }

		public boolean getAlive() {
			return alive;
		}

		public void setAlive(boolean alive) {
			this.alive = alive;
		}




}
