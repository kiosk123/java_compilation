import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer extends Thread {

	private ServerSocket serverSocket;

	@Override
	public void run() {

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(7777));

			while (true) {
				System.out.println("[EchoServer - 연결을 기다리는 중]");
				Socket socket = serverSocket.accept();
				InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[" + socketAddress.toString()+"에서 EchoServer에 연결되었음 ]");	
				new EchoProcess(socket).start();
			}
		} catch (IOException e) {
			System.out.println("EchoServer 에서 예외 발생 : "+e.getMessage());
		} finally {
			if (!serverSocket.isClosed())try {serverSocket.close();} catch (IOException ioe) {}
		}
	}

}
