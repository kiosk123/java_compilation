package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	
	private String ip;
	private int port;
	
	private EchoClient(String ip,int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void execute(){
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedReader reader = null;
		try {
			socket = new Socket(ip,port);
			os = socket.getOutputStream();
			reader = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				System.out.print("입력 : ");
				String input=reader.readLine();
				if(input==null){
					System.err.println("입력 에러.");
					break;
				}
				
				if("quit".equalsIgnoreCase(input.trim())){
					System.out.println("정상 종료");
					break;
				}
				os.write(input.getBytes());
				is = socket.getInputStream();
				byte[] buf = new byte[1024];
				int readLen = is.read(buf);
				byte[] readData = new byte[readLen];
				System.arraycopy(buf, 0, readData, 0, readLen);
				System.out.println("응답  [ "+new String(readData)+" ] ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){try{is.close();}catch(IOException e){}}
			if(os!=null){try{os.close();}catch(IOException e){}}
			if(socket!=null){try{socket.close();}catch(IOException e){}}
		}
	}
	
	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("Using : EchoClient <ip addr> <port>");
			System.exit(1);
		}
//		String ip = "localhost";
//		int port=7777;
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		EchoClient client=new EchoClient(ip,port);
		client.execute();
	}
}
