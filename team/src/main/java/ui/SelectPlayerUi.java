package ui;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import dao.PlayerDao;
import vo.Player;


public class SelectPlayerUi extends AbstractUiTemplate {

	private PlayerDao playerDao;

	private UpdatePlayerUi updatePlayerUi;

	private DeletePlayerUi deletePlayerUi;

	public void setPlayerDao(PlayerDao dao) {
		this.playerDao = dao;
	}

	public void setDeletePlayerUi(DeletePlayerUi ui) {
		this.deletePlayerUi = ui;
	}

	public void setUpdatePlayerUi(UpdatePlayerUi ui) {
		this.updatePlayerUi = ui;
	}

	public void show() {
		//헤더 표시
		showHeader();
		// 콘솔에 입력된 값을 취득
		Integer teamId = getTeamId();
		// 선수 목록을 표시
		showPlayerList(this.playerDao.getPlayerList(teamId));
		System.out.println("Enter 키를 눌러 주세요.");
		getInputedString();
		super.show();
	}

	protected Integer getTeamId() {
		System.out.println("목록을 표시하고 싶은 팀의 ID를 입력한 후 Enter 키을 눌러주세요.");
		//콘솔에 입력된 값을 취득
		String teamId = getInputedString();
		//숫자인지?
		if (StringUtils.isNotEmpty(teamId) && StringUtils.isNumeric(teamId)) {
			return Integer.valueOf(teamId);
		}
		return getTeamId();
	}

	protected void showHeader() {
		System.out.println("--------------------");
		System.out.println("『선수 명단』「선수 목록」");
		System.out.println("");
	}

	protected void showMenu() {
		showHeader();
		System.out.println("1.선수 갱신");
		System.out.println("2.선수 삭제");
		System.out.println("3.메뉴로 돌아가기 ");
		System.out.println("");
		System.out.println("번호를 입력한 후 Enter 키를 눌러 주세요 .");
	}

	protected void showPlayerList(List<Player> playerList) {
		System.out.println("--------------------");
		System.out.println("『 선수 명단 』「선수 목록」");
		// 선수가 한명이라도 있는지?
		if (!playerList.isEmpty()) {
			Player player = playerList.get(0);
			System.out.printf("팀명 : %s%n", player.getTeam().getName());
		}
		System.out.println("ID    이름");
		for (Player player : playerList) {
			// 선수ID와 선수명 표시
			System.out.printf("%d  %s%n", player.getId(), player.getName());
		}
	}

	protected int getMaxMenuNumber() {
		return 3;
	}

	protected int getMinMenuNumber() {
		return 1;
	}

	protected void execute(int number) {
		switch (number) {
		case 1:
			// 1.선수 갱신
			updatePlayerUi.show();
			break;
		case 2:
			// 2.선수 삭제
			deletePlayerUi.show();
			break;
		default:
			return;
		}
	}

}
