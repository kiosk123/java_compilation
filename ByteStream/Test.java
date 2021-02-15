import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

	public static void main(String[] args) {

		FileInputStream in = null;
		FileOutputStream out = null;
		int data = -1;
		
		try {
			out = new FileOutputStream("text.txt", false);

			for (int i = 0; i < 5; i++) {
				out.write(i);
			}

			out.flush();

			in = new FileInputStream("text.txt");

			while ((data = in.read()) != -1) {
				System.out.println(data);
			}
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
