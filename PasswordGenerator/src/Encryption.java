

public interface Encryption {
	String encrypt(String origin)throws Exception;
	String decrypt(String cipher)throws Exception;
	boolean isEqual(String compare1,String compare2)throws Exception;	
}
