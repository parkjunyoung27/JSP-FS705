package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.FoodCommentDTO;
import com.FS705.util.Util;

public class FoodCommentDAO {
	private FoodCommentDAO() {		
	}
	private static FoodCommentDAO instance = new FoodCommentDAO();
	
	public static FoodCommentDAO getInstance() {
		return instance;
	}
	public ArrayList<FoodCommentDTO> boardCommentList(int bno) {
		ArrayList<FoodCommentDTO> cmt = new ArrayList<FoodCommentDTO>();
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
					FoodCommentDTO dto = new FoodCommentDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setCno(rs.getInt("cno"));					
					dto.setCcontent(rs.getString("ccontent"));
					dto.setCdate(rs.getString("cdate"));
					dto.setCip(rs.getString("cip"));
					dto.setNo(rs.getInt("no"));
					dto.setClike(rs.getInt("clike"));
					dto.setCdislike(rs.getInt("cdislike"));
					//dto.setId(rs.getString("id"));
					//dto.setName(rs.getString("name"));
					dto.setId("an");
					dto.setName("안다훈");
					cmt.add(dto);
				}				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return cmt;
	}
	public int foodCommentWrite(FoodCommentDTO cmt) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO comment (bno, ccontent, no, cip) VALUES (?, ?, (SELECT no FROM member WHERE id=?), ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cmt.getBno());
			pstmt.setString(2, cmt.getCcontent());
			pstmt.setString(3, cmt.getId());
			pstmt.setString(4, cmt.getCip());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);			
		}		
		return result;
	}
	public int foodCommentModify(FoodCommentDTO cmt) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE comment SET ccontent=?, cip=? WHERE bno=? AND cno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, cmt.getCcontent());
			pstmt.setString(2, cmt.getCip());
			pstmt.setInt(3, cmt.getBno());
			pstmt.setInt(4, cmt.getCno());
			pstmt.setString(5, cmt.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);			
		}				
		return result;
	}
	public int foodCommentDelete(FoodCommentDTO cmt) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM comment WHERE bno=? AND cno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, cmt.getBno());
			pstmt.setInt(2, cmt.getCno());
			pstmt.setString(3, cmt.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);			
		}		
		return result;
	}	
}
