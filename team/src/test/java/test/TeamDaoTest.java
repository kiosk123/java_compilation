package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.TeamDao;
import vo.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-config.xml")
public class TeamDaoTest {

	@Autowired
	TeamDao dao;
	
	private final static Logger log=LoggerFactory.getLogger(TeamDaoTest.class);
	
	
	@Test
	public void test() {
		log.info(""+dao.getTeamList().size());
		
		for(Team t:dao.getTeamList()){
			log.info(t.getName());
		}
		
		Team team=dao.getTeam(1);
		
		assertNotNull(team);
		
		team=dao.getTeam(8);
		
		assertNull(team);
	}

}
