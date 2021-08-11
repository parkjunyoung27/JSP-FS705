package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.FoodBoardDTO;
import com.FS705.util.Util;

public class FoodBoardDAO {
	private FoodBoardDAO() {
	}

	private static FoodBoardDAO instance = new FoodBoardDAO();

	public static FoodBoardDAO getInstance() {
		return instance;
	}

	public ArrayList<FoodBoardDTO> boardList(int page) {
		ArrayList<FoodBoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM foodboardview LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<FoodBoardDTO>();
				while (rs.next()) {
					FoodBoardDTO dto = new FoodBoardDTO();
					dto.setFoodboardcount(rs.getInt("foodboardcount"));
					dto.setFoodcommentcount(rs.getInt("foodcommentcount"));
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

	public FoodBoardDTO boardView(int bno) {
		FoodBoardDTO view = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM foodboardview WHERE bno=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs != null) {
				view = new FoodBoardDTO();
				if (rs.next()) {
					view.setFoodboardcount(rs.getInt("foodboardcount"));
					view.setFoodcommentcount(rs.getInt("foodcommentcount"));
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

	public int boardWrite(FoodBoardDTO dto) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board (bcategory, btitle, bcontent, no, subCategory, bfile)"
				+ "VALUES ('food', ?, ?,(SELECT no FROM member WHERE id=?), ?, ?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBtitle());
			pstmt.setString(2, dto.getBcontent());
			// pstmt.setString(3, dto.getId());
			pstmt.setString(3, "an");
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

	public FoodBoardDTO boardModifyImport(int bno, String id) {
		FoodBoardDTO modifyImport = null;
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
					modifyImport = new FoodBoardDTO();
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

	public int boardModifyContent(FoodBoardDTO dto) {
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

	public ArrayList<FoodBoardDTO> selectList(int page, int category) {
		ArrayList<FoodBoardDTO> boardList = new ArrayList<FoodBoardDTO>();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM foodboardview WHERE subCategory=? LIMIT ?, 10";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, page);
			rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					FoodBoardDTO dto = new FoodBoardDTO();
					dto.setFoodboardcount(rs.getInt("foodboardcount"));
					dto.setFoodcommentcount(rs.getInt("foodcommentcount"));
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

	public ArrayList<FoodBoardDTO> foodBoardLike() {
		ArrayList<FoodBoardDTO> boardList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, subCategory, DATEDIFF(NOW(),`bdate`) as bdate, blike, "
				+ "bcount, bcategory, id, name FROM foodboardview ORDER BY blike DESC LIMIT 0, 5";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs != null) {
				boardList = new ArrayList<FoodBoardDTO>();
				while (rs.next()) {
					FoodBoardDTO dto = new FoodBoardDTO();
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

	public ArrayList<FoodBoardDTO> searchList(String searchOption, String search, int page) {
		ArrayList<FoodBoardDTO> searchList = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			if (searchOption.equals("btitle") || searchOption.equals("bcontent")) { // 제목 or 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM foodboardview WHERE "+ searchOption +" LIKE '%"+search+"%') AS searchcount "
						+ "FROM foodboardview WHERE " + searchOption + " LIKE '%"+search+"%' LIMIT ?, 10";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, page);

			} else { // 제목 + 내용
				sql = "SELECT *,(SELECT COUNT(*) FROM foodboardview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%')) AS searchcount "
						+ "FROM foodboardview WHERE btitle LIKE CONCAT('%',?,'%') OR bcontent LIKE CONCAT('%',?,'%') LIMIT ?, 10";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, search);
				pstmt.setString(2, search);
				pstmt.setString(3, search);
				pstmt.setString(4, search);
				pstmt.setInt(5, page);
			}
			rs = pstmt.executeQuery();
			if(rs != null) {
				searchList = new ArrayList<FoodBoardDTO>();
				while(rs.next()) {
					FoodBoardDTO dto = new FoodBoardDTO();
					dto.setFoodboardcount(rs.getInt("searchcount"));
					dto.setFoodcommentcount(rs.getInt("foodcommentcount"));
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
