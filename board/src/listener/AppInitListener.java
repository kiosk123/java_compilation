package listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.action.NewRuleAction;

import repository.BoardRepository;
import repository.BoardRepositoryImpl;
import controller.BoardController;
import controller.BoardListController;
import controller.DeleteBoardController;
import controller.HomeController;
import controller.InputFormController;
import controller.InsertBoardController;
import controller.UpdateBoardController;

@WebListener
public class AppInitListener implements ServletContextListener {

	private final static Logger log = LoggerFactory.getLogger(AppInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		ServletContext sc=event.getServletContext();
		
		try {
			Context initContext = new InitialContext();
			DataSource dataSource =null;
			dataSource= (DataSource) initContext.lookup("java:/comp/env/jdbc/board");
			
			//커넥션 풀 성공여부 알려주는 메소드
			dataSourceInfo(dataSource); 
			
			//BoardRepository 생성
			BoardRepository boardRepository=new BoardRepositoryImpl(dataSource);
			boardRepositoryInfo(boardRepository);
			
			//여기서 Repository DI 처리하면서 URI와 클래스를 매핑한다.
			sc.setAttribute("/index.do",new HomeController());
			sc.setAttribute("/boardList.do", new BoardListController(boardRepository));
			sc.setAttribute("/inputForm.do", new InputFormController());
			sc.setAttribute("/insertBoard.do",new InsertBoardController(boardRepository));
			sc.setAttribute("/getBoard.do", new BoardController(boardRepository));
			sc.setAttribute("/updateBoard.do", new UpdateBoardController(boardRepository));
			sc.setAttribute("/deleteBoard.do", new DeleteBoardController(boardRepository));
			
			
		} catch (Exception e) {

		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}
	
	private void dataSourceInfo(DataSource dataSource){
		if (dataSource != null) {
			log.info("커넥션 풀 생성 성공");
		} else {
			log.info("커넥션 풀 생성 실패");
		}
	}
	
	private void boardRepositoryInfo(BoardRepository boardRepository){
		if (boardRepository != null) {
			log.info("보드 리포지토리 생성 성공");
		} else {
			log.info("보드 리포지토리 생성 실패");
		}
	}
}
