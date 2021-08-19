package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.FS705.db.DBConnection;
import com.FS705.dto.LoginDTO;
import com.FS705.util.Util;

public class LoginDAO {
	
	//싱글턴
	private LoginDAO(){}
	
	private static LoginDAO instance = new LoginDAO(); 
	
	public static LoginDAO getInstance() {
		return instance;
	}
	
	//로그인 기능
	public LoginDTO login(LoginDTO dto) {
		LoginDTO login = new LoginDTO();
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
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
			Util.closeAll(rs, pstmt, conn);
		}		
		return login;
	}
	
	//id 중복확인
	public int idCheck(String id) {
		int check = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= "SELECT COUNT(*) FROM member WHERE id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt("count(*)") == 1) {
					check = 1;
				} else {
					check = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	
	//가입하기
	public int join(LoginDTO dto) {
		int count = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO member (name, id, pw, sex, email, birthdate, hint, hintAnswer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getPw());
			pstmt.setString(4, dto.getSex());
			pstmt.setString(5, dto.getEmail());
			pstmt.setString(6, dto.getBirthdate());
			pstmt.setString(7, dto.getHint());
			pstmt.setString(8, dto.getHintAnswer());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return count;
	}
	
	//아이디 찾기
	public LoginDTO idFind(LoginDTO dto) {
		LoginDTO login = new LoginDTO(); 
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT id FROM member WHERE name=? AND sex=? AND birthdate=?";
			
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getSex());
			pstmt.setString(3, dto.getBirthdate());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				login.setId(rs.getString("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, conn);
		}
		return login;
	}
	//질문 출력
	public String pwQuestion(String id, String email) {
		String question = "";
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= "SELECT * FROM member WHERE id=? AND email=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				question = rs.getString("hint");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return question;
	}

	public int pwFind(String id, String email, String pwAnswer) {
		int check = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= "SELECT COUNT(*) FROM member WHERE id=? AND email=? AND hintAnswer=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			pstmt.setString(3, pwAnswer);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt("count(*)") == 1) {
					check = 1;
				} else {
					check = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}
	//패스워드 변경
	public int pwUpdate(LoginDTO dto) {
		int check = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "UPDATE member SET pw=? WHERE id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getId());
			check = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(null, pstmt, conn);
		}
		return check;
	}
}
