package ui;

import org.apache.commons.lang.StringUtils;

import dao.PlayerDao;
import dao.TeamDao;
import vo.Player;
import vo.Team;

public class InsertPlayerUi extends AbstractUi {

	private TeamDao teamDao;

	private PlayerDao playerDao;

	public void setTeamDao(TeamDao teamDao) {
		this.teamDao = teamDao;
	}

	public void setPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}

	@Override
	public void show() {
		final String playerName="선수명";
		
		//메뉴 표시
		showMenu(playerName);
		
		//콘손에 입력된 값을 취득
		String name=getInputedString();
		
		//메뉴로 돌아간다.
		if(StringUtils.isEmpty(name)){
			return;
		//128문자 이내인지
		}else if(UiUtils.isSmallLength(name,playerName,128)){
			//새로운 선수를 생성
			Player player=new Player();
			player.setName(name);
			
			//팀을 결정
			showTeamField(player);
		}
	}
	
	protected void showTeamField(Player player) {
		final String teamId="팀Id";
		
		//메뉴를 표시
		showMenu(teamId);
		
		//콘솔에 입력된 값을 취득
		String id=getInputedString();
		
		//문자 열이 입력되었는지
		if(StringUtils.isEmpty(id)){
			return;
		}else if(UiUtils.isNumeric(id, teamId)){
			//팀 검색
			Team team=this.teamDao.getTeam(Integer.valueOf(id));
			if(team==null){
				//해당 팀은 존재하는 가
				System.out.printf("입력하신 팀 이름이 %s 인 팀은 존재하지 않습니다.\n", id);
			}
			showTeamField(player);
		}else{
			show();
		}
	}

	protected void showMenu(String wanted) {
		System.out.println("........................");
		System.out.println("『선수 명단』「선수 추가」");
		System.out.println("");
		System.out.printf("%s를 입력하고 Enter키를 눌러 주세요\n", wanted);
		System.out.println("아무 것도 입력하지 않고 Enter키를 누르면 메뉴로 돌아갑니다");
	}

}
