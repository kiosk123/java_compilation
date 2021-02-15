import java.util.Comparator;

//내림차순 정렬을 위한 비교기준
public class StringComparator implements Comparator<String>{

	@Override
	public int compare(String obj1, String obj2) {
		
		int result=0;
		
		if(extractWordEquals(extractWord(obj1), extractWord(obj2)))
		{
			result= numCompare(extractNum(obj1, extractWord(obj1)), 
					extractNum(obj2, extractWord(obj2)));									
		}else
			result=wordCompare(obj1, obj2);				
		return result;
	}
	
	//단어를 추출하는 메소드
	private String extractWord(String obj){
		return obj.replaceAll("[\\d]*","");
	}
	
	//추출한 두개의 단어 비교하는 메소드
	private boolean extractWordEquals(String word1,String word2){
		return word1.equals(word2);
	}
	
	//숫자를 추출하기 위한 메소드
	private int extractNum(String origin,String extractWord){
		return Integer.parseInt(origin.replaceAll(extractWord, ""));
	}
	
	//두개의 숫자의 결과를 비교하기 위한 메소드
	private int numCompare(int num1,int num2){
		
		if (num1-num2 < 0) 
			return 1;
		else if (num1-num2 > 0) 
			return -1;
		else
		    return 0;
	}
	
	//두개의 단어 비교하는 메소드
	private int wordCompare(String word1,String word2){
		if (word1.compareTo(word2) < 0) 
			return 1;
		else if (word1.compareTo(word2) > 0) 
			return -1;
		else
		    return 0;
	}
}