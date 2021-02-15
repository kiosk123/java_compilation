package controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.BoardRepository;
import vo.BoardVO;

public class BoardController implements Controller{
	
	private BoardRepository boardRepository;
	
	public BoardController(BoardRepository boardRepository) {
		this.boardRepository=boardRepository;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BigDecimal id=new BigDecimal(request.getParameter("id"));
		BoardVO board=boardRepository.getBoard(id);
		request.setAttribute("board", board);
		return "/WEB-INF/views/updateForm.jsp";
	}
}
