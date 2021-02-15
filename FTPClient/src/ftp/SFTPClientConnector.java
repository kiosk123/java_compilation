package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

class SFTPClientConnector implements FTPClientCommon{

	private String server;
	private int port = 22;
	private JSch jSch;
	private String localWorkingDirectory;
	private Session session;
	private Channel channel;
	private ChannelSftp channelSftp;
		
	public SFTPClientConnector(String server, int port){
		this.server = server;
		this.port = port;
		this.jSch = new JSch();
		this.localWorkingDirectory = System.getProperty("user.dir");
	}
	
	@Override
	public boolean login(String user, String password) {
		boolean result = false;
		try {
			session = jSch.getSession(user, server, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp)channel;
			result = true;
		
		//host나 user가 유효하지 않으면 예외발생
		} catch (JSchException je) {
			System.out.println(je.getMessage());
		}
		return result;
	}

	@Override
	public boolean isConnected() {
		if(channel == null){
			return false;
		}
		return channel.isConnected();
	}

	@Override
	public void logout() {
		if(session != null){
			session.disconnect();
		}
	}

	@Override
	public void connect() {}

	//dir
	@Override
	public void dir() {
		try {
			Vector vector = channelSftp.ls("*");
			for(int i = 0 ; i < vector.size() ; i++){
				System.out.println(vector.get(i));
			}
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}		

	}

	//ldir
	@Override
	public void localDir() {
		File dir = new File(localWorkingDirectory);

		File[] list = dir.listFiles();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (int i = 0; i < list.length; i++) {
			// list[i]가 디렉토리인지 아닌지 isDirectory()메소드를 이용하여 판별
			if (list[i].isDirectory()) {
				System.out.println(simpleDateFormat.format(list[i].lastModified()) + "\tDIR \t" + list[i].getName());
			} else {
				System.out.println(simpleDateFormat.format(list[i].lastModified()) + "\tFILE \t" + list[i].getName());
			}
		}
		
	}

	//pwd
	@Override
	public void pwd() {
		try {
			System.out.println(channelSftp.pwd());
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}		
	}

	//get
	@Override
	public void get(String target) {
		InputStream in = null;
		OutputStream out = null;
		File file = null;
		int readLen = 0;
		boolean success = false;
		
		try{
			in = channelSftp.get(target);			
			
			file = new File(localWorkingDirectory+File.separator+target);
			file.createNewFile();
			
			out = new FileOutputStream(file);
			while((readLen = in.read())!= -1){
				out.write(readLen);
			}
			
			success = true;
		}catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}catch (FileNotFoundException fnfe ) {
			System.out.println(fnfe.getMessage());
		}catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}finally{
			if(in != null){try{in.close();}catch(IOException e){}}
			if(out != null){try{out.close();}catch(IOException e){}}
			if(success == false){
				if((file != null) && (file.exists())){
					file.delete();
				}
			}
		}
		
	}

	//put
	@Override
	public void put(String fileName) {
		File file = new File(localWorkingDirectory+File.separator+fileName);
		FileInputStream in = null;
		
		if(!file.exists() || file.isDirectory()){
			System.out.println("파일이 존재하지 않거나 경로가 잘 못 되었습니다.");
			return;
		}
		
		try {
			in = new FileInputStream(file);
			channelSftp.put(in,file.getName());
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}finally{
			try{if(in != null)in.close();}catch(IOException ioe){}
		}
		
	}

	//rename
	@Override
	public void rename(String renameString) {
		String[] fileName = renameString.split(" ");
		String originName = null, changeName = null;
		if (fileName.length != 2) {
			System.out.println("명령어를 확인해 주십시오");
			return;
		}

		originName = fileName[0];
		changeName = fileName[1];
		
		try {
			channelSftp.rename(originName, changeName);
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}
	}

	//cd 
	@Override
	public void cd(String path) {
		try {
			channelSftp.cd(path);
			System.out.println(channelSftp.pwd());
		} catch (SftpException sftpe) {
			//sftpe.id 에러코드 확인할 수 있는 프로퍼티
			System.out.println(sftpe.getMessage());
		}		
	}

	@Override
	public void disconnect() {
		if(channelSftp != null){
			channelSftp.disconnect();
		}
	}

	@Override
	public void setTransferModeByASCII() {
		help("help");
	}

	@Override
	public void setTransferModeByBinary() {	
		help("help");
	}

	//rm
	@Override
	public void rm(String path) {
		try {
			channelSftp.rm(path);
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}		
	}
	
	//rmdir
	@Override
	public void rmdir(String command) {
		try {
			channelSftp.rmdir(command);
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}				
	}

	@Override
	public void getStatus() {
		help("help");	
	}

	@Override
	public void enterActiveMode() {
		help("help");		
	}

	@Override
	public void enterPassiveMode() {
		help("help");	
	}

	//ls
	@Override
	public void ls() {
		try {
			Vector vector = channelSftp.ls("*");
			for(int i = 0 ; i < vector.size() ; i++){
				System.out.println(vector.get(i));
			}
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}		
	}
		
	//mkdir
	@Override
	public void mkdir(String path) {
		try {
			channelSftp.mkdir(path);
		} catch (SftpException sftpe) {
			System.out.println(sftpe.getMessage());
		}		
	}

	// lcd
	@Override
	public void lcd(String command) {
		if (command.startsWith("lcd") && (command.length() == 3)) {
			System.out.println("현재 로컬 디렉터리 : " + localWorkingDirectory);
		}else if(command.startsWith("lcd ") && (command.length() > 4)){
			command = command.substring(4,command.length()).trim();
			try {
				channelSftp.lcd(command);
				localWorkingDirectory = channelSftp.lpwd();
				System.out.println("현재 로컬 디렉터리 : " + localWorkingDirectory);
			} catch (SftpException sftpe) {
				System.out.println(sftpe.getMessage());
			}
		}else{
			System.out.println("명령어를 확인해 주세요.");
		}
	}

	//help 명령 구현
	@Override
	public void help(String command) {
		String guide = "";
		command = command.trim();
		if(command.equals("help")){
			guide = "\n자세한 사용법은 help <명령어>로 확인해 주세요" +
					"\n명령어 목록 : " +
					"\nhelp, dir, ldir, pwd, get, put,\n" +
					"rename, cd, rm, ls, lcd, rmdir, mkdir" +
					"bye";
			System.out.println(guide);
		}else{
			command = command.substring(5,command.length()).toLowerCase();
			switch (command) {
			case "ldir":
				System.out.println("\nldir : 현재 로컬 경로의 디렉토리와 파일 목록을 출력합니다.");
				break;
			case "dir":
				System.out.println("\ndir : 현재 원격 서버의 경로의 디렉토리와 파일의 목록과 정보를 출력합니다.");
				break;
			case "pwd":
				System.out.println("\npwd : 현재 원격 서버의 경로를 출력합니다.");
				break;
			case "get":
				System.out.println("\nget <파일명> : 접속한 원격 서버에서 파일을 다운 받습니다.");
				break;
			case "put":
				System.out.println("\nput <파일명> : 접속한 원격 서버에 파일을 업로드합니다.");
				break;
			case "rename":
				System.out.println("\nput <원래파일명> <바꿀 파일명> : 접속한 원격 서버의 파일명을 바꿉니다.");
				break;
			case "cd":
				System.out.println("\ncd <상대경로 or 절대경로> : 접속한 원격 서버의 현재 경로를 바꿉니다.");
				break;
			case "rm":
				System.out.println("\nrm <파일명> : 원격 서버에서 파일 하나를 삭제합니다.");
				break;
			case "ls":
				System.out.println("\nls : 현재 원격 서버의 경로의 디렉토리와 파일의 목록을 출력합니다.");
				break;
			case "lcd":
				System.out.println("\nlcd [상대 경로 or 절대 경로]: 현재 로컬의 경로를 출력하거나 로컬의 현재 경로를 변경합니다.");
				break;
			case "rmdir":
				System.out.println("\nrmdir [상대 경로 or 절대 경로]: 디렉토리를 삭제합니다.");
				break;
			case "mkdir":
				System.out.println("\nmkdir [상대 경로 or 절대 경로]: 디렉토리를 생성합니다.");
				break;
			case "bye":
				System.out.println("\nbye : 원격 서버와의 연결을 종료합니다.");
				break;
			default:
				help("help");
				break;
			}
		}		
	}
	
}
