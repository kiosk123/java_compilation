import java.io.File;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws Exception {

		File dir = new File("C:\\Users\\MyCom\\Desktop\\새폴더");

		File[] fileList = dir.listFiles();
	
		System.out.println("----------------파일 정렬전 결과--------------------");
		
		for(File file:fileList){
			System.out.println(file.getName());
		}
		
		//파일 이름을 정렬한다.
		Arrays.sort(fileList,new TestComparator());
		
		System.out.println("----------------파일 정렬후 결과--------------------");
		
		for(File file:fileList){
			System.out.println(file.getName());
		}
		
	}
}
