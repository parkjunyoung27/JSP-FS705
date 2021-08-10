package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.BoardDTO;
import com.FS705.util.Util;

public class BoardDAO {
	
	//싱글턴 패턴 적용
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	

	public static ArrayList<BoardDTO> boardList(int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *, (SELECT count(*) FROM board) as totalcount "
				+ "FROM board "
				+ orderSql
				+ " LIMIT ?, 10"; //10개씩 가져오기
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}


	public static ArrayList<String> optionList(String column) {
		ArrayList<String> list = null;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT " + column + " FROM board";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if( rs != null) {
				list = new ArrayList<String>();
			}while(rs.next()) {
				list.add(rs.getString(column));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public static ArrayList<BoardDTO> selectCategorySubcategory(String category, String subCategory, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,"
				+ "(SELECT count(*) FROM board WHERE bcategory=? AND subCategory=?) "
				+ "as totalcount "
				+ "FROM board WHERE bcategory = ? AND subCategory = ? "
				+ orderSql
				+ " limit ?, 10;" ;	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setString(2, subCategory);
			pstmt.setString(3, category);
			pstmt.setString(4, subCategory);
			pstmt.setInt(5, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}


	public static ArrayList<BoardDTO> selectCategory(String category, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,"
				+ "(SELECT count(*) FROM board WHERE bcategory=? ) "
				+ "as totalcount "
				+ "FROM board WHERE bcategory=? " 
				+ orderSql
				+ " limit ?, 10;" ;	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setString(2, category);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}


	public static ArrayList<BoardDTO> selectSubcategory(String subCategory, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,"
				+ "(SELECT count(*) FROM board WHERE subCategory=? ) "
				+ "as totalcount "
				+ "FROM board WHERE subCategory=? "
				+ orderSql
				+ " limit ?, 10;" ;	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subCategory);
			pstmt.setString(2, subCategory);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}


	public static int delete(int number) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM board WHERE bno = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			result = pstmt.executeUpdate();
			System.out.println("게시글이 삭제 됐습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}


	public static ArrayList<String> subCatgoryList(String view) {
		ArrayList<String> list = null;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT subCategory FROM " + view;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if( rs != null) {
				list = new ArrayList<String>();
			}while(rs.next()) {
				list.add(rs.getString("subCategory"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	public static ArrayList<BoardDTO> searchAll(String searchType, String searchText, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM board "
				+ "WHERE bno LIKE CONCAT('%',?,'%')"
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%'))as totalcount"
				+ "	FROM board" 
				+ "	WHERE bno LIKE CONCAT('%',?,'%') "
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 27; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setInt(27, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}

	public static ArrayList<BoardDTO> searchAllCategory(String searchType, String searchText, String category, int page,
			String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM board "
				+ "WHERE bno LIKE CONCAT('%',?,'%')"
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') AND bcategory = ? )as totalcount"
				+ "	FROM board" 
				+ "	WHERE bno LIKE CONCAT('%',?,'%') "
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') AND bcategory = ? "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 14; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(14, category);
			for(int i = 15; i < 28; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(28, category);
			pstmt.setInt(29, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}


	public static ArrayList<BoardDTO> searchAllSubcategory(String searchType, String searchText, String subCategory,
			int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM board "
				+ "WHERE bno LIKE CONCAT('%',?,'%')"
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') AND subCategory = ? )as totalcount"
				+ "	FROM board" 
				+ "	WHERE bno LIKE CONCAT('%',?,'%') "
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') AND subCategory = ? "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 14; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(14, subCategory);
			for(int i = 15; i < 28; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(28, subCategory);
			pstmt.setInt(29, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}

	public static ArrayList<BoardDTO> searchAllCategorySubcategory(String searchType, String searchText,
			String category, String subCategory, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM board "
				+ "WHERE bno LIKE CONCAT('%',?,'%')"
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') AND bcategory = ? AND subCategory = ?  )as totalcount"
				+ "	FROM board" 
				+ "	WHERE bno LIKE CONCAT('%',?,'%') "
				+ " OR btitle LIKE CONCAT('%',?,'%')"
				+ "	OR bcontent LIKE CONCAT('%',?,'%')"
				+ "	OR bdate LIKE CONCAT('%',?,'%')"
				+ "	OR bcount LIKE CONCAT('%',?,'%')"
				+ "	OR no LIKE CONCAT('%',?,'%')"
				+ "	OR bcategory LIKE CONCAT('%',?,'%')"
				+ "	OR bthumbnail LIKE CONCAT('%',?,'%')"
				+ "	OR subCategory LIKE CONCAT('%',?,'%')"
				+ "	OR bfile LIKE CONCAT('%',?,'%')"
				+ "	OR blike LIKE CONCAT('%',?,'%')"
				+ "	OR bdislike LIKE CONCAT('%',?,'%')"
				+ "	OR commentCount LIKE CONCAT('%',?,'%') AND bcategory = ?  AND subCategory = ? "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 14; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(14, category);
			pstmt.setString(15, subCategory);
			
			for(int i = 16; i < 29; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(29, category);
			pstmt.setString(30, subCategory);
			pstmt.setInt(31, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}


	public static ArrayList<BoardDTO> search(String searchType, String searchText, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT (SELECT count(*) FROM board WHERE "
				+ searchType
				+" LIKE CONCAT('%',?,'%')) as totalcount, * "
				+" FROM board WHERE " + searchType
				+" LIKE CONCAT('%',?,'%') "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchText);
			pstmt.setString(2, searchText);
			pstmt.setInt(3, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}
	
	public static ArrayList<BoardDTO> searchCategory(String searchType, String searchText, String category, int page,
			String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT (SELECT count(*) FROM board WHERE "
				+ searchType
				+" LIKE CONCAT('%',?,'%') AND bcategory = ?) as totalcount, * "
				+" FROM board WHERE " + searchType
				+" LIKE CONCAT('%',?,'%') AND bcategory = ? "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchText);
			pstmt.setString(2, category);
			pstmt.setString(3, searchText);
			pstmt.setString(4, category);
			pstmt.setInt(5, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}

	public static ArrayList<BoardDTO> searchSubcategory(String searchType, String searchText, String subCategory, int page,
			String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT (SELECT count(*) FROM board WHERE "
				+ searchType
				+" LIKE CONCAT('%',?,'%') AND subCategory = ?) as totalcount, * "
				+" FROM board WHERE " + searchType
				+" LIKE CONCAT('%',?,'%') AND subCategory = ? "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchText);
			pstmt.setString(2, subCategory);
			pstmt.setString(3, searchText);
			pstmt.setString(4, subCategory);
			pstmt.setInt(5, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}

	public static ArrayList<BoardDTO> searchBoth(String searchType, String searchText, String category,
			String subCategory, int page, String orderSql) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT (SELECT count(*) FROM board WHERE "
				+ searchType
				+" LIKE CONCAT('%',?,'%') AND bcategory = ? AND subCategory = ?) as totalcount, * "
				+" FROM board WHERE " + searchType
				+" LIKE CONCAT('%',?,'%') AND bcategory = ? AND subCategory = ? "
				+ orderSql
				+ " limit ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchText);
			pstmt.setString(2, category);
			pstmt.setString(3, subCategory);
			pstmt.setString(4, searchText);
			pstmt.setString(5, category);
			pstmt.setString(6, subCategory);
			pstmt.setInt(7, page);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
		}
}




