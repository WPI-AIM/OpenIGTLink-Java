package sample;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.neuronrobotics.sdk.common.Log;
import org.medcare.igtl.messages.ImageMessage;
import org.medcare.igtl.messages.NDArrayMessage;
import org.medcare.igtl.messages.StringMessage;
import org.medcare.igtl.network.GenericIGTLinkServer;
import org.medcare.igtl.network.IOpenIgtPacketListener;
import org.medcare.igtl.util.Header;
import org.medcare.igtl.util.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import Jama.Matrix;

import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;

public class ServerSample implements IOpenIgtPacketListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenericIGTLinkServer server;
		Log.enableDebugPrint(true);
		Log.enableSystemPrint(true);

		try {
			//Set up server
			server = new GenericIGTLinkServer (18944);

			//Add local event listener
			server.addIOpenIgtOnPacket(new ServerSample());
			
			while(!server.isConnected()){
				Thread.sleep(100);
			}
			
			Log.debug("Pushing packet");
			//Create an identify matrix
			TransformNR t = new TransformNR();
			//Push a transform object upstream
			while(true){
				Thread.sleep(1000);
				if(server.isConnected()){
					//Log.debug("Push");
					//server.pushPose("TransformPush", t);
					float data[] = {(float) 1.0, (float) 2.12231233, (float) 4.5};
					//server.sendMessage(new NDArrayMessage("TEMP", data) );
				}else{
					Log.debug("Wait");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onRxTransform(String name, TransformNR t) {
		Log.debug("Received Transform: "+t);  
		
		if(name.equals("RegistrationTransform") || name.equals("CALIBRATION")){
			System.err.println("Received Registration Transform");
			Log.debug("Setting fiducial registration matrix: "+t); 
			return;
		}else if(name.equals("TARGET")){
			System.err.println("Received RAS Transform: TARGET");
				Log.debug("Setting task space pose: "+t); 
			
		}else if(name.equals("myTransform")){
			System.err.println("Received Transformation Matrix: myTransform");
			Log.debug("Setting task space pose: "+t); 
			
		}else{
			System.err.println("Received unidentified transform matrix");
			Log.debug("Setting task space pose: "+t); 
		}
	}

	@Override
	public TransformNR getTxTransform(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Status onGetStatus(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onRxString(String name, String body) {
		// TODO Auto-generated method stub
		System.out.println("Device Name = " + name + " body=" + body);
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
			    InputSource is = new InputSource(new StringReader(body));
			    Document data = builder.parse(is);
			    Element xmlNode = data.getDocumentElement();
			    StringBuffer treeData = new StringBuffer();
			    traverseNode(xmlNode, treeData);
			    System.out.println("TreeData = " + treeData.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void traverseNode (Node n, StringBuffer treeData)
	{
	    String nodename = n.getNodeName();
	    String test = n.getNodeValue();
	    System.out.println ("Node: " + n.getNodeName());
	    treeData.append("Node: " + n.getNodeName());
	    NamedNodeMap atts = n.getAttributes();
	    
	    if( n.getNodeName().equalsIgnoreCase("command") ){
		    for( int i=0;i<atts.getLength();i++){
		    	Node tempNode = atts.item(i);
		    	System.out.println( "Name ="+ tempNode.getNodeName() + " : Value = " + tempNode.getNodeValue());
		    	treeData.append("Name ="+ tempNode.getNodeName() + " : Value = " + tempNode.getNodeValue());
		    }
	
		    if (n.hasChildNodes()) {
		      NodeList nl = n.getChildNodes();
		      int size = nl.getLength();
		      for (int i=0; i<size; i++){
		    	  traverseNode (nl.item(i), treeData);
		      }
		    }
	    }
	}

	@Override
	public String onTxString(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRxDataArray(String name, Matrix data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double[] onTxDataArray(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRxImage(String name, ImageMessage image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTxNDArray(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRxNDArray(String name, float[] data) {
		// TODO Auto-generated method stub
		Log.debug("Name" + name);
		for(int i=0;i<data.length;i++){
			Log.debug("Data[" + i + "]=" + data[i]);
		}
	}

}
