package repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import vo.BoardVO;

public interface BoardRepository {
	public List<BoardVO> getBoardList(Map<String,Object> param)throws Exception;
	public int insertBoard(BoardVO board)throws Exception;
	public int deleteBoard(BigDecimal id)throws Exception;
	public int updateBoard(BoardVO board)throws Exception;
	public BoardVO getBoard(BigDecimal id)throws Exception;
}
