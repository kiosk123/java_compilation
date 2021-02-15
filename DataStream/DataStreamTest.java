import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataStreamTest {

	public static void main(String[] args) {

		DataInputStream in = null;
		DataOutputStream out = null;

		int data = -1;
		
		try {
			
			out = new DataOutputStream(new FileOutputStream("text1.txt", false));
			
			for (int i = 0; i < 5; i++) {
				out.writeInt(i);
			}
			out.writeUTF("English 12345 한글 美國");
			out.flush();

			in = new DataInputStream(new FileInputStream("text1.txt"));

			for (int i = 0; i < 5; i++) {
				System.out.println(in.readInt());
			}
			
			System.out.println(in.readUTF());
		} catch (IOException ioe1) {
			// TODO: handle exception
		} finally {
			try {
				if (in != null)
					in.close();

				if (out != null)
					out.close();
			} catch (IOException ioe2) {

			}
		}

	}
}
