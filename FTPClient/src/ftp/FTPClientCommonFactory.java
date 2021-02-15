package ftp;

public class FTPClientCommonFactory {
	public static FTPClientCommon getFtpClientCommon(String server, int port){
		
		FTPClientCommon ftpClientCommon = null;
		
		//TODO 다른 포트를 쓸수도 있으므로 포트 번호로 구분하지 말고 다른 방법을 찾자
		if(server != null){
			if(port == 21){
				ftpClientCommon = new FTPClientConnector(server, port);
			}else if(port == 22){
				ftpClientCommon = new SFTPClientConnector(server, port);
			}
		}	
		return ftpClientCommon;
	}
}
