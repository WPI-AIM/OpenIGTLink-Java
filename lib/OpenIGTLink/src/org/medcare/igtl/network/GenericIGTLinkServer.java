package org.medcare.igtl.network;

import java.util.ArrayList;

import org.medcare.igtl.messages.ImageMessage;
import org.medcare.igtl.messages.PositionMessage;
import org.medcare.igtl.util.BytesArray;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Header;
import org.medcare.igtl.util.IGTImage;

import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;


import Jama.Matrix;

public class GenericIGTLinkServer extends OpenIGTServer implements IOpenIgtPacketListener{
	
	ArrayList<IOpenIgtPacketListener> listeners = new ArrayList<IOpenIgtPacketListener>();
	public sender s = new sender();
	
	public GenericIGTLinkServer(int port) throws Exception{
		super(port, new ErrorManager(){

			@Override
			public void error(String message, Exception exception, int errorCode) {
				// TODO Auto-generated method stub
				
			}
			
		});
		s.start();
	}
	
	@Override
	public MessageHandler getMessageHandler(Header header, byte[] bodyBuf, ServerThread serverThread) {
		// TODO Auto-generated method stub
		return new GenericServerResponseHandler(header, bodyBuf, serverThread,this);
	}
	
	/**
	 * This method will be called by the IGT server when a TransformNR is received. Supports:
	 * TransformNR
	 * QTRANS
	 * QTransformNR
	 * POSITION
	 * @param name The string in the 'NAME' field of the IGT packet
	 * @param t
	 */
	public void onRxTransform(String name,TransformNR t){
		for(IOpenIgtPacketListener l:listeners){
			l.onRxTransform(name, t);
		}
	}
	/**
	 * Request for a TransformNR for transmition to IGT
	 * @param name A string of what type of TransformNR to get
	 * @return the requested TransformNR 
	 */
	public TransformNR getTxTransform(String name){
		if(listeners.size() != 1){
			throw new RuntimeException("There can be only one listener for this packet type.");
		}
		return listeners.get(0).getTxTransform(name);
	}
	/**
	 * This is the handler for a String packet
	 * @param name A string of what type of data to get
	 * @param body A string of the content
	 */
	public void  onRxString(String name,String body){
		for(IOpenIgtPacketListener l:listeners){
			l.onRxString(name, body);
		}
	}
	
	
	/**
	 * This is the request handler for a String packet
	 * @param name A string of what type of TransformNR to get
	 */
	public String onTxString(String name){
		if(listeners.size() != 1){
			throw new RuntimeException("There can be only one listener for this packet type.");
		}
		return listeners.get(0).onTxString(name);
	}
	
	/**
	 * This is the handler for an array of raw data in an array
	 * @param name A string of what type of data to get
	 * @param data An array of data
	 */
	public void onRxDataArray(String name, Matrix data){
		for(IOpenIgtPacketListener l:listeners){
			l.onRxDataArray(name, data);
		}
	}
	
	/**
	 * THis is a request for an array of data
	 * @param name  A string of what type of data to get
	 * @return an array of data
	 */
	public double [] onTxDataArray(String name){
		if(listeners.size() != 1){
			throw new RuntimeException("There can be only one listener for this packet type.");
		}
		return listeners.get(0).onTxDataArray(name);
	}
	
	/**
	 * This is a handler for an Image sent from IGT packet
	 * @param name A string of what type of data to get
	 * @param image the image
	 * @param t A TransformNR of where the image is
	 */
	public void onRxImage(String name,ImageMessage image){
		for(IOpenIgtPacketListener l:listeners){
			l.onRxImage(name, image);
		}
	}
	
	
	public void addIOpenIgtOnPacket(IOpenIgtPacketListener l){
		if(!listeners.contains(l))
			listeners.add(l);
	}
	public void removeIOpenIgtOnPacket(IOpenIgtPacketListener l){
		if(listeners.contains(l))
			listeners.remove(l);
	}
	
	public void pushPose(String name, TransformNR t){
		
		s.onTaskSpaceUpdate(name, t);
	}
	
	public class sender extends Thread{
		
		private TransformNR pose;
		private String name;
		public synchronized void onTaskSpaceUpdate( String name, TransformNR pose){
			this.pose = pose;
			this.name=name;
		}
		public void run(){
			while(getServerThread()==null){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			while(getServerThread().isAlive()){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(pose!= null){
					//GSF 1/26/12 - This command takes a quaternion, not a rotation matrix
					PositionMessage  message;
					message = new PositionMessage (name,pose.getPositionArray(), pose.getRotationMatrix());
					onTaskSpaceUpdate(null,null);
					
					//PositionMessage  message = new PositionMessage ("TeST",pose.getPositionAray(), pose.getRotation());
					//message.SetMatrix(pose.getPositionAray(), pose.getRotationMatrix());
					//Log.debug("Sending Header: "+message.getHeader()); 
					 BytesArray b = new BytesArray(); 
		             b.putBytes(message.getBody());
					//Log.debug("Sending Body: "+b);
		            
					try {
						sendMessage(message);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}


	
}
