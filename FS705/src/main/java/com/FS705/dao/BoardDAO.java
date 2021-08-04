package com.FS705.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.FS705.db.DBConnection;
import com.FS705.dto.BoardDTO;
import com.FS705.util.Util;

public class BoardDAO {
	
	//싱글턴 패턴 적용
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	

	public static ArrayList<BoardDTO> boardList(int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		
		Connection con = DBConnection.dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="SELECT *, (SELECT count(*) FROM board) as totalcount "
				+ "FROM board LIMIT ?, 20"; //20개씩 가져오기
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setBcount(rs.getInt("bcount"));
				dto.setNo(rs.getInt("no"));
				dto.setBlike(rs.getInt("blike"));
				dto.setBdislike(rs.getInt("bdislike"));
				dto.setCommentCount(rs.getInt("commentCount"));
				dto.setBtitle(rs.getString("btitle"));
				dto.setBcontent(rs.getString("bcontent"));
				dto.setBcategory(rs.getString("bcategory"));
				dto.setBdate(rs.getString("bdate"));
				dto.setBthumbnail(rs.getString("bthumbnail"));
				dto.setSubCategory(rs.getString("subCategory"));
				dto.setBfile(rs.getString("bfile"));
				list.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Util.closeAll(rs, pstmt, con);
		}
		return list;
	}

	
	
}
