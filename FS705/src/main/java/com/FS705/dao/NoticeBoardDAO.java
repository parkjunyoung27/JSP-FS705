package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.NoticeBoardDTO;
import com.FS705.util.Util;

public class NoticeBoardDAO {
	private NoticeBoardDAO() {
	}

	private static NoticeBoardDAO instance = new NoticeBoardDAO();

	public static NoticeBoardDAO getInstance() {
		return instance;
	}

	public ArrayList<NoticeBoardDTO> boardList(int page) {
		ArrayList<NoticeBoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM noticeboardview LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<NoticeBoardDTO>();
				while (rs.next()) {
					NoticeBoardDTO dto = new NoticeBoardDTO();
					dto.setBoardcount(rs.getInt("boardcount"));
					dto.setCommentcount(rs.getInt("commentcount"));
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

	public NoticeBoardDTO boardView(int bno) {
		NoticeBoardDTO view = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM noticeboardview WHERE bno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs != null) {
				view = new NoticeBoardDTO();
				if (rs.next()) {
					view.setBoardcount(rs.getInt("boardcount"));
					view.setCommentcount(rs.getInt("commentcount"));
					view.setBno(rs.getInt("bno"));
					view.setBno2(rs.getInt("bno2"));
					view.setBno3(rs.getInt("bno3"));
					view.setBtitle(rs.getString("btitle"));
					view.setBcontent(rs.getString("bcontent"));
					view.setBfile(rs.getString("bfile"));
					view.setBdate(rs.getString("bdate2"));
					view.setBcount(rs.getInt("bcount"));
					view.setNo(rs.getInt("no"));
					view.setBcategory(rs.getString("bcategory"));
					view.setSubCategory(rs.getString("subCategory"));
					view.setId(rs.getString("id"));
					view.setName(rs.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return view;
	}

	public int boardWrite(NoticeBoardDTO dto) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board (bcategory, btitle, bcontent, no, subCategory, bfile, bthumbnail)"
				+ "VALUES ('notice', ?, ?,(SELECT no FROM member WHERE id=?), ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBtitle());
			pstmt.setString(2, dto.getBcontent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getSubCategory());
			pstmt.setString(5, dto.getBfile());
			pstmt.setString(6, dto.getBthumbnail());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public NoticeBoardDTO boardModifyImport(int bno, String id) {
		NoticeBoardDTO modifyImport = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, bcontent, bfile FROM board WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";

		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if (rs != null) {
				if (rs.next()) {
					modifyImport = new NoticeBoardDTO();
					modifyImport.setBno(rs.getInt("bno"));
					modifyImport.setBtitle(rs.getString("btitle"));
					modifyImport.setBcontent(rs.getString("bcontent"));
					modifyImport.setBfile(rs.getString("bfile"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return modifyImport;
	}

	public int boardModifyContent(NoticeBoardDTO dto) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE board SET subCategory=?, btitle=?, bcontent=?WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubCategory());
			pstmt.setString(2, dto.getBtitle());
			pstmt.setString(3, dto.getBcontent());
			pstmt.setInt(4, dto.getBno());
			pstmt.setString(5, dto.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public int boardDelete(int bno) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM board WHERE bno=?";

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

	public void boardViewCount(int bno) {
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

	public int boardViewVote(int bno, String vote) {
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

	public ArrayList<NoticeBoardDTO> selectList(int page, int category) {
		ArrayList<NoticeBoardDTO> boardList = new ArrayList<NoticeBoardDTO>();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT *,(SELECT COUNT(*) FROM noticeboardview WHERE subCategory=?) AS searchCount FROM noticeboardview WHERE subCategory=? LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, category);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					NoticeBoardDTO dto = new NoticeBoardDTO();
					dto.setBoardcount(rs.getInt("searchCount"));
					dto.setCommentcount(rs.getInt("commentcount"));
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

	public ArrayList<NoticeBoardDTO> noticeBoardLike() {
		ArrayList<NoticeBoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, subCategory, DATEDIFF(NOW(),`bdate`) as bdate, blike, "
				+ "bcount, bcategory, id, name FROM noticeboardview ORDER BY blike DESC LIMIT 0, 3";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<NoticeBoardDTO>();
				while (rs.next()) {
					NoticeBoardDTO dto = new NoticeBoardDTO();
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

	public ArrayList<NoticeBoardDTO> searchList(String searchOption, String search, int page) {
		ArrayList<NoticeBoardDTO> searchList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			if (searchOption.equals("btitle") || searchOption.equals("bcontent")) { // 제목 or 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM noticeboardview WHERE "+ searchOption +" LIKE '%"+search+"%') AS searchcount "
						+ "FROM noticeboardview WHERE " + searchOption + " LIKE '%"+search+"%' LIMIT ?, 10";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, page);

			} else { // 제목 + 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM noticeboardview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%')) AS searchcount "
						+ "FROM noticeboardview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%') LIMIT ?, 10";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, search);
				pstmt.setString(2, search);
				pstmt.setString(3, search);
				pstmt.setString(4, search);
				pstmt.setInt(5, page);
			}
			rs = pstmt.executeQuery();
			if(rs != null) {
				searchList = new ArrayList<NoticeBoardDTO>();
				while(rs.next()) {
					NoticeBoardDTO dto = new NoticeBoardDTO();
					dto.setBoardcount(rs.getInt("searchcount"));
					dto.setCommentcount(rs.getInt("commentcount"));
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


	public int nextPrev(int bno, int nextPrev) {
		int bno2 = 0;
		int preSeq = 0;
		int afterSeq = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		if(nextPrev == 1) {
			// 이전글
			sql = "SELECT bno FROM noticeboardview WHERE bno=(SELECT MAX(bno) FROM noticeboardview WHERE bno < ?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bno);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					preSeq = rs.getInt("bno");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				Util.closeAll(rs, pstmt, conn);
			}
			bno2 = preSeq;
		}else if(nextPrev == 2) {
			// 다음글
			sql = "SELECT bno FROM noticeboardview WHERE bno=(SELECT MIN(bno) FROM noticeboardview WHERE bno > ?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bno);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					afterSeq = rs.getInt("bno");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				Util.closeAll(rs, pstmt, conn);
			}
			bno2 = afterSeq;
		}
		return bno2;
	}

	public ArrayList<NoticeBoardDTO> sortList(int page, int sort) {
		ArrayList<NoticeBoardDTO> boardList = new ArrayList<NoticeBoardDTO>();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		if(sort == 1) {
			sql = "SELECT * FROM noticeboardview LIMIT ?, 10";
		} else if(sort == 2) {
			sql = "SELECT * FROM noticeboardview ORDER BY bno LIMIT ?, 10";
		} else if (sort == 3) {
			sql = "SELECT * FROM noticeboardview ORDER BY blike DESC LIMIT ?, 10";
		} else if (sort == 4) {
			sql = "SELECT * FROM noticeboardview ORDER BY bcount DESC LIMIT ?, 10";
		}
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					NoticeBoardDTO dto = new NoticeBoardDTO();
					dto.setBoardcount(rs.getInt("boardcount"));
					dto.setCommentcount(rs.getInt("commentcount"));
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
}
