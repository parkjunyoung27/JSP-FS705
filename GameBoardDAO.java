package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.GameBoardDTO;
import com.FS705.util.Util;

public class GameBoardDAO {
	private GameBoardDAO() {	
	}
	private static GameBoardDAO instance = new GameBoardDAO();
	
	public static GameBoardDAO getInstance() {
		return instance;
	}
	public ArrayList<GameBoardDTO> boardList(){
		ArrayList<GameBoardDTO> boardList = new ArrayList<GameBoardDTO>();		
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM gameboardview";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs != null ) {
				while(rs.next()) {
					GameBoardDTO dto = new GameBoardDTO();
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
	
	public GameBoardDTO boardView(int bno) {
		GameBoardDTO view = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM gameboardview WHERE bno=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs != null) {
				view = new GameBoardDTO();
				if(rs.next()) {
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
	
	public int boardWrite(GameBoardDTO dto) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO board (bcategory, btitle, bcontent, no, subCategory, bfile)"
				+ "VALUES ('game', ?, ?,(SELECT no FROM member WHERE id=?), ?, ?)" ;
		
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
	public GameBoardDTO modifyImport(int bno, String id) {
		GameBoardDTO modifyImport = null;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bno, btitle, bcontent, bfile FROM board WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			if(rs != null) {
				if(rs.next()) {
					modifyImport = new GameBoardDTO();
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
	public int modifyContent(GameBoardDTO dto) {
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
	public int GameDelete(int bno, String id) {
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
}
