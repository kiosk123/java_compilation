package echo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class EchoExecutor implements Runnable{
	private InputStream is = null;
	private OutputStream os = null;
	private Socket socket=null;
	
	public EchoExecutor(Socket socket) {
		this.socket=socket;
	}
	
	@Override
	public void run() {
		
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			while(true){
				//정해진 바이트 이상으로 데이터를 보낼때
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int readLen = is.read(buf);
				if(readLen==-1){
					System.out.println("클라이언트 연결이 종료되었습니다.");
					break;
				}
				byte[] readData = new byte[readLen];
				// for(int i=0;i<readLen;i++){
				// readData[i]=buf[i];
				// }
				System.arraycopy(buf, 0, readData, 0, readLen);
				System.out.println("READ DATA : [ " + new String(readData) + " ]");
				//baos.write(buf);
				os.write(readData);
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