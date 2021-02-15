
import java.security.MessageDigest;


public class HashEncryption implements Encryption {

	@Override
	public String encrypt(String origin) throws Exception {
				
		/*SHA-256 방식으로 암호화 한다.*/
		MessageDigest shaDigest = MessageDigest.getInstance("SHA-256"); 
		shaDigest.update(origin.getBytes()); 
		byte byteData[] = shaDigest.digest();
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		String sha = sb.toString();
		String cipher=BCrypt.hashpw(sha, BCrypt.gensalt());				
		return cipher;
	}
	
	/*compare1은 로그인 창에서 입력한 password, compare2는 db에서 가져온 패스워드*/
	@Override
	public boolean isEqual(String compare1, String compare2) throws Exception {
		/*SHA-256 방식으로 암호화 한다.*/
		MessageDigest shaDigest = MessageDigest.getInstance("SHA-256"); 
		shaDigest.update(compare1.getBytes()); 
		byte byteData[] = shaDigest.digest();
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		String sha = sb.toString();
		return BCrypt.checkpw(sha, compare2);
	}

	@Override
	public String decrypt(String cipher) throws Exception {
		return null;
	}

}
