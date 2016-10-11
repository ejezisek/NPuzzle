package longToSquare;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long squareCopy=Long.parseLong(args[0]);
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<16; i++)
		{
			byte val=(byte)(squareCopy&0x0F);
			squareCopy>>>=4;
			sb.append(val +" ");
			if((i+1)%4==0)
				sb.append("\n");
		}
		System.out.println(sb);
	}
	

}
