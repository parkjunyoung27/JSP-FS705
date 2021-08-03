package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
	

}
