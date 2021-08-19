package com.FS705.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.FS705.dao.LogDAO;
import com.FS705.dao.SportsCommentDAO;
import com.FS705.dto.CommentDTO;
import com.FS705.dto.LogDTO;
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
			
			String id = "";
			if(session.getAttribute(id) != null) {
				id = (String)session.getAttribute("id");
			}
			
			LogDTO logDto = new LogDTO();
					
			logDto.setLogIp(Util.getIP(request));
			logDto.setLogTarget("CommentDelete");
			logDto.setLogdId((String)session.getAttribute(id));
			logDto.setLogEtc(request.getHeader("User-Agent"));
			logDto.setLogMethod("post");
			LogDAO.insertLog(logDto);
			
			int test = 1;
			if(test == 1) {
			
			int result = 0;
	
			CommentDTO cmt = new CommentDTO();
			cmt.setBno(Util.str2Int2(request.getParameter("bno")));
			cmt.setCno(Util.str2Int2(request.getParameter("cno")));
			cmt.setId("kwon");
				
			SportsCommentDAO dao = SportsCommentDAO.getInstance();
			result = dao.commentDelete(cmt);
			
			if(result == 1) {
				response.sendRedirect("./sportsDetail?bno="+cmt.getBno());
			} else {
				response.sendRedirect("./error?code=commentDeleteError1");
			}
		} else {
			response.sendRedirect("./error?code=commentDeleteError2");
		}
	
	}
		
}
