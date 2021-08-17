package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.SportsDAO;
import com.FS705.dto.BoardDTO;
import com.FS705.util.Util;

@WebServlet("/commentInput")
public class CommentInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommentInput() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		if(request.getParameter("ccontent") != null
				&& request.getParameter("bno") != null
				&& (Util.str2Int2(request.getParameter("bno")) != 0)
				&& session.getAttribute("id") != null
				&& session.getAttribute("name") != null){
			
			BoardDTO dto = new BoardDTO();
			dto.setBno(Util.str2Int(request.getParameter("bno")));
			dto.setCcontent(request.getParameter("ccontent"));
			dto.setId("kwon");
			dto.setCip(Util.getIP(request));
			
			SportsDAO dao = SportsDAO.getInstance();
			int result = dao.commentInput(dto);

			if(result == 1) {
				response.sendRedirect("./sportsdetail?bno=" + dto.getBno());
			} else {
				response.sendRedirect("./error?code=commentInsertError");
			}
		} else {
			response.sendRedirect("./error?code=commentInsertError");
		}
		
		
		
	}

}
