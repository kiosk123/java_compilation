package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class LengthExecutor implements Runnable{
	private InputStream is = null;
	private OutputStream os = null;
	private Socket socket=null;
	
	public LengthExecutor(Socket socket) {
		this.socket=socket;
	}
	
	@Override
	public void run() {
		
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			while(true){
				byte[] lenBuffer = new byte[8];
				int readOffset = 0;
				while(true){
					int tmp = is.read(lenBuffer,readOffset,lenBuffer.length-readOffset);
					if(tmp < 0){
						System.err.println("client 접속 종료.");
						break;
					}
					readOffset += tmp; //읽은 길이만큼 readLen에 추가
					if(readOffset == lenBuffer.length)
						break;
				}
				//버퍼를 다 채우지 못했다는 것은 데이터를 읽는 데 실패한 것을 의미하기 때문에
				if(readOffset != lenBuffer.length){
					System.err.println("길이 필드 read 실패");
					break;
				}
//				System.out.println("길이 필드 [ "+new String(lenBuffer)+"]");
//				int remainLength=Integer.parseInt(new String(lenBuffer))-lenBuffer.length;
//				byte[] data=new byte[remainLength];
//				readOffset=0;
				
				int totalLength = Integer.parseInt(new String(lenBuffer));
				byte[] data = new byte[totalLength];
				System.arraycopy(lenBuffer, 0, data, 0, lenBuffer.length);
				
				while(true){
					int tmp = is.read(data, readOffset, data.length-readOffset);
					if(tmp < 0){
						System.err.println("client 접속 종료.");
						break;
					}
					readOffset += tmp; //읽은 길이만큼 readLen에 추가
					if(readOffset == data.length)
						break;
				}
				System.out.println("[전체 데이터 : "+new String(data)+"]");
				os.write(data);
				os.flush();
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