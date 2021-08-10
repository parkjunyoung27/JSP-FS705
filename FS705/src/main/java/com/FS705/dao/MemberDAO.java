package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.MemberDTO;
import com.FS705.util.Util;

public class MemberDAO {
	
	private MemberDAO() {}
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	public static ArrayList<MemberDTO> memberList(int page, String orderSql) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member "
				+ orderSql
				+ " LIMIT ?, 3"; //3개씩 가져오기
		
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

	public static ArrayList<MemberDTO> selectGradeGender(String grade, String gender, int page, String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member WHERE grade = ? AND sex = ? "
				+ orderSql
				+ " LIMIT ?, 3"; //3개씩 가져오기
		
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
	
	
	public static ArrayList<MemberDTO> selectGrade(String grade, int page, String orderSql) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member WHERE grade = ? "
				+ orderSql
				+ " LIMIT ?, 3"; //3개씩 가져오기
		
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

	public static ArrayList<MemberDTO> selectGender(String gender, int page, String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM member) as totalcount "
				+ "FROM member WHERE sex = ? "
				+ orderSql
				+ " LIMIT ?, 3"; //3개씩 가져오기
		
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
	
	public static ArrayList<MemberDTO> searchAll(String searchType, String searchText, int page, String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM member "
				+ "WHERE no LIKE CONCAT('%',?,'%')"
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%'))as totalcount"
				+ "	FROM member" 
				+ "	WHERE no LIKE CONCAT('%',?,'%') "
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%')"
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 25; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setInt(25, page);
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
	
	public static ArrayList<MemberDTO> searchAllGrade(String searchType, String searchText, String grade, 
			int page, String orderSql) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM member "
				+ "WHERE no LIKE CONCAT('%',?,'%')"
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%') AND grade = ? )as totalcount"
				+ "	FROM member" 
				+ "	WHERE no LIKE CONCAT('%',?,'%') "
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%') AND grade = ? "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 13; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(13, grade);

			for(int i = 14; i < 26; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(26, grade);
			pstmt.setInt(27, page);
			
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
	
	public static ArrayList<MemberDTO> searchAllGender(String searchType, String searchText, String gender, int page,
			String orderSql) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM member "
				+ "WHERE no LIKE CONCAT('%',?,'%')"
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%') AND sex = ? )as totalcount"
				+ "	FROM member" 
				+ "	WHERE no LIKE CONCAT('%',?,'%') "
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%') AND sex = ? "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 13; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(13, gender);

			for(int i = 14; i < 26; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(26, gender);
			pstmt.setInt(27, page);
			
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
	
	public static ArrayList<MemberDTO> searchAllGradeGender(String searchType, String searchText, String grade,
			String gender, int page, String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM member "
				+ "WHERE no LIKE CONCAT('%',?,'%')"
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%') AND grade = ? AND sex = ? )as totalcount"
				+ "	FROM member" 
				+ "	WHERE no LIKE CONCAT('%',?,'%') "
				+ " OR name LIKE CONCAT('%',?,'%')"
				+ "	OR id LIKE CONCAT('%',?,'%')"
				+ "	OR pw LIKE CONCAT('%',?,'%')"
				+ "	OR sex LIKE CONCAT('%',?,'%')"
				+ "	OR email LIKE CONCAT('%',?,'%')"
				+ "	OR joindate LIKE CONCAT('%',?,'%')"
				+ "	OR birthdate LIKE CONCAT('%',?,'%')"
				+ "	OR grade LIKE CONCAT('%',?,'%')"
				+ "	OR hint LIKE CONCAT('%',?,'%')"
				+ "	OR hintAnswer LIKE CONCAT('%',?,'%')"
				+ "	OR profile LIKE CONCAT('%',?,'%') AND grade = ? AND sex = ? "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 13; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(13, grade);
			pstmt.setString(14, gender);

			for(int i = 15; i < 27; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setString(27, grade);
			pstmt.setString(28, gender);
			pstmt.setInt(29, page);
			
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
	
	public static ArrayList<MemberDTO> search(String searchType, String searchText, int page, String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT (SELECT count(*) FROM member WHERE "
				+ searchType
				+" LIKE CONCAT('%',?,'%')) as totalcount, * "
				+" FROM member WHERE " + searchType
				+" LIKE CONCAT('%',?,'%') "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchText);
			pstmt.setString(2, searchText);
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
		return list;
		}	
	
	public static ArrayList<MemberDTO> searchGrade(String searchType, String searchText, String grade, int page,
			String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT count(*) FROM member WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND grade= ?) as totalcount "		
				+" FROM member WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND grade = ? "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchType);
			pstmt.setString(2, searchText);
			pstmt.setString(3, grade);
			pstmt.setString(4, searchType);
			pstmt.setString(5, searchText);
			pstmt.setString(6, grade);
			pstmt.setInt(7, page);
			
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
	
	public static ArrayList<MemberDTO> searchGender(String searchType, String searchText, String gender, int page,
			String orderSql) {
		
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT count(*) FROM member WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND sex = ?) as totalcount "		
				+" FROM member WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND sex = ? "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchType);
			pstmt.setString(2, searchText);
			pstmt.setString(3, gender);
			pstmt.setString(4, searchType);
			pstmt.setString(5, searchText);
			pstmt.setString(6, gender);
			pstmt.setInt(7, page);
			
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
	
	public static ArrayList<MemberDTO> searchBoth(String searchType, String searchText, String grade, String gender,
			int page, String orderSql) {
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT count(*) FROM member WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND grade = ? AND sex = ?) as totalcount "		
				+" FROM member WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND grade = ? AND sex = ? "
				+ orderSql
				+ " limit ?, 3";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchType);
			pstmt.setString(2, searchText);
			pstmt.setString(3, grade);
			pstmt.setString(4, gender);
			pstmt.setString(5, searchType);
			pstmt.setString(6, searchText);
			pstmt.setString(7, grade);
			pstmt.setString(8, gender);
			pstmt.setInt(9, page);
			
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

		public static int delete(int number) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM member WHERE no = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			result = pstmt.executeUpdate();
			System.out.println("회원 1명이 삭제됐습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}
}
