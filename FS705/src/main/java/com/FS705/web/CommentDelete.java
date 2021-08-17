package com.FS705.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.util.Util;

@WebServlet("/commentDelete")
public class CommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommentDelete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		if(request.getParameter("bno") != null 
				&& Util.str2Int(request.getParameter("bno")) != 0
				&& request.getParameter("cno") != null 
				&& Util.str2Int(request.getParameter("cno")) != 0
				&& request.getParameter("ccontent") != null 
				&& session.getAttribute("id") != null){
		
		BoardDTO dto = new BoardDTO();
		dto.setBno(Util.str2Int(request.getParameter("bno")));
		dto.setCno(Util.str2Int(request.getParameter("cno")));
		dto.setId("kwon");
		
		SportsDAO dao = SportsDAO.getInstance();
		int result = dao.commentDelete(dto);
		
		if(result == 1) {
			response.sendRedirect("./sportsdetail?bno=" + dto.getBno());
		} else {
			response.sendRedirect("./error?code=CommentDeleteError");
		}
	}

}
