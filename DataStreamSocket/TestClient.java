package test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class TestClient {
	public static void usage() {
		System.out.println("\nUsage : java TestClient server port");
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			usage();
			System.exit(1);
		}

		Socket socket = null;
		try {
			socket = new Socket(args[0], Integer.parseInt(args[1]));
		} catch (IOException ie) {
			System.out.println("cannot establish socket connection to "
					+ args[0] + ":" + args[1] + " - " + ie);
			System.exit(2);
		}
		TestData td = new TestData("I", "GMSH0001", "0", "0001", "20020930",
				"09000000", "트레이딩", "박대박", "123456789012", "대박을 건지자!", "검은손",
				"/best/good.pdf", "니들이 대박맛을 알어!");
		// TestData td = new TestData();
		TestData td2 = new TestData();

		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		try {
			// first write to server socket
			td.print();
			td.writeDataExternal(out);
			out.flush();
			System.out.println("write to socket server ends");
			// later, read from server socket
			System.out.println("0");
			td2.readDataExternal(in);
			System.out.println("1");
			td2.print();
			System.out.println("read from socket server ends");
		} finally {
			out.close();
			in.close();
			socket.close();
		}
	}
}