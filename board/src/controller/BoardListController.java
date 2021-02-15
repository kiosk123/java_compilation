package controller;

import static util.Util.stringIsEmpty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repository.BoardRepository;
import vo.BoardViewVO;

public class BoardListController implements Controller {

	private final static Logger log=LoggerFactory.getLogger(BoardListController.class);
	
	private BoardRepository boardRepository;
	
	public BoardListController(BoardRepository boardRepository){
		log.info("BoardListController 객체 생성");
		this.boardRepository=boardRepository;
	}
	
	public BoardListController(){}
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		
		String title= request.getParameter("title");
		Map<String, Object> param=new HashMap<String,Object>();
		
		//검색한 제목 값이 있으면 
		if(!stringIsEmpty(title)){
			param.put("title",title);
		}
		
		//뷰에서 뿌려줄 용도로 써줄 객체
		BoardViewVO boardViewVO=new BoardViewVO();
		boardViewVO.setBoardList(boardRepository.getBoardList(param));
		request.setAttribute("view",boardViewVO);
		
		return "/WEB-INF/views/boardList.jsp";
	}

}
