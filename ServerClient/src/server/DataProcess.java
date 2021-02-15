package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DataProcess extends Thread{

	private InputStream is = null;
	private OutputStream os = null;
	private Socket socket;
	
	public DataProcess(Socket socket) {
		this.socket=socket;
	}
	
	
	@Override
	public void run() {
		try {
			byte[] buffer=new byte[1024];
			int readLen=0; //남아있는 바이트 길이를 나타냄
			int totalLen=0; //전체 데이터 길이 저장
			int remain=0;   //남은 길이 저장
			boolean isNumberRead=false;
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
			while(true){
				buffer[readLen] =(byte)is.read();							
//				if(data == -1){
//					System.out.println("클라이언트 연결이 종료되었습니다.");
//					break;
//				}
				
				//넘버값 가져옴
				if((readLen == 8)&&(isNumberRead == false)){
					byte[] number=new byte[8];
					System.out.println(readLen);
					System.arraycopy(buffer,0,number,0,readLen);
					totalLen=Integer.parseInt(new String(number));
					System.out.println(totalLen);
				}
				
				
				readLen++;
//				byte[] readData = new byte[readLen];							
//				System.arraycopy(buf, 0, readData, 0, readLen);
//				System.out.println("READ DATA : [ " + new String(readData) + " ]");
//				os.write(readData);
//				os.flush();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {try {os.close();} catch (IOException e) {}}
			if (is != null) {try {is.close();} catch (IOException e) {}}
			if (socket != null) {try {socket.close();} catch (IOException e){}}
		}
	}	
}
