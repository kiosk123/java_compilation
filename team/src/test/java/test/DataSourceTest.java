package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/application-config.xml")
public class DataSourceTest {

	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate template;
	
	@Before
	public void setup(){
		this.template=new JdbcTemplate(dataSource);
	}
	
	
	@Test
	public void dataSourceTest() {
		assertNotNull(dataSource);
	}
	
	@Test
	public void dataTest(){
		String name=template.queryForObject("select name from team where team_id=?", String.class,1);
		
		assertNotNull(name);
		
		assertEquals(name, "Manchester FC");
	}

}
