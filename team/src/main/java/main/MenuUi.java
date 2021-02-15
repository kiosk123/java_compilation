package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ui.AbstractUiTemplate;
import ui.InsertPlayerUi;
import ui.SelectPlayerUi;
import ui.SelectTeamUi;

public class MenuUi extends AbstractUiTemplate {

	private SelectTeamUi selectTeamUi;
	private InsertPlayerUi insertPlayerUi;
	private SelectPlayerUi selectPlayerUi;

	public void setSelectTeamUi(SelectTeamUi selectTeamUi) {
		this.selectTeamUi = selectTeamUi;
	}

	public void setInsertPlayerUi(InsertPlayerUi insertPlayerUi) {
		this.insertPlayerUi = insertPlayerUi;
	}
	
	public void setSelectPlayerUi(SelectPlayerUi selectPlayerUi) {
		this.selectPlayerUi = selectPlayerUi;
	}
	
	public static void main(String[] args) {
		ApplicationContext context 
		= new ClassPathXmlApplicationContext("classpath:config/application-config.xml");

		MenuUi menuUi = context.getBean(MenuUi.class);
		while (true)
			menuUi.show();
	}

	@Override
	protected void showMenu() {
		System.out.println("...........................");
		System.out.println("[선수명단] [메뉴]");
		System.out.println("1.종료");
		System.out.println("2.팀 록록");
		System.out.println("3.선수 추가");
		System.out.println("4.선수 목록");
		System.out.println("");
		System.out.println("번호를 입력한 후 Enter 키를 눌러주세요");
	}

	@Override
	protected int getMaxMenuNumber() {
		return 4;
	}

	@Override
	protected int getMinMenuNumber() {
		return 1;
	}

	@Override
	protected void execute(int number) {
		switch (number) {
		case 1:
			// 1.종료
			System.out.println("종료되었습니다.");
			System.exit(0);
			break;

		case 2:
			// 팀목록
			this.selectTeamUi.show();
			break;
		case 3:
			// 선수추가
			this.insertPlayerUi.show();
			break;
		case 4:
			// 선수목록
			this.selectPlayerUi.show();
			break;
		}
	}

}
