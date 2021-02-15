package ftp;

public interface FTPClientCommon {
	boolean login(String user, String password);
	boolean isConnected();
	void logout();
	void connect();
	void dir();
	void localDir();
	void pwd();
	void get(String target);
	void put(String fileName);
	void rename(String renameString);
	void cd(String path);
	void disconnect();
	void setTransferModeByASCII();
	void setTransferModeByBinary();
	void rm(String path);
	void getStatus();
	void enterActiveMode();
	void enterPassiveMode();
	void ls();
	void lcd(String path);
	void help(String command);
	void rmdir(String path);
	void mkdir(String path);
}
