package controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.BoardRepository;
import vo.BoardVO;

public class UpdateBoardController implements Controller{

	private BoardRepository boardRepository;
	
	public UpdateBoardController(BoardRepository boardRepository){
		this.boardRepository=boardRepository;
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String boardId=request.getParameter("id");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		BoardVO board=new BoardVO();
		board.setId(new BigDecimal(boardId));
		board.setTitle(title);
		board.setContent(content);
		boardRepository.updateBoard(board);
		return "redirect:/board/boardList.do";
	}
	
}
