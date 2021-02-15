import java.util.Collections;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {

		Vector<String> vector = new Vector<String>();

		System.out.println("---------------------------첫번째 테스트----------------------");

		for (int i = 1; i <= 10; i++) {
			vector.add("filename" + i);
		}

		//비교기준을 사용하여 vector 정렬
		Collections.sort(vector, new StringComparator());

		for (String value : vector) {
			System.out.println(value);
		}

		System.out.println("---------------------------두번째 테스트----------------------");

		vector = new Vector<String>();

		for (int i = 1; i <= 10; i++) {
			String odd = "filename";
			String even = "otherfile";

			if (i % 2 != 0)
				vector.add(odd + i);
			else
				vector.add(even + i);
		}

		//비교기준을 사용하여 vector 정렬
		Collections.sort(vector, new StringComparator());

		for (String value : vector) {
			System.out.println(value);
		}

	}
}
