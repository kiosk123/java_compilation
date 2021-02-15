package dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import vo.Team;

public class TeamDaoImpl implements TeamDao {

	private JdbcTemplate template;
	private RowMapper<Team> mapper;
	private ResultSetExtractor<Team> extractor;
	
	public TeamDaoImpl(DataSource dataSource) {
		this.template=new JdbcTemplate(dataSource);
		mapper=(rs,row)->{		
			Team team=new Team();
			team.setId(rs.getInt("team_id"));
			team.setName(rs.getString("name"));
			return team;
		};
		
		extractor=(rs)->{
			if(rs.next()){
				Team team=new Team();
				team.setId(rs.getInt("team_id"));
				team.setName(rs.getString("name"));
				return team;
			}else{
				return null;
			}
		};
	}
	
	
	@Override
	public Team getTeam(Integer teamId) throws DataAccessException {
		return template.query("select * from team where team_id=?",new Object[]{teamId},extractor);
	}

	@Override
	public List<Team> getTeamList() throws DataAccessException {
		return template.query("select * from team", mapper);
	}	
}
