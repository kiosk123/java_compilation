package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repository.BoardRepository;
import vo.BoardVO;

public class InsertBoardController implements Controller{
	
	private BoardRepository boardRepository;
	
	private static final Logger log=LoggerFactory.getLogger(InsertBoardController.class);
	
	public InsertBoardController(BoardRepository boardRepository){
		this.boardRepository=boardRepository;
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		BoardVO board=new BoardVO();
		board.setTitle(title);
		board.setContent(content);
		
		int affected=boardRepository.insertBoard(board);
		log.info("affected row : "+affected);
		
		return "redirect:/board/boardList.do";
	}
}
