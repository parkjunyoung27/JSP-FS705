package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.LogDTO;
import com.FS705.dto.MemberDTO;
import com.FS705.util.Util;

public class MemberDAO {
	
	private MemberDAO() {}
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public static ArrayList<MemberDTO> memberList(int page) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member LIMIT ?, 20"; //20개씩 가져오기
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt("no"));
				dto.setGrade(rs.getInt("grade"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setSex(rs.getString("sex"));
				dto.setEmail(rs.getString("email"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setBirthdate(rs.getString("birthdate"));
				dto.setHint(rs.getString("hint"));
				dto.setHintAnswer(rs.getString("hintAnswer"));
				dto.setProfile(rs.getString("profile"));
				dto.setTotalCount(rs.getInt("totalCount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}

	public static ArrayList<MemberDTO> selectGradeGender(String grade, String gender, int page) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member WHERE grade = ? AND sex = ? LIMIT ?, 20"; //20개씩 가져오기
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int(grade));
			pstmt.setString(2, gender);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt("no"));
				dto.setGrade(rs.getInt("grade"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setSex(rs.getString("sex"));
				dto.setEmail(rs.getString("email"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setBirthdate(rs.getString("birthdate"));
				dto.setHint(rs.getString("hint"));
				dto.setHintAnswer(rs.getString("hintAnswer"));
				dto.setProfile(rs.getString("profile"));
				dto.setTotalCount(rs.getInt("totalCount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;	}
	
	
	public static ArrayList<MemberDTO> selectGrade(String grade, int page) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member WHERE grade = ? LIMIT ?, 20"; //20개씩 가져오기
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Util.str2Int(grade));
			pstmt.setInt(2, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt("no"));
				dto.setGrade(rs.getInt("grade"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setSex(rs.getString("sex"));
				dto.setEmail(rs.getString("email"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setBirthdate(rs.getString("birthdate"));
				dto.setHint(rs.getString("hint"));
				dto.setHintAnswer(rs.getString("hintAnswer"));
				dto.setProfile(rs.getString("profile"));
				dto.setTotalCount(rs.getInt("totalCount"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}

	public static ArrayList<MemberDTO> selectGender(String gender, int page) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member WHERE sex = ? LIMIT ?, 20"; //20개씩 가져오기
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, gender);
			pstmt.setInt(2, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt("no"));
				dto.setGrade(rs.getInt("grade"));
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setSex(rs.getString("sex"));
				dto.setEmail(rs.getString("email"));
				dto.setJoindate(rs.getString("joindate"));
				dto.setBirthdate(rs.getString("birthdate"));
				dto.setHint(rs.getString("hint"));
				dto.setHintAnswer(rs.getString("hintAnswer"));
				dto.setProfile(rs.getString("profile"));
				dto.setTotalCount(rs.getInt("totalCount"));
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
		String sql = "SELECT DISTINCT " + column + " FROM member";
		
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


	
}
