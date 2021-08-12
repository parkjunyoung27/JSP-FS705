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

@WebServlet("/gameCommentModify")
public class GameCommentModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameCommentModify() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
//		if(request.getParameter("bno") != null && Util.str2Int(request.getParameter("bno")) != 0
//			&& request.getParameter("cno") != null && Util.str2Int(request.getParameter("cno")) != 0
//			&& request.getParameter("ccontent") != null && request.getSession().getAttribute("id") != null){
		int test = 1;
		if(test == 1) {
			int result = 0;
			GameCommentDTO cmt = new GameCommentDTO();
			cmt.setBno(Util.str2Int(request.getParameter("bno")));
			cmt.setCno(Util.str2Int(request.getParameter("cno")));
			cmt.setCcontent(Util.replace(request.getParameter("ccontent")));
			cmt.setCip(Util.getIP(request));
			//cmt.setId((String) request.getSession().getAttribute("id"));
			cmt.setId("an");
			result = GameCommentDAO.getInstance().gameCommentModify(cmt);
			
			if(result == 1) {
				response.sendRedirect("./gameView?bno="+cmt.getBno());			
			} else {
				response.sendRedirect("./error?code=gameCommentModifyError1");
			}
		} else {
			response.sendRedirect("./error?code=gameCommentModifyError2");
		}
	}

}
