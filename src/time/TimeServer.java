package time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimeServer {
	private static final int PORT = 7070;

	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		Scanner scanner = new Scanner(System.in);
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss a");
		String date = format.format(new Date());
		
		
		try {
			// 1. 소켓 생성
			datagramSocket = new DatagramSocket(PORT);
			
			// 2. 수신 패킷 생성
			DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
			
			// 3. 데이터 수신 대기
			datagramSocket.receive(receivePacket);
			
			//4. 수신 
			String message = new String (receivePacket.getData(),0,receivePacket.getLength(),"utf-8");
			System.out.println("[UDP Server] received : " + message);
			
			// 5. 에코잉
			byte[] sendData = date.getBytes( "utf-8" );
			if("time".equals(message)){
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getSocketAddress() );
			datagramSocket.send( sendPacket );
			}
		} catch (SocketException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		if (datagramSocket != null && datagramSocket.isClosed() == false) {
			datagramSocket.close();
		}
	}
}
