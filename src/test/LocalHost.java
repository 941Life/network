package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();

			String hostAddress = inetAddress.getHostAddress();
			System.out.println(hostAddress);
			
			String hostName = inetAddress.getHostName();
			System.out.println(hostName);
			
			byte[] addresses = inetAddress.getAddress();
			for ( int i = 0; i < addresses.length;i++){
				int address = addresses[i] & 0x000000ff;
				System.out.print(address);
				if (i <3){
					System.out.print(".");
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
