package dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import vo.Player;

public interface PlayerDao {
	void insertPlayer(Player player)throws DataAccessException;
	List<Player> getPlayerList(Integer teamId)throws DataAccessException;
	Player getPlayer(Integer id)throws DataAccessException;
	void deletePlayer(Player player)throws DataAccessException;
	void updatePlayer(Player player)throws DataAccessException;
}
