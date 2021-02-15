package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class LengthClient {
	
	private String ip;
	private int port;
	//아토믹 인티저 자체가 동기화 
//	private static AtomicInteger SEQ=new AtomicInteger(0);
	private static int SEQ=0;
	private LengthClient(String ip,int port){
		this.ip = ip;
		this.port = port;
	}
	
	public void execute(byte[] body){
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedReader reader = null;
		try {
			socket = new Socket(ip,port);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			
			byte[] sendData=createMessage(body);
			os.write(sendData);
			byte[] lenBuffer=new byte[8];
			int readOffset=0;
			while(true){
				int tmp=is.read(lenBuffer,readOffset,lenBuffer.length-readOffset);
				if(tmp < 0){
					System.err.println("서버 접속 종료.");
					break;
				}
				readOffset+=tmp; //읽은 길이만큼 readLen에 추가
				if(readOffset == lenBuffer.length)
					break;
			}
//			//버퍼를 다 채우지 못했다는 것은 데이터를 읽는 데 실패한 것을 의미하기 때문에
//			if(readOffset != lenBuffer.length){
//				System.err.println("길이 필드 read 실패");
//				break;
//			}
//			System.out.println("길이 필드 [ "+new String(lenBuffer)+"]");
//			int remainLength=Integer.parseInt(new String(lenBuffer))-lenBuffer.length;
//			byte[] data=new byte[remainLength];
//			readOffset=0;
			
			int totalLength = Integer.parseInt(new String(lenBuffer));
			byte[] data=new byte[totalLength];
			System.arraycopy(lenBuffer, 0, data, 0, lenBuffer.length);
			
			while(true){
				int tmp=is.read(data,readOffset,data.length-readOffset);
				if(tmp < 0){
					System.err.println("서버 접속 종료.");
					break;
				}
				readOffset+=tmp; //읽은 길이만큼 readLen에 추가
				if(readOffset == data.length)
					break;
			}
			System.out.println("[전체 데이터 : "+new String(data)+"]");
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is!=null){try{is.close();}catch(IOException e){}}
			if(os!=null){try{os.close();}catch(IOException e){}}
			if(socket!=null){try{socket.close();}catch(IOException e){}}
		}
	}
	private byte[] createMessage(byte[] data){
		//데이터 조립
		int offset=0;
		byte[] rtnValue=new byte[70+data.length];
		//LENGTH 8 0
		String lengthStr=String.format("%08d",rtnValue.length);
		System.arraycopy(lengthStr.getBytes(),0,rtnValue,0,8); offset+=8;
 		//GID    32 8
		String hostname=null;
		try {
			hostname=InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostname="localhos";
		}
		byte[] hostNameBytes=new byte[8];
		Arrays.fill(hostNameBytes,(byte)' ');
		for(int i=0; i<hostname.length()&&i < 8;i++){
			hostNameBytes[i]=hostname.getBytes()[i];
		}
		
		System.arraycopy(hostNameBytes,0, rtnValue, offset, 8); offset+=8;
		//System time : 17 : yyyyMMddHHmmssSSS
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time=sdf.format(new Date());
		System.arraycopy(time.getBytes(), 0, rtnValue, offset, 17); offset+=17;
		
		String seq=null;
		synchronized (LengthClient.class) {
			SEQ++;
			if(SEQ==99999){
				SEQ=0;
			}
			seq=String.format("%05d",SEQ);
		}	
				
		System.arraycopy(seq.getBytes(),0,rtnValue, offset, 5); offset+=5;
		System.arraycopy("00".getBytes(),0,rtnValue, offset, 2); offset+=2;
		//SYNC   1 40
		rtnValue[40]='S'; offset+=1;
		//REQ / RES 1  41
		rtnValue[41]='S'; offset+=1;
		//RESULT 1 42
		rtnValue[42]='0'; offset+=1;
		//RULEID 12 43 : AAAAAAAAAAA00
		System.arraycopy("AAAAAAAAAA00".getBytes(),0,rtnValue, 43, 12); offset+=12;
		//NODE
		System.arraycopy("00".getBytes(),0,rtnValue,55,2); offset+=2;
		//여부 필드
		System.arraycopy("             ".getBytes(),0,rtnValue,57,13); offset+=13;
		//데이터
		System.arraycopy(data,0, rtnValue,70, data.length);
//		rtnValue="00000080ABCEFGEF20170119635009990000000SS0AAAAAAAAAAAAA00             XXXXXXXXXX".getBytes();
		return rtnValue;
	}
	public static void main(String[] args) {
//		if(args.length != 2){
//			System.out.println("Using : EchoClient <ip addr> <port>");
//			System.exit(1);
//		}
		String ip = "localhost";
		int port=7777;
//		String ip = args[0];
//		int port = Integer.parseInt(args[1]);
		LengthClient client=new LengthClient(ip,port);
		byte[] data="xxxxxxxxxxxxxxxxxxxxxxxxxx".getBytes();
		client.execute(data);
	}
}
