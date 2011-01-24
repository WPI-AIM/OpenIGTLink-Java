/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/messages/ImageMessage.java $
  Language:  java
  Date:      $Date: 2010-18-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.messages;

import org.medcare.igtl.util.BytesArray;
import org.medcare.igtl.util.Header;

/**
 *** This class create an Position object from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andre Charles Legendre
 * 
 */
public class PositionMessage extends OpenIGTMessage {

        public static int IGTL_POSITION_MESSAGE_DEFAULT_SIZE = 28;
        public static int IGTL_POSITION_MESSAGE_POSITON_ONLY_SIZE = 12;
        public static int IGTL_POSITION_MESSAGE_WITH_QUATERNION3_SIZE = 24;

        public static int POSITION_ONLY = 0;
        public static int WITH_QUATERNION3 = 3;
        public static int ALL = 4;
        private int quaternionSize = ALL;

        public double position[] = new double[3]; // float 32bits
        public double quaternion[]; // float 32bits
        private byte position_data[];
        private int bodyLength;

        /**
         *** Constructor to be used to create message to send them with this
         * constructor you must use method SetImageHeader, then CreateBody and then
         * getBytes to send them
         *** 
         * @param deviceName
         *            Device Name
         **/
        public PositionMessage(String deviceName) {
                super(deviceName);
        }

        /**
         *** Constructor to be used to create message from received data
         * 
         * @param header
         * @param body
         * @throws Exception 
         */
        public PositionMessage(Header header, byte body[]) throws Exception {
                super(header, body);
        }

        /**
         *** To create body from body array
         * 
         *** 
         * @return true if unpacking is ok
         * @throws Exception 
         */
        @Override
        public boolean UnpackBody() throws Exception {
                bodyLength = body.length;
                position_data = new byte[bodyLength];
                System.arraycopy(body, 0, position_data, 0, bodyLength);
                setPositionData(position_data);
                return true;
        }

        /**
         *** To create body from image_header and image_data
         *  SetPositionData must have called first
         * 
         *** 
         * @return the bytes array containing the message
         */
        @Override
        public byte[] PackBody() {
                body = new byte[position_data.length];
                System.arraycopy(position_data, 0, body, 0, position_data.length);
                header = new Header(VERSION, "POSITION", deviceName, body);
                return getBytes();
        }

        /**
         *** To create position_data from image characteristics and to get the byte array to send
         * @param position
         * @param quaternion
         * @param quaternionSize
         *** 
         * @return the bytes array created from the value
         */
        public byte[] setPositionData(double position[], double quaternion[], int quaternionSize) {
                setPosition(position);
                setQuaternion(quaternion);
                this.quaternionSize = quaternionSize;
                bytesArray = new BytesArray();
                bytesArray.putDouble(position[0], 4);
                bytesArray.putDouble(position[1], 4);
                bytesArray.putDouble(position[2], 4);
                for (int i = 0; i < quaternionSize; i++)
                        bytesArray.putDouble(quaternion[i], 4);
                position_data = bytesArray.getBytes();
                return position_data;
        }

        /**
         *** To extract image characteristics from position_data byte array
         * @param position_data
         * @throws Exception 
         */
        public void setPositionData(byte position_data[]) throws Exception {
                this.position_data = position_data;
                bytesArray = new BytesArray();
                bytesArray.putBytes(position_data);
                position = new double[3]; // float 32bits
                position[0] = bytesArray.getDouble(4); // float32
                position[1] = bytesArray.getDouble(4); // float32
                position[2] = bytesArray.getDouble(4); // float32
                setPosition(position);
                if (bodyLength == IGTL_POSITION_MESSAGE_DEFAULT_SIZE) {
                        quaternionSize = ALL;
                } else if (bodyLength == IGTL_POSITION_MESSAGE_POSITON_ONLY_SIZE) {
                        quaternionSize = POSITION_ONLY;
                } else if (bodyLength == IGTL_POSITION_MESSAGE_WITH_QUATERNION3_SIZE) {
                        quaternionSize = WITH_QUATERNION3;
                } else {
                        throw new Exception("Unknown POSITION_MESSAGE_SIZE : " + bodyLength);
                }
                quaternion = new double[4]; // float 32bits
                for (int l = 0; l <= 3; l++)
                        quaternion[l] = 0.0; // float32
                for (int i = 0; i < quaternionSize; i++) {
                        quaternion[i] = bytesArray.getDouble(4); // float32
                }
                setQuaternion(quaternion);
        }

        /**
         *** To get position_data byte array
         *** 
         * @return the position_data bytes array
         */
        public byte[] getPositionData() {
                return this.position_data;
        }

        /**
         *** To set Image position
         * @param position
         *** 
         */
        public void setPosition(double position[]) {
                this.position = position;
        }

        /**
         *** To set Image position
         * @param x
         * @param y
         * @param z
         *** 
         */
        public void setPosition(double x, double y, double z) {
                this.position = new double[3]; // float 32bits
                this.position[0] = x;
                this.position[1] = y;
                this.position[2] = z;
        }

        /**
         *** To get Image position
         *** 
         * @return the position bytes array
         */
        public double[] getPosition() {
                return this.position;
        }

        /**
         *** To set Image quaternion
         * @param quaternion
         *** 
         */
        void setQuaternion(double quaternion[]) {
                this.quaternion = quaternion;
        }

        /**
         *** To set Image quaternion
         * @param ox
         * @param oy
         * @param oz
         * @param w
         *** 
         */
        void setQuaternion(double ox, double oy, double oz, double w) {
                quaternion = new double[4]; // float 32bits
                quaternion[0] = ox;
                quaternion[1] = oy;
                quaternion[2] = oz;
                quaternion[3] = w;
        }

        /**
         *** To get Image quaternion
         *** 
         * @return the quaternion array
         */
        public double[] getQuaternion() {
                return quaternion;
        }

        /**
         *** To get position String
         *** 
         * @return the position String
         */
        @Override
        public String toString() {
                String positionString = "POSITION Device Name : " + this.getDeviceName();
                positionString = positionString + " position   = (" + Double.toString(this.position[0]) + ", " + Double.toString(this.position[1]) + ", " + Double.toString(this.position[2]) + ") quaternion = (";
                for (int i = 0; i < quaternionSize; i++)
                        positionString = positionString + Double.toString(this.quaternion[i]) + ", ";
                positionString = positionString + ")\n";
                return positionString;
        }
}

