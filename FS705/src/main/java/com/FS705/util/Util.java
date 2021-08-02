package com.FS705.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class Util {
	//ip가져오기 //있을 때 까지 
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARED-FOR");
		if(ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null) {
			ip = request.getHeader("WL-Proxy-Client-Ip");
		}
		if(ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static int str2Int(String str) {//120A이렇게 들어오면 120 리턴
		int result = 0;
		String temp = "";
		for(int i =0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))) {
				temp += str.charAt(i);
			}
		}
		result = Integer.parseInt(temp);
		return result;
	}
	
	public static int str2Int2(String str) {
		try {
			return Integer.parseInt(str);
		}catch(Exception e) {//문제가 발생한다면 0 
			return 0; //순수하게 숫자가 아니면 0 으로 바뀜 
		}
	}
	
	public static String replace(String content) {
		content = content.replaceAll(">", "&gt;"); //html처리
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll("/", "&#47;");
		content = content.replaceAll("\n", "<br>;"); // 엔터키 처리
				
		return content;
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		
		try {
			if(rs != null) {rs.close();}
			if(pstmt != null) {pstmt.close();}
			if(conn != null) {conn.close();}
		} catch(SQLException e){
			e.printStackTrace();
		}
		
	}
}


