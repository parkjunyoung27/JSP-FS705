package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.FS705.db.DBConnection;
import com.FS705.util.Util;

public class SportsDAO {
//싱글턴을 바꿔주세요.
	//생성자를 외부에서 못 보게 ***로 숨겨주세요.
	private SportsDAO() {}
	
	//단 하나의 객체 생성해주기 = ***** 붙여서 클래스 변수로 만들기
	private static SportsDAO sportsDAO = new SportsDAO();
	
	//생성된 객체 가져오기 = getInstance()입니다.
	public static SportsDAO getInstance() {
		return sportsDAO;
	}
	//그 후 아래 실제 코드 작성하기
	public ArrayList<HashMap<String, Object>> sportsList(int page){
		ArrayList<HashMap<String, Object>> sportsList = null;
		//conn
		Connection con = null;
		con = DBConnection.dbConn();
		//pstmt
		PreparedStatement pstmt = null;
		//rs
		ResultSet rs = null;
		//sql
		String sql = "SELECT * FROM sportsview LIMIT ?, 5;";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			if(rs != null) {				
				sportsList = new ArrayList<HashMap<String,Object>>();
				while (rs.next()) {
					HashMap<String,Object> map = new HashMap<String, Object>();
					map.put("sportsCount", rs.getInt("sportsCount"));
					map.put("commentCount", rs.getInt("commentCount"));
					map.put("bno", rs.getInt("bno"));
					map.put("btitle", rs.getString("btitle"));
					map.put("bcontent", rs.getString("bcontent"));
					map.put("bdate", rs.getString("bdate"));
					map.put("bdate2", rs.getString("bdate2"));
					map.put("bcount", rs.getInt("bcount"));
					map.put("blike", rs.getInt("blike"));
					map.put("bdislike", rs.getInt("bdislike"));
					map.put("no", rs.getInt("no"));
					map.put("bcategory",rs.getString("bcategory"));
					map.put("bthumbnail", rs.getString("bthumbnail"));
					map.put("subCategory",rs.getString("subCategory"));
					map.put("id", rs.getString("id"));
					map.put("name", rs.getString("name"));
					sportsList.add(map);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return sportsList;
	}
	
	public int sportsWrite(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO sports (btitle, bcontent, bfilename, bthumbnail, no)"
				+ " VALUES(?, ?, ?, ?, (SELECT no FROM member WHERE id=?))";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String) map.get("title"));
			pstmt.setString(2, (String) map.get("content"));
			pstmt.setString(3, (String) map.get("saveFile"));
			pstmt.setString(4, (String) map.get("thumbnail"));
			pstmt.setString(5, (String) map.get("id"));
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}
	
	public HashMap<String, Object> detail(int sno) {
		HashMap<String, Object> dto = null;
		Connection con = DBConnection.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM sportsview WHERE bno=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new HashMap<String, Object>();
				//dto.put("commentcount", rs.getInt("commentcount"));
				dto.put("sportsCount", rs.getInt("sportsCount"));
				dto.put("commentCount", rs.getInt("commentCount"));
				dto.put("bno", rs.getInt("bno"));
				dto.put("btitle", rs.getString("btitle"));
				dto.put("bcontent", rs.getString("bcontent"));
				dto.put("bdate", rs.getString("bdate"));
				dto.put("bcount", rs.getInt("bcount"));
				dto.put("bthumbnail", rs.getString("bthumbnail"));
				dto.put("bcategory", rs.getString("bcategory"));
				dto.put("subCategory", rs.getString("subCategory"));
				dto.put("blike", rs.getInt("blike"));
				dto.put("bdislike", rs.getInt("bdislike"));
				dto.put("no", rs.getInt("no"));
				dto.put("id", rs.getString("id"));
				dto.put("name", rs.getString("name"));
				dto.put("bfile", rs.getString("bfile"));// 파일명 불러오기
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return dto;
	}
	
	//삭제하기 전 파일 이름 불러오기
	public ArrayList<String> findFileName(HashMap<String, Object> map) {
		ArrayList<String> filename = null;
		Connection con = DBConnection.dbConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bfile, bthumbnail FROM sportsview "
				+ "WHERE bno=? AND no=(SELECT no FROM member WHERE id=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String)map.get("bno")));
			pstmt.setString(2, (String) map.get("id"));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				filename = new ArrayList<String>();
				
				String bfilename = rs.getString("bfilename");
				if(bfilename != null && bfilename.contains(".")) {					
					filename.add(bfilename);
				} else {
					filename.add(null);
				}
				String bthumbnail = rs.getString("bthumbnail");//1
				if(bthumbnail != null && bthumbnail.contains(".")) {
					filename.add(bthumbnail);					
				}else {
					filename.add(null);//1indexl
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return filename;
	}
	
	public int del(HashMap<String, Object> map) {
		int result = 0;
		Connection con = DBConnection.dbConn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM sports WHERE bno=? AND "
				+ "no=(SELECT no FROM member WHERE id=?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int((String) map.get("bno")));
			pstmt.setString(2, (String) map.get("id"));
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, con);
		}
		return result;
	}
	
}
