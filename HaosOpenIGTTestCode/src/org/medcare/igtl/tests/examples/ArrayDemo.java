package org.medcare.igtl.tests.examples;

class ArrayDemo {
    public static void main(String[] args) {
         int[] anArray;              // declares an array of integers

         anArray = new int[10];      // allocates memory for 10 integers
           
         anArray[0] = 100; // initialize first element
         anArray[1] = 200; // initialize second element
         anArray[2] = 300; // etc.
         anArray[3] = 400;
         anArray[4] = 500;
         anArray[5] = 600;
         anArray[6] = 700;
         anArray[7] = 800;
         anArray[8] = 900;
         anArray[9] = 1000;

         System.out.println("Element at index 0: " + anArray[0]);
         System.out.println("Element at index 1: " + anArray[1]);
         
         
         char[] copyFrom = { 'd', 'e', 'c', 'a', 'f', 'f', 'e',
 			    'i', 'n', 'a', 't', 'e', 'd' };
         char[] copyTo = new char[7];
         // move char array from copyFrom to copyTo
         System.arraycopy(copyFrom, 2, copyTo, 0, 7);
         System.out.println(new String(copyTo));

    }
} 