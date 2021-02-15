
public class LamdaTest {

	public static void main(String[] args) {
		
		//람다는 인터페이스에 메소드 하나만 있어야지 가능
		Nice nice=()->{
			System.out.println("");
		};
		
		nice.doNice();
		
		int num1=3;
		int num2=2;
		
		CalAdd cal1=(int a,int b)->{return a+b;};
		CalAdd cal2=(int a,int b)->a+b;
		CalAdd cal3=(a,b)->a+b;
		Square cal4=(i)->i*i;
		Square cal5=i->i*i;
		Square cal6=(int i)->{return i*i;};
		
	}

}

interface Nice{
	
	void doNice();
}

interface Square{
	int square(int i);
}

interface CalAdd{
	int add(int num1,int num2);
}