package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.HumorCommentDTO;
import com.FS705.util.Util;

public class HumorCommentDAO {
	private HumorCommentDAO() {		
	}
	private static HumorCommentDAO instance = new HumorCommentDAO();
	
	public static HumorCommentDAO getInstance() {
		return instance;
	}
	public ArrayList<HumorCommentDTO> boardCommentList(int bno) {
		ArrayList<HumorCommentDTO> cmt = new ArrayList<HumorCommentDTO>();
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
					HumorCommentDTO dto = new HumorCommentDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setCno(rs.getInt("cno"));					
					dto.setCcontent(rs.getString("ccontent"));
					dto.setCdate(rs.getString("cdate"));
					dto.setCip(rs.getString("cip"));
					dto.setNo(rs.getInt("no"));
					dto.setClike(rs.getInt("clike"));
					dto.setCdislike(rs.getInt("cdislike"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
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
	public int humorCommentWrite(HumorCommentDTO cmt) {
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
	public int humorCommentModify(HumorCommentDTO cmt) {
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
	public int humorCommentDelete(HumorCommentDTO cmt) {
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
