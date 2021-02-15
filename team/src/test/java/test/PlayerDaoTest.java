package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.PlayerDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/application-config.xml")
public class PlayerDaoTest {
	@Autowired
	PlayerDao dao;
	
	private final static Logger log=LoggerFactory.getLogger(TeamDaoTest.class);
	
	
	@Test
	public void test() {
		dao.getPlayerList(1);
		assertNull(dao.getPlayer(1));
	}
}
