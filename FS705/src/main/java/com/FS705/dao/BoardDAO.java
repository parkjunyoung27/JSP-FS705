package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.BoardDTO;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;

public class BoardDAO {
	
	//싱글턴 패턴 적용
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	

	public static ArrayList<BoardDTO> boardList(int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *, (SELECT count(*) FROM board) as totalcount "
				+ "FROM board LIMIT ?, 20"; //20개씩 가져오기
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


	public static ArrayList<BoardDTO> selectCategorySub(String category, String subCategory, int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,"
				+ "(SELECT count(*) FROM board WHERE bcategory=? AND subCategory=?) "
				+ "as totalcount "
				+ "FROM board WHERE bcategory=? AND subCategory=? limit ?, 20;" ;	
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


	public static ArrayList<BoardDTO> selectCategory(String category, int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,"
				+ "(SELECT count(*) FROM board WHERE bcategory=? ) "
				+ "as totalcount "
				+ "FROM board WHERE bcategory=? limit ?, 20;" ;	
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


	public static ArrayList<BoardDTO> selectSubCategory(String subCategory, int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,"
				+ "(SELECT count(*) FROM board WHERE subCategory=? ) "
				+ "as totalcount "
				+ "FROM board WHERE subCategory=? limit ?, 20;" ;	
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

	
	
}















