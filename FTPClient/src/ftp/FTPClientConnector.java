package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FTPClientConnector implements FTPClientCommon {
	private String server;
	private int port = 21;
	private FTPClient ftpClient;
	private String localWorkingDirectory;
	

	public FTPClientConnector(String server, int port) {
		this.server = server;
		this.port = port;
		ftpClient = new FTPClient();
		localWorkingDirectory = System.getProperty("user.dir");
	}
	
	//FTP가 연결되어 있는 지 확인
	@Override
	public boolean isConnected(){
		if(ftpClient == null){
			return false;
		}
		return ftpClient.isConnected();
	}

	//계정과 패스워드로 로그인
	@Override
	public boolean login(String user, String password) {
		boolean result = false;
		try {
			this.connect();
			result = ftpClient.login(user, password);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return result;
	}

	// 서버로부터 로그아웃
	@Override
	public void logout() {
		try {
			ftpClient.logout();
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// 서버로 연결
	@Override
	public void connect() {
		try {
			ftpClient.connect(server, port);
			//연결 시도후, 성공했는지 응답 코드 확인
			int reply = ftpClient.getReplyCode();
			//System.out.println("REPLY CODE : "+reply); //220이면 성공
			//서버로 부터 온 응답 출력
			System.out.println(ftpClient.getReplyString());
			
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("서버로부터 연결을 거부당했습니다");
			}
		} catch (IOException ioe) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
					System.err.println(f.getMessage());
				}
			}
		}
	}

	// FTP의 dir 명령, 모든 파일 리스트를 가져온다
	@Override
	public void dir() {
		FTPFile[] files = null;
		try {
			files = this.ftpClient.listFiles();
			System.out.println(ftpClient.getReplyString());
			for (FTPFile file : files) {
				System.out.println(file.toFormattedString());
			}
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// ldir 명령
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

	// pwd 명령, 현재 경로값을 가져온다.
	@Override
	public void pwd() {
		try {
			ftpClient.printWorkingDirectory();
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	//get 파일을 전송 받는다
	@Override
	public void get(String target) {
		OutputStream output = null;
		File local = null;
		String localPath = localWorkingDirectory + File.separator + target;
		boolean success = false;
		
		try {
			local = new File(localPath);
			local.createNewFile();
			output = new FileOutputStream(local);
			
			success = ftpClient.retrieveFile(target, output);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.out.println("Error : " + ioe.getMessage());
		}finally{
			if(output != null){try{output.close();}catch(IOException ioe){}}
			if(success == false){
				if(local.exists()){
					local.delete();
				}
			}
		}
	}

	//put 파일을 전송한다.
	@Override
	public void put(String fileName) {
		InputStream input = null;
		File local = null;

		try {
			local = new File(localWorkingDirectory + File.separator + fileName);
			input = new FileInputStream(local);
			System.out.println("파일명 : " + local.getName());
			
			ftpClient.storeFile(local.getName(), input);
			System.out.println(ftpClient.getReplyString());
			
		} catch (FileNotFoundException fnfe) {
			System.err.println("파일을 찾을 수 없습니다.");
		} catch (IOException ioe) {
			
			System.err.println(ioe.getMessage());
		}finally{
			if(input != null){try{input.close();}catch(IOException ioe){}}
		}
	}

	//rename 명령
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
			ftpClient.rename(originName, changeName);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	//cd 서버 디렉토리 이동
	@Override
	public void cd(String path) {
		try {
			ftpClient.changeWorkingDirectory(path);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// 서버로부터 연결을 닫는다
	@Override
	public void disconnect() {
		try {
			ftpClient.disconnect();
			System.err.println(server + "와 연결을 종료 합니다");
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// ascii 모드로 설정
	@Override
	public void setTransferModeByASCII() {
		try {
			ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// binary 모드로 설정
	@Override
	public void setTransferModeByBinary() {
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// rm 명령시 실행
	@Override
	public void rm(String path) {
		try {
			ftpClient.deleteFile(path);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// status 명령 실행시 사용
	@Override
	public void getStatus() {
		try {
			ftpClient.getStatus();
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	// active 명령 사용시
	@Override
	public void enterActiveMode() {
		ftpClient.enterLocalActiveMode();
		System.out.println(ftpClient.getReplyString());
		System.out.println("Active mode!");
	}

	// passive 명령 사용시
	@Override
	public void enterPassiveMode() {
		ftpClient.enterLocalPassiveMode();
		System.out.println(ftpClient.getReplyString());
		System.out.println("Passive mode!");
	}
	
	// ls 명령 사용시
	@Override
	public void ls(){
		FTPFile[] directories = null;
		FTPFile[] files = null;
		try {
			directories = ftpClient.listDirectories();
			files = ftpClient.listFiles();
			System.out.println(ftpClient.getReplyString());
			
			for(FTPFile directory : directories){
				System.out.println(directory.getName());
			}
			
			for(FTPFile file : files){
				System.out.println(file.getName());
			}
			
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	
	//rmdir
	@Override
	public void rmdir(String command) {
		try {
			ftpClient.removeDirectory(command);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	//mkdir
	@Override
	public void mkdir(String path) {
		try {
			ftpClient.makeDirectory(path);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}		
	}

	// lcd
	@Override
	public void lcd(String command) {
		String directoryPath = null;
		File file = null;
		command = command.trim();
		
		//lcd 명령만 사용할 경우
		if (command.startsWith("lcd") && (command.length() == 3)) {
			System.out.println("현재 로컬 디렉터리 : " + localWorkingDirectory);

		//.. 명령으로 상위경로 이동할 경우
		} else if (command.startsWith("lcd ..") && (command.length() == 6)) {
			try {
				directoryPath = command.substring(4, command.length()).trim();
				file = new File(localWorkingDirectory+File.separator+directoryPath);

				if (file.exists() && file.isDirectory()) {
					localWorkingDirectory = file.getCanonicalPath();
					System.out.println("현재 로컬 디렉터리 : " + localWorkingDirectory);
				} else {
					System.out.println("파일 경로를 다시 확인해 주세요");
				}

			} catch (IOException ioe) {
				System.out.println();
			}
		//'../디렉토리' 형식으로 경로 이동할 경우
		} else if (command.startsWith("lcd ..") && (command.length() > 6)) {
			try {

				directoryPath = command.substring(4, command.length()).trim();
				file = new File(localWorkingDirectory+File.separator+directoryPath);
				file = new File(file.getCanonicalPath()); // 상대경로 존재여부 확인

				if (file.exists() && file.isDirectory()) {
					localWorkingDirectory = file.getCanonicalPath();
					System.out.println("현재 로컬 디렉터리 : " + localWorkingDirectory);
				} else {
					System.out.println("파일 경로를 다시 확인해 주세요");
				}

			} catch (IOException ioe) {
				System.err.println(" Error : " + ioe.getMessage());
			}
		//'절대경로 또는 현재디렉토리의 하위경로' 형식으로 이동할 경우
		} else if (command.startsWith("lcd ") && (command.length() > 4)) {
			directoryPath = command.substring(4, command.length()).trim();
			char root = String.valueOf(directoryPath.charAt(0)).toLowerCase().charAt(0);
			String driverIndex = "";
			if(directoryPath.length() >= 3){
				driverIndex = directoryPath.substring(0,3).toLowerCase();
			}
			
			try {
				if ((pathPatternMatches(driverIndex)) || root == '/') {
					file = new File(directoryPath);
					file = new File(file.getCanonicalPath()); // 상대경로 존재여부 확인
					if (file.exists() && file.isDirectory()) {
						localWorkingDirectory = file.getCanonicalPath();
						System.out.println("현재 로컬 디렉터리 : "+ localWorkingDirectory);
					} else {
						System.out.println("파일 경로를 다시 확인해 주세요");
					}
				} else {
					file = new File(localWorkingDirectory+File.separator+directoryPath);
					file = new File(file.getCanonicalPath());
					if (file.exists() && file.isDirectory()) {
						localWorkingDirectory = file.getCanonicalPath();
						System.out.println("현재 로컬 디렉터리 : "+ localWorkingDirectory);
					} else {
						System.out.println("파일 경로를 다시 확인해 주세요");
					}
				}
			} catch (IOException ioe) {
				System.err.println(" Error : " + ioe.getMessage());
			}
		} else {
			help("help lcd");
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
					"\nhelp, ldir, pwd, get, put,\n" +
					"rename, cd, ascii, binary, rm,\n" +
					"status, active, passive,ls, lcd,\n" +
					"rmdir, mkdir, bye";
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
			case "ascii":
				System.out.println("\nascii : 파일 전송 모드를 ascii로 전환합니다.");
				break;
			case "binary":
				System.out.println("\nbinary : 파일 전송 모드를 binary로 전환합니다.");
				break;
			case "rm":
				System.out.println("\nrm <파일명> : 원격 서버에서 파일 하나를 삭제합니다.");
				break;
			case "status":
				System.out.println("\nstatus : 원격 서버와의 연결 상태를 확인합니다.");
				break;
			case "rmdir":
				System.out.println("\nrmdir [상대 경로 or 절대 경로]: 디렉토리를 삭제합니다");
				break;
			case "active":
				System.out.println("\nactive : active 모드로 전환합니다.");
				break;
			case "mkdir":
				System.out.println("\nmkdir [상대 경로 or 절대 경로]: 디렉토리를 생성합니다.");
				break;
			case "passive":
				System.out.println("\npassive : passive 모드로 전환합니다.");
				break;
			case "ls":
				System.out.println("\nls : 현재 원격 서버의 경로의 디렉토리와 파일의 목록을 출력합니다.");
				break;
			case "lcd":
				System.out.println("\nlcd [상대 경로 or 절대 경로]: 현재 로컬의 경로를 출력하거나 로컬의 현재 경로를 변경합니다.");
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

	private boolean pathPatternMatches(String pattern){
		String regexString = "^[a-z]:\\\\$";
		Pattern p = Pattern.compile(regexString);
		Matcher m = p.matcher(pattern);
		return m.matches();
	}
	
	
}

