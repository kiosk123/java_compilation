import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EchoProcess extends Thread {

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;

	public EchoProcess(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		
			while (true) {
				String msg	= input.readUTF();
				System.out.println("EchoServer와 연결된 클라이언트에서 온 메시지 : " + msg);
				output.writeUTF(msg);
				output.flush();
			}
		} catch (Exception e) {
			System.out.println(socket.getRemoteSocketAddress().toString()+"과 연결중 EcoProcess에서 예외 발생 :"+e.getMessage());
		}finally{
			if(input!=null){try {input.close();} catch (IOException e){}}
			if(output!=null){try {output.close();} catch (IOException e){}}
			if(socket!=null){try {socket.close();} catch (IOException e){}}
		}
	}
}
