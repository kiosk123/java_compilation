import duck.Duck;
import duck.MallardDuck;

public class Main {
	public static void main(String[] args){
		Duck mallard=new MallardDuck();
		mallard.setFlyBehavior(()->{System.out.println("청둥오리 날개를 퍼덕.");});
		mallard.setQuackBehavior(()->{System.out.println("꽥");});
		mallard.performFly();
		mallard.performQuack();
		mallard.swim();
		mallard.display();
	}
}
