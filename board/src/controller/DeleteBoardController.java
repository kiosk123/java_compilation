package controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.BoardRepository;

public class DeleteBoardController implements Controller{

	private BoardRepository boardRepository;
	
	public DeleteBoardController(BoardRepository boardRepository) {
		this.boardRepository=boardRepository;
	}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		boardRepository.deleteBoard(new BigDecimal(id));
		return "redirect:/board/boardList.do";
	}
	
}
