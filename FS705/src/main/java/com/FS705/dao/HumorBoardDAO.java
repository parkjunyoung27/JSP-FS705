package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.HumorBoardDTO;
import com.FS705.util.Util;

public class HumorBoardDAO {
	private HumorBoardDAO() {
	}

	private static HumorBoardDAO instance = new HumorBoardDAO();

	public static HumorBoardDAO getInstance() {
		return instance;
	}

	public ArrayList<HumorBoardDTO> boardList(int page) {
		ArrayList<HumorBoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM humorboardview LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<HumorBoardDTO>();
				while (rs.next()) {
					HumorBoardDTO dto = new HumorBoardDTO();
					dto.setHumorboardcount(rs.getInt("humorboardcount"));
					dto.setHumorcommentcount(rs.getInt("humorcommentcount"));
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

	public HumorBoardDTO boardView(int bno) {
		HumorBoardDTO view = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM humorboardview WHERE bno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs != null) {
				view = new HumorBoardDTO();
				if (rs.next()) {
					view.setHumorboardcount(rs.getInt("humorboardcount"));
					view.setHumorcommentcount(rs.getInt("humorcommentcount"));
					view.setBno(rs.getInt("bno"));
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

	public int boardWrite(HumorBoardDTO dto) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board (bcategory, btitle, bcontent, no, subCategory, bfile)"
				+ "VALUES ('humor', ?, ?,(SELECT no FROM member WHERE id=?), ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBtitle());
			pstmt.setString(2, dto.getBcontent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getSubCategory());
			pstmt.setString(5, dto.getBfile());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}

	public HumorBoardDTO boardModifyImport(int bno, String id) {
		HumorBoardDTO modifyImport = null;
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
					modifyImport = new HumorBoardDTO();
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

	public int boardModifyContent(HumorBoardDTO dto) {
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

	public int boardDelete(int bno, String id) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM board WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, id);
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

	public ArrayList<HumorBoardDTO> selectList(int page, int category) {
		ArrayList<HumorBoardDTO> boardList = new ArrayList<HumorBoardDTO>();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM humorboardview WHERE subCategory=? LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					HumorBoardDTO dto = new HumorBoardDTO();
					dto.setHumorboardcount(rs.getInt("humorboardcount"));
					dto.setHumorcommentcount(rs.getInt("humorcommentcount"));
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

	public ArrayList<HumorBoardDTO> humorBoardLike() {
		ArrayList<HumorBoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, subCategory, DATEDIFF(NOW(),`bdate`) as bdate, blike, "
				+ "bcount, bcategory, id, name FROM humorboardview ORDER BY blike DESC LIMIT 0, 5";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<HumorBoardDTO>();
				while (rs.next()) {
					HumorBoardDTO dto = new HumorBoardDTO();
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

	public ArrayList<HumorBoardDTO> searchList(String searchOption, String search, int page) {
		ArrayList<HumorBoardDTO> searchList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			if (searchOption.equals("btitle") || searchOption.equals("bcontent")) { // 제목 or 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM humorboardview WHERE "+ searchOption +" LIKE '%"+search+"%') AS searchcount "
						+ "FROM humorboardview WHERE " + searchOption + " LIKE '%"+search+"%' LIMIT ?, 10";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, page);

			} else { // 제목 + 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM humorboardview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%')) AS searchcount "
						+ "FROM humorboardview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%') LIMIT ?, 10";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, search);
				pstmt.setString(2, search);
				pstmt.setString(3, search);
				pstmt.setString(4, search);
				pstmt.setInt(5, page);
			}
			rs = pstmt.executeQuery();
			if(rs != null) {
				searchList = new ArrayList<HumorBoardDTO>();
				while(rs.next()) {
					HumorBoardDTO dto = new HumorBoardDTO();
					dto.setHumorboardcount(rs.getInt("searchcount"));
					dto.setHumorcommentcount(rs.getInt("humorcommentcount"));
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
}
