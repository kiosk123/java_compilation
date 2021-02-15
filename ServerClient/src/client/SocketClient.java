package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import message.Message;

class SocketClient {
	
	public byte[] request(byte[] data) {
		Socket socket=null;
		OutputStream os = null;
		InputStream is = null;
		byte[] buf=new byte[1024];
		byte[] readData=null;
		int readLen=0;
		try {
			socket=new Socket("127.0.0.1",7777);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			
			os.write(data);
			os.flush();				
			
			readLen=is.read(buf);				
			readData=new byte[readLen];
			System.arraycopy(buf,0,readData,0,readLen);		
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (os != null) {try {os.close();} catch (IOException e) {}}
			if (is != null) {try {is.close();} catch (IOException e) {}}
			if (socket != null) {try {socket.close();} catch (IOException e){}}
		}
		return readData;
	}


	public static void main(String[] args) {
		SocketClient client = new SocketClient();
//		String ruleId = args[0];
//		String data = args[1];
		
		String ruleId="aaaaaaaaaaaa";
		String data="Hello";
		Message message = new Message();
		message.createSync("S");
		message.createReqAndRes("S");
		message.createResult("0");
		message.createNode(0);
		byte[] createdData=message.createMessage(ruleId,data.getBytes());
//		System.out.println("Create Data Length : "+createdData.length);
		byte[] readData=client.request(createdData);
		System.out.println(new String(readData));	
	}
}