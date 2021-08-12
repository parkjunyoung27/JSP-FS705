package com.FS705.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.GameCommentDAO;
import com.FS705.dto.GameCommentDTO;
import com.FS705.util.Util;

@WebServlet("/gameCommentDelete")
public class GameCommentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameCommentDelete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0
//		&& request.getParameter("cno") != null && Util.str2Int(request.getParameter("cno")) != 0
//		&& request.getSession().getAttribute("id") != null){
		int test = 1;
		if(test == 1) {
			int result = 0;
			GameCommentDTO cmt = new GameCommentDTO();
			cmt.setBno(Util.str2Int(request.getParameter("bno")));
			cmt.setCno(Util.str2Int(request.getParameter("cno")));
			//cmt.setId((String) request.getSession().getAttribute("id"));
			cmt.setId("an");
			result = GameCommentDAO.getInstance().gameCommentDelete(cmt);
			
			if(result == 1) {
				response.sendRedirect("./gameView?bno="+cmt.getBno());
			} else {
				response.sendRedirect("./error?code=gameCommentDeleteError1");				
			}
		} else {
			response.sendRedirect("./error?code=gameCommentDeleteError2");
		}		
	}
}
