package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.FS705.db.DBConnection;
import com.FS705.dto.LogDTO;
import com.FS705.util.Util;


public class LogDAO {
	
	//싱글턴 패턴 적용
	private LogDAO() {}
	private static LogDAO instance = new LogDAO();
	public static LogDAO getInstance() {
		return instance;
	}
	
	public static void insertLog(LogDTO logDto) {
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO log(logIp, logTarget, logId, logEtc, logMethod) "
				+"VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, logDto.getLogIp());
			pstmt.setString(2, logDto.getLogTarget());
			pstmt.setString(3, logDto.getLogdId());
			pstmt.setString(4, logDto.getLogEtc());
			pstmt.setString(5, logDto.getLogMethod());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, conn);
		}
		
	}

	public ArrayList<LogDTO> loglist(int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *,(SELECT count(*) FROM log) as totalcount "
				+ "FROM log "
				+ orderSql
				+ " LIMIT ?, 20"; //20개씩 가져오기
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> selectIpTarget(String ip, String target, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT "
				+ "(SELECT count(*) FROM log WHERE logTarget=? AND logIp=?) "
				+ "as totalcount, "
				+ "logNo, logIp, logDate, logTarget, logId, logEtc, logMethod "
				+ "FROM log WHERE logTarget=? AND logIp=? "
				+  orderSql
				+ " limit ?, 20;" ;	
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, target);
			pstmt.setString(2, ip);
			pstmt.setString(3, target);
			pstmt.setString(4, ip);
			pstmt.setInt(5, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
				dto.setTotalCount(rs.getInt("totalcount"));
				list.add(dto);
				}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}

	public ArrayList<LogDTO> selectIP(String ip, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= "SELECT "
				+"(SELECT count(*) FROM log WHERE logIp=?) as totalcount,"
				+"logNo, logIp, logDate, logTarget, logId, logEtc, logMethod"
				+" FROM log WHERE logIp=? "
				+ orderSql
				+ " limit ?, 20 ";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ip);
			pstmt.setString(2, ip);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> selectTarget(String target, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= "SELECT "
				+"(SELECT count(*) FROM log WHERE logTarget=?) as totalcount,"
				+"logNo, logIp, logDate, logTarget, logId, logEtc, logMethod"
				+" FROM log WHERE logTarget=? "
				+ orderSql
				+ " limit ?, 20 ";	
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, target);
			pstmt.setString(2, target);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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
	
	//검색옵션에 들어갈 리스트
	public ArrayList<String> list(String column) {
		ArrayList<String> list = null;
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT DISTINCT " + column + " FROM log";
		
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

	public ArrayList<LogDTO> search(String searchType, String searchText, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		System.out.println(searchType);
	//	System.out.println(search);
		String sql = "SELECT (SELECT count(*) FROM log WHERE "
				+ searchType
				+" LIKE CONCAT('%',?,'%')) as totalcount, "
				+"logNo, logIp, logDate, logId, logEtc, logTarget ,logMethod"
				+" FROM log WHERE " + searchType
				+" LIKE CONCAT('%',?,'%') "
				+ orderSql
				+ " limit ?, 20";
		//where 0 은 전체가 나온다 
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchText);
			pstmt.setString(2, searchText);
			pstmt.setInt(3, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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
	

	public ArrayList<LogDTO> searchAll(String searchType, String searchText, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM log WHERE logNo LIKE CONCAT('%',?,'%')"
				+ " OR logIp LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%'))as totalcount"
				+ "	FROM log" 
				+ "	WHERE logNo LIKE CONCAT('%',?,'%') "
				+ "	OR logIp LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%') "
				+ orderSql
				+ " limit ?, 20";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 15; i++) {				
				pstmt.setString(i, searchText);
			}
			pstmt.setInt(15, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> searchTarget(String searchType, String searchText, String target, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		System.out.println(searchType);
	//	System.out.println(search);
		String sql = "SELECT *, (SELECT count(*) FROM log WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND logTarget= ?) as totalcount "		
				+" FROM log WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND logTarget = ? "
				+ orderSql
				+ " limit ?, 20";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchType);
			pstmt.setString(2, searchText);
			pstmt.setString(3, target);
			pstmt.setString(4, searchType);
			pstmt.setString(5, searchText);
			pstmt.setString(6, target);
			pstmt.setInt(7, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> searchIp(String searchType, String searchText, String ip, int page, String orderSql) {
	
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT count(*) FROM log WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND logIp= ?) as totalcount "		
				+" FROM log WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND logIP = ? "
				+ orderSql
				+ " limit ?, 20";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchType);
			pstmt.setString(2, searchText);
			pstmt.setString(3, ip);
			pstmt.setString(4, searchType);
			pstmt.setString(5, searchText);
			pstmt.setString(6, ip);
			pstmt.setInt(7, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> searchBoth(String searchType, String searchText, String ip, String target, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT count(*) FROM log WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND logIp= ? AND logTarget = ?) as totalcount "		
				+" FROM log WHERE ?"
				+" LIKE CONCAT('%',?,'%') AND logIP = ? AND logTarget = ? "
				+ orderSql
				+ " limit ?, 20";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchType);
			pstmt.setString(2, searchText);
			pstmt.setString(3, ip);
			pstmt.setString(4, target);
			pstmt.setString(5, searchType);
			pstmt.setString(6, searchText);
			pstmt.setString(7, ip);
			pstmt.setString(8, target);
			pstmt.setInt(9, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> searchAllIp(String searchType, String searchText, String ip, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM log WHERE logNo LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%')"
				+ " AND logIp= ? )as totalcount"
				+ "	FROM log" 
				+ "	WHERE logNo LIKE CONCAT('%',?,'%') "
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%') "
				+ " AND logIp= ? "
				+ orderSql
				+ " limit ?, 20";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 8; i++) {				
				pstmt.setString(i, searchText);}
			pstmt.setString(8, ip);
			for(int j = 9; j < 16; j++) {				
				pstmt.setString(j, searchText);
			}
			pstmt.setString(16, ip);
			pstmt.setInt(17, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public ArrayList<LogDTO> searchAllTarget(String searchType, String searchText, String target, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM log WHERE logNo LIKE CONCAT('%',?,'%')"
				+ " OR logIp LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%')"
				+ " AND logTarget= ? )as totalcount"
				+ "	FROM log" 
				+ "	WHERE logNo LIKE CONCAT('%',?,'%') "
				+ "	OR logIp LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%') "
				+ " AND logTarget= ? "
				+ orderSql
				+ " limit ?, 20";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 8; i++) {				
				pstmt.setString(i, searchText);}
			pstmt.setString(8, target);
			for(int j = 9; j < 16; j++) {				
				pstmt.setString(j, searchText);
			}
			pstmt.setString(16, target);
			pstmt.setInt(17, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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
	

	public ArrayList<LogDTO> searchAllIpTarget(String searchType, String searchText, String ip, String target, int page, String orderSql) {
		ArrayList<LogDTO> list = new ArrayList<LogDTO>();
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		String sql = "SELECT *, (SELECT COUNT(*) FROM log WHERE logNo LIKE CONCAT('%',?,'%')"
				+ " OR logIp LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%')"
				+ " AND logTarget= ? AND logIp= ? )as totalcount"
				+ "	FROM log" 
				+ "	WHERE logNo LIKE CONCAT('%',?,'%') "
				+ "	OR logIp LIKE CONCAT('%',?,'%')"
				+ "	OR logDate LIKE CONCAT('%',?,'%')"
				+ "	OR logId LIKE CONCAT('%',?,'%')"
				+ "	OR logEtc LIKE CONCAT('%',?,'%')"
				+ "	OR logTarget LIKE CONCAT('%',?,'%')"
				+ "	OR logMethod LIKE CONCAT('%',?,'%') "
				+ " AND logTarget= ? AND logIp= ? "
				+ orderSql
				+ " limit ?, 20";
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i < 8; i++) {				
				pstmt.setString(i, searchText);}
			pstmt.setString(8, target);
			pstmt.setString(9, ip);
			for(int j = 10; j < 17; j++) {				
				pstmt.setString(j, searchText);
			}
			pstmt.setString(17, target);
			pstmt.setString(18, ip);
			pstmt.setInt(19, page);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LogDTO dto = new LogDTO();
				dto.setLogNo(rs.getInt("logNo"));
				dto.setLogIp(rs.getString("logIp"));
				dto.setLogDate(rs.getDate("logDate"));
				dto.setLogTarget(rs.getString("logTarget"));
				dto.setLogdId(rs.getString("logId"));
				dto.setLogEtc(rs.getString("logEtc"));
				dto.setLogMethod(rs.getString("logMethod"));
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

	public static int deleteLog(int number) {
		int result = 0;
		Connection conn = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM log WHERE logNo = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			result = pstmt.executeUpdate();
			System.out.println("행이 삭제 됐습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(null, pstmt, conn);
		}
		return result;
	}
}
