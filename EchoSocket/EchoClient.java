import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class EchoClient extends Thread{
	
	private Socket socket;
	private Scanner scanner;
	private DataInputStream input;
	private DataOutputStream output;

	@Override
	public void run() {
		
		scanner=new Scanner(System.in);
		
		try {
			socket=new Socket();
			System.out.println("EchoClient에서 EchoServer에 연결요청 ");
			socket.connect(new InetSocketAddress("localhost",7777));
			System.out.println("EchoClient에서 EchoServer에 연결 성공  ");
			input=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		    output=new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		    
		    while(true){
		    	System.out.print("EchoClient에서 서버로 보내는 메시지 ( 종료는 : \'quit\'를 입력 ): ");
		    	String msg=scanner.nextLine();
		    	if(msg.equals("quit")){
		    		input.close();
		 		    output.close();
		 		    socket.close();
		 		    break;
		    	}  		
		    	
		    	output.writeUTF(msg);
		    	output.flush();		    	
		    	msg=input.readUTF();		    	
		    	System.out.println("EchoServer에서 온 메시지 : "+msg);
		    }
		   
		} catch (IOException e) {
			System.out.println("EchoClient에서 예외 발생 : "+e.getMessage());
			e.printStackTrace();
		}finally{
			if(input!=null){try {input.close();} catch (IOException e){}}
			if(output!=null){try {output.close();} catch (IOException e){}}
			if(socket!=null){try {socket.close();} catch (IOException e){}}
		}
	}	
}
