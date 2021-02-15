package repository;


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.Util;
import vo.BoardVO;

public class BoardRepositoryImpl implements BoardRepository {

	private DataSource dataSource;

	private static final Logger log = LoggerFactory
			.getLogger(BoardRepositoryImpl.class);

	public BoardRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// 보드 리스트를 뿌려준다.
	@Override
	public List<BoardVO> getBoardList(Map<String, Object> param)
			throws Exception {
		log.info("getBoardList 메소드 시작");

		List<BoardVO> boardList = new ArrayList<BoardVO>();
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			// 검색창 제목 검색 값이 있을 때
			if (param.get("title")!=null) {
				log.info("getBoardList 메소드의 if 문 실행");
				String title = (String) param.get("title");

				log.info("title 변수의 값은 : " + title);
				sql = "select id, title from board where title LIKE ? order by id desc";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + title + "%");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					BoardVO board = new BoardVO();
					board.setId(rs.getBigDecimal("id"));
					board.setTitle(rs.getString("title"));
					boardList.add(board);
				}
				log.info("getBoardList 메소드 종료");
				return boardList;
				// 검색창 제목 검색 값이 없을 때
			} else {
				log.info("getBoardList 메소드의 else문 실행");
				sql = "select id, title from board order by id desc";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					log.info("rs.next() 루프문 실행");
					BoardVO board = new BoardVO();
					board.setId(rs.getBigDecimal("id"));
					log.info("board ID value : " + board.getId());
					board.setTitle(rs.getString("title"));
					log.info("board title value : " + board.getTitle());
					boardList.add(board);
				}
				log.info("getBoardList 메소드 종료");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.info("getBoardList 메소드에서 예외발생 " + e.getMessage());
			throw e;
		} finally {
			Util.close(conn, pstmt, rs);
		}
		return boardList;
	}

	//새로운 글 삽입
	@Override
	public int insertBoard(BoardVO board) throws Exception {
		int affected = 0;

		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("select max(id)+1 as newid from board");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				board.setId(rs.getBigDecimal("newid"));
			}

			pstmt = conn.prepareStatement("insert into board(id,title,content) values(?,?,?)");
			pstmt.setInt(1, board.getId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			affected = pstmt.executeUpdate();
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
			log.info("insertBoard 메소드에서 예외발생 " + e.getMessage());
			throw e;
		} finally {
			conn.setAutoCommit(true);
			Util.close(conn, pstmt,rs);
		}
		
		return affected;
	}

	@Override
	public int deleteBoard(BigDecimal id) throws Exception {
		int affected = 0;

		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("delete from board where id=?");
			pstmt.setBigDecimal(1,id);
			affected = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.info("deleteBoard 메소드에서 예외발생 " + e.getMessage());
			throw e;
		} finally {
			Util.close(conn, pstmt);
		}
		
		return affected;
	}

	@Override
	public int updateBoard(BoardVO board) throws Exception {
		int affected = 0;

		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement("update board set title=? ,content=? where id=?");
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getId());
			affected = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			log.info("updateBoard 메소드에서 예외발생 " + e.getMessage());
			throw e;
		} finally {
			Util.close(conn, pstmt);
		}
		
		return affected;
	}

	//게시글 하나를 가져온다
	@Override
	public BoardVO getBoard(BigDecimal id) throws Exception {
		String sql = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO boardVO=null;

		try {
			conn = dataSource.getConnection();

			sql = "select id, title, content from board where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setBigDecimal(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				boardVO=new BoardVO();
				boardVO.setId(rs.getBigDecimal("id"));
				boardVO.setTitle(rs.getString("title"));
				boardVO.setContent(rs.getString("content"));
				log.info("board ID : "+boardVO.getId());
				log.info("board Title : "+boardVO.getTitle());
				log.info("board Content : "+boardVO.getContent());
			}
			
		} catch (SQLException e) {

			log.error("getBoard 메소드에서 예외발생 " + e.getMessage(), e);
			throw e;
		} finally {
			Util.close(conn, pstmt, rs);
		}
		
		return boardVO;
	}

}
