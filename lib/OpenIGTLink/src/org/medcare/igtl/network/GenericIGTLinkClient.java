package org.medcare.igtl.network;

import java.util.ArrayList;

import org.medcare.igtl.messages.ImageMessage;
import org.medcare.igtl.util.ErrorManager;
import org.medcare.igtl.util.Header;
import org.medcare.igtl.util.Status;

import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;
import com.neuronrobotics.sdk.common.Log;

import Jama.Matrix;


public class GenericIGTLinkClient extends OpenIGTClient implements IOpenIgtPacketListener
{
	ArrayList<IOpenIgtPacketListener> listeners = new ArrayList<IOpenIgtPacketListener>();

	public GenericIGTLinkClient(String hostName, int port) throws Exception{
		super(hostName, port, new ErrorManager(){

			@Override
			public void error(String message, Exception exception, int errorCode) {
				System.err.println(message);
			}
		});
		Log.debug("GenericIGTLinkClient started");
	}

	@Override
	public ResponseHandler getResponseHandler(Header header, byte[] bodyBuf)
	{
		return new GenericClientResponseHandler(header,bodyBuf,this,this);
	}

	/**
	 * This method will be called by the IGT server when a transform is received. Supports:
	 * TRANSFORM
	 * QTRANS
	 * QTRANSFORM
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
	 * Request for a transform for transmition to IGT
	 * @param name A string of what type of transform to get
	 * @return the requested transform 
	 */
	public TransformNR getTxTransform(String name){
		if(listeners.size() != 1){
			throw new RuntimeException("There can be only one listener for this packet type.");
		}
		return listeners.get(0).getTxTransform(name);
	}
	/**
	 * Request for status from IGT/Slicer
	 * @param name A string of what type of transform to get
	 * @return the requested status
	 */
	public Status onGetStatus(String name){
		if(listeners.size() != 1){
			throw new RuntimeException("There can be only one listener for this packet type.");
		}
		return listeners.get(0).onGetStatus(name);
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
	 * @param name A string of what type of transform to get
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
	 * @param t A transform of where the image is
	 */
	public void onRxImage(String name,ImageMessage image){
		for(IOpenIgtPacketListener l:listeners){
			l.onRxImage(name, image);
		}
	}
	
	public void stopClient(){
		interrupt();
		while(isConnected());
	}


	public void addIOpenIgtOnPacket(IOpenIgtPacketListener l){
		if(!listeners.contains(l))
			listeners.add(l);
	}
	public void removeIOpenIgtOnPacket(IOpenIgtPacketListener l){
		if(listeners.contains(l))
			listeners.remove(l);
	}

}



