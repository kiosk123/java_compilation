import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterTest {

	public static void main(String[] args) {

		FileWriter out = null;
		FileReader in = null;
		try {

			out = new FileWriter(new File("data.txt"));

			out.write("msg");
			out.append(".");
			out.flush();

			in = new FileReader("data.txt");
			int c;
			while ((c = in.read()) != -1) {
				System.out.print((char) c);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e2) {}
		}

	}
}
