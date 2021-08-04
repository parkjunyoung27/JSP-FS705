package com.FS705.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FS705.dao.SportsDAO;
import com.FS705.util.Util;

@WebServlet("/sportsDetail")
public class SportsDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SportsDetail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("sno") != null 
				&& Util.str2Int2(request.getParameter("sno")) != 0
				){
			int sno = Util.str2Int(request.getParameter("sno"));
			SportsDAO dao = SportsDAO.getInstance();
			HashMap<String, Object> dto = dao.detail(sno);
			//댓글은 갤러리 땐 하지 않겠습니다. 이미 자유게시판에 해서.
			/*
			 * if(((int)dto.get("commentcount")) > 0) { ArrayList<HashMap<String, Object>>
			 * commentList = dao.commentList(gno); request.setAttribute("commentList",
			 * commentList); }
			 */
			RequestDispatcher rd = request.getRequestDispatcher("./sportsDetail.jsp");
			request.setAttribute("dto", dto);
			rd.forward(request, response);			
		} else {
			response.sendRedirect("./sports");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
