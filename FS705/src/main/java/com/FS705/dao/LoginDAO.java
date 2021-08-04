package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.FS705.db.DBConnection;
import com.FS705.dto.LoginDTO;

public class LoginDAO {
	//싱글턴
	private LoginDAO(){}
	
	public static LoginDAO instance = new LoginDAO(); 
	
	public static LoginDAO getInstance() {
		return instance;
	}
	//로그인 기능S
	public LoginDTO login(LoginDTO dto) {
		//dto
		LoginDTO login = new LoginDTO();
		//dbconn접속
		Connection conn = DBConnection.dbconn();
		//pstmt
		PreparedStatement pstmt = null;
		//결괏값 저장
		ResultSet rs = null;
		//sql문
		String sql = "SELECT * FROM member WHERE id=? AND pw=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				login.setNo(rs.getInt("no"));
				login.setGrade(rs.getInt("grade"));
				login.setName(rs.getString("name"));
				login.setId(rs.getString("id"));
				login.setPw(rs.getString("pw"));
				login.setSex(rs.getString("sex"));
				login.setEmail(rs.getString("Email"));
				login.setJoindate(rs.getString("joindate"));
				login.setBirthdate(rs.getString("Birthdate"));
				login.setHint(rs.getString("hint"));
				login.setHintAnswer(rs.getString("hintAnswer"));
				login.setProfile(rs.getString("profile"));				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return login;
	}
	
	
	
}