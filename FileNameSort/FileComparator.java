import java.io.File;
import java.util.Comparator;

//내림차순 정렬을 위한 비교기준
public class FileComparator implements Comparator<File>{

	@Override
	public int compare(File obj1, File obj2) {
		
		int result=0;
		
		if(extractWordEquals(extractWord(obj1.getName()), extractWord(obj2.getName())))
		{
			result= numCompare(extractNum(obj1.getName(), extractWord(obj1.getName())), 
						extractNum(obj2.getName(), extractWord(obj2.getName())));									
		}else
			result=wordCompare(obj1.getName(), obj2.getName());				
		return result;
	}
	
	//단어를 추출하는 메소드 - NewFile01.html 이라는 파일이름에서 NewFile만 추출한다.
	private String extractWord(String obj){
		String temp=obj.replaceAll("[\\d]","");
		return temp.substring(0,temp.indexOf("."));
	}
	
	//추출한 두개의 단어 비교하는 메소드
	private boolean extractWordEquals(String word1,String word2){
		return word1.equals(word2);
	}
	
	//숫자를 추출하기 위한 메소드 - NewFile01.html이라는 파일이름에서 숫자 1만 추출
	private int extractNum(String origin,String extractWord){		
		String temp=origin.substring(0,origin.indexOf("."));
		return Integer.parseInt(temp.replaceAll(extractWord, ""));
	}
	
	//두개의 숫자의 결과를 비교하기 위한 메소드
	private int numCompare(int num1,int num2){
		
		if (num1-num2 < 0) 
			return -1;
		else if (num1-num2 > 0) 
			return 1;
		else
		    return 0;
	}
	
	//두개의 단어 비교하는 메소드
	private int wordCompare(String word1,String word2){
		if (word1.compareTo(word2) < 0) 
			return -1;
		else if (word1.compareTo(word2) > 0) 
			return 1;
		else
		    return 0;
	}
}