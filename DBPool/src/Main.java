import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class Main {

	public static void main(String[] args) {
		
		/*POOL_NAME은 여러개의 커넥션 풀을 생성시 
		    커넥션풀을 구분하기 위한이름 커넥션 풀을 
		    실제 환경에서 세팅하려면 static 클래스로 만드는 게 좋을 듯*/
		final String POOL_NAME ="/rapadb2_pool";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try
		{
			ConnectionFactory connectionFactory = 
						new DriverManagerConnectionFactory (
							"jdbc:mysql://localhost:3306/rapadb2?characterEncoding=utf8",
							"rapauser2",
							"rapapass2"
						);
			PoolableConnectionFactory poolableConnectionFactory = 
					new PoolableConnectionFactory(connectionFactory, null);
			
			GenericObjectPoolConfig objectPoolConfig = new GenericObjectPoolConfig();
			
			//5분동안 커넥션을 사용하지 않으면 회수함
			objectPoolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			objectPoolConfig.setTestWhileIdle(true);
			objectPoolConfig.setMinIdle(4);
			objectPoolConfig.setMaxTotal(50);

			//커넥션 풀 세팅
			GenericObjectPool<PoolableConnection> objectPool =
				new GenericObjectPool<PoolableConnection>(
						poolableConnectionFactory,
						objectPoolConfig
					);
			PoolingDriver poolingDriver = new PoolingDriver();
			poolingDriver.registerPool(POOL_NAME, objectPool);
			
			// 커넥션 풀 목록을 확인
			PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:mysql");
			String[] names = driver.getPoolNames();
			
			for(int cnt=0; cnt < names.length; cnt ++)
			{
				System.out.println(cnt + " : " + names[cnt] + "<br>");
			}
			
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:"+POOL_NAME);
			String sql = "select file1 from bbs where idx=14";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				if(rs.getString("file1")==null)
				{
					System.out.println("true");
				}
			}
			
			
			System.out.print("OK");
			
		}catch (Exception e)
		{
			System.out.println("Fail"+e.getMessage());
		}finally {
			try{if(conn!=null)conn.close();}catch (Exception e) {}
		}

	}

}
