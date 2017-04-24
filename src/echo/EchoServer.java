package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int SERVER_PORT = 6060;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();

			// 2. 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localhostAddress = inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(localhostAddress, SERVER_PORT));
			System.out.println(" [server] binding " + localhostAddress + ":" + SERVER_PORT);

			// 3. accept (연결 요청을 기다림)
			Socket socket = serverSocket.accept(); // blocking

			// 4. 연결 성공
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			int remoteHost = remoteAddress.getPort();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			System.out.println("[server] connected from " + remoteHostAddress + ":" + remoteHost);

			try {
				// 5. IOStream 받아오기
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);

				while (true) {
					String message = br.readLine(); // blocking
					if (message == null) {
						// 클라이언트가 소켓을 닫음
						System.out.println("[server] disconnected by client");
						break;
					}

					System.out.println("[server] received : " + message);

					// 데이터 쓰기
					// pw.print( message + "\n" );
					pw.println(message);
				}
			} catch (SocketException e) {
				// 클라이언트가 소켓을 정상적으로 닫지 않고 종료한 경우
				System.out.println(" [server] closed by client ");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					socket.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원정리
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
