import java.io.File;
import java.util.Comparator;

public class TestComparator implements Comparator<File> {

	@Override
	public int compare(File obj1, File obj2) {
		return obj1.getName().compareTo(obj2.getName());
	}

}
