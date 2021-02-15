package ui;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public abstract class AbstractUiTemplate extends AbstractUi {

	@Override
	public void show() {
		//메뉴 표시
		showMenu();
		
		//콘솔에 입력된 값을 취득
		String intputedString=getInputedString();
		
		//입력 문자열 확인
		if(isValidNumber(intputedString)){
			//처리실행
			execute(NumberUtils.toInt(intputedString));
		}

	}
	
	abstract protected void showMenu();
	abstract protected int getMaxMenuNumber();
	abstract protected int getMinMenuNumber();
	abstract protected void execute(int number);
	
	protected boolean isValidNumber(String str){
		//문자열이 입력되고 있는지
		if(StringUtils.isBlank(str))
			return false;
		//숫자인지
		else if(!StringUtils.isNumeric(str))
			return false;
		
		//숫자이므로 int로 반환
		int number=NumberUtils.toInt(str);
		
		//숫자가 메뉴에 표시되고 있는 번호의 범위를 벗어났는지...
		if(getMinMenuNumber()>number||number>getMaxMenuNumber()){
			return false;
		}
		return true;
	}

}
