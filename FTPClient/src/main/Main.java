package main;

import java.util.Scanner;

import ftp.FTPClientCommon;
import ftp.FTPClientCommonFactory;


public class Main {
	
	private static final int ARGUMENTS_LENGTH = 1;
	private static final String USAGE = "사용법 : java -jar FTPClient.jar <server> [port]";
	private static String server = null;
	private static int port = 21;
	
	public static void main(String args[]) {
		
		boolean result = argsServerAndPort(args);
		if(!result)
			return;
		
		FTPClientCommon ftp = FTPClientCommonFactory.getFtpClientCommon(server, port);
		if(ftp == null){
			System.out.println("FTP 포트번호 21번 SFTP는 포트번호 22번  입니다.");
			return;
		}
		ftp.connect();
		
		setIdAndPassword(ftp);
		
		while(ftp.isConnected()){
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("\n명령 : ");
			String command = scanner.nextLine();
			if(command.equals("bye")){
				ftp.logout();
				ftp.disconnect();				
			}else if(command.equals("ascii")){				
				ftp.setTransferModeByASCII();				
			}else if(command.equals("binary")){				
				ftp.setTransferModeByBinary();				
			}else if(command.equals("status")){				
				ftp.getStatus();				
			}else if(command.equals("dir")){	
				ftp.dir();
			}else if(command.equals("ldir")){	
				ftp.localDir();
			}else if(command.equals("pwd")){
				ftp.pwd();
			}else if(command.equals("active")){
				ftp.enterActiveMode();
			}else if(command.equals("passive")){
				ftp.enterPassiveMode();
			}else if(command.equals("ls")){
				ftp.ls();
			}else if(command.startsWith("lcd")){
				ftp.lcd(command);
			}else if(command.startsWith("help")){
				ftp.help(command);
			}else if(command.startsWith("cd ") && (command.length() > 3)){	
				command = command.substring(3,command.length()).trim();
				ftp.cd(command);	
			}else if(command.startsWith("rmdir ") && (command.length() > 6)){
				command = command.substring(6,command.length()).trim();
				ftp.rmdir(command);
			}else if(command.startsWith("mkdir ") && (command.length() > 6)){
				command = command.substring(6,command.length()).trim();
				ftp.mkdir(command);
			}else if(command.startsWith("get ") && (command.length() > 4)){	
				command = command.substring(4,command.length()).trim();
				ftp.get(command);	
			}else if(command.startsWith("rm ") && (command.length() > 3)){	
				command = command.substring(7, command.length()).trim();
				ftp.rm(command);
			}else if(command.startsWith("put ") && (command.length() > 4)){	
				command = command.substring(4,command.length()).trim();
				ftp.put(command);
			}else if(command.startsWith("rename ") && (command.length() > 7)){	
				command = command.substring(7,command.length());
				ftp.rename(command);
			}else{	
				ftp.help("help");
			}			
		}
	}
	
	/* 서버 ip와 포트 입력 처리*/
	private static boolean argsServerAndPort(String[] args){
		boolean result = false;
		if( args.length == ARGUMENTS_LENGTH ){
			server = args[0].trim();
			result = true;
		}else if( args.length == ( ARGUMENTS_LENGTH + 1 )){
			server = args[0].trim();
			
			try{
				port = Integer.parseInt(args[1].trim());
				result = true;
			}catch (NumberFormatException e) {
				System.err.println(USAGE);
				result = false;
			}
			
		}else{
			System.err.println(USAGE);
			result = false;
		}
		return result;
	}
	
	/* 유저 아이디와 패스워드 입력처리 */
	private static void setIdAndPassword(FTPClientCommon ftp){
		Scanner scanner = new Scanner(System.in);
		System.out.print("접속 아이디 입력 : ");
		String id = scanner.nextLine();
		if(id.equals("bye")){
			ftp.logout();
			ftp.disconnect();
			return;
		}		
		System.out.print("비밀 번호 입력 : ");
		String password = scanner.nextLine();
		if(password.equals("bye")){
			ftp.logout();
			ftp.disconnect();
			return;
		}
		boolean loginSuccess = ftp.login(id, password);
		if(loginSuccess){
			System.out.println("접속 성공");
		}else{
			System.out.println("아이디와 비번을 다시 확인해 주세요\n");
			setIdAndPassword(ftp);
		}
	}
}
