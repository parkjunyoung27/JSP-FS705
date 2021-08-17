package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.BoardDTO;
import com.FS705.util.Util;

public class SportsDAO {
	
	private SportsDAO() {}
	private static SportsDAO sportsDAO = new SportsDAO();
	public static SportsDAO getInstance() {
		return sportsDAO;
	}
	
	public ArrayList<BoardDTO> sportsList(int page){
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM sportsview LIMIT ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {				
					BoardDTO dto = new BoardDTO();
					dto.setSportsCount(rs.getInt("sportsCount"));
					dto.setCommentCount(rs.getInt("commentCount"));
					dto.setBno(rs.getInt("bno"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setBcontent(rs.getString("bcontent"));
					dto.setBdate(rs.getString("bdate"));
					dto.setBdate2(rs.getString("bdate2"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBlike(rs.getInt("blike"));
					dto.setBdislike(rs.getInt("bdislike"));
					dto.setNo(rs.getInt("no"));
					dto.setBcategory(rs.getString("bcategory"));
					dto.setBthumbnail(rs.getString("bthumbnail"));
					dto.setSubCategory(rs.getString("subCategory"));
					dto.setId(rs.getString("id")); 
					dto.setName(rs.getString("name"));
					list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		
		return list;
		
	}
	
	public BoardDTO detail(int bno) {
		BoardDTO dto = null;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM sportsview WHERE bno=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs != null) {
				dto = new BoardDTO();
				if (rs.next()) {
					dto.setSportsCount(rs.getInt("sportsCount"));
					dto.setCommentCount(rs.getInt("commentCount"));
					dto.setBno(rs.getInt("bno"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setBcontent(rs.getString("bcontent"));
					dto.setBdate(rs.getString("bdate"));
					dto.setBdate2(rs.getString("bdate2"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBthumbnail(rs.getString("bthumbnail"));
					dto.setBcategory(rs.getString("bcategory"));
					dto.setSubCategory(rs.getString("subCategory"));
					dto.setBlike(rs.getInt("blike"));
					dto.setBdislike(rs.getInt("bdislike"));
					dto.setNo(rs.getInt("no"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					dto.setBfile(rs.getString("bfile")); // 파일명 불러오기
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return dto;
	}
	
	public int sportsWrite(BoardDTO dto) {
		int result = 0;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board (bcategory, btitle, bcontent, bfile, bthumbnail, no, subCategory)"
				+ " VALUES('sports', ?, ?, ?, ?, (SELECT no FROM member WHERE id=?), ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getBtitle());
			pstmt.setString(2, dto.getBcontent());
			pstmt.setString(3, dto.getBfile());
			pstmt.setString(4, dto.getBthumbnail());
			pstmt.setString(5, dto.getId());
			pstmt.setString(6, dto.getSubCategory());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
		
	}
	
	// 수정하기 (가져오기)
	public BoardDTO modifyImport(int bno, String id) {
		BoardDTO modifyImport = null;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, bcontent, bfile "
				+ "FROM board WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if(rs != null) {
				if(rs.next()) {
					modifyImport = new BoardDTO();
					modifyImport.setBno(rs.getInt("bno"));
					modifyImport.setBtitle(rs.getString("btitle"));
					modifyImport.setBcontent(rs.getString("bcontent"));
					modifyImport.setBfile(rs.getString("bfile"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		
		return modifyImport;
		
	}

	// 수정하기 (적용)
	public int sportsModify(BoardDTO dto) {
		int result = 0;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET subCategory=?, btitle=?, bcontent=? "
				+ "WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getSubCategory());
			pstmt.setString(2, dto.getBtitle());
			pstmt.setString(3, dto.getBcontent());
			pstmt.setInt(4, dto.getBno());
			pstmt.setString(5, dto.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
		
	}

	//삭제하기 전 파일 이름 불러오기
	public ArrayList<String> findFileName(int bno, String id) {
		ArrayList<String> filename = null;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bfile, bthumbnail FROM sportsview "
				+ "WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				filename = new ArrayList<String>();
				
				String bfile = rs.getString("bfile");
				
				if(bfile != null && bfile.contains(".")) {					
					filename.add(bfile);
				} else {
					filename.add(null);
				}
				
				String bthumbnail = rs.getString("bthumbnail");//1
				
				if(bthumbnail != null && bthumbnail.contains(".")) {
					filename.add(bthumbnail);					
				} else {
					filename.add(null);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		
		return filename;
		
	}
	
	public int del(int bno, String id) {
		int result = 0;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM board WHERE bno=? AND "
				+ "no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
		
	}
	

	public void count(int bno) {
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET bcount=bcount+1 WHERE bno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		
	}
	

	public int sportsVote(int bno, String vote) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET " + vote + "=" + vote + "+1 WHERE bno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}
	
	public ArrayList<BoardDTO> selectList(int page, int category) {
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM sportsview WHERE subCategory=? LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setSportsCount(rs.getInt("sportsCount"));
					dto.setCommentCount(rs.getInt("commentCount"));
					dto.setBno(rs.getInt("bno"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setSubCategory(rs.getString("subCategory"));
					dto.setBfile(rs.getString("bfile"));
					dto.setBdate(rs.getString("bdate"));
					dto.setBlike(rs.getInt("blike"));
					dto.setBdislike(rs.getInt("bdislike"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBcategory(rs.getString("bcategory"));
					dto.setBfile(rs.getString("bfile"));
					dto.setBthumbnail(rs.getString("bthumbnail"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));

					boardList.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}

		return boardList;
	}

	public ArrayList<BoardDTO> sportsLike() {
		ArrayList<BoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, subCategory, DATEDIFF(NOW(),`bdate`) as bdate, blike, "
				+ "bcount, bcategory, id, name FROM sportsview ORDER BY blike DESC LIMIT 0, 5";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<BoardDTO>();
				while (rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setSubCategory(rs.getString("subCategory"));
					dto.setBdate(rs.getString("bdate"));
					dto.setBlike(rs.getInt("blike"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBcategory(rs.getString("bcategory"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					boardList.add(dto);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return boardList;
	}

	public ArrayList<BoardDTO> searchList(String searchOption, String search, int page) {
		ArrayList<BoardDTO> searchList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			if (searchOption.equals("btitle") || searchOption.equals("bcontent")) { // 제목 or 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM sportsview WHERE "+ searchOption +" LIKE '%"+search+"%') AS searchcount "
						+ "FROM sportsview WHERE " + searchOption + " LIKE '%"+search+"%' LIMIT ?, 10";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, page);

			} else { // 제목 + 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM sportsview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%')) AS searchcount "
						+ "FROM sportsview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%') LIMIT ?, 10";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, search);
				pstmt.setString(2, search);
				pstmt.setString(3, search);
				pstmt.setString(4, search);
				pstmt.setInt(5, page);
			}
			rs = pstmt.executeQuery();
			if(rs != null) {
				searchList = new ArrayList<BoardDTO>();
				while(rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setSportsCount(rs.getInt("sportsCount"));
					dto.setCommentCount(rs.getInt("commentCount"));
					dto.setBno(rs.getInt("bno"));
					dto.setBtitle(rs.getString("btitle"));
					dto.setSubCategory(rs.getString("subCategory"));
					dto.setBfile(rs.getString("bfile"));
					dto.setBdate(rs.getString("bdate"));
					dto.setBlike(rs.getInt("blike"));
					dto.setBdislike(rs.getInt("bdislike"));
					dto.setBcount(rs.getInt("bcount"));
					dto.setBcategory(rs.getString("bcategory"));
					dto.setBfile(rs.getString("bfile"));
					dto.setBthumbnail(rs.getString("bthumbnail"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					searchList.add(dto);					
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return searchList;
	}
	
	// 댓글
	public ArrayList<BoardDTO> sportsCommentList(int bno) {
		ArrayList<BoardDTO> commentList = new ArrayList<BoardDTO>();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM commentview WHERE bno=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if(rs != null) {
				while(rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setCno(rs.getInt("cno"));			
					dto.setCcontent(rs.getString("ccontent"));
					dto.setCip(rs.getString("cip"));
					dto.setNo(rs.getInt("no"));
					dto.setClike(rs.getInt("clike"));
					dto.setCdislike(rs.getInt("cdislike"));
					dto.setId("kwon");
					commentList.add(dto);
				}				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		
		return commentList;
		
	}

	public int commentInput(BoardDTO dto) {
		int result  = 0;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO comment (bno, ccontent, no, cip) VALUES (?, ?, (SELECT no FROM member WHERE id=?), ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			pstmt.setString(2, dto.getCcontent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getCip());
			result = pstmt.executeUpdate(); // 공부 필요
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
	
		return result;
				
	}

	public int commentModify(BoardDTO dto) {
		int result = 0;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE comment SET ccontent=?, cip=? WHERE bno=? AND cno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getCcontent());
			pstmt.setString(2, dto.getCip());
			pstmt.setInt(3, dto.getBno());
			pstmt.setInt(4, dto.getCno());
			pstmt.setString(5, dto.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
		
	}

	public int commentDelete(BoardDTO dto) {
		int result = 0;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM comment WHERE bno=? AND cno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			pstmt.setInt(2, dto.getCno());
			pstmt.setString(3, dto.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		
		return result;
		
	}


}
