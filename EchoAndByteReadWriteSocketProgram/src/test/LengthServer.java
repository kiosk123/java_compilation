package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LengthServer {
	private int port;

	private Socket socket;

	public LengthServer(int port) {
		this.port = port;
	}

	public void execute() {
		ServerSocket serverSocket = null;
		try {
			// 포트 기동
			serverSocket = new ServerSocket(port);
			System.out.println("서버 소켓 포트 : "+port);

			while(true){
				// 클라이언트 접근 대기
				socket = serverSocket.accept();
				System.out.println("클라이언트 접근");
				new Thread(new LengthExecutor(socket)).start();
				
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {try {serverSocket.close();} catch (IOException e) {}}
		}
	}

	public static void main(String[] args) {
		int port = 7777;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		LengthServer server = new LengthServer(port);
		server.execute();
	}
}
