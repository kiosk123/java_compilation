package ui;

import org.apache.commons.lang.StringUtils;

import dao.PlayerDao;
import vo.Player;

public class DeletePlayerUi extends AbstractUi {

	private PlayerDao dao;

	public void setDao(PlayerDao dao) {
		this.dao = dao;
	}

	@Override
	public void show() {
		//메뉴 표시
		showMenu();
		
		//콘솔에 입력된 값 취득
		String id=getInputedString();
		
		//문자열이 입력되어 있는지
		if(StringUtils.isEmpty(id))
			return;
		else if(UiUtils.isNumeric(id, "선수ID")){
			//ID로 선수취득
			Player player=this.dao.getPlayer(Integer.valueOf(id));
			if(player==null){
				//해당 선수가 존재하지 않는다
				System.out.printf("입력한 선수 ID %s 인 선수는 존재하지 않습니다.", id);
				show();
			}else{
				//선수를 삭제
				this.dao.deletePlayer(player);
				System.out.printf("입력한 선수 ID %s 인 선수를 삭제합니다.", id);
			}
		}else {
			show();
		}

	}
	
	protected void showMenu() {
		System.out.println("........................");
		System.out.println("『선수 명단』「선수 삭제」");
		System.out.println("");
		System.out.printf("선수 ID를 입력한 후 Enter를 눌러주세요");
		System.out.println("아무 것도 입력하지 않고 Enter키를 누르면 메뉴로 돌아갑니다");
	}

}
