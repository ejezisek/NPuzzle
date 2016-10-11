package teste;

import java.nio.ByteBuffer;

public class Main {

	public static void main(String[] args) {
		long val1=Long.MAX_VALUE;
		long val2=1;
		long val3=-1;
		printBytes(longToBytes(val2<<4));
		printBytes(longToBytes((val2<<60)*15));
		printBytes(longToBytes(val1));
		printBytes(longToBytes((val1+val2)>>>2));
		printBytes(longToBytes(val3>>>2));
	}
	public static void printBytes(byte[] vals)
	{
		for(byte val:vals)
			printByte(val);
		System.out.println();
	}
	public static void printByte(byte val)
	{
		byte valCopy=val;
		
		for(int i=0; i<8; i++)
		{
			System.out.print((valCopy>>7)&0x1);
			valCopy<<=1;
		}
		System.out.print(" ");
		
	}
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
}
