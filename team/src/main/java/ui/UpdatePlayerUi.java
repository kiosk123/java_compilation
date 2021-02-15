package ui;

import org.apache.commons.lang.StringUtils;

import dao.PlayerDao;
import dao.TeamDao;
import vo.Player;
import vo.Team;

public class UpdatePlayerUi extends AbstractUi {

	private PlayerDao playerDao;

	private TeamDao teamDao;

	public void setPlayerDao(PlayerDao dao) {
		this.playerDao = dao;
	}

	public void setTeamDao(TeamDao service) {
		this.teamDao = service;
	}

	public void show() {

		Player player = getPlayer();
		if (player == null) {
			return;
		}

		String name = getName(player);
		if (StringUtils.isNotEmpty(name)) {
			player.setName(name);
		}

		Team team = getTeam(player);
		if (team != null) {
			player.setTeam(team);
		}

		this.playerDao.updatePlayer(player);
		System.out.printf("선수ID '%s'인 선수를, 선수명 '%s', 팀 '%s'로 수정합니다.%n", player.getId(), player.getName(), player.getTeam().getName());
	}

	protected Player getPlayer() {
		final String playerId = "선수ID";
		// 메뉴 표시
		showMenu(playerId);
		System.out.println("아무것도 입력하지 않고 Enter를 누르면 메뉴로 돌아갑니다.");
		// 콘솔에 입력된 값을 취득
		String id = getInputedString();
		// 문자열이 입력되어 있는지?
		if (StringUtils.isEmpty(id)) {
			return null;
			// 숫자인지?
		} else if (UiUtils.isNumeric(id, playerId)) {
			// ID로 선수를 검색
			Player player = this.playerDao.getPlayer(Integer.valueOf(id));

			if (player == null) {
				// 해당하는 선수가 존재하지 않는다
				System.out.printf("입력하신 선수ID가 '%s'인 선수는 존재하지않습니다.%n", id);
				return getPlayer();
			}
			return player;
		}
		return getPlayer();
	}

	protected String getName(Player player) {
		final String playerName = "선수명";
		// 메뉴 표시
		showMenu(playerName);
		System.out.println("아무것도 입력하지 않고 Enter를 누르면 변경되지 않습니다.");
		System.out.printf("원래값：%s%n", player.getName());
		// 콘솔에 입력된 값을 취득
		String name = getInputedString();
		// 문자열이 입력되어 있는지?
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		// 128문자 이내인지?
		if (UiUtils.isSmallLength(name, playerName, 128)) {
			return name;
		}
		return getName(player);
	}

	protected Team getTeam(Player player) {
		final String teamId = "팀ID";
		// 메뉴 표시
		showMenu(teamId);
		System.out.println("아무것도 입력하지 않고 Enter를 누르면 변경되지 않습니다.");
		System.out.printf("원래값：%s %s%n", player.getTeam().getId(), player.getTeam().getName());
		// 콘솔에 입력된 값을 취득
		String id = getInputedString();
		// 문자열이 입력되어 있는지?
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		// 숫자인지?
		if (UiUtils.isNumeric(id, teamId)) {
			// ID로 팀을 검색
			Team team = this.teamDao.getTeam(Integer.valueOf(id));
			if (team == null) {
				// 해당하는 선수가 존재하지 않는다
				System.out.printf("입력하신 팀명이 '%s'인 팀은 존재하지 않습니다.%n", id);
				return getTeam(player);
			}
			return team;
		}
		return getTeam(player);
	}

	protected void showMenu(String wanted) {
		System.out.println("--------------------");
		System.out.println("『선수 명단』「선수 갱신」");
		System.out.println("");
		System.out.printf("%s를 입력한 후 Enter를 눌러주세요.", wanted);
	}

}
