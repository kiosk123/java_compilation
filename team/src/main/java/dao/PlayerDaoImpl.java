package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import vo.Player;
import vo.Team;

public class PlayerDaoImpl implements PlayerDao {

	private SimpleJdbcInsert insert;	
	private JdbcTemplate template;
	private RowMapper<Player> mapper;
	private ResultSetExtractor<Player> extractor;

	public PlayerDaoImpl(DataSource dataSource) {

		// new SimpleJdbcInsert(dataSource)도 가능하다.
		this.insert = new SimpleJdbcInsert(dataSource).withTableName("player")
				.usingGeneratedKeyColumns("player_id");		
		template=new JdbcTemplate(dataSource);
		mapper=(rs,row)->{
			Player player=new Player();
			player.setId(rs.getInt("player_id"));
			player.setName(rs.getString("playerName"));
			Team team=new Team();
			team.setId(rs.getInt("team_id"));
			team.setName(rs.getString("teamName"));
			player.setTeam(team);
			return player;
		};
		
		extractor=(rs)->{
			if(rs.next()){
				Player player=new Player();
				player.setId(rs.getInt("player_id"));
				player.setName(rs.getString("playerName"));
				Team team=new Team();
				team.setId(rs.getInt("team_id"));
				team.setName(rs.getString("teamName"));
				player.setTeam(team);
				return player;
			}else{
				return null;
			}
		};
		
	}

	@Override
	public void insertPlayer(Player player) throws DataAccessException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", player.getName());
		param.put("team_id", player.getTeam().getId());

		// 선수등록
		int newId = this.insert.executeAndReturnKey(param).intValue();
		player.setId(newId);
	}
	
	@Override
	public List<Player> getPlayerList(Integer teamId) throws DataAccessException {
		return template.query( "SELECT player_id, player.name AS playerName, "
	            + " team.team_id, team.name AS teamName "
	            + " FROM player, team WHERE player.team_id = team.team_id AND team.team_id = ?"
	            ,new Object[]{teamId},mapper);
	}

	@Override
	public Player getPlayer(Integer id) throws DataAccessException {
		return template.query("SELECT player_id, player.name AS playerName, "
	            + " team.team_id, team.name AS teamName "
	            + " FROM player, team WHERE player.team_id = team.team_id AND player_id = ?"
	            ,new Object[]{id},extractor);
	}

	@Override
	public void deletePlayer(Player player) throws DataAccessException {
		template.update("delete from player where player_id=?"
				,new Object[]{player.getId()});		
	}

	@Override
	public void updatePlayer(Player player) throws DataAccessException {
		template.update("update player set name=?, team_id=? where player_id=?"
				,new Object[]{player.getName(),player.getTeam().getId(),player.getId()});		
	}	
	
}
