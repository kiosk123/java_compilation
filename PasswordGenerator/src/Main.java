public class Main {

	public static void main(String[] args)throws Exception {
			
		Encryption encrypt=new HashEncryption();
		
		System.out.println(encrypt.encrypt("123123123"));

	}

}
