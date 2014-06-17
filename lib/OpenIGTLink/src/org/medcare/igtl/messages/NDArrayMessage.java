package org.medcare.igtl.messages;

/*=========================================================================

Program:   OpenIGTLink Library
Language:  java
Date:      $Date: 2014-17-06 17:31 PM EST
Version:   $Revision: 0$

Copyright (c) AIMLab, Worcester Polytechnic Institute

This software is distributed WITHOUT ANY WARRANTY; without even
the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE.  See the above copyright notices for more information.

Author: Nirav Patel: napatel@wpi.edu
=========================================================================*/

import org.medcare.igtl.util.BytesArray;
import org.medcare.igtl.util.Header;
//import com.neuronrobotics.sdk.common.ByteList;

import com.neuronrobotics.sdk.common.Log;

/**
*** This class create an Transform object from bytes received or help to generate
* bytes to send from it
* 
* @author Andre Charles Legendre
* 
*/
public class NDArrayMessage extends OpenIGTMessage {

    private byte type;
    private byte dim;
    private short size[]; 
    private byte data[];
    
    enum DATA_TYPE { TYPE_INT8(2),  
    		TYPE_UINT8(3),
    	    TYPE_INT16(4),
    	    TYPE_UINT16(5),
    	    TYPE_INT32(6),
    	    TYPE_UINT32(7),
    	    TYPE_FLOAT32(10),
    	    TYPE_FLOAT64(11),
    	    TYPE_COMPLEX(13) ;
    
	    private byte value;
	
	    private DATA_TYPE(int value) {
	            this.value = (byte)(value & 0xFF);
	    }
    };
	
      /**
       *** Constructor to be used to create message to send them with this
       * constructor you must use method SetImageHeader, then CreateBody and then
       * getBytes to send them
       *** 
       * @param deviceName
       *            Device Name
       **/
      public NDArrayMessage(String deviceName) {
              super(deviceName);
      }

      /**
       *** Constructor to be used to create message from received data
       * 
       * @param header
       * @param body
       * @throws Exception 
       */
      public NDArrayMessage(Header header, byte body[]) throws Exception {
            super(header, body);
      		//varify CRC here to make sure this is correct message
      		long calculated_crc = BytesArray.crc64(body, body.length, 0L);
      		long recvd_crc = header.getCrc();
      		
      		//System.out.println("Transform: Calculated CRC=" + calculated_crc + "REceived CRC=" + recvd_crc );
      }

      public NDArrayMessage(String deviceName, byte type, byte dim, short[] size, float[] data) {
      	super(deviceName);
      	//set the data from the array
      	PackBody();
		}

		/**
       *** To create body from body array
       * 
       *** 
       * @return true if unpacking is ok
       */
      @Override
      public boolean UnpackBody() throws Exception {
              return true;
      }

      /**
       *** To create body from image_header and image_data
       *  SetTransformData must have called first
       * 
       *** 
       * @return the bytes array containing the body
       */
      @Override
      public byte[] PackBody() {
    	  
    	  //we need to preapre bopdy here
              return getBytes();
      }


      /**
       *** To get transform String
       *** 
       * @return the transform String
       */
      @Override
      public String toString() {
              String transformString = "NDArray Device Name           : " + getDeviceName();
              return transformString;
      }
      
      public void printDoubleDataArray(double[][] matrixArray) {
  		for (int i = 0; i < matrixArray.length; i++) {
  			for (int j = 0; j < matrixArray[0].length; j++) {
  				System.out.print(matrixArray[i][j] + " ");
  			}
  			Log.debug("\n");
  		}
  	}
}

